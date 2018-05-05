package foodprint.authority.web

import foodprint.authority.User
import foodprint.authority.UserType
import foodprint.authority.UserSite
import foodprint.authority.RoleGroup
import foodprint.authority.UserRoleGroup
import foodprint.erp.SiteGroup
import foodprint.erp.Site
import foodprint.erp.Workstation

import foodprint.common.DomainDataException

import grails.transaction.Transactional
import grails.util.Environment
import grails.converters.JSON

class UserController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService
    def springSecurityService
    def foodauthService

    def i18nType

    def index = {

        def list = User.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByUserType = {

        def userType = params.userType as UserType

        if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
            def pass = false
            def roles = springSecurityService.getPrincipal().getAuthorities()
            roles.find {
                if ((userType==UserType.ADMIN && (it.getAuthority()=="ROLE_ADMINUSER_MAINTAIN" || it.getAuthority()=="ROLE_ADMINUSERSITES_MAINTAIN" || it.getAuthority()=="ROLE_ADMINUSERROLEGROUP_MAINTAIN")) ||
                    (userType==UserType.USER && (it.getAuthority()=="ROLE_USERUSER_MAINTAIN" || it.getAuthority()=="ROLE_USERUSERSITES_MAINTAIN" || it.getAuthority()=="ROLE_USERUSERROLEGROUP_MAINTAIN"))) {
                    pass = true
                    return true // break
                }
                return false // keep looping
            }

            if (!pass) {
                render(status: 403, text: [success: false, error: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
                return
            }
        }

        params.criteria = { eq('userType', userType) } >> params.criteria

        def list = User.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }
    @Transactional
    def switchSite() {
        def result
        try {

            def user = springSecurityService.currentUser

            if (user.siteGroup != Site.get(params.id).siteGroup) {
                throw new DomainDataException("使用者與農場不屬於同一群組")
            }
            if (!UserSite.findByUserAndSite(user, Site.get(params.id))) {
                throw new DomainDataException("沒有管理此農場之權限")
            }
            
            user.lastLoginSite = Site.get(params.id)

            if (user.isDirty('lastLoginSite')) {
                result = domainService.save(user)
            }
            else throw new DomainDataException("Site is not changed.")
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
    def switchWorkstation() {

        def result
        try {

            def user = springSecurityService.currentUser

            user.lastLoginWorkstation = Workstation.get(params.id)

            if (user.lastLoginSite != user.lastLoginWorkstation.site) {
                throw new DomainDataException("所選工作站不屬於該公司")
            }

            if (user.isDirty('lastLoginWorkstation')) {
                result = domainService.save(user)
            }
            else throw new DomainDataException("Workstation is not changed.")
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
    def switchPanel() {

        def result
        try {

            def user = springSecurityService.currentUser
            user.lastLoginPanel = params.name

            if (user.isDirty('lastLoginPanel')) {
                result = domainService.save(user)
            }
            else throw new DomainDataException("Panel is not changed.")

        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }

    }

    def show = {

        def user = User.get(params.id)

        if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
            def pass = false
            def roles = springSecurityService.getPrincipal().getAuthorities()
            roles.find {
                if (it.getAuthority()=="ROLE_USER_MAINTAIN" ||
                    (user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSER_MAINTAIN") ||
                    (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSER_MAINTAIN")) {
                    pass = true
                    return true // break
                }
                return false // keep looping
            }

            if (!pass) {
                render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
                return
            }
        }

        if (user) {
            render (contentType: 'application/json') {
                [success: true, data: user]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def user = new User()

        render (contentType: 'application/json') {
            [success: true, data: user]
        }
    }

    @Transactional
    def save() {
        def result
        try{

            def user = new User(params)

            def validateResult

            if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
                validateResult = foodauthService.validateUser(params.activationCode, "NEW")

                if (!validateResult.success) {
                    render (contentType: 'application/json') {
                        validateResult
                    }
                    return
                }

                user.userType = validateResult.userType as UserType
            }
            else if (Environment.current == Environment.DEVELOPMENT) {
                if (springSecurityService.currentUser) {
                    if (params.activationCode == 'aaaa')
                        user.userType = UserType.ADMIN
                    else
                        user.userType = UserType.USER
                }
                else { //註冊時
                    user.userType = UserType.ADMIN
                }
            }
            if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
                def pass = false
                def roles = springSecurityService.getPrincipal().getAuthorities()
                roles.find {
                    if ((user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ANONYMOUS") || //for 註冊
                        (user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSER_MAINTAIN") ||
                        (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSER_MAINTAIN")) {
                        pass = true
                        return true // break
                    }
                    return false // keep looping
                }

                if (!pass) {
                    throw new DomainDataException(message(code: "${i18nType}.springSecurity.errors.access.denied"))
                }
            }

            def siteGroup 

            if (springSecurityService.currentUser) {
                siteGroup = springSecurityService.currentUser.siteGroup
            }
            else {
                // 註冊時，必須使用 ADMIN activation code
                if (user.userType != UserType.ADMIN) {
                    throw new DomainDataException("User activation code cannot be used for register.")
                }

                siteGroup = new SiteGroup()
                siteGroup.title = siteGroup.name
                domainService.save(siteGroup)
            }

            user.siteGroup = siteGroup

            result = domainService.save(user)

            // 使用者為 ADMIN，預設加入同 group 之所有農場
            if (user.userType == UserType.ADMIN) {
                siteGroup.sites.each { site ->
                    UserSite.create(user, site)
                }
                
                RoleGroup.findAllByNameLikeAndSiteGroup("DEFAULT_%", null).each { roleGroup ->
                    UserRoleGroup.create(user, roleGroup)
                }
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
    def update() {

        def result
        try {
            def user = User.get(params.id)

            if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
                def pass = false
                def roles = springSecurityService.getPrincipal().getAuthorities()
                roles.find {
                    if (it.getAuthority()=="ROLE_USER_MAINTAIN" ||
                        (user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSER_MAINTAIN") ||
                        (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSER_MAINTAIN")) {
                        pass = true
                        return true // break
                    }
                    return false // keep looping
                }

                if (!pass) {
                    throw new DomainDataException(message(code: "${i18nType}.springSecurity.errors.access.denied"))
                }
            }

            user.properties = params

            // validate licenese
            if (user.isDirty('activationCode')) {
                
                if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
                    def userCount = User.findAllByActivationCode(params.activationCode).size()
                    def validateResult = foodauthService.validateUser(params.activationCode, "NEW", userCount)
                    if (!validateResult.success) {
                        render (contentType: 'application/json') {
                            validateResult
                        }
                        return
                    }

                    user.userType = validateResult.userType as UserType

                }
                else if (Environment.current == Environment.DEVELOPMENT) {
                    if (params.activationCode == 'aaaa')
                        user.userType = UserType.ADMIN
                    else
                        user.userType = UserType.USER
                }

                if (user.userType == UserType.USER && user.userSites.size() > 1) {
                    UserSite.removeAll(user)
                    if (user.lastLoginSite)
                        UserSite.create(user, user.lastLoginSite)
                }
            }

            result = domainService.save(user)
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
            def user = User.get(params.id)

            if (grailsApplication.config.grails.useAuthority) {
                // 確認使用者是否有權限執行此功能
                def pass = false
                def roles = springSecurityService.getPrincipal().getAuthorities()
                roles.find {
                    if ((user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSER_MAINTAIN") ||
                        (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSER_MAINTAIN")) {
                        pass = true
                        return true // break
                    }
                    return false // keep looping
                }

                if (!pass) {
                    throw new DomainDataException(message(code: "${i18nType}.springSecurity.errors.access.denied"))
                }
            }
            result = domainService.delete(user)

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
