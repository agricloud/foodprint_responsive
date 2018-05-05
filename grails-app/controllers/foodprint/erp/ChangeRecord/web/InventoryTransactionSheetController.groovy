package foodprint.erp.ChangeRecord.web

import grails.converters.JSON
import grails.transaction.Transactional

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.ChangeOrder.InventoryTransactionSheet

import foodprint.common.DomainDataException

class InventoryTransactionSheetController {

    def grailsApplication
    def domainService
    def sheetService

    def i18nType

    def index = {

        def list = InventoryTransactionSheet.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        def sheet = InventoryTransactionSheet.read(params.id)

        if (sheet) {
            render (contentType: 'application/json') {
                [success: true, data: sheet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        if (params.typeName.id) {
            def sheet = new InventoryTransactionSheet(params)
            sheet.name = sheetService.generateSheetNameByTypeName(sheet, params.site.id, params.timeZoneId)

            render (contentType: 'application/json') {
                [success: true, data: sheet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.sheet.create.failed")]
            }
        }
    }

    @Transactional
    def save() {

        def result
        
        try {
            def sheet = new InventoryTransactionSheet(params)
            result = sheetService.validateSheetNameByTypeName(/*Site.get(params.site.id), */sheet, params.timeZoneId)
            result = domainService.save(sheet)

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
            def sheet =  InventoryTransactionSheet.get(params.id)
            sheet.properties = params
            result = domainService.save(sheet)
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
            def sheet = InventoryTransactionSheet.get(params.id)
            result = domainService.delete(sheet)
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
