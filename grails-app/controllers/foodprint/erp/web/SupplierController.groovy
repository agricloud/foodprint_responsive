package foodprint.erp.web

import foodprint.erp.*

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class SupplierController {

    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {
        def list = Supplier.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByIsManufacturer = {

        params.criteria = { eq('isManufacturer', params.isManufacturer.toBoolean()) } >> params.criteria

        def list = Supplier.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def supplier = Supplier.get(params.id);
        if (supplier) {
            render (contentType: 'application/json') {
                [success: true, data: supplier]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def supplier = new Supplier()
        render (contentType: 'application/json') {
            [success: true, data: supplier]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def supplier = new Supplier(params)

            // 如果供應商為製造商，則複製新增製造商。
            if (params.isManufacturer && params.isManufacturer.toBoolean()==true) {

                def manufacturer = new Manufacturer(params)
                manufacturer.name += "CreatedBySupplier"
                manufacturer.isSupplier = true

                domainService.save(manufacturer)

                manufacturer.supplier = supplier
                supplier.manufacturer = manufacturer
            }

            result = domainService.save(supplier)
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

            def supplier = Supplier.get(params.id)

            if (params.isManufacturer && params.isManufacturer.toBoolean()!=supplier.isManufacturer) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.supplier.isManufacturer.cannot.change")]
                }
                return
            }

            if (supplier.manufacturer) {
                supplier.manufacturer.properties = params
                supplier.manufacturer.name += "CreatedBySupplier"
            }

            supplier.properties = params
            result = domainService.save(supplier)
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
            // 於前端提示將會同時刪除manufacturer
            def supplier = Supplier.get(params.id)
            result = domainService.delete(supplier)
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
