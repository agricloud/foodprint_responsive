package foodprint.erp.web

import foodprint.erp.PurchaseSheet
import foodprint.erp.PurchaseSheetDet
import foodprint.erp.PurchaseReturnSheetDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class PurchaseSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def i18nType

    def index = {

        def sheet = PurchaseSheet.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = PurchaseSheetDet.createCriteria().list(params, params.criteria)
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

        def sheetDet = PurchaseSheetDet.get(params.id);

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

            def sheetDet = new PurchaseSheetDet(params)

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
                [success: false, message: message(code: "${i18nType}.purchaseSheetDet.message.create.failed")]
            }
        }

    }

    @Transactional
    def save() {

        def result
        
        try {

            // 總額不可手動更新
            if (params.totalPrice) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.sheet.totalPrice.cannot.change")]
                }
                return
            }

            def sheetDet = new PurchaseSheetDet(params)

            sheetDet.batch = batchService.findOrCreateBatchInstanceByJson(params, sheetDet, params.site.id, params).data
            
            inventoryDetailService.processInventoryByCreate(sheetDet, params.site.id, params)
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

            def newSheetDet = new PurchaseSheetDet(params)
            def oldSheetDet = PurchaseSheetDet.get(params.id)

            // 退單已存在不允許更新
            if (PurchaseReturnSheetDet.findByPurchaseSheetDet(oldSheetDet)) {
                throw new DomainDataException(message(code: "${i18nType}.sheetDetail.returnSheetDetail.exists.cannot.update"))
            }

            // 總額不可手動更新
            if (params.totalPrice) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.sheet.totalPrice.cannot.change")]
                }
                return
            }

            newSheetDet.batch = batchService.findOrCreateBatchInstanceByJson(params, newSheetDet, params.site.id, params).data
            params.batch.id = newSheetDet.batch.id
            inventoryDetailService.processInventoryByUpdate(oldSheetDet, newSheetDet, params.site.id, params)

            // // 重新計算單身進貨總額、單頭進貨總額
            // if (oldSheetDet.qty!=newSheetDet.qty || oldSheetDet.price!=newSheetDet.price) {
            //     oldSheetDet.header.totalPrice-=oldSheetDet.totalPrice
            //     oldSheetDet.totalPrice = newSheetDet.qty*newSheetDet.price
            //     oldSheetDet.header.totalPrice+=oldSheetDet.totalPrice
            // }

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
            def sheetDet = PurchaseSheetDet.get(params.id)
            
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
