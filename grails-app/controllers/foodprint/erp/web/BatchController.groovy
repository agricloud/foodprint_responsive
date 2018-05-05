package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.BatchSourceType
import foodprint.erp.Batch
import foodprint.erp.Factory
import foodprint.erp.Workstation
import foodprint.erp.Supplier
import foodprint.erp.ManufactureOrderStatus
import foodprint.common.Country

import foodprint.erp.BatchOperation
import foodprint.erp.BillOfMaterial
import foodprint.erp.BillOfMaterialDet
import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation
import foodprint.erp.Inventory
import foodprint.erp.InventoryDetail
import foodprint.erp.TransferType
import foodprint.erp.TransferOrder
import foodprint.erp.TransferOrderDet
import foodprint.erp.ManufactureOrder
import foodprint.erp.ManufactureOrderStatus
import foodprint.erp.TypeName
import foodprint.erp.SheetType
import foodprint.sft.BatchStatus
import foodprint.sft.PickingStatus
import foodprint.sft.BatchRealtimeRecord
import foodprint.sft.BatchOperationRealtimeRecord
import foodprint.sft.BatchOperationRealtimeRecordType
import foodprint.sft.WorkFlowType

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.util.Environment

class BatchController {

    def grailsApplication
    def domainService
    def enumService
    def foodauthService
    def dateService

    def i18nType

