package foodprint.erp.web

import foodprint.erp.*
import foodprint.sft.pull.*
import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class ManufactureOrderDetController {

    def grailsApplication
    def domainService
    def batchService
    def jasperReportService
    def sheetService
    def enumService
    def dateService
    def batchBoxDetService
    def inventoryDetailService

    def index = {

        def list = ManufactureOrderDet.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByManufactureOrderStatusAndWorkstationAndMoveType = {
        def workstation = Workstation.get(params.workstation.id)
        def site = Site.get(params.site.id)
        def action = params.actionName

        params.criteria = {
            header {
                eq('workstation', workstation)
                eq('manufactureType', ManufactureType.FACTORY)
                or{
                    eq('status', ManufactureOrderStatus.UNDISBURSED)
                }
            }
        } >> params.criteria

        if (params.moveType == "MoveOut"){
            params.criteria = { 
                isNull('expectPickingDate') 
                isNull('pickingDate')
            } >> params.criteria

        } else {
            params.criteria = { 
                isNotNull('expectPickingDate')
                isNull('pickingDate')  
            } >> params.criteria
        }

        def list = ManufactureOrderDet.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def sheet = ManufactureOrderDet.get(params.id);


        if (sheet) {

            render (contentType: 'application/json') {
                [success: true, data: sheet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    @Transactional
    def moveOut() {
        def result

        try {
            def i18nType = grailsApplication.config.grails.i18nType
            def site = Site.get(params.site.id)
            def workstation = Workstation.get(params.workstation.id)
            def data = JSON.parse(params.data)

            data.each{ orgdata ->
                def manufactureOrderDet = ManufactureOrderDet.get(orgdata.id)
                def manufactureOrder = ManufactureOrder.get(orgdata["manufactureOrder.id"])

                def item = Item.get(orgdata["item.id"])
                def batchBox = BatchBox.findByItemAndSite(item, site)
                def inventory = Inventory.findByItemAndWarehouseAndSite(item, batchBox.warehouse, site)

                if(!manufactureOrderDet || !manufactureOrder || !item || manufactureOrder.workstation != workstation){
                    throw new DomainDataException(message(code:"${i18nType}.material.moveOut.failed"))
                } else {
                    if(!inventory || inventory.qty < Double.parseDouble(orgdata.qty)){
                        throw new DomainDataException(message(code:"${i18nType}.inventory.quantity.not.enough"))
                    } else {
                        manufactureOrderDet.expectPickingDate = dateService.getDateYEARtoMIN()
                        domainService.save(manufactureOrderDet)
                    
                        //查詢庫存資料
                        def inventoryDet = InventoryDetail.createCriteria().list({
                            eq('item', item)
                            eq('site', site)
                            gt('qty', 0.0d)
                        })
                        def inventoryDetIndex = 0
                        def remainderQty = Double.parseDouble(orgdata.qty) - inventoryDet[inventoryDetIndex].qty

                        //產生領料單
                        def materialSheetName = manufactureOrder.typeName.name + "-" + manufactureOrder.name
                        def materialSheet = MaterialSheet.findByNameAndSite(materialSheetName, site)
                        def materialTypeName = TypeName.findBySheetTypeAndSite(SheetType.MATERIALSHEET, site)
                        if (!materialSheet){
                            materialSheet = new MaterialSheet(
                                typeName: materialTypeName,
                                name: materialSheetName,
                                factory: manufactureOrder.factory,
                                workstation: workstation,
                                site: site)

                            domainService.save(materialSheet)
                        }
                        //產生領料單單身
                        def materialSheetDetList = MaterialSheetDet.findAllByHeaderAndSite(materialSheet, site)
                        def newMaterialSheetDetSequence
                        if (materialSheetDetList){
                            newMaterialSheetDetSequence = (materialSheetDetList.sequence.max() + 1)
                        } else {
                            newMaterialSheetDetSequence = 1
                        }
                        def batch = Batch.findByNameAndSite(manufactureOrder.batchName, site)
                        def batchOperation = BatchOperation.findByBatchAndOperationAndSite(batch, manufactureOrderDet.operation, site)

                        while(remainderQty > 0){
                            def materialSheetDet = new MaterialSheetDet()
                                materialSheetDet.header = materialSheet
                                materialSheetDet.typeName = materialTypeName
                                materialSheetDet.name = materialSheetName
                                materialSheetDet.sequence = newMaterialSheetDetSequence
                                materialSheetDet.manufactureOrder = manufactureOrder
                                materialSheetDet.batchOperation = batchOperation
                                materialSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                                materialSheetDet.item = item
                                materialSheetDet.unit = item.unit
                                materialSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                                materialSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                                materialSheetDet.qty = inventoryDet[inventoryDetIndex].qty
                                materialSheetDet.site = site
                        
                            domainService.save(materialSheetDet)

                            inventoryDetIndex = inventoryDetIndex + 1 
                            remainderQty = remainderQty + inventoryDet[inventoryDetIndex].qty
                            newMaterialSheetDetSequence = newMaterialSheetDetSequence + 1
                        
                            inventoryDetailService.processInventoryByCreate(materialSheetDet, params.site.id, params)
                        }
                        if (remainderQty == 0){
                            def materialSheetDet = new MaterialSheetDet()
                                materialSheetDet.header = materialSheet
                                materialSheetDet.typeName = materialTypeName
                                materialSheetDet.name = materialSheetName
                                materialSheetDet.sequence = newMaterialSheetDetSequence
                                materialSheetDet.manufactureOrder = manufactureOrder
                                materialSheetDet.batchOperation = batchOperation
                                materialSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                                materialSheetDet.item = item
                                materialSheetDet.unit = item.unit
                                materialSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                                materialSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                                materialSheetDet.qty = inventoryDet[inventoryDetIndex].qty
                                materialSheetDet.site = site
                        
                            domainService.save(materialSheetDet)
                            inventoryDetailService.processInventoryByCreate(materialSheetDet, params.site.id, params)
                        }else{
                            def materialSheetDet = new MaterialSheetDet()
                                materialSheetDet.header = materialSheet
                                materialSheetDet.typeName = materialTypeName
                                materialSheetDet.name = materialSheetName
                                materialSheetDet.sequence = newMaterialSheetDetSequence
                                materialSheetDet.manufactureOrder = manufactureOrder
                                materialSheetDet.batchOperation = batchOperation
                                materialSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                                materialSheetDet.item = item
                                materialSheetDet.unit = item.unit
                                materialSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                                materialSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                                materialSheetDet.qty = remainderQty + inventoryDet[inventoryDetIndex].qty
                                materialSheetDet.site = site
                        
                            domainService.save(materialSheetDet)
                            inventoryDetailService.processInventoryByCreate(materialSheetDet, params.site.id, params)
                        }
                        
                        //產生批量成形箱資料
                        batchBoxDetService.saveBatchBoxDet(orgdata["item.id"], orgdata.qty, params.site.id, params.timeZoneId)
                    }
                }
            }
            result = [success: true, message: message(code: "${i18nType}.material.moveOut.success")]
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }
        render (contentType: 'application/json') {
            result 
        }
    }

    @Transactional
    def moveIn() {
        def result

        try {
            def i18nType = grailsApplication.config.grails.i18nType
            def site = Site.get(params.site.id)
            def workstation = Workstation.get(params.workstation.id)
            def data = JSON.parse(params.data)

            data.each{ orgdata ->
                def manufactureOrderDet = ManufactureOrderDet.get(orgdata.id)
                def manufactureOrder = ManufactureOrder.get(orgdata["manufactureOrder.id"])
                if (!manufactureOrder || !manufactureOrderDet || manufactureOrder.workstation != workstation){
                    throw new DomainDataException(message(code:"${i18nType}.material.moveIn.failed"))
                }else{
                    manufactureOrderDet.pickingDate = dateService.getDateYEARtoMIN()
                    domainService.save(manufactureOrderDet)
                    //修改製令狀態
                    def manufactureOrderDetList = ManufactureOrderDet.findAllByHeaderAndSiteAndPickingDateIsNull(manufactureOrder,site)

                    if (!manufactureOrderDetList){
                        manufactureOrder.status = ManufactureOrderStatus.PENDING
                        domainService.save(manufactureOrder)
                    }
                }
            }
            result = [success: true, message: message(code: "${i18nType}.material.moveIn.success")]
            
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }
        render (contentType: 'application/json') {
            result 
        }
    }
}