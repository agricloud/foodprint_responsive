package foodprint.erp

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class SaleSheetService {

    def grailsApplication
    def domainService
    def sheetService

    def i18nType

    def create = { sheetParams, siteId, String timeZoneId ->
        if (sheetParams.typeName.id) {
            def sheet = new SaleSheet(sheetParams)
            sheet.name = sheetService.generateSheetNameByTypeName(sheet, siteId, timeZoneId)

            return [success: true, data: sheet]
        }
        else throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.create.failed"))
    }

    @Transactional
    def save(SaleSheet sheet, String timeZoneId) {
        def result
        sheetService.validateSheetNameByTypeName(sheet, timeZoneId)
        result = domainService.save(sheet)
        return result
    }

    def createAndSave = { sheetParams, String timeZoneId ->
        def sheet = create(sheetParams, timeZoneId).data
        return save(sheet, timeZoneId)
    }
}
