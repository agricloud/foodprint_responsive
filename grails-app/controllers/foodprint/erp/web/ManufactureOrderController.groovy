
package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.ManufactureOrderStatus
import foodprint.erp.Factory
import foodprint.erp.Workstation
import foodprint.erp.Supplier
import foodprint.erp.Batch
import foodprint.erp.ManufactureOrder

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class ManufactureOrderController {

    def grailsApplication
    def domainService
    def batchService
    def jasperReportService
    def sheetService
    def enumService
    def dateService

    def i18nType

    def index = {

        def list = ManufactureOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWorkstationIsNotNull = {
        params.criteria = {
            isNotNull("workstation")
        } >> params.criteria

        def list = ManufactureOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexBySupplierIsNotNull = {
        params.criteria = {
            isNotNull("supplier")
        } >> params.criteria

        def list = ManufactureOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByNonFinishedStatusAndFactoryAndWorkstationOrSupplier = {

        def factory = Factory.get(params.factory.id)
        def workstation = Workstation.get(params.workstation.id)
        def supplier = Supplier.get(params.supplier.id)

        params.criteria = {
            eq('factory', factory)
            or{
                eq('workstation', workstation)
                eq('supplier', supplier)
            }
            ne('status', ManufactureOrderStatus.ASSIGNEDFINISHED)
            ne('status', ManufactureOrderStatus.FINISHED)
        } >> params.criteria

        def list = ManufactureOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByNonFinishedStatus = {

        params.criteria = {
            ne('status', ManufactureOrderStatus.ASSIGNEDFINISHED)
            ne('status', ManufactureOrderStatus.FINISHED)
        } >> params.criteria

        def list = ManufactureOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheet = ManufactureOrder.get(params.id);


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

    def create = {

        if (params.typeName.id) {
            def sheet = new ManufactureOrder(params)
            
            sheet.name = sheetService.generateSheetNameByTypeName(sheet, params.site.id, params.timeZoneId)
            sheet.manufactureType = sheet.manufactureType ?: sheet.typeName.manufactureType

            render (contentType: 'application/json') {
                [success: true, data: sheet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.sheet.create.failed")]
            }
        }
    }

    @Transactional
    def save() {

        def result

        try {
            ckeckParams()

            def sheet = new ManufactureOrder(params)

            sheetService.validateSheetNameByTypeName(sheet, params.timeZoneId)

            result = domainService.save(sheet)

            params["batch.name"] = params.batchName
            batchService.createBatchInstanceByJson(params, sheet, params.site.id, params)
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

            ckeckParams()

            def sheet = ManufactureOrder.get(params.id)

            // 製令狀態為已發料、生產中、完工、結案者不可更新
            if (!(sheet.status in [ManufactureOrderStatus.PENDING, ManufactureOrderStatus.APPROVED])) {
                throw new DomainDataException(message(code: "${i18nType}.manufactureOrder.cannot.update", args: [enumService.name(sheet.status).title]))
            }

            sheet.properties = params

            if (sheet.isDirty('batchName')) {
                params["batch.name"] = params.batchName
                batchService.createBatchInstanceByJson(params, sheet, params.site.id, params)
                domainService.delete(Batch.findByNameAndSite(sheet.getPersistentValue('batchName'), Site.get(params.site.id)))
            }
            else if (sheet.isDirty('expectQty')) {
                def batch = Batch.findByNameAndSite(sheet.batchName, Site.get(params.site.id))
                batch.expectQty = sheet.expectQty
            }

            result = domainService.save(sheet)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    private ckeckParams = {

        // 不可手動變更製令狀態
        if (params.containsKey('status')) {
            throw new DomainDataException(message(code: "${i18nType}.manufactureOrder.status.cannot.change"))
        }
        // 不可手動變更製令分批狀態
        else if (params.containsKey('isSplit')) {
            throw new DomainDataException(message(code: "${i18nType}.manufactureOrder.isSplit.cannot.change"))
        }
    }


    @Transactional
    def delete() {

        def result
        
        try {
            def sheet = ManufactureOrder.get(params.id)
            /*
             * note:
             * 不可使用關聯處理刪除 sheet.batches.each { batch ->
             * 否則造成 ObjectDeletedException: deleted object would be re-saved by cascade
             */
            Batch.findByManufactureOrderAndSite(sheet, Site.get(params.site.id)).each { batch ->
                result = domainService.delete(batch)
            }

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

    def print() {

        def reportTitle = message(code: "${i18nType}.manufactureOrder.report.title.label")

        //設定額外傳入參數
        def parameters = [:]

        //設定準備傳入的資料
        def reportData = []
        def manufactureOrder = ManufactureOrder.get(params.id)
        def data = manufactureOrder.properties

        reportData << data

        def reportFile = jasperReportService.printPdf params, 'ManufactureOrder.jasper', reportTitle, parameters, reportData, params.timeZoneId

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }

}
