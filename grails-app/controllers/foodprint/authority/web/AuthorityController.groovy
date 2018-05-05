package foodprint.authority.web

import foodprint.authority.*
import grails.transaction.Transactional

/*
 * 尚未使用
 */
class AuthorityController {

    def grailsApplication
    def domainService
    def springSecurityService

    def enumService

    def i18nType

    def indexRoleGroupTree = {

        // def user = params.id ? User.get(params.id) : springSecurityService.currentUser
        def user = User.get(1)
        def siteGroup = SiteGroup.get(params.siteGroup.id)

        def result = [children: findAllRoleGroupAndRole(user)]

        result.children.each { node ->
            if (node.className == "RoleGroup") {
                node.children = []
                node.children = findRoleByRoleGroup(RoleGroup.findByNameAndSiteGroup(node.name, siteGroup))
            }

        }

        render (contentType: 'application/json') {
            result
        }

    }

    def expand = {

        def childJson = []

        if (params.id == "root") {
            // def user = params.id ? User.get(params.id) : springSecurityService.currentUser
            def user = User.get(1)
        
            childJson = findAllRoleGroupAndRole(user)            
        }
        else {
            childJson = findRoleByRoleGroup(RoleGroup.get(params.id))
        }


        render (contentType: 'application/json') {
            childJson
        }
    }


    def findAllRoleGroupAndRole = { User user ->
        
        def result = []

        //default權限群組
        def roleGroups = RoleGroup.findAllBySiteGroupIsNullAndNameLike("DEFAULT_%")

        //siteGroup之權限群組
        def siteGroup = SiteGroup.get(user.siteGroup.id)
        roleGroups += RoleGroup.findAllBySiteGroupAndNameNotLike(siteGroup, "USER_%")
        
        //user權限群組
        roleGroups += RoleGroup.findAllBySiteGroupAndNameLike(siteGroup, "USER_${user.id}_%")

        roleGroups.each { roleGroup ->
            def node = [:]
                node.className = "RoleGroup"
                // node.objId = "rg-${roleGroup.id}"
                node.id = "rg-${roleGroup.id}"
                node.objId = roleGroup.id
                node.name = roleGroup.name
                node.title = roleGroup.title
            if (!RoleGroupRole.findByRoleGroup(roleGroup))
                node.children = []
            result << node
        }

        def roles = Role.findAll()
        roles.each { role ->
            def node = [:]
                node.className = "Role"
                // node.objId = "r-${role.id}"
                node.id = "r-${role.id}"
                node.objId = role.id
                node.authority = role.authority
                node.title = message(code: "${i18nType}.role.${role.authority}.label")
                node.leaf = true
            result << node
        }

        return result
    }

    def findRoleByRoleGroup = { RoleGroup roleGroup ->
        
        def result = []
        def roleGroupRoles = RoleGroupRole.findAllByRoleGroup(roleGroup)
        roleGroupRoles.each { roleGroupRole ->
            def node = [:]
                node.className = "Role"
                // node.objId = "r-${roleGroupRole.role.id}"
                node.id = "rgr-${roleGroupRole.role.id}"
                node.objId = roleGroupRole.role.id
                node.authority = roleGroupRole.role.authority
                node.title = message(code: "${i18nType}.role.${roleGroupRole.role.authority}.label")
                node.leaf = true
            result << node
        }
        return result
    }


}
