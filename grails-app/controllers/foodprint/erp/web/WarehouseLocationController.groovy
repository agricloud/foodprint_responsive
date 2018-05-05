package foodprint.erp.web

import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class WarehouseLocationController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def warehouse = Warehouse.get(params.warehouse.id)

        if (warehouse) {
            
            params.criteria = { eq('warehouse', warehouse) } >> params.criteria

            def list = WarehouseLocation.createCriteria().list(params, params.criteria)

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

        def warehouseLocation = WarehouseLocation.get(params.id);
        if (warehouseLocation) {
            render (contentType: 'application/json') {
                [success: true, data: warehouseLocation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }



    def create = {

        if (params.warehouse.id) {

            def warehouseLocation = new WarehouseLocation(params)

            render (contentType: 'application/json') {
                [success: true, data: warehouseLocation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.warehouseLocation.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def warehouseLocation = new WarehouseLocation(params)
            result = domainService.save(warehouseLocation)
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
            def warehouseLocation = WarehouseLocation.get(params.id)
            warehouseLocation.properties = params
            result = domainService.save(warehouseLocation)
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
            def warehouseLocation = WarehouseLocation.get(params.id)
            result = domainService.delete(warehouseLocation)
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
