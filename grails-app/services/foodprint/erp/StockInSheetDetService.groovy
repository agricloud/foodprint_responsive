package foodprint.erp

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class StockInSheetDetService {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def i18nType

    def create = { sheetDetParams ->

        if (sheetDetParams.header.id) {

            def sheetDet = new StockInSheetDet(sheetDetParams)

            sheetDet.typeName = sheetDet.header.typeName
            sheetDet.name = sheetDet.header.name

            if (sheetDet.header.details)
                sheetDet.sequence = sheetDet.header.details*.sequence.max()+1
            else sheetDet.sequence = 1

            return [success: true, data: sheetDet]
        }
        throw new DomainDataException(messageSource.getMessage("${i18nType}.stockInSheetDet.message.create.failed"))

    }

    @Transactional
    def save(StockInSheetDet sheetDet, sheetDetParams, siteId, params) {
        def result
        sheetDet.batch = batchService.findOrCreateBatchInstanceByJson(sheetDetParams, sheetDet, siteId, params).data
        inventoryDetailService.processInventoryByCreate(sheetDet, siteId, params)
        result = domainService.save(sheetDet)
        return result
    }

    def createAndSave = { sheetDetParams, siteId, params ->
        def sheetDet = create(sheetDetParams).data
        return save(sheetDet, sheetDetParams, siteId, params)
    }

}
