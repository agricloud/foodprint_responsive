package foodprint.sft

import foodprint.erp.*
import foodprint.sft.*

import foodprint.common.Country
import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.util.Environment

class BatchExecutionService {

    def grailsApplication
    def domainService
    def inventoryDetailService

    @Transactional
    def batchCheckIn(batch, batchOperation, batchRealTimeRecord, manufactureOrder, workstation, site) {
        def updateResult
        def i18nType = grailsApplication.config.grails.i18nType
        def batchOperationRealtimeRecord = new BatchOperationRealtimeRecord(
            batch: batch,
            batchOperation: batchOperation,
            recordType: BatchOperationRealtimeRecordType.BATCHROUTE,
            checkInQty: batchRealTimeRecord.qty,
            checkInDate: new Date(),
            site: site )
        updateResult = domainService.save(batchOperationRealtimeRecord)

        batchRealTimeRecord.status = BatchStatus.CHECKOUT
        updateResult = domainService.save(batchRealTimeRecord)

        batchOperation.startDate = new Date()
        updateResult = domainService.save(batchOperation)

        return updateResult
    }
    
    @Transactional
    def batchCheckOut(batch, batchOperation, batchRealTimeRecord, manufactureOrder, workstation, 
        outputQty, inWarehouse, warehouseLocation, batchOperationRealtimeRecord, site) {
        def updateResult
        def i18nType = grailsApplication.config.grails.i18nType

        def batchNextOperationSequence = BatchOperation.createCriteria().list({
                eq('batch', batch)
                eq('site',site)
                isNull('startDate')
            }).sequence.min()
                
        def batchNextOperation 

        if(batchNextOperationSequence){
            batchNextOperation = BatchOperation.where {
                batch==batch && startDate == null && site==site && sequence == batchNextOperationSequence
            }.find()
        }

        def transferOrder
        def transferOrderDet
        def typeNameTransferOrder
        def transferOrderName
        def inWorkstation
        def inSequence = 0
        def inOperation
        def transferInType
        if(!batchNextOperation){
            typeNameTransferOrder = TypeName.findBySheetTypeAndSite(SheetType.OPERATIONSTORAGE, site)
            transferInType = TransferType.WAREHOUSE

            //生產入庫單>>待生產入庫單services
            def stockInSheetTypeName = TypeName.findBySheetTypeAndSite(SheetType.STOCKINSHEET, site)
            def stockInSheet = new StockInSheet(
                typeName:stockInSheetTypeName,
                name:batch.name,
                factory:manufactureOrder.factory,
                workstation:workstation,
                site:site)
            updateResult = domainService.save(stockInSheet)

            def stockInSheetDet = new StockInSheetDet(
                typeName:stockInSheetTypeName,
                name:batch.name,
                sequence:1,
                header:stockInSheet,
                item:batch.item,
                unit:batch.item.unit,
                batch:batch,
                warehouse:inWarehouse,
                warehouseLocation:warehouseLocation,
                qty:outputQty,
                manufactureOrder:manufactureOrder,
                site:site)
            updateResult = domainService.save(stockInSheetDet)

            def params = [:]
            params.site = site
            params["site.id"] = site.id
            params.batch = batch
            params["batch.name"] = batch.name
            
            inventoryDetailService.processInventoryByCreate(stockInSheetDet, params["site.id"], params)
            
            transferOrderName = batch.name+(batchOperation.sequence)+"-"+inWarehouse.name

        } else {
            typeNameTransferOrder = TypeName.findBySheetTypeAndSite(SheetType.OPERATIONTRANSFER, site)
            transferInType = TransferType.WORKSTATION
            transferOrderName =(batch.name+(batchOperation.sequence)+"-"+(batchNextOperation.sequence))
            inWorkstation = batchNextOperation.workstation
            inSequence = batchNextOperation.sequence
            inOperation = batchNextOperation.operation
        }

        transferOrder = new TransferOrder( 
            typeName:typeNameTransferOrder, 
            name:transferOrderName, 
            transferDate: new Date(),
            factory:manufactureOrder.factory, 
            transferInType:transferInType, 
            inWorkstation:inWorkstation,
            inWarehouse:inWarehouse,
            outWorkstation:batchOperation.workstation, 
            transferOutType:TransferType.WORKSTATION, 
            site:site )
        updateResult = domainService.save(transferOrder)

        transferOrderDet = new TransferOrderDet( 
            header:transferOrder,
            typeName:typeNameTransferOrder, 
            name:transferOrderName, 
            sequence:1,
            manufactureOrder:manufactureOrder, 
            inSequence:inSequence,
            inOperation:inOperation, 
            item:batch.item, 
            unit:batch.item.unit, 
            spec:batch.item.spec,
            qty:outputQty, 
            batch:batch, 
            site:site)
        updateResult = domainService.save(transferOrderDet)
        transferOrder.addToDetails(transferOrderDet)

        batchOperationRealtimeRecord.checkOutQty = Double.parseDouble(outputQty)
        //報廢數量計算
        def scrapQty = batchOperationRealtimeRecord.checkInQty - Double.parseDouble(outputQty)
        if (scrapQty > 0 ){
            batchOperationRealtimeRecord.scrapQty = scrapQty
        } else if (scrapQty < 0 ){
            batchOperationRealtimeRecord.surplusQty = -scrapQty
        }
        
        batchOperationRealtimeRecord.checkOutDate = new Date()
        batchOperationRealtimeRecord.transferOrderDet = transferOrderDet
        updateResult = domainService.save(batchOperationRealtimeRecord)

        batchOperation.endDate = new Date()
        updateResult = domainService.save(batchOperation)
    
        def updateBatchRealtimerecordResult
        if(batchNextOperation){//尚未完工
            batchRealTimeRecord.status = BatchStatus.CHECKIN
            batchRealTimeRecord.batchOperation = batchNextOperation
            batchRealTimeRecord.qty = Double.parseDouble(outputQty)
            batchRealTimeRecord.outputQty = Double.parseDouble(outputQty)
            updateResult = domainService.save(batchRealTimeRecord)
        }else { //已完工
            batchRealTimeRecord.status = BatchStatus.FINISHED
            batchRealTimeRecord.qty = Double.parseDouble(outputQty)
            batchRealTimeRecord.outputQty = Double.parseDouble(outputQty)
            updateResult = domainService.save(batchRealTimeRecord)
        
            def manufactureOrderIsFinished = true
            def batchList = Batch.createCriteria().list({
                isNull('effectEndDate')
                eq('manufactureOrder', manufactureOrder)
            })
            batchList.each{ moBatch ->
                def batchRealTimeRecordList = BatchRealtimeRecord.createCriteria().list({
                    eq('batch', moBatch)  
                })
                if (!batchRealTimeRecordList || batchRealTimeRecordList.status == BatchStatus.CHECKIN || batchRealTimeRecordList.status == BatchStatus.CHECKOUT){
                    manufactureOrderIsFinished = false
                }
            }

            if (manufactureOrderIsFinished){
                manufactureOrder.status = ManufactureOrderStatus.FINISHED
                updateResult = domainService.save(manufactureOrder)
            }
        }
        return updateResult
    }
}