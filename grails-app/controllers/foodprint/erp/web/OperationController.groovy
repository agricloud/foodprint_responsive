package foodprint.erp.web

import foodprint.erp.Operation

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class OperationController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST",  index: "GET"]
    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def list = Operation.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }
    def show = {

        def operation = Operation.get(params.id);
        if (operation) {
            render (contentType: 'application/json') {
                [success: true, data: operation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def operation = new Operation()
        render (contentType: 'application/json') {
            [success: true, data: operation]
        }
    }

    @Transactional
    def save() {
        def result
        try {

            def operation = new Operation(params)
            result = domainService.save(operation)
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
            def operation = Operation.get(params.id)

            operation.properties = params
            result = domainService.save(operation)
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
            def operation = Operation.get(params.id)
            result = domainService.delete(operation)
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
