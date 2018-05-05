package foodprint.sft.web

import foodprint.sft.Report
import foodprint.sft.ReportParam
import foodprint.sft.BatchReportDet

import foodprint.common.DomainDataException

import org.hibernate.criterion.CriteriaSpecification
import grails.transaction.Transactional

class ReportParamController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        log.debug "${controllerName}-${actionName}"

        def report = Report.get(params.report.id);

        if (report) {

            params.criteria = { eq('report', report) } >> params.criteria

            params.criteria = params.criteria << {
                createAlias('param', 'param', CriteriaSpecification.LEFT_JOIN)
                createAlias('operation', 'operation', CriteriaSpecification.LEFT_JOIN)
                createAlias('workstation', 'workstation', CriteriaSpecification.LEFT_JOIN)
                createAlias('supplier', 'supplier', CriteriaSpecification.LEFT_JOIN)
            }

            def list = ReportParam.createCriteria().list(params, params.criteria)

            render (contentType: 'application/json') {
                [data: list, total: list.totalCount]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }

    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def reportParam = ReportParam.get(params.id)

        if (reportParam) {
            render (contentType: 'application/json') {
                [success: true, data: reportParam]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (params.report.id) {

            def reportParam = new ReportParam(params)

            render (contentType: 'application/json') {
                [success: true, data: reportParam]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.reportParam.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            log.debug "${controllerName}-${actionName}"

            def reportParam = new ReportParam(params)
            result = domainService.save(reportParam)
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

            log.debug "${controllerName}-${actionName}"

            def newReportParam = new ReportParam(params)
            def oldReportParam = ReportParam.get(params.id)

            //version1:
            //已存在關聯批號履歷參數(BatchReportDet)不允許變更工作站、供應商、製程
            def batchReportDets = BatchReportDet.findAllByReportParamAndSite(oldReportParam, oldReportParam.site)
            if (batchReportDets) {
                if (oldReportParam.operation!=newReportParam.operation ||
                   oldReportParam.workstation!=newReportParam.workstation ||
                   oldReportParam.supplier!=newReportParam.supplier) {

                    throw new DomainDataException(message(code: "${i18nType}.reportParam.batchReportDet.exists.operation.workstation.supplier.cannot.change"))
                }
            }

            //version2:
            // //已存在關聯批號履歷參數(BatchReportDet)，且變更工作站、供應商、製程，須將關聯批號履歷參數全部刪除
            // def batchReportDets = BatchReportDet.findAllByReportParamAndSite(oldReportParam, oldReportParam.site)
            // if (batchReportDets) {
            //     if (oldReportParam.operation!=newReportParam.operation ||
            //        oldReportParam.workstation!=newReportParam.workstation ||
            //        oldReportParam.supplier!=newReportParam.supplier) {

            //         batchReportDets.each{
            //             domainService.delete(it)
            //         }
            //     }
            // }

            oldReportParam.properties=params
            result = domainService.save(oldReportParam)
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
        log.debug "${controllerName}-${actionName}"

        def result
        try {
            def reportParam = ReportParam.get(params.id)
            //version1:
            //刪除履歷參數設定時，若有關聯的批號履歷參數(BatchReportDet)不允許刪除
            //version2:
            // //刪除履歷參數設定時，同時把過去曾關聯產生的批號履歷參數(BatchReportDet)刪除
            // def batchReportDets = BatchReportDet.findAllByReportParamAndSite(reportParam, reportParam.site)
            // batchReportDets.each{
            //     domainService.delete(it)
            // }

            result = domainService.delete(reportParam)
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
