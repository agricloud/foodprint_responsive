package foodprint.erp.api

import foodprint.erp.Item
import grails.transaction.Transactional

class ItemController {

    static namespace = 'api'

    def grailsApplication
    def domainService

    def index = {
        // def list = Item.createCriteria().list(params, params.criteria)
        // render (contentType: 'application/json') {
        //     [data: list, total: list.totalCount]
        // }
        render "hello api item"
    }


}
