package foodprint.erp.web

import foodprint.erp.SaleSheet
import foodprint.erp.SaleSheetDet
import foodprint.erp.SaleReturnSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class SaleSheetDetController {

    def grailsApplication
    def domainService
    def inventoryDetailService
    def saleSheetDetService

    def i18nType

    def index = {

        def sheet = SaleSheet.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = SaleSheetDet.createCriteria().list(params, params.criteria)
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

        def sheetDet = SaleSheetDet.get(params.id)


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
        def result
        try {
            result = saleSheetDetService.create(params)
        }
        catch (DomainDataException e) {
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def save() {
        def result

        try {
            def sheetDet = new SaleSheetDet(params)
            result = saleSheetDetService.save(sheetDet, params.site.id, params)
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

            def newSheetDet = new SaleSheetDet(params)
            def oldSheetDet = SaleSheetDet.get(params.id)

            // 退單已存在不允許更新
            if (SaleReturnSheetDet.findBySaleSheetDet(oldSheetDet)) {
                throw new DomainDataException(message(code: "${i18nType}.sheetDetail.returnSheetDetail.exists.cannot.update"))
            }

            inventoryDetailService.processInventoryByUpdate(oldSheetDet, newSheetDet, params.site.id, params)

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
            def sheetDet = SaleSheetDet.get(params.id)
            inventoryDetailService.processInventoryByDelete(sheetDet, params.site.id, params)
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
