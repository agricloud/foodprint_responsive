package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.Customer
import foodprint.erp.SaleSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class SaleSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService
    def saleSheetService

    def i18nType

    def index = {

        def list = SaleSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByFactoryAndCustomer = {

        def factory = Factory.get(params.factory.id)
        def customer = Customer.get(params.customer.id)

        params.criteria = {
            eq('factory', factory)
            eq('customer', customer)
        } >> params.criteria

        def list = SaleSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheet = SaleSheet.get(params.id)

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
            result = saleSheetService.create(params, params.site.id, params.timeZoneId)
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
            def sheet = new SaleSheet(params)
            result = saleSheetService.save(sheet, params.timeZoneId)
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
            def newSheet =  new SaleSheet(params)
            def oldSheet =  SaleSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更客戶
            if (oldSheet.details && newSheet.customer!=oldSheet.customer) {
                throw new DomainDataException(message(code: "${i18nType}.saleSheet.details.exists.customer.cannot.change", args: [oldSheet]))
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
            def sheet = SaleSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.saleSheet.report.title.label")

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
        parameters["SORT_FIELDS"]=sortList
        //設定準備傳入的資料
        def reportData=[]
        def saleSheet = SaleSheet.get(params.id)
        saleSheet.details.each{ saleSheetDet ->
            def data=[:]
            data.dateCreated=saleSheet.dateCreated
            data.lastUpdated=saleSheet.lastUpdated
            data.typeName=saleSheet.typeName
            data.name=saleSheet.name
            data.customer=saleSheet.customer
            data.shippingAddress=saleSheet.shippingAddress
            data.executionDate = saleSheetDet.executionDate
            data.sequence=saleSheetDet.sequence
            data.item=saleSheetDet.item
            data.warehouse=saleSheetDet.warehouse
            data.warehouseLocation=saleSheetDet.warehouseLocation
            data.batch=saleSheetDet.batch
            data.qty=saleSheetDet.qty
            data.customerOrderDet=saleSheetDet.customerOrderDet
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'SaleSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
