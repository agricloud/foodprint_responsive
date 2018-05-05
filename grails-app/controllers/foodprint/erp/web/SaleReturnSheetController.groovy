package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.SaleReturnSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class SaleReturnSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService

    def i18nType

    def index = {

        def list = SaleReturnSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }
    def show = {

        def returnSheet = SaleReturnSheet.get(params.id)

        if (returnSheet) {

            render (contentType: 'application/json') {
                [success: true, data: returnSheet]
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
            def returnSheet = new SaleReturnSheet(params)
            returnSheet.name = sheetService.generateSheetNameByTypeName(returnSheet, params.site.id, params.timeZoneId)

            render (contentType: 'application/json') {
                [success: true, data: returnSheet]
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
            def returnSheet = new SaleReturnSheet(params)
            sheetService.validateSheetNameByTypeName(returnSheet, params.timeZoneId)
            result = domainService.save(returnSheet)
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
            def newReturnSheet = new SaleReturnSheet(params)
            def oldReturnSheet = SaleReturnSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldReturnSheet.details && newReturnSheet.factory!=oldReturnSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldReturnSheet]))
            }

            // 單身建立後不允許變更客戶
            if (oldReturnSheet.details && newReturnSheet.customer!=oldReturnSheet.customer) {
                throw new DomainDataException(message(code: "${i18nType}.saleReturnSheet.details.exists.customer.cannot.change", args: [oldReturnSheet]))
            }

            oldReturnSheet.properties = params
            result = domainService.save(oldReturnSheet)
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
            def returnSheet = SaleReturnSheet.get(params.id)
            result = domainService.delete(returnSheet)
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

        def reportTitle = message(code: "${i18nType}.saleReturnSheet.report.title.label")

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
        def saleReturnSheet = SaleReturnSheet.get(params.id)
        saleReturnSheet.details.each{ saleReturnSheetDet ->
            def data = [:]
            data.dateCreated = saleReturnSheet.dateCreated
            data.lastUpdated = saleReturnSheet.lastUpdated
            data.typeName = saleReturnSheet.typeName
            data.name = saleReturnSheet.name
            data.customer = saleReturnSheet.customer
            data.pickUpAddress = saleReturnSheet.pickUpAddress
            data.executionDate = saleReturnSheetDet.executionDate
            data.sequence = saleReturnSheetDet.sequence
            data.item = saleReturnSheetDet.item
            data.warehouse = saleReturnSheetDet.warehouse
            data.warehouseLocation = saleReturnSheetDet.warehouseLocation
            data.batch = saleReturnSheetDet.batch
            data.qty = saleReturnSheetDet.qty
            data.saleSheetDet = saleReturnSheetDet.saleSheetDet
            data.customerOrderDet = saleReturnSheetDet.customerOrderDet
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'SaleReturnSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
