package foodprint.authority.web

import foodprint.authority.*
import foodprint.erp.Site
import grails.transaction.Transactional
import grails.util.Environment

class UserSiteController {

    def grailsApplication
    def domainService
    def springSecurityService
    def sessionRegistry

    def i18nType

    def indexSitesByUser = {

        def user = params.id ? User.get(params.id) : springSecurityService.currentUser

        if (user) {

            params.criteria = { eq('user', user) } >> params.criteria

            def list = UserSite.createCriteria().list(params, params.criteria)*.site

            if (list) {
                render (contentType: 'application/json') {
                    [data: list, total: list.size()]
                }
            }
            else {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.user.userSites.null")]
                }
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.failed")]
            }
        }

    }

    @Transactional
    def updateSitesByUser() {
        
        def user = User.get(params.id)

        def sites = UserSite.findAllByUser(user)*.site

        params.sites = params.sites.tokenize(',')

        if (user.userType == UserType.USER && params.sites.size() > 1) {
            render (contentType: 'application/json') {
                [success: false, message: "一般使用者可管理農場不可超過一筆"]//message(code: "${i18nType}.default.message.save.success", args: [user])]
            }
            return
        }

        def cloneSites = sites.collect()        

        // 若 params.sites 中的 site 已被指派過，則從 params.sites 中移除。
        cloneSites.each { site ->
            def found = false
            def id = site.id.toString()
            id in params.sites ? found = true && params.sites.remove(id) : UserSite.remove(user, site)
        }

        cloneSites = sites.collect()

        // 若 params.sites 中的 site 未被指派過，則新增 UserSite。
        params.sites.each { id ->
            def found = false
            (id.toLong() in cloneSites*.id && user.siteGroup == Site.get(id).siteGroup) ? found = true : UserSite.create(user, Site.get(id))
        }

        // 若是使用者目前正在登入的公司被移除，則將 lastLoginSite 設置為 null。
        if (user.lastLoginSite && !UserSite.findByUserAndSite(user, user.lastLoginSite)) {
            user.lastLoginSite = null
            domainService.save(user)
            
            // 若是使用者目前正在登入的公司被移除，則強制登出。
            sessionRegistry.getAllPrincipals().find { principal ->
                if (principal.getUsername() == user.username) {
                    sessionRegistry.getAllSessions(principal, false).each { ss ->
                        ss.expireNow()
                    }
                    return true // break
                }
                return false // keep looping
            }
        }

        if (user.userType == UserType.USER) {
            user.lastLoginSite = Site.get(params.sites[0])
            domainService.save(user)
        }
    

        render (contentType: 'application/json') {
            [success: true, message: message(code: "${i18nType}.default.message.save.success", args: [user])]
        }

    }
}
