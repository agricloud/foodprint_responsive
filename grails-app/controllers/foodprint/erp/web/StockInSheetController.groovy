package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.StockInSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class StockInSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService
    def stockInSheetService

    def i18nType

    def index = {

        def list = StockInSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }
    def show = {

        def sheet = StockInSheet.get(params.id)

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
            result = stockInSheetService.create(params, params.site.id, params.timeZoneId)
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
            def sheet = new StockInSheet(params)
            result = stockInSheetService.save(sheet, params.timeZoneId)
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
            def newSheet = new StockInSheet(params)
            def oldSheet = StockInSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更工作站
            if (oldSheet.details && newSheet.workstation!=oldSheet.workstation) {
                throw new DomainDataException(message(code: "${i18nType}.stockInSheet.details.exists.workstation.cannot.change", args: [oldSheet]))
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
            def sheet = StockInSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.stockInSheet.report.title.label")

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
        def stockInSheet = StockInSheet.get(params.id)
        stockInSheet.details.each{ stockInSheetDet ->
            def data = [:]
            data.dateCreated = stockInSheet.dateCreated
            data.lastUpdated = stockInSheet.lastUpdated
            data.typeName = stockInSheet.typeName
            data.name = stockInSheet.name
            data.workstation = stockInSheet.workstation
            data.executionDate = stockInSheetDet.executionDate
            data.sequence = stockInSheetDet.sequence
            data.item = stockInSheetDet.item
            data.batch = stockInSheetDet.batch
            data.warehouse = stockInSheetDet.warehouse
            data.warehouseLocation = stockInSheetDet.warehouseLocation
            data.manufactureOrder = stockInSheetDet.manufactureOrder
            data.qty = stockInSheetDet.qty
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'StockInSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
