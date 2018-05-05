package foodprint.erp.web

import foodprint.erp.SiteGroup

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class SiteGroupController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = SiteGroup.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def siteGroup = SiteGroup.get(params.id)
        if (siteGroup) {
            render (contentType: 'application/json') {
                [success: true, data: siteGroup]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    // def create = {
    //     def siteGroup = new SiteGroup()
    //     render (contentType: 'application/json') {
    //         [success: true, data: siteGroup]
    //     }
    // }

    // def save = {
    //     def siteGroup = new SiteGroup(params)
    //     render (contentType: 'application/json') {
    //         domainService.save(siteGroup)
    //     }
    // }

    @Transactional
    def update() {
        def result
        try {
            def siteGroup = SiteGroup.get(params.id)
            siteGroup.properties = params
            result = domainService.save(siteGroup)
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
