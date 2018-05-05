package foodprint.erp.web

import foodprint.erp.SaleReturnSheet
import foodprint.erp.SaleReturnSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class SaleReturnSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def i18nType

    def index = {

        def returnSheet = SaleReturnSheet.get(params.header.id)

        if (returnSheet) {

            params.criteria = { eq('header', returnSheet) } >> params.criteria

            def list = SaleReturnSheetDet.createCriteria().list(params, params.criteria)
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


    def show = {

        def returnSheetDet = SaleReturnSheetDet.get(params.id);


        if (returnSheetDet) {

            render (contentType: 'application/json') {
                [success: true, data: returnSheetDet]
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

            def returnSheetDet = new SaleReturnSheetDet(params)

            returnSheetDet.typeName = returnSheetDet.header.typeName
            returnSheetDet.name = returnSheetDet.header.name

            if (returnSheetDet.header.details)
                returnSheetDet.sequence = returnSheetDet.header.details*.sequence.max()+1
            else returnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true, data: returnSheetDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.saleReturnSheetDet.message.create.failed")]
            }
        }

    }

    @Transactional
    def save() {
        def result
        try {
            def returnSheetDet = new SaleReturnSheetDet(params)

            result = domainService.save(returnSheetDet)

            inventoryDetailService.processInventoryByCreate(returnSheetDet, params.site.id, params)
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
            def newReturnSheetDet = new SaleReturnSheetDet(params)
            def oldReturnSheetDet = SaleReturnSheetDet.get(params.id)

            inventoryDetailService.processInventoryByUpdate(oldReturnSheetDet, newReturnSheetDet, params.site.id, params)

            oldReturnSheetDet.properties = params
            result = domainService.save(oldReturnSheetDet)
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
            def returnSheetDet = SaleReturnSheetDet.get(params.id)
            inventoryDetailService.processInventoryByDelete(returnSheetDet, params.site.id, params)
            result = domainService.delete(returnSheetDet)
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
