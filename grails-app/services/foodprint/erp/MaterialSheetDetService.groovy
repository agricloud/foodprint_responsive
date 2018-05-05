package foodprint.erp

import foodprint.sft.MaterialSheetDetCustomizeDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class MaterialSheetDetService {

    def grailsApplication
    def domainService
    def enumService
    def inventoryDetailService
    def sheetCustomizeService

    def i18nType

    def create = { sheetDetParams ->

        if (sheetDetParams.header.id) {

            def sheetDet = new MaterialSheetDet(sheetDetParams)

            sheetDet.typeName = sheetDet.header.typeName
            sheetDet.name = sheetDet.header.name

            if (sheetDet.header.details)
                sheetDet.sequence = sheetDet.header.details*.sequence.max()+1
            else sheetDet.sequence = 1

            return [success: true, data: sheetDet]
        }
        else throw new DomainDataException(messageSource.getMessage("${i18nType}.materialSheetDet.message.create.failed"))

    }

    @Transactional
    def save(MaterialSheetDet sheetDet, customizeParams, siteId, params) {

        def result

        result = domainService.save(sheetDet)

        inventoryDetailService.processInventoryByCreate(sheetDet, siteId, params)

        // 變更製令狀態為已發料
        def manufactureOrder = sheetDet.manufactureOrder
        if (manufactureOrder.status == ManufactureOrderStatus.PENDING || manufactureOrder.status == ManufactureOrderStatus.APPROVED) {
            manufactureOrder.status = ManufactureOrderStatus.DISBURSED
            domainService.save(manufactureOrder)
        }

        def customizeDetResult = sheetCustomizeService.saveOrUpdate(sheetDet.id, customizeParams, MaterialSheetDetCustomizeDet, siteId, params)
        return customizeDetResult ?: result

    }

    def createAndSave = { sheetDetParams, customizeParams, siteId, params ->
        def sheetDet = create(sheetDetParams).data
        return save(sheetDet, customizeParams, siteId, params)
    }
}
