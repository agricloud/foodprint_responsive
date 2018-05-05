package foodprint.sft.web.pull

import foodprint.common.Country
import foodprint.sft.pull.*
import foodprint.erp.*

import grails.converters.JSON
import grails.transaction.Transactional

class BatchBoxDetController {

    def grailsApplication
    def domainService
    def dateService
    def batchBoxDetService
    
    def index = {

        def list = BatchBoxDet.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByItem = {
        def site = Site.get(params.site.id)
        def item = Item.get(params.item.id)
        def batchBox = BatchBox.findByItemAndSite(item, site)
        params.criteria = {
                eq('batchBox', batchBox)
                eq('status', BatchFormStatus.PENDING)
        } >> params.criteria

        def list = BatchBoxDet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        log.info "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType

        def batchBoxDet = BatchBoxDet.get(params.id);

        if (batchBoxDet) {

            render (contentType: 'application/json') {
                [success: true, data: batchBoxDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def batchBoxDet = new BatchBoxDet()
        render (contentType: 'application/json') {
            [success: true, data: batchBoxDet]
        }
    }


    def save = {

        def i18nType = grailsApplication.config.grails.i18nType

        def site = Site.get(params.site.id)

        def batchBoxDet = new BatchBoxDet(params)

        render (contentType: 'application/json') {
            domainService.save(batchBoxDet)
        }
    }

    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def batchBoxDet = BatchBoxDet.get(params.id)

        render (contentType: 'application/json') {
            domainService.save(batchBoxDet)
        }
    }

}
