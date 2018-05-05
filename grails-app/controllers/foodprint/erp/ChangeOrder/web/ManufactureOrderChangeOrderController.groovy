
package foodprint.erp.ChangeOrder.web


import grails.converters.JSON
import grails.transaction.Transactional

import foodprint.erp.ChangeOrder.ManufactureOrderChangeOrder
import foodprint.erp.TypeName
import foodprint.erp.ManufactureOrder
import foodprint.erp.ManufactureOrderStatus

import foodprint.common.DomainDataException

class ManufactureOrderChangeOrderController {

    def grailsApplication
    def domainService
    def enumService
    def dateService

    def i18nType

    def index = {

        def list = ManufactureOrderChangeOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheet = ManufactureOrderChangeOrder.get(params.id)

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
    def create() {
        def result
        try {

            if (!params.manufactureOrder.id)
                throw new DomainDataException(message(code: "${i18nType}.manufactureOrderChangeOrder.message.create.failed"))

            def sheet = new ManufactureOrderChangeOrder(params)
            def manufactureOrder = ManufactureOrder.get(params.manufactureOrder.id)
            sheet.typeName = manufactureOrder.typeName
            sheet.name = manufactureOrder.name
            sheet.executionDate = manufactureOrder.executionDate
            sheet.factory = manufactureOrder.factory
            sheet.workstation = manufactureOrder.workstation
            sheet.supplier = manufactureOrder.supplier
            sheet.item = manufactureOrder.item
            sheet.expectQty = manufactureOrder.expectQty
            sheet.batchName = manufactureOrder.batchName

            // 未完工之製令才可做製令變更
            if (manufactureOrder.status in [ManufactureOrderStatus.ASSIGNEDFINISHED, ManufactureOrderStatus.FINISHED]) {
                throw new DomainDataException(message(code: "${i18nType}.manufactureOrderChangeOrder.manufactureOrder.status.finished", args: [enumService.name(manufactureOrder.status).title]))
            }

            if (ManufactureOrderChangeOrder.findAllByManufactureOrderAndSite(manufactureOrder, sheet.site))
                sheet.sequence = ManufactureOrderChangeOrder.findAllByManufactureOrderAndSite(manufactureOrder, sheet.site).sequence.max()+1
            else sheet.sequence = 1

            result = [success: true, data: sheet]
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
    def save() {
        def result
        try {

            def manufactureOrder = ManufactureOrder.get(params.manufactureOrder.id)
            // 未完工之製令才可做製令變更
            if (manufactureOrder.status in [ManufactureOrderStatus.ASSIGNEDFINISHED, ManufactureOrderStatus.FINISHED]) {
                throw new DomainDataException(message(code: "${i18nType}.manufactureOrderChangeOrder.manufactureOrder.status.finished", args: [enumService.name(manufactureOrder.status).title]))
            }

            def sheet = new ManufactureOrderChangeOrder(params)

            // 目前僅允許指定完工/結案之狀態變更
            if (sheet.status != ManufactureOrderStatus.ASSIGNEDFINISHED) {
                throw new DomainDataException(message(code: "${i18nType}.manufactureOrderChangeOrder.status.error", args: [enumService.name(sheet.status).title]))
            }

            sheet.originVersion = manufactureOrder.version
            sheet.originEditor = manufactureOrder.editor
            sheet.originCreator = manufactureOrder.creator
            sheet.originDateCreated = manufactureOrder.dateCreated
            sheet.originLastUpdated = manufactureOrder.lastUpdated
            sheet.typeName = manufactureOrder.typeName
            sheet.name = manufactureOrder.name
            sheet.originStatus = manufactureOrder.status
            sheet.originFactoryId = manufactureOrder.factory.id
            sheet.originWorkstationId = manufactureOrder.workstation?.id
            sheet.originSupplierId = manufactureOrder.supplier?.id
            sheet.originCustomerOrderDetId = manufactureOrder.customerOrderDet?.id
            sheet.originItemId = manufactureOrder.item.id
            sheet.originExpectQty = manufactureOrder.expectQty
            sheet.originBatchName = manufactureOrder.batchName

            //目前只提供指定結案功能，日後若開放變更其他狀態，則須調整邏輯。
            manufactureOrder.editor = "manufactureOrderSave"
            manufactureOrder.status = sheet.status

            result = domainService.save(sheet)

            // if (manufactureOrder.workstation && sheet.status == ManufactureOrderStatus.ASSIGNEDFINISHED) {
                // TODO: 確認並更新 workstation狀態。
                // def saveWorkstationStatusResult = 

                // if (!saveWorkstationRealStatusResult.success) {
                    // transactionStatus.setRollbackOnly()

                //     render (contentType: 'application/json') {
                //         saveWorkstationRealStatusResult
                //     }
                //     return
                // }
            // } //end else if (sheet.status == ManufactureOrderStatus.ASSIGNEDFINISHED)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }

    }

    //目前只提供指定結案功能，日後若開放變更其他狀態，則須提供更新功能
    //規則：當單據狀態為結案或指定結案時不可update，其他狀態則允許update。
    // @Transactional
    // def update() {
    //
    //     def newSheet = new ManufactureOrderChangeOrder(params)
    //     def oldSheet = ManufactureOrderChangeOrder.get(params.id)
    //
    //     //單別、單號一旦建立不允許變更
    //     if (newSheet.typeName!=oldSheet.typeName || newSheet.name!=oldSheet.name) {
    //         render (contentType: 'application/json') {
    //             [success: false, message: message(code: "${i18nType}.sheet.typeName.name.cannot.change")]
    //         }
    //         return
    //     }
    //
    //     //廠別一旦建立不允許變更
    //     if (newSheet.factory!=oldSheet.factory) {
    //         render (contentType: 'application/json') {
    //             [success: false, message: message(code: "${i18nType}.manufactureOrderChangeOrder.factory.cannot.change", args: [oldSheet])]
    //         }
    //         return
    //     }
    //
    //     //工作站/供應商一旦建立不允許變更
    //     if (newSheet.workstation!=oldSheet.workstation || newSheet.supplier!=oldSheet.supplier) {
    //
    //         render (contentType: 'application/json') {
    //             [success: false, message: message(code: "${i18nType}.manufactureOrderChangeOrder.workstation.supplier.cannot.change")]
    //         }
    //         return
    //     }
    //
    //     //品項一旦建立不允許變更
    //     if (newSheet.item!=oldSheet.item) {
    //         render (contentType: 'application/json') {
    //             [success: false, message: message(code: "${i18nType}.manufactureOrderChangeOrder.item.cannot.change")]
    //         }
    //         return
    //     }
    //
    //     if (newSheet.qty<=0) {
    //         render (contentType: 'application/json') {
    //             [success: false, message: message(code: "${i18nType}.sheet.qty.min.error", args: [newSheet])]
    //         }
    //         return
    //     }
    //
    //     def result = batchService.findOrCreateBatchInstanceByJson(params, newSheet)
    //
    //     if (!result.success) {
    //         render (contentType: 'application/json') {
    //             result
    //         }
    //     }
    //     else {
    //         oldSheet.properties = params
    //
    //         oldSheet.batch = result.data
    //         render (contentType: 'application/json') {
    //             domainService.save(oldSheet)
    //         }
    //     }
    // }


    @Transactional
    def delete() {
        def result
        try {
            def sheet = ManufactureOrderChangeOrder.get(params.id)
            def manufactureOrder = sheet.manufactureOrder

            manufactureOrder.editor = sheet.originEditor
            manufactureOrder.status = sheet.originStatus
            // manufactureOrder.factory = Factory.findByNameAndSite(sheet.originFactoryName, sheet.site)
            // manufactureOrder.workstation = Workstation.findByNameAndSite(sheet.originWorkstationName, sheet.site)
            // manufactureOrder.supplier = Supplier.findByNameAndSite(sheet.originSupplierName, sheet.site)
            // manufactureOrder.customerOrderDet = CustomerOrderDet.findByTypeNameAndNameAndSequenceAndSite(sheet.originCustomerOrderDetTypeName, sheet.originCustomerOrderDetName, sheet.originCustomerOrderDetSequence, sheet.site)
            // manufactureOrder.item = Item.findByNameAndBrandAndSite(sheet.originItemName, Brand.findByNameAndSite(sheet.originBrandName, sheet.site), sheet.site)
            // manufactureOrder.expectQty = sheet.originQty
            // manufactureOrder.batch = Batch.findByNameAndSite(sheet.originBatchName, sheet.site)

            result = domainService.save(manufactureOrder)

            // if (manufactureOrder.workstation) {
            //     result = workstationRealStatusService.crearteWorkstationRealStatusInstance(sheet, controllerName, actionName)
            // }
            result = domainService.delete(sheet)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

}
