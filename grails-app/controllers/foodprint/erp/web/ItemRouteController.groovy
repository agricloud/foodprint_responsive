package foodprint.erp.web

import foodprint.erp.*

import foodprint.common.DomainDataException

import grails.transaction.Transactional
import grails.converters.JSON

class ItemRouteController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def item = Item.get(params.item.id)

        if (item) {

            params.criteria = { eq('item', item) } >> params.criteria

            def list = ItemRoute.createCriteria().list(params, params.criteria)

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

        def itemRoute = ItemRoute.get(params.id);
        if (itemRoute) {

            render (contentType: 'application/json') {
                [success: true, data: itemRoute]
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

            def itemRoute = new ItemRoute(params)

            if (itemRoute.item.itemRoutes)
                itemRoute.sequence = ((int)(itemRoute.item.itemRoutes*.sequence.max()/10))*10+10
            else itemRoute.sequence = 10

            render (contentType: 'application/json') {
                [success: true, data: itemRoute]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.itemRoute.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def itemRoute = new ItemRoute(params)
            result = domainService.save(itemRoute)
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
            def itemRoute = ItemRoute.get(params.id)
            itemRoute.properties = params
            result = domainService.save(itemRoute)
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
            def itemRoute = ItemRoute.get(params.id)
            result = domainService.delete(itemRoute)
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
