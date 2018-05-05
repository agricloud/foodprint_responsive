package foodprint.trc.web

import grails.converters.*

import foodprint.erp.Batch
import foodprint.erp.ManufactureOrder
import foodprint.erp.MaterialSheetDet
import foodprint.erp.MaterialReturnSheetDet
import foodprint.sft.ParamType
import foodprint.sft.ReportType
import foodprint.sft.BatchReportDet

class ReportViewerController {

    def grailsApplication
    def sheetInquiryService
    def dateService

    def i18nType

    def index = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)
        def product = [:]
        product.head = [:]
        product.body = [:]
        product.title = message(code: "${i18nType}.view.reportViewer.productionIntro.label")


        product.head["batch.name"] = batch.name
        product.head["item.title"] = batch.item.title
        product.head["item.description"] = batch.item.description
        product.head["batch.remark"] = batch?.remark

        product.body["item.name"] = batch.item.name
        product.body["batch.manufactureDate"] = batch.manufactureDate
        product.body["batch.expirationDate"] = batch.expirationDate
        product.body["item.spec"] = batch.item.spec


        def otherReports = []
        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParam*.report.unique()




        domainReports.each() { report ->
            def reportMap = [:]
            reportMap.params=[]
            reportMap.title = report.title
            reportMap.reportType = report.reportType


            if (reportMap.reportType == ReportType.OTHER) {
                batchReportDets.each() { batchReportDet ->
                    if (batchReportDet.reportParam.report == report) {
                        def param = [:]

                        param["param.name"] = batchReportDet.reportParam.param.name
                        param["param.title"] = batchReportDet.reportParam.param.title
                        param["param.description"] = batchReportDet.reportParam.param.description
                        param["batchReportDet.value"] = batchReportDet.value

                        reportMap.params << param
                    }
                }
                otherReports << reportMap
            }

        }


        [batch: batch, product: product,reports: otherReports]



    }

    def nutrition = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)

        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParam*.report.unique()

        def reportMap = [:]


        reportMap.params = []

        domainReports.each() { report ->

            if (report.reportType == ReportType.NUTRITION) {

                reportMap.title = report.title
                reportMap.reportType = report.reportType

                batchReportDets.each() { batchReportDet ->
                    if (batchReportDet.reportParam.report == report) {
                        def param = [:]

                        param["param.name"] = batchReportDet.reportParam.param.name
                        param["param.title"] = batchReportDet.reportParam.param.title
                        param["param.description"] = batchReportDet.reportParam.param.description
                        param["param.unit"] = batchReportDet.reportParam.param.unit
                        param["batchReportDet.value"] = batchReportDet.value

                        reportMap.params << param

                    }
                }
            }
        }

        [batch: batch, report: reportMap]



    }

    def material = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def masterBatch = Batch.findByName(params.name)

        // log.debug "查詢履歷主批號 ${masterBatch.id}::${masterBatch.name}"

        def site = masterBatch.site
        def manufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(masterBatch.name, site)

        def reportMap = [:]
        reportMap.title = message(code: "${i18nType}.view.reportViewer.materialReport.label")
        reportMap.params=[:]

        int index = 0
        while(index < manufactureOrders.size()) {

            def manufactureOrder = manufactureOrders[index]

            // log.debug "check 製令 ${manufactureOrder.id}::${manufactureOrder.typeName}-${manufactureOrder.name} 之領料單"

            def materialSheetDets = MaterialSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, masterBatch.site)
            materialSheetDets.each { materialSheetDet ->

                // log.debug "check 領料單單身 ${materialSheetDet.id}::${materialSheetDet.typeName}-${materialSheetDet.name}-${materialSheetDet.sequence} 用料"

                //確認此領料單是否為半製品
                def semiManufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(materialSheetDet.batch.name, site)
                //確認此領料單是否存在領退單
                def materialReturnSheetDets = MaterialReturnSheetDet.findAllByMaterialSheetDetAndSite(materialSheetDet, site)
                //計算此領料單實際領用量 = 領料量-領退量
                def applyQty = materialSheetDet.qty
                materialReturnSheetDets.each { materialReturnSheetDet ->
                    applyQty -= materialReturnSheetDet.qty
                }

                if (applyQty!=0) {
                    if (semiManufactureOrders == []) {//非半製品

                        def batch = Batch.findByNameAndSite(materialSheetDet.batch.name, site)

                        // log.debug "原料批號 ${batch.id}::${batch.name}-${batch.item.title}"

                        //批號不重複才加入
                        if (!reportMap.params[batch.name]) {
                            def param = [:]
                            // param["batch.name"] = batch.name
                            // param["item.name"] = batch.item.name
                            param["item.title"] = batch.item.title
                            param["item.spec"] = batch.item.spec
                            param["batch.country"] = batch.country
                            param["item.description"] = batch.item.description
                            param["default.image"] = "/attachment/show/${batch.item.id}?domainName=item&fileFormat=img&useBlank=true&site.id=${site.id}"

                            reportMap.params[batch.name] = param
                        }
                    }
                    else {
                        semiManufactureOrders.each{ mo->
                            // log.debug "半製品製令 ${mo.id}::${mo.typeName}-${mo.name}"
                            // log.debug "半製品 ${mo.batch.id}::${mo.batch.name}-${mo.batch.item.title}"
                            manufactureOrders << mo
                        }
                    }
                } //end if (applyQty!=0)
            }
            index++
        } // end while

        [batch: masterBatch, report: reportMap]

    }
    def cultivate = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)

        def batchRouteReportMap = [:]
        batchRouteReportMap.title = message(code: "${i18nType}.view.reportViewer.cultivateReport.label")

        batchRouteReportMap.params = []



        batch.batchOperations.each() { batchOperation ->
            def param = [:]
            // param["batchOperation.id"] = batchOperation.id
            // param["batchOperation.sequence"] = batchOperation.sequence

            param["view.reportViewer.cultivate.operation.title"] = batchOperation?.operation?.title
            param["view.reportViewer.cultivate.batchOperation.endDate"] = batchOperation?.startDate

            param["view.reportViewer.cultivate.workstationOrSupplier.title"] = batchOperation?.workstation ? batchOperation?.workstation.title : batchOperation?.supplier.title
            param["operation.description"] = batchOperation?.operation?.description

            param["default.image"] = "/attachment/show/${batchOperation.id}?domainName=batchOperation&fileFormat=img&useBlank=true&site.id=${batch.site?.id}"

            batchRouteReportMap.params << param

        }

        [batch: batch, report: batchRouteReportMap]

    }
    def quality = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)



        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParam*.report.unique()

        def reportMap = [:]


        reportMap.params = []

        def success = true, message

        for (report in domainReports) {

            if (report.reportType == ReportType.INSPECT) {

                reportMap.title = report.title
                reportMap.reportType = report.reportType

                for (batchReportDet in batchReportDets) {
                    if (batchReportDet.value=="") {
                        success = false
                        message = "檢驗尚未完成"
                        break
                    }
                    if (batchReportDet.reportParam.report == report) {

                        def param = [
                            "view.reportViewer.quality.param.title":          null,
                            "view.reportViewer.quality.batchReportDet.value": null,
                            "param.upper":                                    null,
                            "view.reportViewer.quality.param.unit":           null,
                            "view.reportViewer.quality.qualified":            null,
                            "view.reportViewer.quality.dateCreated":          null
                        ] // 決定gsp表格顯示的順序

                        param["view.reportViewer.quality.param.title"] = batchReportDet.reportParam.param.title

                        if (batchReportDet.reportParam.param.paramType == ParamType.INTEGER || batchReportDet.reportParam.param.paramType == ParamType.DOUBLE) {
                            if (batchReportDet.reportParam.param.paramType == ParamType.INTEGER)
                                param["view.reportViewer.quality.batchReportDet.value"] = batchReportDet.value?.toInteger()

                            if (batchReportDet.reportParam.param.paramType == ParamType.DOUBLE)
                                param["view.reportViewer.quality.batchReportDet.value"] = batchReportDet.value?.toDouble()

                            param["param.upper"] = batchReportDet.reportParam.param.upper?.toDouble()
                            if (param["view.reportViewer.quality.batchReportDet.value"] <= param["param.upper"])
                                param["view.reportViewer.quality.qualified"] = true
                            else param["view.reportViewer.quality.qualified"] = false
                        }

                        if (batchReportDet.reportParam.param.paramType == ParamType.BOOLEAN) {
                            param["view.reportViewer.quality.batchReportDet.value"] = batchReportDet.value?.toBoolean()
                            param["param.upper"] = batchReportDet.reportParam.param.upper?.toBoolean()
                            if (param["view.reportViewer.quality.batchReportDet.value"] == param["param.upper"])
                                param["view.reportViewer.quality.qualified"] = true
                            else param["view.reportViewer.quality.qualified"] = false

                        }
                        if (batchReportDet.reportParam.param.paramType == ParamType.STRING) {
                            param["view.reportViewer.quality.batchReportDet.value"] = batchReportDet.value
                            //未處理是否合格欄位
                            param["param.upper"] = ""
                            param["view.reportViewer.quality.qualified"] = ""
                        }

                        param["view.reportViewer.quality.param.unit"] = batchReportDet.reportParam.param.unit
                        param["view.reportViewer.quality.dateCreated"] = batchReportDet.batchOperation.endDate

                        reportMap.params << param
                    }
                }
                if (!success)
                    break
            }

        }

        if (success)
            [success: true, batch: batch, report: reportMap]
        else
            [success: false, message: message]

    }

    def operator = {

        if (!params?.name || params?.name == 'null') {
            flash.message = message(code: "${i18nType}.view.reportViewer.unselect.batch.error.label")
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)

        def batchRouteReportMap = [:]
        batchRouteReportMap.title = message(code: "${i18nType}.view.reportViewer.operatorReport.label")

        batchRouteReportMap.params = []



        def operators = batch.batchOperations*.operator.unique()

        operators.each { operator ->
            if (operator) {
                def param = [:]

                param["default.image"] = "/attachment/show/${operator.id}?domainName=employee&fileFormat=img&useBlank=true&site.id=${batch.site.id}"
                param["view.reportViewer.operator.title"] = operator.title
                param["view.reportViewer.operator.introduction"] = operator.introduction
                param["view.reportViewer.operator.experience"] = operator.experience
                param["view.reportViewer.operator.mainWork"] = operator.mainWork
                param["view.reportViewer.operator.area"] = operator.area
                param["view.reportViewer.operator.description"] = operator.description


                batchRouteReportMap.params << param
            }

        }

        [batch: batch, report: batchRouteReportMap]

    }

    def search = {
        render (view: 'search')
    }

    def query = {


        def batch = Batch.findByName(params.name)

        if (batch) {
            redirect (uri: '/reports/'+batch.name)
            return
        }
        else {
            if (params.name == "")
                flash.message = "請輸入批號！"
            else flash.message = "查無批號！"
            redirect (uri: '/reports')
        }

    }

    def questionnaire = {
        redirect(url: "https://docs.google.com/forms/d/1nbMqNMfWWHh6Y2unpzDz5biqqLtRMGFgg9mKuCBwNCA/viewform")

    }


}
