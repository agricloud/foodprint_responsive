 package foodprint.sft.web

import foodprint.sft.ItemCategoryLayer1
import foodprint.erp.Item

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class ItemCategoryLayer1Controller {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = ItemCategoryLayer1.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def itemCategoryLayer1 = ItemCategoryLayer1.get(params.id)
        if (itemCategoryLayer1) {
            render (contentType: 'application/json') {
                [success: true, data: itemCategoryLayer1]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def itemCategoryLayer1 = new ItemCategoryLayer1()
        render (contentType: 'application/json') {
            [success: true, data: itemCategoryLayer1]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def itemCategoryLayer1 = new ItemCategoryLayer1(params)
            result = domainService.save(itemCategoryLayer1)
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
            def itemCategoryLayer1 = ItemCategoryLayer1.get(params.id)
            itemCategoryLayer1.properties = params
            result = domainService.save(itemCategoryLayer1)
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
            def itemCategoryLayer1 = ItemCategoryLayer1.get(params.id)
            result = domainService.delete(itemCategoryLayer1)

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
