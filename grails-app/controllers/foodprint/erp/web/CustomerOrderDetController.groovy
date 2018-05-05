package foodprint.erp.web

import foodprint.erp.CustomerOrder
import foodprint.erp.CustomerOrderDet
import foodprint.erp.SaleSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class CustomerOrderDetController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def sheet = CustomerOrder.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = CustomerOrderDet.createCriteria().list(params, params.criteria)

            render (contentType: 'application/json') {
                [data: list, total: list.totalCount]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }

    }

    def indexByConvertToDeliveryKanban = {
        def i18nType = grailsApplication.config.grails.i18nType
        
        def sheet = CustomerOrder.get(params.header.id)

        params.criteria = { 
            eq('convertToDeliveryKanban', false)
            eq('header', sheet)
         } >> params.criteria

        def list = CustomerOrderDet.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def sheetDet = CustomerOrderDet.get(params.id)

        if (sheetDet) {

            render (contentType: 'application/json') {
                [success: true, data: sheetDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }


    def create = {

        if (params.header.id) {

            def sheetDet = new CustomerOrderDet(params)

            sheetDet.typeName = sheetDet.header.typeName
            sheetDet.name = sheetDet.header.name

            if (sheetDet.header.details)
                sheetDet.sequence = sheetDet.header.details*.sequence.max()+1
            else sheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true, data: sheetDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.customerOrderDet.message.create.failed")]
            }
        }

    }

    @Transactional
    def save() {
        def result
        try {
            def sheetDet = new CustomerOrderDet(params)
            result = domainService.save(sheetDet)
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
            def newSheetDet = new CustomerOrderDet(params)
            def oldSheetDet = CustomerOrderDet.get(params.id)

            if (SaleSheetDet.findByCustomerOrderDet(oldSheetDet)) {
                throw new DomainDataException(message(code: "${i18nType}.customerOrderDet.isForeignKey.in.saleSheetDet.cannot.update", args: [oldSheetDet]))
            }

            oldSheetDet.properties = params
            result = domainService.save(oldSheetDet)

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
            def sheetDet = CustomerOrderDet.get(params.id)
            result = domainService.delete(sheetDet)

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
