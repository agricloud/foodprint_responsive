package foodprint.erp.web

import foodprint.erp.Factory
import foodprint.erp.Warehouse

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class WarehouseController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def list = Warehouse.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByFactory = {

        def factory = Factory.get(params.factory.id)

        params.criteria = { eq('factory', factory) } >> params.criteria

        def list = Warehouse.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def warehouse = Warehouse.get(params.id)
        if (warehouse) {
            render (contentType: 'application/json') {
                [success: true, data: warehouse]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def warehouse = new Warehouse()
        render (contentType: 'application/json') {
            [success: true, data: warehouse]
        }
    }
    @Transactional
    def save() {
        def result
        try {
            def warehouse = new Warehouse(params)
            result = domainService.save(warehouse)
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

            def warehouse = Warehouse.get(params.id)

            if (warehouse.factory.id != params.factory.id.toLong()) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.warehouse.factory.cannot.change")]
                }
                return
            }

            warehouse.properties = params
            result = domainService.save(warehouse)
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
            def warehouse = Warehouse.get(params.id)
            result = domainService.delete(warehouse)
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
