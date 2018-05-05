package foodprint.trc.web

import foodprint.erp.TypeName

import grails.converters.JSON
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

//jasperreport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class TraceController {

    def grailsApplication
    def traceService
    def dateService
    def jasperReportService

    def convertService

    def i18nType

    def backwardTraceRoot = {

        render (contentType: 'application/json') {
            traceService.backwardTraceRoot(params.id, params.site.id)
        }
    }


    def backwardTrace = {

        def childJson
        if (params.className == "Batch")
            childJson = traceService.backwardTraceByBatch(params.name, params.site.id)
        else if (params.className == "ManufactureOrder") {
            String typeNameName = params.name.split("-")[0]
            String name = params.name.split("-")[1]
            TypeName typeName = TypeName.findByName(typeNameName)
            childJson = traceService.backwardTraceByManufactureOrder(typeName, name, params.site.id)
        }

        render (contentType: 'application/json') {
            childJson
        }
    }

    def forwardTraceRoot = {
        render (contentType: 'application/json') {
            traceService.forwardTraceRoot(params.id, params.site.id)
        }
    }

    def forwardTrace = {

        // println convertService.materialReturnSheetParseJson(foodprint.erp.MaterialReturnSheet.get(3))
        def childJson
        if (params.className == "Batch")
            childJson = traceService.forwardTraceByBatch(params.name, params.site.id)
        else if (params.className == "ManufactureOrder") {
            String typeNameName = params.name.split("-")[0]
            String name = params.name.split("-")[1]
            TypeName typeName = TypeName.findByName(typeNameName)
            childJson = traceService.forwardTraceByManufactureOrder(typeName, name, params.site.id)
        }

        render (contentType: 'application/json') {
            childJson
        }

    }

    def backwardTracePrint = {

        def root = traceService.backwardTraceRoot(params.id, params.site.id)
        def nodes = [root]
        def leafs = [:]
        def reportTitle = message(code: "${i18nType}.backwardTrace.report.title.label")
        def detailReportTitle = message(code: "${i18nType}.backwardTrace.detailReport.title.label")

        //回溯出葉節點
        int index = 0
        while(index < nodes.size()) {
            def node = nodes[index]
            if (node.leaf) {
                // println "i'm leaf"+ node.type+"/"+node.className+"/"+node.name"/"+node.batch
                // 不加入重複的葉節點

                if (!leafs."${node.className}/${node.name}/${node.batch}/${node.sheetDetail}") {
                    leafs."${node.className}/${node.name}/${node.batch}/${node.sheetDetail}" = node
                }
            }
            else if (!node.children || node.children!=[]) {
                params.name = node.name
                if (node.className == "Batch") {
                    nodes += traceService.backwardTraceByBatch(params.name, params.site.id)
                }
                else if (node.className == "ManufactureOrder") {
                    String typeNameName = node.name.split("-")[0]
                    String name = node.name.split("-")[1]
                    TypeName typeName = TypeName.findByName(typeNameName)
                    nodes += traceService.backwardTraceByManufactureOrder(typeName, name, params.site.id)
                }
            }
            index++
        }

        def reportFile = print(reportTitle, detailReportTitle, root, leafs.values(), params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')

    }

    def forwardTracePrint = {

        def root = traceService.forwardTraceRoot(params.id, params.site.id)
        def nodes = [root]
        def leafs = [:]
        def reportTitle = message(code: "${i18nType}.forwardTrace.report.title.label")
        def detailReportTitle = message(code: "${i18nType}.forwardTrace.detailReport.title.label")

        //回溯出葉節點
        int index = 0
        while(index < nodes.size()) {
            def node = nodes[index]
            if (node.leaf) {
                // println "i'm leaf"+ node.type+"/"+node.className+"/"+node.name+"/"+node.batch
                // 不加入重複的葉節點
                if (!leafs."${node.className}/${node.name}/${node.batch}/${node.sheetDetail}") {
                    leafs."${node.className}/${node.name}/${node.batch}/${node.sheetDetail}" = node
                }
            }
            else if (!node.children || node.children!=[]) {
                params.name = node.name
                if (node.className == "Batch") {
                    nodes += traceService.forwardTraceByBatch(params.name, params.site.id)
                }
                else if (node.className == "ManufactureOrder") {
                    String typeNameName = node.name.split("-")[0]
                    String name = node.name.split("-")[1]
                    TypeName typeName = TypeName.findByName(typeNameName)
                    nodes += traceService.forwardTraceByManufactureOrder(typeName, name, params.site.id)
                }
            }
            index++
        }

        def reportFile = print(reportTitle, detailReportTitle, root, leafs.values(), params.timeZoneId)

        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')

    }

    def print(reportTitle, detailReportTitle, root, leafs, timeZoneId) {

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('executionDate');
        sortField.setOrder(SortOrderEnum.DESCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);

        //子報表1資料 = 根節點單據
        def detailData1 = []
        root.sheetDetail.each {  sheet ->
            sheet.dateCreated = dateService.parseUserISO8601ToUTC(sheet.dateCreated, root.sheetDetailTimezone.ID)
            sheet.executionDate = dateService.parseUserISO8601ToUTC(sheet.executionDate, root.sheetDetailTimezone.ID)
            detailData1<< sheet
        }
        //子報表1參數
        def detailParams1 = [:]
        detailParams1["SORT_FIELDS"]=sortList

        //子報表2資料 = 葉節點單據
        def detailData2 = []
        leafs.each { leaf ->

            if (leaf.sheetDetail) {
                leaf.sheetDetail.each { sheet ->
                    sheet.nodeType = leaf.type
                    sheet.nodeName = leaf.name
                    sheet.nodeTel = leaf.tel
                    sheet.nodeFax = leaf.fax
                    sheet.nodeAddress = leaf.address
                    sheet.nodeContact = leaf.contact
                    sheet.dateCreated = dateService.parseUserISO8601ToUTC(sheet.dateCreated, leaf.sheetDetailTimezone.ID)
                    sheet.executionDate = dateService.parseUserISO8601ToUTC(sheet.executionDate, leaf.sheetDetailTimezone.ID)
                    detailData2<< sheet
                }
            }
            else {//無單據的葉節點 ex: 庫存
                if (leaf.className != "Batch") {//新公司初始直接建立舊有庫存批號，未知其供應商時，逆溯報表不應歸類為leaf。
                    def data=[:]
                    data.nodeType = leaf.type
                    data.nodeName = leaf.name
                    data.nodeTel = leaf.tel
                    data.nodeFax = leaf.fax
                    data.nodeAddress = leaf.address
                    data.nodeContact = leaf.contact
                    data["item.name"] = leaf.item.name
                    data["item.title"] = leaf.item.title
                    data["item.brand.name"] = leaf.item.brand.name
                    data["item.brand.title"] = leaf.item.brand.title
                    data["item.spec"] = leaf.item.spec
                    data["item.unit"] = leaf.item.unit
                    data["batch.name"] = leaf.batch.name
                    data.qty = leaf.qty
                    data["warehouse.name"] = leaf["warehouse.name"]
                    data["warehouse.title"] = leaf["warehouse.title"]
                    detailData2<< data
                }
            }
        }
        //子報表2參數
        def detailParams2 = [:]
        detailParams2["report.title"]=detailReportTitle
        detailParams2["SORT_FIELDS"]=sortList

        //設定額外傳入參數
        def parameters=[:]
        parameters["detailData1"]=detailData1
        parameters["detailData2"]=detailData2
        parameters["detailParams1"]=detailParams1
        parameters["detailParams2"]=detailParams2

        jasperReportService.printPdf params, 'TraceReport.jasper', reportTitle, parameters, [root], timeZoneId

    }

}
