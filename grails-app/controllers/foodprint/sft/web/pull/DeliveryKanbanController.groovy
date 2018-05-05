package foodprint.sft.web.pull

import foodprint.common.Country
import foodprint.sft.pull.*
import foodprint.erp.*

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class DeliveryKanbanController {

    def grailsApplication
    def domainService
    def dateService
    def batchBoxDetService
    def sheetService
    def inventoryDetailService
    
    def index = {

        def list = DeliveryKanban.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByNotAssignExpectShippingDate = {

        params.criteria = { 
            isNull('expectShippingDateStart')
            isNull('expectShippingDateEnd')
        } >> params.criteria

        def list = DeliveryKanban.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByExpectShippingDate = {
        params.criteria = { 
            between('expectShippingDateEnd', params.shippingDate, params.shippingDate.plus(1)) 
        } >> params.criteria

        if (params.nonShipping){
            params.criteria = { isNull('shippingDate') } >> params.criteria
        }
        def list = DeliveryKanban.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }


    def show = {

        log.info "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType

        def deliveryKanban = DeliveryKanban.get(params.id);

        if (deliveryKanban) {

            render (contentType: 'application/json') {
                [success: true, data: deliveryKanban]
            }
        } else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def deliveryKanban = new DeliveryKanban()
        render (contentType: 'application/json') {
            [success: true, data: deliveryKanban]
        }
    }


    def save = {

        def i18nType = grailsApplication.config.grails.i18nType

        def site = Site.get(params.site.id)

        def deliveryKanban = new DeliveryKanban(params)

        render (contentType: 'application/json') {
            domainService.save(deliveryKanban)
        }
    }

    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def deliveryKanban = DeliveryKanban.get(params.id)

        render (contentType: 'application/json') {
            domainService.save(deliveryKanban)
        }
    }

    def orderChangeToDeliverKanban = {
        def i18nType = grailsApplication.config.grails.i18nType
        def site = Site.get(params.site.id)
        def splitMethod = params.splitMethod
        def splitQty = params.splitQty.toDouble()
        def typeName = TypeName.findBySheetTypeAndSite(SheetType.DELIVERYKANBAN, site)
        def qty = 0
        def orderQty = 0
        def kanbanStore = []
        def count = 0
        
        def customerOrderDet = CustomerOrderDet.get(params.customerOrderDet.id)
        orderQty = customerOrderDet.qty
        def item = Item.get(params.item.id)

        if (splitMethod =="qty" ) {
            qty = splitQty;
            count = orderQty/splitQty as int;
            for( def createIndex = 1 ; createIndex <= count ; createIndex++ ) {
                if ( createIndex == count ) {
                    qty = orderQty;
                }
                def data = [:]
                data["typeName.name"] = typeName.name
                data.name = (customerOrderDet.name + String.format("%03d", customerOrderDet.sequence))
                data.sequence = createIndex
                data["item.id"] = item.id
                data["item.name"] = item.name
                data["item.title"] = item.title
                data["item.unit"] = item.unit
                data.qty = qty
                data["customerOrderDet.id"] = customerOrderDet.id
                data["customerOrderDet.name"] = customerOrderDet.name

                kanbanStore << data

                orderQty = orderQty - qty
            }
        } else {
            qty = orderQty/splitQty as int;
            count = splitQty
            for( def createIndex = 1 ; createIndex <= count ; createIndex++ ) {
                if ( createIndex == count ) {
                    qty = orderQty;
                }

                def data = [:]
                data["typeName.name"] = typeName.name
                data.name = (customerOrderDet.name + String.format("%03d", customerOrderDet.sequence))
                data.sequence = createIndex
                data["item.id"] = item.id
                data["item.name"] = item.name
                data["item.title"] = item.title
                data["item.unit"] = item.unit
                data.qty = qty
                data["customerOrderDet.id"] = customerOrderDet.id
                data["customerOrderDet.name"] = customerOrderDet.name

                kanbanStore << data

                orderQty = orderQty - qty
            }
        }

        render (contentType: 'application/json') {
            [data: kanbanStore]
        }
    }

    @Transactional
    def orderChangeToDeliverKanbanAndSave() {
        def result

        try {
            def i18nType = grailsApplication.config.grails.i18nType
            def site = Site.get(params.site.id)
            def typeName = TypeName.findBySheetTypeAndSite(SheetType.DELIVERYKANBAN, site)
            def jsonData = JSON.parse(params.kanbanData)
            def customerOrderDet = CustomerOrderDet.get(params.customerOrderDet.id)
            def item = Item.get(params.item.id)

            def createSuccess = true
            def createResult
            jsonData.eachWithIndex{ data, index ->
                def deliveryKanban = new DeliveryKanban( 
                    typeName:typeName, 
                    name:data.name, 
                    sequence:data.sequence, 
                    item:item,
                    qty:data.qty, 
                    customerOrderDet:customerOrderDet,  
                    site:site )

                domainService.save(deliveryKanban)
            }

            customerOrderDet.convertToDeliveryKanban = true
            domainService.save(customerOrderDet)

            result = [success: true, message: message(code: "${i18nType}.deliverKanban.save.success")]
        }catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }
        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def delete() {

        def i18nType = grailsApplication.config.grails.i18nType

        def deliveryKanban = DeliveryKanban.get(params.id)
        def result
        try {

            result = domainService.delete(deliveryKanban)

        }catch(e) {
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [DeliveryKanban, e.getMessage()])
            result = [success: false, message: msg]
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def release() {
        def result

        try {
            def i18nType = grailsApplication.config.grails.i18nType
            def deliveryKanban = DeliveryKanban.get(params.deliveryKanban.id)
            deliveryKanban.expectShippingDateStart = params.expectShippingDateStart
            deliveryKanban.expectShippingDateEnd = params.expectShippingDateEnd
            domainService.save(deliveryKanban)

            result = [success: true, message: message(code: "${i18nType}.deliverKanban.release.success")]
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result 
        }
    } 
    @Transactional
    def shipping() {
        def result

        try {
            def i18nType = grailsApplication.config.grails.i18nType

            def site = Site.get(params.site.id)
            def data = JSON.parse(params.data)
        
            data.each { orgdata ->
                def item = Item.get(orgdata["item.id"])
                def batchBox = BatchBox.findByItemAndSite(item, site)
                def inventory = Inventory.findByItemAndWarehouseAndSite(item, batchBox.warehouse, site)
                def deliveryKanban = DeliveryKanban.get(orgdata.id)

                if(!inventory || inventory.qty < Double.parseDouble(orgdata.qty)){
                    throw new DomainDataException(message(code:"${i18nType}.inventory.quantity.not.enough"))
                }else {
                    //寫入實際出貨日期
                    deliveryKanban.shippingDate = dateService.getDateYEARtoMIN()
                    domainService.save(deliveryKanban)
                    //查詢庫存資料
                    def inventoryDet = InventoryDetail.createCriteria().list({
                        eq('item', item)
                        eq('site', site)
                        gt('qty', 0.0d)
                    })
                    def inventoryDetIndex = 0
                    def remainderQty = Double.parseDouble(orgdata.qty) - inventoryDet[inventoryDetIndex].qty
                    //產生銷貨單
                    def saleSheetTypeName = TypeName.findBySheetTypeAndSite(SheetType.SALESHEET, site)
                    def saleSheet = new SaleSheet()
                    saleSheet.typeName = saleSheetTypeName
                    saleSheet.name = sheetService.generateSheetNameByTypeName(saleSheet, params.site.id, params.timeZoneId)
                    saleSheet.factory = deliveryKanban.customerOrderDet.header.factory
                    saleSheet.customer = deliveryKanban.customerOrderDet.header.customer
                    saleSheet.shippingAddress = deliveryKanban.customerOrderDet.header.shippingAddress
                    saleSheet.site = site
                    domainService.save(saleSheet)

                    def saleSheetDetSequence = 1
                    while(remainderQty > 0){
                        def saleSheetDet = new SaleSheetDet()
                        saleSheetDet.typeName = saleSheetTypeName
                        saleSheetDet.name = saleSheet.name
                        saleSheetDet.sequence = saleSheetDetSequence
                        saleSheetDet.header = saleSheet
                        saleSheetDet.item = deliveryKanban.item
                        saleSheetDet.unit = deliveryKanban.item.unit
                        saleSheetDet.customerOrderDet = deliveryKanban.customerOrderDet
                        saleSheetDet.qty = inventoryDet[inventoryDetIndex].qty
                        saleSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                        saleSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                        saleSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                        saleSheetDet.site = site
                        domainService.save(saleSheetDet)

                        inventoryDetIndex = inventoryDetIndex + 1 
                        remainderQty = remainderQty - inventoryDet[inventoryDetIndex].qty
                        saleSheetDetSequence = saleSheetDetSequence + 1
                        
                        inventoryDetailService.processInventoryByCreate(saleSheetDet, params.site.id, params)
                    }
                    if (remainderQty == 0) {
                        def saleSheetDet = new SaleSheetDet()
                        saleSheetDet.typeName = saleSheetTypeName
                        saleSheetDet.name = saleSheet.name
                        saleSheetDet.sequence = saleSheetDetSequence
                        saleSheetDet.header = saleSheet
                        saleSheetDet.item = deliveryKanban.item
                        saleSheetDet.unit = deliveryKanban.item.unit
                        saleSheetDet.customerOrderDet = deliveryKanban.customerOrderDet
                        saleSheetDet.qty = inventoryDet[inventoryDetIndex].qty
                        saleSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                        saleSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                        saleSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                        saleSheetDet.site = site
                        domainService.save(saleSheetDet)

                        inventoryDetailService.processInventoryByCreate(saleSheetDet, params.site.id, params)
                    }else {
                        def saleSheetDet = new SaleSheetDet()
                        saleSheetDet.typeName = saleSheetTypeName
                        saleSheetDet.name = saleSheet.name
                        saleSheetDet.sequence = saleSheetDetSequence
                        saleSheetDet.header = saleSheet
                        saleSheetDet.item = deliveryKanban.item
                        saleSheetDet.unit = deliveryKanban.item.unit
                        saleSheetDet.customerOrderDet = deliveryKanban.customerOrderDet
                        saleSheetDet.qty = remainderQty + inventoryDet[inventoryDetIndex].qty
                        saleSheetDet.batch = inventoryDet[inventoryDetIndex].batch
                        saleSheetDet.warehouse = inventoryDet[inventoryDetIndex].warehouse
                        saleSheetDet.warehouseLocation = inventoryDet[inventoryDetIndex].warehouseLocation
                        saleSheetDet.site = site
                        domainService.save(saleSheetDet)

                        inventoryDetailService.processInventoryByCreate(saleSheetDet, params.site.id, params)
                    }

                    //產生批量成形箱資料
                    batchBoxDetService.saveBatchBoxDet(orgdata["item.id"], orgdata.qty, params.site.id, params.timeZoneId)
                }
            }

            result = [success: true, message: message(code: "${i18nType}.deliverKanban.shipping.success")]
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result 
        }
    }
}
