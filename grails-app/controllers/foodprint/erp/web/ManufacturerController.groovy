package foodprint.erp.web

import foodprint.erp.Manufacturer

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class ManufacturerController {

    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {
        def list = Manufacturer.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def manufacturer = Manufacturer.get(params.id);
        if (manufacturer) {
            render (contentType: 'application/json') {
                [success: true, data: manufacturer]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def manufacturer=new Manufacturer()
        render (contentType: 'application/json') {
            [success: true, data: manufacturer]
        }
    }

    @Transactional
    def save() {
        def result
        try {

            if (params.name.size()>=17 && params.name[-17..-1]=="CreatedBySupplier" ) {
                throw new DomainDataException(message(code: "${i18nType}.manufacturer.name.invalid"))
            }
            def manufacturer = new Manufacturer(params)
            result = domainService.save(manufacturer)
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

            def manufacturer = Manufacturer.get(params.id)

            if (params.isSupplier && params.isSupplier.toBoolean()!=manufacturer.isSupplier) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.manufacturer.isSupplier.cannot.change")]
                }
                return
            }

            if (manufacturer.isSupplier == true ) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.manufacturer.belongs.to.supplier.cannot.update")]
                }
                return
            }
 
            manufacturer.properties = params
            result = domainService.save(manufacturer)
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
            // 於前端提示將會同時刪除suppplier
            def manufacturer = Manufacturer.get(params.id)
            result = domainService.delete(manufacturer)
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
