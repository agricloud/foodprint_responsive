package foodprint.authority.web

import foodprint.authority.*

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class RoleGroupController {

    def grailsApplication
    def domainService

    def enumService

    def i18nType

    def index = {

        def list = RoleGroup.createCriteria().list(params, params.criteria)
        def listDefault = RoleGroup.createCriteria().list(params, {
            isNull('siteGroup.id')
            like("name", "DEFAULT_%")
        })

        list.addAll(listDefault)

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }

    }

    def indexWithoutAuthority = {

        def list = RoleGroup.createCriteria().list(params, params.criteria)
        def listDefault = RoleGroup.createCriteria().list(params, {
            isNull('siteGroup.id')
            like("name", "DEFAULT_%")
            ne("name", "DEFAULT_AUTHORITY")
        })

        list.addAll(listDefault)

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }

    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def roleGroup = RoleGroup.get(params.id);

        if (roleGroup) {

            render (contentType: 'application/json') {
                [success: true, data: roleGroup]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        def roleGroup = new RoleGroup()
        render (contentType: 'application/json') {
            [success: true, data: roleGroup]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            log.debug "${controllerName}-${actionName}"

            if (params.name.startsWith('DEFAULT_')) {
                return ["請避免於開頭使用預設保留字 DEFAULT 命名"]//message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
            }
            def roleGroup = new RoleGroup(params)
            result = domainService.save(roleGroup)
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

            // 目前name一旦儲存不允許變更
            /*
            params.name = params.name.toUpperCase()

            if (!params.name.startsWith('SITEGROUP_'))
                params.name = "SITEGROUP_${params.name}"
            */

            def roleGroup = RoleGroup.get(params.id)

            // 系統預設權限群組不可變更
            if (roleGroup.name.startsWith('DEFAULT_')) {
                throw new DomainDataException("系統預設權限群組不可變更")//message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
            }

            if (params.name.startsWith('DEFAULT_')) {
                return ["請避免於開頭使用預設保留字 DEFAULT 命名"]//message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
            }

            roleGroup.properties = params
            result = domainService.save(roleGroup)
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
            def roleGroup = RoleGroup.get(params.id)
            if (roleGroup.name.startsWith('DEFAULT_')) {
                throw new DomainDataException("系統預設權限群組不可刪除")//message(code: "${i18nType}.default.message.save.success", args: [roleGroup])]
            }

            result = domainService.delete(roleGroup)
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
