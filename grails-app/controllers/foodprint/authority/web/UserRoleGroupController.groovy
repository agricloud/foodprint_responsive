package foodprint.authority.web

import foodprint.authority.*
import grails.transaction.Transactional
import grails.converters.JSON

class UserRoleGroupController {

    def grailsApplication
    def springSecurityService
    def sessionRegistry
    
    def domainService
    def enumService

    def i18nType

    def indexRoleGroupsByUser = {

        def user = params.id ? User.get(params.id) : springSecurityService.currentUser

        if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
            def pass = false
            def roles = springSecurityService.getPrincipal().getAuthorities()
            roles.find {
                if ((user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSERROLEGROUP_MAINTAIN") ||
                    (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSERROLEGROUP_MAINTAIN")) {
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

        if (user) {

            params.criteria = { eq('user', user) } >> params.criteria

            def list = UserRoleGroup.createCriteria().list(params, params.criteria)*.roleGroup

            render (contentType: 'application/json') {
                [success: true, data: list, total: list.size()]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }

    }


    @Transactional
    def updateRoleGroupsByUser() {

        def user = User.get(params.id)

        if (grailsApplication.config.grails.useAuthority) {
            // 確認使用者是否有權限執行此功能
            def pass = false
            def roles = springSecurityService.getPrincipal().getAuthorities()
            roles.find {
                if ((user.userType==UserType.ADMIN && it.getAuthority()=="ROLE_ADMINUSERROLEGROUP_MAINTAIN") ||
                    (user.userType==UserType.USER && it.getAuthority()=="ROLE_USERUSERROLEGROUP_MAINTAIN")) {
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

        def roleGroups = UserRoleGroup.findAllByUser(user)*.roleGroup

        def extraMsg = ""

        params.roleGroups = params.roleGroups.tokenize(',')

        def cloneRoleGroups = roleGroups.collect()        

        cloneRoleGroups.each { roleGroup ->
            def found = false
            def id = roleGroup.id.toString()
            id in params.roleGroups ? found = true && params.roleGroups.remove(id) : UserRoleGroup.remove(user, roleGroup)
        }

        cloneRoleGroups = roleGroups.collect()
        params.roleGroups.each { id ->
            if (user.userType == UserType.USER && RoleGroup.get(id).name == "DEFAULT_AUTHORITY") {
                log.info message(code: "${i18nType}.userRoleGroup.userUser.cannot.own.authority.roleGroup")
                extraMsg = "<br>" + message(code: "${i18nType}.userRoleGroup.userUser.cannot.own.authority.roleGroup")
            }
            else {
                def found = false
                id.toLong() in cloneRoleGroups*.id ? found = true : UserRoleGroup.create(user, RoleGroup.get(id))
            }
        }

        // 變更自身權限會自動更新，變更他人權限，會強制將對方登出。
        if (user == springSecurityService.currentUser) {
            // Flushing the Cached Authentication 刷新緩存權限，如果對其他使用者做 reauthenticate，會使當前登入的使用者切換爲其他使用者。
            springSecurityService.reauthenticate user.username
        }
        else {
            // 強制登出 user
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

        render (contentType: 'application/json') {
            [success: true, message: message(code: "${i18nType}.default.message.save.success", args: [user]) + extraMsg]
        }

    }

}
