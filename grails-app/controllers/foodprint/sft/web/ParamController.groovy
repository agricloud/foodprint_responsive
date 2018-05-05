package foodprint.sft.web

import foodprint.erp.Site
import foodprint.sft.ParamType
import foodprint.sft.Param
import foodprint.sft.ReportParam
import foodprint.sft.BatchReportDet

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class ParamController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {

        def list = Param.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }
     def show = {

        def param = Param.get(params.id)
        if (param) {

            render (contentType: 'application/json') {
                [success: true, data: param]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        def param = new Param()
        render (contentType: 'application/json') {
            [success: true, data: param]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def param = new Param(params)
            result = domainService.save(param)
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
            def site = Site.get(params.site.id)
            def param = Param.get(params.id)
            param.properties = params

            // 批號履歷參數已存在，不允許變更名稱、類型、單位。
            if (param.isDirty('title') || param.isDirty('paramType') || param.isDirty('unit')) {
                def reportParams = ReportParam.findAllByParamAndSite(param, site)
                reportParams.find {
                    if (BatchReportDet.findByReportParamAndSite(it, site)) {
                        throw new DomainDataException(message(code: "${i18nType}.param.batchReportDet.exists.cannot.change"))
                    }
                }
            }
            
            result = domainService.save(param)
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
            def param = Param.get(params.id)
            result = domainService.delete(param)
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
