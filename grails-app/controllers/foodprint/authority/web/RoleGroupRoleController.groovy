package foodprint.authority.web

import foodprint.authority.*
import grails.transaction.Transactional

class RoleGroupRoleController {

    def grailsApplication
    def springSecurityService
    def sessionRegistry
    
    def domainService
    def enumService

    def i18nType

    def indexRolesByRoleGroup = {

        def roleGroup = RoleGroup.get(params.id)

        if (roleGroup) {

            params.criteria = { eq('roleGroup', roleGroup) } >> params.criteria

            def list = RoleGroupRole.createCriteria().list(params, params.criteria)*.role

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

    // @Transactional
    def updateRolesByRoleGroup() {

        def roleGroup = RoleGroup.get(params.id)

        if (roleGroup.name.startsWith('DEFAULT_')) {
            render (contentType: 'application/json') {
                [success: false, message: "系統預設權限群組不可變更"]//message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
            }
            return
        }

        def roles = RoleGroupRole.findAllByRoleGroup(roleGroup)*.role

        params.roles = params.roles.tokenize(',')

        def cloneRoles = roles.collect()

        cloneRoles.each { role ->
            def found = false
            def id = role.id.toString()
            id in params.roles ? found = true && params.roles.remove(id) : RoleGroupRole.remove(roleGroup, role)
        }

        cloneRoles = roles.collect()
        params.roles.each { id ->
            def found = false
            id.toLong() in cloneRoles*.id ? found = true : RoleGroupRole.create(roleGroup, Role.get(id))
        }

        // 若登入中的 user 有此 roleGroup 權限則強制登出 user
        sessionRegistry.getAllPrincipals().each { principal ->
            if (UserRoleGroup.findByUserAndRoleGroup(User.get(principal.id), roleGroup)) {
                sessionRegistry.getAllSessions(principal, false).each { ss ->
                    ss.expireNow()
                }
            }
        }

        render (contentType: 'application/json') {
            [success: true, message: message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
        }

    }

}
