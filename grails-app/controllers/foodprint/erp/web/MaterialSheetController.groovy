package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory
import foodprint.erp.Workstation
import foodprint.erp.Supplier
import foodprint.erp.MaterialSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class MaterialSheetController {

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService
    def materialSheetService

    def i18nType

    def index = {

        def list = MaterialSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByFactoryAndWorkstationOrSupplier = {

        def factory = Factory.get(params.factory.id)
        def workstation = Workstation.get(params.workstation.id)
        def supplier = Supplier.get(params.supplier.id)

        params.criteria = {
            eq('factory', factory)
            or{
                eq('workstation', workstation)
                eq('supplier', supplier)
            }
        } >> params.criteria

        def list = MaterialSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheet = MaterialSheet.get(params.id)

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
            result = materialSheetService.create(params, params.site.id, params.timeZoneId)
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
            def sheet = new MaterialSheet(params)
            result = materialSheetService.save(sheet, params.timeZoneId)
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
            def newSheet = new MaterialSheet(params)

            def oldSheet = MaterialSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldSheet.details && newSheet.factory!=oldSheet.factory) {
                throw new DomainDataException(message(code: "sheet.details.exists.factory.cannot.change", args: [oldSheet]))
            }

            // 單身建立後不允許變更工作站/供應商
            if (oldSheet.details && (oldSheet.workstation!=newSheet.workstation || oldSheet.supplier!=newSheet.supplier)) {
                throw new DomainDataException(message(code: "${i18nType}.materialSheet.details.exists.workstationOrSupplier.cannot.change", args: [oldSheet]))
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
            def sheet = MaterialSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.materialSheet.report.title.label")

        // 報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        // 設定額外傳入參數
        def parameters = [:]
        parameters["SORT_FIELDS"] = sortList
        // 設定準備傳入的資料
        def reportData = []
        def materialSheet = MaterialSheet.get(params.id)
        materialSheet.details.each{ materialSheetDet ->
            def data = [:]
            data.dateCreated = materialSheet.dateCreated
            data.lastUpdated = materialSheet.lastUpdated
            data.typeName = materialSheet.typeName
            data.name = materialSheet.name
            data.workstation = materialSheet.workstation
            data.supplier = materialSheet.supplier
            data.executionDate = materialSheetDet.executionDate
            data.sequence = materialSheetDet.sequence
            data.item = materialSheetDet.item
            data.warehouse = materialSheetDet.warehouse
            data.warehouseLocation = materialSheetDet.warehouseLocation
            data.batch = materialSheetDet.batch
            data.qty = materialSheetDet.qty
            data.manufactureOrder = materialSheetDet.manufactureOrder
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'MaterialSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
