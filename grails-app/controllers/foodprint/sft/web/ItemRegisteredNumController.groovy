package foodprint.sft.web

import foodprint.sft.ItemRegisteredNum
import foodprint.erp.Item

import foodprint.common.DomainDataException

import org.hibernate.criterion.CriteriaSpecification
import grails.transaction.Transactional

class ItemRegisteredNumController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def item = Item.get(params.item.id)

        if (item) {

            params.criteria = { eq('item', item) } >> params.criteria

            params.criteria = params.criteria << {
                createAlias('manufacturer', 'manufacturer', CriteriaSpecification.LEFT_JOIN)
            }

            def list = ItemRegisteredNum.createCriteria().list(params, params.criteria)

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

        def itemRegisteredNum = ItemRegisteredNum.get(params.id)
        if (itemRegisteredNum) {
            render (contentType: 'application/json') {
                [success: true, data: itemRegisteredNum]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (params.item.id) {

            def itemRegisteredNum = new ItemRegisteredNum(params)

            render (contentType: 'application/json') {
                [success: true, data: itemRegisteredNum]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.itemRegisteredNum.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {

            def itemRegisteredNum = new ItemRegisteredNum(params)
            result = domainService.save(itemRegisteredNum)
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
            def itemRegisteredNum = ItemRegisteredNum.get(params.id)
            itemRegisteredNum.properties = params
            result = domainService.save(itemRegisteredNum)
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
            def itemRegisteredNum = ItemRegisteredNum.get(params.id)
            result = domainService.delete(itemRegisteredNum)
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
