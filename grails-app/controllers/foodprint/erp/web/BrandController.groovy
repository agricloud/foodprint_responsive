package foodprint.erp.web

import foodprint.erp.Brand

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class BrandController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = Brand.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def brand = Brand.get(params.id)
        if (brand) {
            render (contentType: 'application/json') {
                [success: true, data: brand]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def brand = new Brand()
        render (contentType: 'application/json') {
            [success: true, data: brand]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def brand = new Brand(params)
            result = domainService.save(brand)
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
            def brand = Brand.get(params.id)

            brand.properties = params
            result = domainService.save(brand)

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
            def brand = Brand.get(params.id)
            result = domainService.delete(brand)
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
