package foodprint.erp.web

import foodprint.erp.Item

import foodprint.common.DomainDataException
import foodprint.sft.WorkFlowType

import grails.transaction.Transactional

class ItemController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = Item.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWorkFlowType = {
        params.criteria = { 
            or{
                eq('workFlowType', WorkFlowType.PULL) 
                eq('workFlowType', WorkFlowType.COMBINE) 
            }
        } >> params.criteria

        def list = Item.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }
    def show = {

        def item = Item.get(params.id)
        if (item) {
            render (contentType: 'application/json') {
                [success: true, data: item]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def item = new Item()
        render (contentType: 'application/json') {
            [success: true, data: item]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def item = new Item(params)
            result = domainService.save(item)
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
            def item = Item.get(params.id)
            item.properties = params
            result = domainService.save(item)
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
            def item = Item.get(params.id)
            result = domainService.delete(item)
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
