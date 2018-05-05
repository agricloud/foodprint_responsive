package foodprint.erp

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class SaleSheetDetService {

    def grailsApplication
    def domainService
    def inventoryDetailService

    def i18nType

    def create = { sheetDetParams ->

        if (sheetDetParams.header.id) {

            def sheetDet = new SaleSheetDet(sheetDetParams)

            sheetDet.typeName = sheetDet.header.typeName
            sheetDet.name = sheetDet.header.name

            if (sheetDet.header.details)
                sheetDet.sequence = sheetDet.header.details*.sequence.max()+1
            else sheetDet.sequence = 1

            return [success: true, data: sheetDet]
        }
        else throw new DomainDataException(messageSource.getMessage("${i18nType}.saleSheetDet.message.create.failed"))

    }

    @Transactional
    def save(SaleSheetDet sheetDet, siteId, params) {
        def result

        result = domainService.save(sheetDet)
        inventoryDetailService.processInventoryByCreate(sheetDet, siteId, params)
        
        return result
    }

    def createAndSave = { sheetDetParams, siteId, params ->
        def sheetDet = create(sheetDetParams).data
        return save(sheetDet, siteId, params)
    }

}
