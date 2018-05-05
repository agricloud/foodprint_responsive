package foodprint.erp.web

import foodprint.erp.StockInSheet
import foodprint.erp.StockInSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional


class StockInSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService
    def stockInSheetDetService

    def i18nType

    def index = {

        def sheet = StockInSheet.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = StockInSheetDet.createCriteria().list(params, params.criteria)

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

        def sheetDet = StockInSheetDet.get(params.id);

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
            result = stockInSheetDetService.create(params)
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
            def sheetDet = new StockInSheetDet(params)
            result = stockInSheetDetService.save(sheetDet, params, params.site.id, params)
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

            def newSheetDet = new StockInSheetDet(params)
            def oldSheetDet = StockInSheetDet.get(params.id)

            newSheetDet.batch = batchService.findOrCreateBatchInstanceByJson(params, newSheetDet, params.site.id, params).data
            params.batch.id = newSheetDet.batch.id
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
            def sheetDet = StockInSheetDet.get(params.id)
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
