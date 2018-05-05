package foodprint.sft.web

import foodprint.sft.Report

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class ReportController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService

    def enumService

    def i18nType

    def index = {

        def list = Report.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def report = Report.get(params.id);

        if (report) {

            render (contentType: 'application/json') {
                [success: true, data: report]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        def report = new Report()
        render (contentType: 'application/json') {
            [success: true, data: report]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            log.debug "${controllerName}-${actionName}"
            def report = new Report(params)
            result = domainService.save(report)
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

        def report = Report.get(params.id)

            // 履歷參數已存在 不允許變更品項
            if (report.reportParams) {
                if (report.item.id != params.item.id.toLong()) {
                    throw new DomainDataException(message(code: "${i18nType}.report.reportParams.exists.item.cannot.change"))
                }
            }

            report.properties = params
            result = domainService.save(report)
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
            def report = Report.get(params.id)
            result = domainService.delete(report)
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
