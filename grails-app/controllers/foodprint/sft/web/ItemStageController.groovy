package foodprint.sft.web

import foodprint.sft.ItemStage
import foodprint.erp.Item

import foodprint.common.DomainDataException

import grails.transaction.Transactional
import grails.converters.JSON

class ItemStageController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def item = Item.get(params.item.id)

        if (item) {

            params.criteria = { eq('item', item) } >> params.criteria

            def list = ItemStage.createCriteria().list(params, params.criteria)

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

        def itemStage = ItemStage.get(params.id);
        if (itemStage) {

            render (contentType: 'application/json') {
                [success: true, data: itemStage]
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

            def itemStage = new ItemStage(params)

            if (itemStage.item.itemStages)
                itemStage.sequence = ((int)(itemStage.item.itemStages*.sequence.max()/10))*10+10
            else itemStage.sequence = 10

            render (contentType: 'application/json') {
                [success: true, data: itemStage]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.itemStage.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def itemStage = new ItemStage(params)
            result = domainService.save(itemStage)
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
            def itemStage = ItemStage.get(params.id)
            itemStage.properties = params
            result = domainService.save(itemStage)
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
            def itemStage = ItemStage.get(params.id)
            result = domainService.delete(itemStage)
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
