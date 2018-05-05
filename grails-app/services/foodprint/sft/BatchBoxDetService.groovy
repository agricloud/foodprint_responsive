package foodprint.sft

import foodprint.common.Country

import foodprint.erp.*
import foodprint.sft.pull.*
import grails.transaction.Transactional

import foodprint.common.DomainDataException

class BatchBoxDetService {

    def grailsApplication
    def messageSource
    def domainService
    def enumService
    def dateService
    def sheetService

    def i18nType

    @Transactional
    def saveBatchBoxDet( itemId, qty, siteId, timeZoneId ){
        def item = Item.get(itemId)
        def site = Site.get(siteId)
        def updateResult

        def batchBox = BatchBox.findByItemAndSite(item, site)

    	def formLevel = batchBox.formLevel
    	def accQty = batchBox.qty
        def kanbanQty = batchBox.kanbanQty
        def accKanbanQty = batchBox.accumulationQty

    	def remainderQty = kanbanQty - accKanbanQty - Double.parseDouble(qty)

        def batchBoxDet = BatchBoxDet.findAllByBatchBoxAndSite(batchBox, site)
        def newBatchBoxDetSequence
        if (batchBoxDet){
            newBatchBoxDetSequence = ( batchBoxDet.sequence.max() + 1 )
        }else{
            newBatchBoxDetSequence = 1
        }
        while(remainderQty <= 0) {
            def newBatchBoxDet = new BatchBoxDet(
                batchBox: batchBox, 
                sequence: newBatchBoxDetSequence, 
                warehouse: batchBox.warehouse, 
                qty: kanbanQty, 
                site: site)
            updateResult = domainService.save(newBatchBoxDet)

            updateResult = updateBatchBox(itemId, batchBox, kanbanQty, siteId, timeZoneId)

            newBatchBoxDetSequence = newBatchBoxDetSequence + 1
            remainderQty = remainderQty + kanbanQty
        }
        if (remainderQty > 0 ) {
            batchBox.accumulationQty = kanbanQty - remainderQty
            updateResult = domainService.save(batchBox)
        }else {
            batchBox.accumulationQty = 0
            updateResult = domainService.save(batchBox)
        }

        return updateResult 
    }

    def updateBatchBox = { itemId, batchBox, qty, siteId, timeZoneId ->
        def item = Item.get(itemId)
        def site = Site.get(siteId)
        def updateResult
        //批量累積數量大於或等於預設成形數量
        if( (batchBox.qty + qty) >= batchBox.formLevel){
            //批量累積數量等於預設成形數量
            if( (batchBox.qty + qty == batchBox.formLevel)){
                batchBox.qty = 0.0
                updateResult = domainService.save(batchBox)
            } else {
            //批量累積數量大於預設成形數量
                batchBox.qty = (batchBox.qty + qty) - batchBox.formLevel
                updateResult = domainService.save(batchBox)
            }
            if (updateResult.success){
                updateResult = makingBatchBox(itemId, batchBox, qty, siteId, timeZoneId )
            }
        } else {
            //批量累積數量小於預設成形數量
            batchBox.qty = batchBox.qty + qty
            updateResult = domainService.save(batchBox)
        }
        return updateResult
    }

