 package foodprint.sft.web

import foodprint.sft.OperationCategoryLayer1

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class OperationCategoryLayer1Controller {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = OperationCategoryLayer1.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def operationCategoryLayer1 = OperationCategoryLayer1.get(params.id)
        if (operationCategoryLayer1) {
            render (contentType: 'application/json') {
                [success: true, data: operationCategoryLayer1]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def operationCategoryLayer1 = new OperationCategoryLayer1()
        render (contentType: 'application/json') {
            [success: true, data: operationCategoryLayer1]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def operationCategoryLayer1 = new OperationCategoryLayer1(params)
            result = domainService.save(operationCategoryLayer1)
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
            def operationCategoryLayer1 = OperationCategoryLayer1.get(params.id)
            operationCategoryLayer1.properties = params
            result = domainService.save(operationCategoryLayer1)
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
            def operationCategoryLayer1 = OperationCategoryLayer1.get(params.id)
            result = domainService.delete(operationCategoryLayer1)
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
