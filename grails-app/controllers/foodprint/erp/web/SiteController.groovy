package foodprint.erp.web

import foodprint.erp.Site
import foodprint.authority.User
import foodprint.authority.UserType
import foodprint.authority.UserSite
import grails.transaction.Transactional
import grails.util.Environment

import foodprint.common.DomainDataException

class SiteController {

    def grailsApplication
    def domainService
    def springSecurityService
    def foodauthService
    def defaultDataService

    def i18nType

    def index = {
        def list = Site.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def site = Site.get(params.id);
        if (site) {
            render (contentType: 'application/json') {
                [success: true, data: site]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def site = new Site()

        render (contentType: 'application/json') {
            [success: true, data: site]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            // validate license
            if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {

                def validateResult = foodauthService.validateSiteLicense(params.activationCode, grailsApplication.config.grails.deviceCode)

                if (!validateResult.success) {
                    render (contentType: 'application/json') {
                        validateResult
                    }
                    return
                }
            }

            def site = new Site(params)

            def currentUser = springSecurityService?.currentUser

            site.siteGroup = currentUser?.siteGroup ?: site.siteGroup

            result = domainService.save(site)


            defaultDataService.generateParamData(site)

            def users = User.findAllByUserTypeAndSiteGroup(UserType.ADMIN, site.siteGroup)
            users.each { user ->
                UserSite.create(user, site) ?:
                    log.error("There is something wrong when dispatch site to user ${user.id}...")
            }
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }
        catch (e) {
            log.error e
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def update() {
        def result
        try {
        
            def site = Site.get(params.id)
            
            // validate licenese
            if (params.activationCode && site.activationCode!=params.activationCode) {
                if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
                    def validateResult = foodauthService.validateSiteLicense(params.activationCode, grailsApplication.config.grails.deviceCode)
                    if (!validateResult.success) {
                        render (contentType: 'application/json') {
                            validateResult
                        }
                        return
                    }
                }
            }

            site.properties = params

            if (site.dirtyPropertyNames.size == 0) {
                site.discard()
                result = [success: true, message: message(code: "${i18nType}.default.nothing.changed")]
            }
            else {
                
                result = domainService.save(site)
            }
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
        def site = Site.get(params.id)
        try {
            defaultDataService.deleteParamData(site)
            result = domainService.delete(site)
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