    def index = {

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByName = {

        params.criteria = { eq('name', params.name) } >> params.criteria

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByItem = {

        def item = Item.get(params.item.id)

        params.criteria = { eq('item', item) } >> params.criteria

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByBatchSourceType = {

        def batchSourceType = params.batchSourceType as BatchSourceType

        params.criteria = { eq('batchSourceType', batchSourceType) } >> params.criteria

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByNonPurchaseBatchSourceType = {

        params.criteria = { ne('batchSourceType', BatchSourceType.PURCHASE) } >> params.criteria

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByManufactureOrderNonFinishedStatusAndFactoryAndWorkstationOrSupplier = {

        def factory = Factory.get(params.factory.id)
        def workstation = Workstation.get(params.workstation.id)
        def supplier = Supplier.get(params.supplier.id)

        params.criteria = {

            manufactureOrder {
                eq('factory', factory)
                or{
                    eq('workstation', workstation)
                    eq('supplier', supplier)
                }

                ne('status', ManufactureOrderStatus.ASSIGNEDFINISHED)
                ne('status', ManufactureOrderStatus.FINISHED)
            }
        } >> params.criteria

        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByBatchSourceTypeAndManufactureOrderStatusAndWorkFlowType = {

        params.criteria = { 
            eq('batchSourceType', BatchSourceType.MANUFACTURE)
            isNull('effectEndDate')
            isEmpty('batchRealtimeRecords')

            'manufactureOrder'{
                or{
                    eq('status', ManufactureOrderStatus.PENDING)
                    eq('status', ManufactureOrderStatus.PARTIALLY)
                }
            }

        } >> params.criteria

        if (params.workFlowType =='PUSH'){
            params.criteria = {
                item {
                    or{
                        eq('workFlowType', WorkFlowType.PUSH)
                        eq('workFlowType', WorkFlowType.COMBINE)
                    }
                }   
            } >> params.criteria

        }else{
            params.criteria = {
                item {
                    or{
                        eq('workFlowType', WorkFlowType.PULL)
                        eq('workFlowType', WorkFlowType.COMBINE)
                    }
                }   
            } >> params.criteria
        }


        def list = Batch.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def typeahead = {

        def list = Batch.createCriteria().list(params,{
            like('name',params.query+"%")
        })


        def typeaheadList = []

        list.each{ batch ->
            def typeahead = [:]
            typeahead.value = batch.name
            typeahead.itemName = batch.item.name
            typeahead.itemTitle = batch.item.title
            typeahead.itemDescription = batch.item.description
            typeaheadList << typeahead
        }

        render (contentType: 'application/json') {
            typeaheadList
        }
    }

    def show = {

        log.info "${controllerName}-${actionName}"

        def batch = Batch.get(params.id);

        if (batch) {

            render (contentType: 'application/json') {
                [success: true, data: batch]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
            try {
                def validateBatchResult = foodauthService.validateBatch(site.activationCode, Batch.findAllBySite(site).size())

                if (!validateBatchResult.success) {
                    render (contentType: 'application/json') {
                        validateBatchResult
                    }
                    return
                }

            }
            catch (e) {
                render (contentType: 'application/json') {
                    [success: false, message: 'Could not validate your License, please contact us.']
                }
                return
            }
        }
        
        def batch = new Batch()
        render (contentType: 'application/json') {
            [success: true, data: batch]
        }
    }


    @Transactional
    def save() {

        def result

        try {
            def site = Site.get(params.site.id)
            if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
                try {
                    def validateBatchResult = foodauthService.validateBatch(site.activationCode, Batch.findAllBySite(site).size())

                    if (!validateBatchResult.success) {
                        render (contentType: 'application/json') {
                            validateBatchResult
                        }
                        return
                    }
                }
                catch (e) {
                    throw new DomainDataException("Could not validate your License, please contact us.")
                }
            }

            if (params.manufactureDate) {
                throw new DomainDataException(message(code: "${i18nType}.batch.manufactureDate.cannot.change"))
            }

            def batch = new Batch(params)

            
            if (batch.batchSourceType==BatchSourceType.MANUFACTURE) {
                batch.item = batch.manufactureOrder.item
                batch.expectQty = batch.manufactureOrder.expectQty
                batch.country = Country.TAIWAN
            }
            else if (batch.batchSourceType==BatchSourceType.OUTSRCMANUFACTURE) {
                batch.item = batch.manufactureOrder.item
                batch.expectQty = batch.manufactureOrder.expectQty
                batch.manufacturer = batch.manufactureOrder.supplier.manufacturer
                batch.country = batch.manufactureOrder.supplier.country
            }
            else if (batch.batchSourceType==BatchSourceType.STOCKIN) {
                batch.item = batch.manufactureBatch.item
                batch.expectQty = batch.manufactureBatch.expectQty
                batch.country = batch.manufactureBatch.country
                batch.manufactureOrder = batch.manufactureBatch.manufactureOrder
            }
            else if (batch.batchSourceType==BatchSourceType.OUTSRCPURCHASE) {
                batch.item = batch.manufactureBatch.item
                batch.expectQty = batch.manufactureBatch.expectQty
                batch.manufacturer = batch.manufactureBatch.manufacturer
                batch.country = batch.manufactureBatch.country
                batch.manufactureOrder = batch.manufactureBatch.manufactureOrder
            }

            result = domainService.save(batch)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def update() {

        def result
        
        try {

            if (params.manufactureDate) {
                throw new DomainDataException(message(code: "${i18nType}.batch.manufactureDate.cannot.change"))
            }
            
            def batch = Batch.get(params.id)

            // batch.properties = params

            batch.effectEndDate = params.effectEndDate ?: null
            batch.expirationDate = params.expirationDate ?: null
            batch.remark = params.remark


            result = domainService.save(batch)

        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def delete() {

        def result

        try {
            def batch = Batch.get(params.id)

            def inventoryDetails = InventoryDetail.findAllByBatchAndSite(batch, Site.get(params.site.id))
            inventoryDetails.each { inventoryDetail ->
                if (inventoryDetail.qty == 0) {
                    domainService.delete(inventoryDetail)
                }
            }
            result = domainService.delete(batch)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }


    /**
    * 此方法已不使用 僅供參考寫法
    * @param batch.id
    * 取得Batch對應之Item的ItemRoute
    * 可直接呼叫ItemRoute中已有的listJson方法
    * 由於ItemRoute篩選的params為item.id
    * 而Batch接收的params為batch.id
    * 因此需method中找出item.id才可call ItemRoute.listJson
    * 使用redirect重新導向ItemRoute較為精簡
    * 也可避免params中屬性命名相同等衝突
    */
    def itemRouteList = {
        log.debug "BatchController--itemRouteList"
        redirect(controller: "itemRoute", action: "index" ,params: ["item.id":Batch.get(params.id).item.id])
    }

    @Transactional
    def release() {
        def result

        try {
            def site = Site.get(params.site.id)
            def data = JSON.parse(params.data)

            data.each{ orgdata ->
                def batch = Batch.get(orgdata.id)
                if (!batch.batchOperations) {
                        throw new DomainDataException(message(code:"${i18nType}.batch.batchOperation.not.found"))
                }else {
                    if (batch.batchRealtimeRecords) {
                        throw new DomainDataException(message(code:"${i18nType}.batch.is.released"))
                    }else {
                        def bom = BillOfMaterial.findByItemAndSite(batch.item, site)
                        if (!bom){
                            throw new DomainDataException(message(code:"${i18nType}.bom.not.found"))
                        }else {
                            def bomDetList = BillOfMaterialDet.findAllByHeaderAndSite(bom,site)
                            def manufactureOrder = ManufactureOrder.get(orgdata["manufactureOrder.id"])
                            def manufactureOrderTypeName = manufactureOrder.typeName

                            def transferOrderTypeName = TypeName.findBySheetTypeAndSite(SheetType.ORDERRELEASE,site)
                            def batchMinOperationSequence = BatchOperation.createCriteria().list({
                                eq('batch', batch)
                                eq('site',site)
                                isNull('startDate')
                            }).sequence.min()

                            def batchOperation = BatchOperation.where {
                                batch==batch && sequence==batchMinOperationSequence && site==site
                            }.find()
                              
                            def batchRealtimeRecord = new BatchRealtimeRecord(
                                batch:batch, 
                                batchOperation:batchOperation, 
                                releaseDate: new Date(), 
                                dueDate:(new Date()).next(), 
                                status:BatchStatus.CHECKIN, 
                                qty:batch.expectQty, 
                                expectQty:batch.expectQty, 
                                outputQty:batch.expectQty, 
                                pickingStatus:PickingStatus.PENDING,
                                priority:1, 
                                site:site)

                            domainService.save(batchRealtimeRecord)

                            def manufactureOrderBatchList = Batch.createCriteria().list({
                                isNull('effectEndDate')
                                isEmpty('batchRealtimeRecords')
                                eq('manufactureOrder', manufactureOrder)
                            })
                            if(manufactureOrderBatchList){
                                manufactureOrder.status = ManufactureOrderStatus.PARTIALLY
                            }else {
                                manufactureOrder.status = ManufactureOrderStatus.APPROVED
                            }
                            domainService.save(manufactureOrder)

                            def transferOrder = new TransferOrder( 
                                typeName:transferOrderTypeName, 
                                name:batch.name, 
                                transferDate: new Date(),
                                factory:batchOperation.workstation.factory, 
                                transferInType:TransferType.WORKSTATION, 
                                inWorkstation:batchOperation.workstation,
                                transferOutType:TransferType.WAREHOUSE, 
                                site:site)

                            domainService.save(transferOrder)
                            bomDetList.eachWithIndex{ bomDet, index ->
                                def batchOperationOfBOM = BatchOperation.findByBatchAndOperationAndSite(batch,bomDet.operation,site)
                                if (!batchOperationOfBOM){
                                    throw new DomainDataException(message(code:"${i18nType}.bom.batchOperation.not.found"))
                                }else{
                                    def transferOrderDet = new TransferOrderDet( 
                                    typeName:transferOrderTypeName, 
                                    name:batch.name, 
                                    sequence:(index+1), 
                                    header: transferOrder,
                                    manufactureOrder:batch.manufactureOrder, 
                                    inSequence:batchOperationOfBOM.sequence,
                                    inOperation:bomDet.operation, 
                                    item:bomDet.item, 
                                    unit:bomDet.item.unit, 
                                    spec:bomDet.item.spec,
                                    qty:batch.expectQty * bomDet.denominator, 
                                    batch:batch, 
                                    site:site)
                                    domainService.save(transferOrderDet)
                                }
                            }
                        }
                    }
                }
            }
            result = [success: true, message: message(code: "${i18nType}.batch.release.success")]
        }catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()

            result = e.getResult()
        }
        render (contentType: 'application/json') {
            result
        }
    }


    def batchSplit = {
        def i18nType = grailsApplication.config.grails.i18nType
        def site = Site.get(params.site.id)
        def splitMethod = params.splitMethod
        def splitQty = params.splitQty.toDouble()

        def qty = 0
        def batchQty = 0
        def batchStore = []
        def count = 0
        
        def orgBatch = Batch.get(params.batch.id)
        def manufactureOrder = ManufactureOrder.get(params.manufactureOrder.id)

        batchQty = orgBatch.expectQty
        def item = Item.get(params.item.id)

        if (splitMethod == "qty" ) {
            qty = splitQty;
            count = batchQty/splitQty as int;
            for( def createIndex = 1 ; createIndex <= count ; createIndex++ ) {
                if ( createIndex == count ) {
                    qty = batchQty;
                }
                def data = [:]
                data.name = orgBatch.name + "-"+ String.format("%03d", createIndex)

                data["item.id"] = item.id
                data["item.name"] = item.name
                data["item.title"] = item.title
                data["item.brand.name"] = item.brand.name
                data["item.brand.title"] = item.brand.title
                data["item.spec"] = item.spec
                data["item.unit"] = item.unit

                data.expectQty = qty
                data["manufactureOrder.typeName.name"] = manufactureOrder.typeName.name
                data["manufactureOrder.name"] = manufactureOrder.name

                batchStore << data

                batchQty = batchQty - qty
            }
        }
        else {
            qty = batchQty/splitQty as int;
            count = splitQty
            for( def createIndex = 1 ; createIndex <= count ; createIndex++ ) {
                if ( createIndex == count ) {
                    qty = batchQty;
                }

                def data = [:]
                data.name = orgBatch.name + "-"+ String.format("%03d", createIndex)
                
                data["item.id"] = item.id
                data["item.name"] = item.name
                data["item.title"] = item.title
                data["item.brand.name"] = item.brand.name
                data["item.brand.title"] = item.brand.title
                data["item.spec"] = item.spec
                data["item.unit"] = item.unit

                data.expectQty = qty
                data["manufactureOrder.typeName.name"] = manufactureOrder.typeName.name
                data["manufactureOrder.name"] = manufactureOrder.name

                batchStore << data

                batchQty = batchQty - qty
            }
        }

        render (contentType: 'application/json') {
            [data: batchStore]
        }
    }

    @Transactional
    def batchSplitSave(){
        def result
        try {
            def i18nType = grailsApplication.config.grails.i18nType
            def site = Site.get(params.site.id)
        
            def jsonData = JSON.parse(params.splitData)
            def orgBatch = Batch.get(params.batch.id)
            def manufactureOrder = ManufactureOrder.get(params.manufactureOrder.id)
            def item = Item.get(params.item.id)
        
            jsonData.eachWithIndex{ data, index ->
                def batch = new Batch( 
                    name: data.name, 
                    item: item,
                    expectQty: data.expectQty, 
                    batchType: orgBatch.batchType,
                    batchSourceType: orgBatch.batchSourceType,
                    manufactureOrder: manufactureOrder,  
                    country: orgBatch.country,
                    site: site)
                domainService.save(batch)

                def batchOperations = BatchOperation.findAllByBatchAndSite(orgBatch, site)
                if(!batchOperations){
                    throw new DomainDataException(message(code:"${i18nType}.batch.batchOperation.not.found"))
                }else{
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
                }
            }
            orgBatch.effectEndDate = dateService.getDateYEARtoMIN()
            domainService.save(orgBatch)

            manufactureOrder.isSplit = true
            domainService.save(manufactureOrder)
            
            result = [success: true, message: message(code: "${i18nType}.batchSplit.save.success")]
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result 
        }
    }
}
