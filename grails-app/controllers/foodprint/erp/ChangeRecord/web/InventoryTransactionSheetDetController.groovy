package foodprint.erp.ChangeRecord.web

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.ChangeOrder.InventoryTransactionSheet
import foodprint.erp.ChangeOrder.InventoryTransactionSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class InventoryTransactionSheetDetController {

    def grailsApplication
    def domainService
    def enumService
    def batchService
    def inventoryDetailService

    def i18nType

    def index = {

        def sheet = InventoryTransactionSheet.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = InventoryTransactionSheetDet.createCriteria().list(params, params.criteria)
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

        def sheetDet = InventoryTransactionSheetDet.read(params.id)

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

            def sheetDet = new InventoryTransactionSheetDet(params)

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
                [success: false, message: message(code: "${i18nType}.inventoryTransactionSheetDet.message.create.failed")]
            }
        }

    }

    @Transactional
    def save() {

        def result

        try {
            def sheetDet = new InventoryTransactionSheetDet(params)

            sheetDet.batch = batchService.findOrCreateBatchInstanceByJson(params, sheetDet, params.site.id, params).data
            result = inventoryDetailService.processInventoryByCreate(sheetDet, params.site.id, params)
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
            def newSheetDet = new InventoryTransactionSheetDet(params)
            def oldSheetDet = InventoryTransactionSheetDet.get(params.id)
            
            newSheetDet.batch = batchService.findOrCreateBatchInstanceByJson(params, newSheetDet, params.site.id, params).data
            params.batch.id = newSheetDet.batch.id
            inventoryDetailService.processInventoryByUpdate(oldSheetDet, newSheetDet, params.site.id, params)

            oldSheetDet.properties = params

            result = domainService.save(oldSheetDet)

        }
        catch(DomainDataException e) {
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
            def sheetDet = InventoryTransactionSheetDet.get(params.id)

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
