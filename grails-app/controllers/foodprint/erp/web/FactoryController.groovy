package foodprint.erp.web

import foodprint.erp.Factory

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class FactoryController {

    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {
        def list = Factory.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def factory = Factory.get(params.id);
        if (factory) {
            render (contentType: 'application/json') {
                [success: true, data: factory]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def factory = new Factory()
        render (contentType: 'application/json') {
            [success: true, data: factory]
        }
    }

    @Transactional
    def save() {
        def result
        try {

            def factory = new Factory(params)
            result = domainService.save(factory)
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
            def factory = Factory.get(params.id)
            factory.properties = params
            result = domainService.save(factory)
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
            def factory = Factory.get(params.id)
            result = domainService.delete(factory)
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
