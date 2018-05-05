package foodprint.authority.web

import foodprint.authority.*
import grails.transaction.Transactional

class RoleController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService

    def enumService

    def i18nType

    def index = {

        def list = Role.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def role = Role.get(params.id);

        if (role) {

            render (contentType: 'application/json') {
                [success: true, data: role]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
}
