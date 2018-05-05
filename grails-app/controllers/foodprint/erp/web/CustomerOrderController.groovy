package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.Customer
import foodprint.erp.CustomerOrder

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class CustomerOrderController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService

    def i18nType

    def index = {

        def list = CustomerOrder.createCriteria().list(params, params.criteria)
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

        def list = CustomerOrder.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheet = CustomerOrder.get(params.id)

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
            def sheet = new CustomerOrder(params)
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
            def sheet = new CustomerOrder(params)
            sheetService.validateSheetNameByTypeName(sheet, params.timeZoneId)
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
            def newSheet = new CustomerOrder(params)
            def oldSheet = CustomerOrder.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更客戶
            if (oldSheet.details && newSheet.customer!=oldSheet.customer) {
                throw new DomainDataException(message(code: "${i18nType}.customerOrder.details.exists.customer.cannot.change", args: [oldSheet]))
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
        //當訂單單身存在時
        //且與製令/銷貨單/銷退單任一個有關聯時
        //刪除單頭應回傳失敗

        def result
        
        try {
            def sheet = CustomerOrder.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.customerOrder.report.title.label")

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
        def customerOrder = CustomerOrder.get(params.id)
        customerOrder.details.each{ customerOrderDet ->
            def data = [:]
            data.dateCreated = customerOrder.dateCreated
            data.lastUpdated = customerOrder.lastUpdated
            data.typeName = customerOrder.typeName
            data.name = customerOrder.name
            data.customer = customerOrder.customer
            data.shippingAddress = customerOrder.shippingAddress
            data.dueDate = customerOrder.dueDate
            data.executionDate = customerOrderDet.executionDate
            data.sequence = customerOrderDet.sequence
            data.item = customerOrderDet.item
            data.qty = customerOrderDet.qty

            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'CustomerOrder.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
