package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.Supplier
import foodprint.erp.PurchaseSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class PurchaseSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService

    def i18nType

    def index = {

        def list = PurchaseSheet.createCriteria().list(params, params.criteria)
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

        def list = PurchaseSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        def sheet = PurchaseSheet.get(params.id)

        if (sheet) {

            render (contentType: 'application/json') {
                [success: true, data: sheet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code:"${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (params.typeName.id) {
            def sheet = new PurchaseSheet(params)
            sheet.name = sheetService.generateSheetNameByTypeName(sheet, params.site.id, params.timeZoneId)

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
            def sheet = new PurchaseSheet(params)
            sheetService.validateSheetNameByTypeName(sheet, params.timeZoneId)

            // 總額不可手動更新
            if (params.totalPrice) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.totalPrice.cannot.change"))
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


    @Transactional
    def update() {
        def result
        try {

            def newSheet = new PurchaseSheet(params)
            def oldSheet = PurchaseSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更供應商
            if (oldSheet.details && newSheet.supplier!=oldSheet.supplier) {
                throw new DomainDataException(message(code: "${i18nType}.purchaseSheet.details.exists.supplier.cannot.change", args: [oldSheet]))
            }

            // 總額不可手動更新
            if (params.totalPrice) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.totalPrice.cannot.change"))
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
            def sheet = PurchaseSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.purchaseSheet.report.title.label")

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
        def purchaseSheet = PurchaseSheet.get(params.id)
        purchaseSheet.details.each{ purchaseSheetDet ->
            def data = [:]
            data.dateCreated = purchaseSheet.dateCreated
            data.lastUpdated = purchaseSheet.lastUpdated
            data.typeName = purchaseSheet.typeName
            data.name = purchaseSheet.name
            data.supplier = purchaseSheet.supplier
            data.executionDate = purchaseSheetDet.executionDate
            data.sequence = purchaseSheetDet.sequence
            data.item = purchaseSheetDet.item
            data.warehouse = purchaseSheetDet.warehouse
            data.warehouseLocation = purchaseSheetDet.warehouseLocation
            data.batch = purchaseSheetDet.batch
            data.qty = purchaseSheetDet.qty
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'PurchaseSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
