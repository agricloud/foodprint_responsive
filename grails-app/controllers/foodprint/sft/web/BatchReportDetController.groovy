package foodprint.sft.web

import foodprint.erp.Site
import foodprint.erp.Employee
import foodprint.erp.BatchOperation
import foodprint.erp.ManufactureOrder
import foodprint.erp.ManufactureOrderStatus
import foodprint.sft.ItemStage
import foodprint.sft.ParamType
import foodprint.sft.ReportParam
import foodprint.sft.BatchReportDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class BatchReportDetController {

    def grailsApplication
    def domainService
    def enumService
    def foodpaintService

    def i18nType

    /**
     * @param batch.id
     * @param operation.id
     * 找出指定批號及製程中所有相關參數
    **/

    def index = { //byBatchOperation

        log.debug "BatchReportDetController--batchReportDetsList"

        def batchOperation = BatchOperation.get(params.id)
        def batch = batchOperation.batch
        def operation = batchOperation.operation
        def workstation = batchOperation.workstation
        def supplier = batchOperation.supplier
        def site = Site.get(params.site.id)

        if (batch && operation && (workstation||supplier)) {

            def reportParams = ReportParam.findAll() {
                report.item==batch.item && operation==operation && (workstation==workstation || supplier==supplier) && site==site
            }

            reportParams.each {
                if (!BatchReportDet.findByBatchOperationAndReportParamAndSite(batchOperation, it, site)) {
                    println "新增批號履歷參數..批號"+batch.name+"/途程:"+batchOperation.sequence+"/參數:"+it.param.id+"/"+it.param.title
                    def newBRD = new BatchReportDet(batch: batch, reportParam: it, value: null, batchOperation: batchOperation, site: site, creator: params.creator)
                    domainService.save(newBRD)
                }
            }

            def batchReportDets = []
            if (reportParams.size > 0) {
                batchReportDets = BatchReportDet.createCriteria().list {
                    eq('batchOperation', batchOperation)
                    'in'("reportParam", reportParams)
                }
            }

            render (contentType: 'application/json') {
                [success: true, data: batchReportDets, total: batchReportDets.size()]
            }

        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.not.found", args: [message(code: "${i18nType}.batchReportDet.label")] )]
            }
        }
    }

    @Transactional
    def saveOrUpdate() {
        def result
        try {
            log.debug "BatchReportDetController--saveOrUpdate"
            log.debug params

            def success = []
            def batchOperation = BatchOperation.findById(params.id)
            // 更新製程開始結束日期
            if (!batchOperation) {
                throw new DomainDataException(message(code: "${i18nType}.default.message.not.found", args: [message(code: "${i18nType}.batchOperation.label"), params.id]))
            }
            if (params.startDate) {
                batchOperation.startDate = params.startDate
                if (batchOperation.sequence == batchOperation.batch.batchOperations*.sequence.min()) {
                    def manufactureOrder = batchOperation.batch.manufactureOrder
                    manufactureOrder.status = ManufactureOrderStatus.INPROCESS
                    domainService.save(manufactureOrder)
                }
            }
            else
                batchOperation.startDate = null
            if (params.endDate)
                batchOperation.endDate = params.endDate
            else
                batchOperation.endDate = null
            if (params.itemStage.id)
                batchOperation.itemStage = ItemStage.findById(params.itemStage.id)
            else
                batchOperation.itemStage = null
            if (params.operator.id)
                batchOperation.operator = Employee.findById(params.operator.id)
            else
                batchOperation.operator = null
            if (params.remark)
                batchOperation.remark = params.remark
            else
                batchOperation.remark = null

            //製程日期、作業員、備註
            if (!domainService.save(batchOperation).success)
                throw new DomainDataException("${message(code: "${i18nType}.batchOperation.date.label")}、${message(code: "${i18nType}.batchOperation.operator.label")}、${message(code: "${i18nType}.batchOperation.remark.label")}")
            else
                success<< "${message(code: "${i18nType}.batchOperation.date.label")}、${message(code: "${i18nType}.batchOperation.operator.label")}、${message(code: "${i18nType}.batchOperation.remark.label")}"

            //更新履歷參數值
            params.each { key, value ->

                if (key!="file" && key!="_dc" && key!="format" && key!="action"
                    && key!="controller" && key!="criteria" && key!="creator" && key!="editor"
                    && key!="id" && key!="startDate" && key!="endDate" && key!="itemStage" && key!="itemStage.id" && key!="operator" && key!="operator.id"
                    && key!="remark" && key!="batchOperation.id" && key!="site" && key!="site.id" && key!="siteGroup" && key!="siteGroup.id"
                    && key!="senchaEnv" && key!="timeZoneId") {

                    def batchReportDet = BatchReportDet.get(key)
                    if (!batchReportDet) {
                        throw new DomainDataException(message(code: "${i18nType}.default.message.not.found", args: [message(code: "${i18nType}.batchReportDet.label"), key]))
                    }

                    batchReportDet.value = value
                    batchReportDet.batchOperation = BatchOperation.findById(params.id)

                    success<< batchReportDet.reportParam.param.title
                }//end if

            }//end each

            result = [success: true, message: message(code: "${i18nType}.default.message.update.success", args: [success.join(' , ')])]
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

}
