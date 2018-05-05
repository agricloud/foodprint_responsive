package foodprint.erp.web

import foodprint.erp.PurchaseReturnSheet
import foodprint.erp.PurchaseReturnSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class PurchaseReturnSheetDetController {

    def grailsApplication
    def domainService
    def inventoryDetailService

    def i18nType

    def index = {

        def returnSheet = PurchaseReturnSheet.get(params.header.id)

        if (returnSheet) {

            params.criteria = { eq('header', returnSheet) } >> params.criteria

            def list = PurchaseReturnSheetDet.createCriteria().list(params, params.criteria)
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

        def returnSheetDet = PurchaseReturnSheetDet.get(params.id);

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

            def returnSheetDet = new PurchaseReturnSheetDet(params)

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
                [success: false, message: message(code: "${i18nType}.purchaseReturnSheetDet.message.create.failed")]
            }
        }

    }

    @Transactional
    def save() {
        def result
        try {
            def returnSheetDet = new PurchaseReturnSheetDet(params)

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
            def newReturnSheetDet = new PurchaseReturnSheetDet(params)
            def oldReturnSheetDet = PurchaseReturnSheetDet.get(params.id)

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
            def returnSheetDet = PurchaseReturnSheetDet.get(params.id)
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
