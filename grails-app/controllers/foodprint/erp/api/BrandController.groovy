package foodprint.erp.api

import foodprint.erp.Brand
import grails.transaction.Transactional

class BrandController {

    static namespace = 'api'

    def grailsApplication
    def domainService

    def index = {
        // def list = Brand.createCriteria().list(params, params.criteria)
        // render (contentType: 'application/json') {
        //     [data: list, total: list.totalCount]
        // }
        render "hello api brand"
    }

}