    //批量成形
    def makingBatchBox = {itemId, batchBox, qty, siteId, timeZoneId ->
        def item = Item.get(itemId)
        def site = Site.get(siteId)
        def batchBoxDet = BatchBoxDet.findAllByBatchBoxAndStatusAndSite(batchBox, BatchFormStatus.PENDING, site)
        def updateResult
        batchBoxDet.each { boxDet ->
            boxDet.status = BatchFormStatus.COMPLETE
            updateResult = domainService.save(boxDet)
            if (updateResult.success) {
                //自製件
                if (item.itemType == ItemType.MANUFACTURE ){
                    //待確認搜尋方式
                    def typeNameManufactureOrder = TypeName.findBySheetTypeAndManufactureTypeAndSite(SheetType.MANUFACTUREORDER, ManufactureType.FACTORY, site)
                    def itemRoute = ItemRoute.findAllByItemAndSite(item, site)
                    if (!itemRoute){
                        println "品項標準途程未建立"
                        def msg = messageSource.getMessage("${i18nType}.itemRoute.not.found", null, Locale.getDefault())
                        throw new DomainDataException(msg) 
                    }else{
                        def leanworkstation = item.defaultWorkstation
                        if (!leanworkstation){
                            println "品項預設工作站未建立"
                            def msg = messageSource.getMessage("${i18nType}.item.defaultWorkstation.not.found", null, Locale.getDefault())
                            throw new DomainDataException(msg) 
                        }else{
                            def leanFactory = leanworkstation.factory
                            //產生製令
                            def manufactureOrder = new ManufactureOrder()
                            manufactureOrder.typeName = typeNameManufactureOrder
                            manufactureOrder.name = sheetService.generateSheetNameByTypeName(manufactureOrder, siteId, timeZoneId)
                            manufactureOrder.manufactureType = manufactureOrder.typeName.manufactureType
                            manufactureOrder.status = ManufactureOrderStatus.UNDISBURSED
                            manufactureOrder.batchName = manufactureOrder.typeName.name + "-" + manufactureOrder.name
                            manufactureOrder.factory = leanFactory
                            manufactureOrder.workstation = leanworkstation
                            manufactureOrder.item = item 
                            manufactureOrder.expectQty = boxDet.qty
                            manufactureOrder.site = site 
                            updateResult = domainService.save(manufactureOrder)
                        
                            if (updateResult.success) {
                                //產生批號
                                def newBatch = new Batch()
                                newBatch.name = manufactureOrder.typeName.name + "-" + manufactureOrder.name
                                newBatch.item = item
                                newBatch.batchSourceType = BatchSourceType.MANUFACTURE
                                newBatch.manufactureOrder = manufactureOrder
                                newBatch.expectQty = boxDet.qty
                                newBatch.country = Country.TAIWAN
                                newBatch.site = site
                                updateResult = domainService.save(newBatch)
                                if (updateResult.success) {
                                    //產生批號途程
                                    def sartD = new Date()
                                    itemRoute.each{ route ->
                                        def newBatchOperation = new BatchOperation()
                                        newBatchOperation.batch = newBatch
                                        newBatchOperation.sequence = route.sequence
                                        newBatchOperation.operation = route.operation
                                        newBatchOperation.workstation = route.workstation
                                        newBatchOperation.site = site
                                        updateResult = domainService.save(newBatchOperation)

                                        newBatch.addToBatchOperations(newBatchOperation)

                                        sartD = sartD.next()
                                    }  
                                    if (updateResult.success) {
                                        //產生運搬看板
                                        def itemBOM = BillOfMaterial.findByItemAndSite(item, site)
                                        def itemBOMDet = BillOfMaterialDet.findAllByHeaderAndSite(itemBOM, site)
                                        itemBOMDet.each { bomDet ->

                                            def newManufactureOrderDet = new ManufactureOrderDet()
                                            newManufactureOrderDet.header = manufactureOrder
                                            newManufactureOrderDet.typeName = manufactureOrder.typeName
                                            newManufactureOrderDet.name = manufactureOrder.name
                                            newManufactureOrderDet.item = bomDet.item
                                            newManufactureOrderDet.expectQty = boxDet.qty * bomDet.qty
                                            newManufactureOrderDet.qty = boxDet.qty * bomDet.qty
                                            newManufactureOrderDet.warehouse = batchBox.warehouse
                                            newManufactureOrderDet.operation = bomDet.operation
                                            newManufactureOrderDet.site = site
                                            updateResult = domainService.save(newManufactureOrderDet)
                                        }
                                    }
                                }

                                if (batchBox.autoSplit){
                                    batchSplit(item, batchBox, newBatch, manufactureOrder, site, timeZoneId)
                                }
                            }
                        }
                    }
                //委外件
                } else if ( item.itemType == ItemType.OUTSRCMANUFACTURE ){
                    def msg = messageSource.getMessage("${i18nType}.itemType.OUTSRCMANUFACTURE", null, Locale.getDefault())
                    updateResult = [success: true, message: msg, data: boxDet]    
                //採購件
                } else if ( item.itemType == ItemType.PURCHASE ){
                    def msg = messageSource.getMessage("${i18nType}.itemType.PURCHASE", null, Locale.getDefault())
                    updateResult = [success: true, message: msg, data: boxDet]
                }
            }
        }
        return updateResult
    }

    //批號拆批
    def batchSplit = {item, batchBox, splitBatch, manufactureOrder, site, timeZoneId ->
        def batchQty = splitBatch.expectQty
        def qty = batchBox.splitQty
        def count = batchQty/batchBox.splitQty as int;
        for( def createIndex = 1 ; createIndex <= count ; createIndex++ ) {
            if ( createIndex == count ) {
                qty = batchQty;
            }
            def batch = new Batch( 
                name: splitBatch.name + "-"+ String.format("%03d", createIndex), 
                item: item,
                expectQty: qty, 
                batchType: splitBatch.batchType,
                batchSourceType: splitBatch.batchSourceType,
                manufactureOrder: manufactureOrder,  
                country: splitBatch.country,
                site: site)
            domainService.save(batch)

            batchQty = batchQty - qty

            def batchOperations = BatchOperation.findAllByBatchAndSite(splitBatch, site)

            batchOperations.each{ route ->
                def newBatchOperation = new BatchOperation()
                newBatchOperation.batch = batch
                newBatchOperation.sequence = route.sequence
                newBatchOperation.operation = route.operation
                newBatchOperation.workstation = route.workstation
                newBatchOperation.site = site
                domainService.save(newBatchOperation)

                batch.addToBatchOperations(newBatchOperation)
            }

            splitBatch.effectEndDate = dateService.getDateYEARtoMIN()
            domainService.save(splitBatch)

            manufactureOrder.isSplit = true
            domainService.save(manufactureOrder)
        }
    }
}