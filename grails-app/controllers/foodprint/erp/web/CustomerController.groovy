package foodprint.erp.web

import foodprint.erp.Customer

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class CustomerController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def list = Customer.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

     def show = {

        def customer = Customer.get(params.id);
        if (customer) {
            render (contentType: 'application/json') {
                [success: true, data: customer]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def customer = new Customer()
        render (contentType: 'application/json') {
            [success: true, data: customer]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def customer = new Customer(params)
            result = domainService.save(customer)
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
            def customer = Customer.get(params.id)
            customer.properties = params
            result = domainService.save(customer)
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
            def customer = Customer.get(params.id)
            result = domainService.delete(customer)
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
