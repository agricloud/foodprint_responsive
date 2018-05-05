package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.Supplier
import foodprint.erp.OutSrcPurchaseSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class OutSrcPurchaseSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService
    def outSrcPurchaseSheetService

    def i18nType

    def index = {

        def list = OutSrcPurchaseSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByFactoryAndSupplier = {

        def factory = Factory.get(params.factory.id)
        def supplier = Supplier.get(params.supplier.id)

        params.criteria = {
            eq('factory', factory)
            eq('supplier', supplier)
        } >> params.criteria

        def list = OutSrcPurchaseSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        def sheet = OutSrcPurchaseSheet.get(params.id)

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
        def result
        try {
            result = outSrcPurchaseSheetService.create(params, params.site.id, params.timeZoneId)
        }
        catch (DomainDataException e) {
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
            def sheet = new OutSrcPurchaseSheet(params)
            result = outSrcPurchaseSheetService.save(sheet, params.timeZoneId)
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
            def newSheet = new OutSrcPurchaseSheet(params)
            def oldSheet = OutSrcPurchaseSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更供應商
            if (oldSheet.details && newSheet.supplier!=oldSheet.supplier) {
                throw new DomainDataException(message(code: "${i18nType}.outSrcPurchaseSheet.details.exists.supplier.cannot.change", args: [oldSheet]))
            }

            oldSheet.properties = params
            result = domainService.save(oldSheet)
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
            def sheet = OutSrcPurchaseSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.outSrcPurchaseSheet.report.title.label")

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters = [:]
        parameters["SORT_FIELDS"] = sortList
        //設定準備傳入的資料
        def reportData = []
        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.id)
        outSrcPurchaseSheet.details.each{ outSrcPurchaseSheetDet ->
            def data = [:]
            data.dateCreated = outSrcPurchaseSheet.dateCreated
            data.lastUpdated = outSrcPurchaseSheet.lastUpdated
            data.typeName = outSrcPurchaseSheet.typeName
            data.name = outSrcPurchaseSheet.name
            data.supplier = outSrcPurchaseSheet.supplier
            data.executionDate = outSrcPurchaseSheetDet.executionDate
            data.sequence = outSrcPurchaseSheetDet.sequence
            data.item = outSrcPurchaseSheetDet.item
            data.warehouse = outSrcPurchaseSheetDet.warehouse
            data.warehouseLocation = outSrcPurchaseSheetDet.warehouseLocation
            data.batch = outSrcPurchaseSheetDet.batch
            data.qty = outSrcPurchaseSheetDet.qty
            data.manufactureOrder = outSrcPurchaseSheetDet.manufactureOrder
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'OutSrcPurchaseSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
