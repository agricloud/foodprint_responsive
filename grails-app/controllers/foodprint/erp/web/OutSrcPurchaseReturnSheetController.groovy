package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.OutSrcPurchaseReturnSheet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class OutSrcPurchaseReturnSheetController{

    def grailsApplication
    def domainService
    def jasperReportService
    def sheetService

    def i18nType

    def index = {

        def list = OutSrcPurchaseReturnSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }
    def show = {

        def returnSheet = OutSrcPurchaseReturnSheet.get(params.id)

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
            def returnSheet = new OutSrcPurchaseReturnSheet(params)
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

            def returnSheet = new OutSrcPurchaseReturnSheet(params)
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
            def newReturnSheet = new OutSrcPurchaseReturnSheet(params)
            def oldReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)

            // 單身建立後不允許變更廠別
            if (oldReturnSheet.details && newReturnSheet.factory!=oldReturnSheet.factory) {
                throw new DomainDataException(message(code: "${i18nType}.sheet.details.exists.factory.cannot.change", args: [oldReturnSheet]))
            }
            // 單身建立後不允許變更供應商
            if (oldReturnSheet.details && newReturnSheet.supplier!=oldReturnSheet.supplier) {
                throw new DomainDataException(message(code: "${i18nType}.outSrcPurchaseReturnSheet.details.exists.supplier.cannot.change", args: [oldReturnSheet]))
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
            def returnSheet = OutSrcPurchaseReturnSheet.get(params.id)
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

        def reportTitle = message(code: "${i18nType}.outSrcPurchaseReturnSheet.report.title.label")

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
        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)
        outSrcPurchaseReturnSheet.details.each{ outSrcPurchaseReturnSheetDet ->
            def data = [:]
            data.dateCreated = outSrcPurchaseReturnSheet.dateCreated
            data.lastUpdated = outSrcPurchaseReturnSheet.lastUpdated
            data.typeName = outSrcPurchaseReturnSheet.typeName
            data.name = outSrcPurchaseReturnSheet.name
            data.supplier = outSrcPurchaseReturnSheet.supplier
            data.executionDate = outSrcPurchaseReturnSheetDet.executionDate
            data.sequence = outSrcPurchaseReturnSheetDet.sequence
            data.item = outSrcPurchaseReturnSheetDet.item
            data.warehouse = outSrcPurchaseReturnSheetDet.warehouse
            data.warehouseLocation = outSrcPurchaseReturnSheetDet.warehouseLocation
            data.batch = outSrcPurchaseReturnSheetDet.batch
            data.qty = outSrcPurchaseReturnSheetDet.qty
            data.outSrcPurchaseSheetDet = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet
            data.manufactureOrder = outSrcPurchaseReturnSheetDet.manufactureOrder
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'OutSrcPurchaseReturnSheet.jasper', reportTitle, parameters, reportData, params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')
    }
}
