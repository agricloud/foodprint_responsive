package foodprint.erp.web

import foodprint.erp.BillOfMaterial

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class BillOfMaterialController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = BillOfMaterial.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def billOfMaterial = BillOfMaterial.get(params.id)
        if (billOfMaterial) {
            render (contentType: 'application/json') {
                [success: true, data: billOfMaterial]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def billOfMaterial = new BillOfMaterial()
        render (contentType: 'application/json') {
            [success: true, data: billOfMaterial]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def billOfMaterial = new BillOfMaterial(params)
            billOfMaterial.updatedDate = new Date()
            result = domainService.save(billOfMaterial)
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
            def billOfMaterial = BillOfMaterial.get(params.id)

            billOfMaterial.properties = params
            billOfMaterial.updatedDate = new Date()
            result = domainService.save(billOfMaterial)

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
            def billOfMaterial = BillOfMaterial.get(params.id)
            result = domainService.delete(billOfMaterial)
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
