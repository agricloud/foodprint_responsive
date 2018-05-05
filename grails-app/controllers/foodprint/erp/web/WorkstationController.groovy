package foodprint.erp.web

import foodprint.erp.Factory
import foodprint.erp.Workstation

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class WorkstationController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def list = Workstation.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByFactory = {

        def factory = Factory.get(params.factory.id)

        params.criteria = { eq('factory', factory) } >> params.criteria

        def list = Workstation.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

     def show = {

        def workstation = Workstation.get(params.id)
        if (workstation) {
            render (contentType: 'application/json') {
                [success: true, data: workstation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def workstation = new Workstation()
        render (contentType: 'application/json') {
            [success: true, data: workstation]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def workstation = new Workstation(params)
            result = domainService.save(workstation)
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

            def workstation = Workstation.get(params.id)

            if ( workstation.factory.id != params.factory.id.toLong()) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.workstation.factory.cannot.change")]
                }
                return
            }

            workstation.properties = params
            result = domainService.save(workstation)
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
            def workstation = Workstation.get(params.id)
            result = domainService.delete(workstation)

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
