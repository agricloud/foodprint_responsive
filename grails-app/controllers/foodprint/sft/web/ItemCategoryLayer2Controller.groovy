package foodprint.sft.web

import foodprint.sft.ItemCategoryLayer1
import foodprint.sft.ItemCategoryLayer2
import foodprint.erp.Item

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class ItemCategoryLayer2Controller {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def itemCategoryLayer1 = ItemCategoryLayer1.get(params.itemCategoryLayer1.id)

        if (itemCategoryLayer1) {

            params.criteria = { eq('itemCategoryLayer1', itemCategoryLayer1) } >> params.criteria

            def list = ItemCategoryLayer2.createCriteria().list(params, params.criteria)

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

        def itemCategoryLayer2 = ItemCategoryLayer2.get(params.id)
        if (itemCategoryLayer2) {
            render (contentType: 'application/json') {
                [success: true, data: itemCategoryLayer2]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (params.itemCategoryLayer1.id) {

            def itemCategoryLayer2 = new ItemCategoryLayer2(params)

            render (contentType: 'application/json') {
                [success: true, data: itemCategoryLayer2]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.itemCategoryLayer2.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def itemCategoryLayer2 = new ItemCategoryLayer2(params)
            result = domainService.save(itemCategoryLayer2)
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
            def  itemCategoryLayer2 = ItemCategoryLayer2.get(params.id)
            itemCategoryLayer2.properties = params
            result = domainService.save(itemCategoryLayer2)
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
            def itemCategoryLayer2 = ItemCategoryLayer2.get(params.id)
            result = domainService.delete(itemCategoryLayer2)

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
