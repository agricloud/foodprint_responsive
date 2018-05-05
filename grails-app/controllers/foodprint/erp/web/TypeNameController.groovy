package foodprint.erp.web

import foodprint.erp.SheetType
import foodprint.erp.SheetType.Group
import foodprint.erp.SheetFormatType
import foodprint.erp.TransactionType
import foodprint.erp.TypeName

import foodprint.common.DomainDataException

import grails.transaction.Transactional
import grails.converters.JSON

class TypeNameController {

    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {
        def list = TypeName.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexBySheetTypeGroup = {
        def sheetTypeGroup = params.sheetTypeGroup as Group

        sheetTypeGroup.sheetTypes ?: SheetType.values()

        params.criteria = {
            'in'("sheetType", sheetTypeGroup.sheetTypes)
        } >> params.criteria

        def list = TypeName.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    // 所有單據之單別暫時共用維護介面，日後再依不同模組拆分。
    def indexNonInventorySheetTypeGroup = {

        params.criteria = {
            not {'in'("sheetType", Group.INVENTORY.sheetTypes)}
        } >> params.criteria

        def list = TypeName.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexBySheetTypes = {  

        def sheetTypes = params.sheetTypes instanceof String ? [params.sheetTypes] : params.sheetTypes as ArrayList

        sheetTypes.eachWithIndex { sheetType, index ->       
            sheetTypes[index] = sheetType as SheetType
        }

        params.criteria = {
            'in'("sheetType", sheetTypes)
        } >> params.criteria

        def list = TypeName.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def typeName = TypeName.read(params.id)
        if (typeName) {
            render (contentType: 'application/json') {
                [success: true, data: typeName]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        def typeName = new TypeName(sheetType: params.sheetType as SheetType, name: params.name, title: params.title, fullTitle: params.fullTitle,
                // sheetFormatType: params.sheetFormatType ? params.sheetFormatType as SheetFormatType : params.sheetFormatType,
                // sheetFormatType: params.sheetFormatType,
                yearDigit: params.yearDigit, runningDigit: params.runningDigit)

        typeName.sheetFormatType = params.sheetFormatType ? params.sheetFormatType as SheetFormatType : typeName.sheetFormatType
        typeName.transactionType = params.transactionType ? params.transactionType as TransactionType : typeName.getDefaultTransactionType()
        typeName.multiplier = typeName.getDefaultMultiplier()

        render (contentType: 'application/json') {
            [success: true, data: typeName]
        }
    }

    @Transactional
    def save() {
        def result
        try {

            def typeName = new TypeName(params)

            result = domainService.save(typeName)
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

            def newTypeName = new TypeName(params)
            def oldTypeName = TypeName.get(params.id)

            // sheetType一旦建立不允許變更
            if (newTypeName.sheetType && oldTypeName.sheetType!=newTypeName.sheetType) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.typeName.sheetType.cannot.change")]
                }
                return
            }

            // transactionType一旦建立不允許變更
            if (newTypeName.transactionType && oldTypeName.transactionType!=newTypeName.transactionType) {
                render (contentType: 'application/json') {
                    [success: false, message: message(code: "${i18nType}.typeName.transactionType.cannot.change")]
                }
                return
            }

            oldTypeName.properties = params
            result = domainService.save(oldTypeName)
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
            def typeName = TypeName.get(params.id)
            result = domainService.delete(typeName)

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
