package foodprint.erp

import grails.util.Environment
import org.springframework.transaction.annotation.Transactional

import foodprint.common.Country
import foodprint.erp.ChangeOrder.InventoryTransactionSheetDet

import foodprint.common.DomainDataException

class BatchService {

    def grailsApplication
    def messageSource
    def domainService
    def enumService
    def foodauthService

    def i18nType

    def findOrCreateBatchInstanceByXml(recordXml, sheet) {

        def site = Site.findByName(recordXml.siteName.text())
        // 如果批號欄位沒有資料則不新增batch
        // 但單據仍會儲存
        if (recordXml.batchName.text() || recordXml.batchName.text().trim()) {
            def batch = Batch.findByNameAndSite(recordXml.batchName.text(), site)

            if (!batch) {
                batch = new Batch(name:recordXml.batchName.text(), country:Country.TAIWAN, site:site) // 暫先預設台灣
            }

            batch.importFlag = batch.importFlag++
            batch.item = sheet.item

            // if (sheet.instanceOf(PurchaseSheetDet)) {
            // }
            // if (sheet.instanceOf(StockInSheetDet)) {
            // }
            // if (sheet.instanceOf(OutSrcPurchaseSheetDet)) {
            // }
            if (sheet.instanceOf(ManufactureOrder)) {
                batch.expectQty = sheet.expectQty
            }
            def result = domainService.save(batch)
            if (result.success) {
                return batch
            }
            return null
        }
        else {
            return null
        }
    }

    def findOrCreateBatchInstanceByJson (sheetParams, sheet, siteId, params) {
        //如果批號欄位沒有資料則不新增batch

        def site = Site.get(siteId)
        def batch = Batch.findByNameAndSite(sheetParams["batch.name"], site)

        if (!batch) {
           return createBatchInstanceByJson(sheetParams, sheet, siteId, params)
        }

        if (sheet) {

            // 單據品項須與批號品項一致
            if (sheet.item != batch.item) {
                throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.item.batch.item.not.equal", (Object[])[sheet.item, batch.item], Locale.getDefault()))
            }

            // 採購批號
            if (sheet.instanceOf(PurchaseSheetDet)) {
                // 批號來源類型必須為採購
                if (batch.batchSourceType != BatchSourceType.PURCHASE)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.purchaseSheetDet.batch.batchSourceType.error", (Object[])[sheet], Locale.getDefault()))
                // 批號不可存有製令
                if (batch.manufactureOrder)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.purchaseSheetDet.batch.manufactureOrder.error", (Object[])[sheet], Locale.getDefault()))
                def manufacturer = Manufacturer.get(sheetParams.batch.manufacturer.id)
                // 必須輸入供應商
                if (!manufacturer) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.batch.manufacturer.nullable.error", (Object[])[sheet], Locale.getDefault()))
                }
                // 輸入之供應商須與批號供應商一致
                if (batch.manufacturer != manufacturer)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.purchaseSheetDet.manufacturer.batch.manufacturer.not.equal", (Object[])[manufacturer, batch.manufacturer], Locale.getDefault()))
                // 輸入之供應商(製造商)國別須與批號產地一致
                if (batch.country != manufacturer.country)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.country.batch.country.not.equal", (Object[])[enumService.name(manufacturer.country).title, enumService.name(batch.country).title], Locale.getDefault()))
            }
            // 生產入庫批號
            if (sheet.instanceOf(StockInSheetDet)) {
                // 批號來源類型必須為自製、入庫
                if (batch.batchSourceType != BatchSourceType.STOCKIN && batch.batchSourceType != BatchSourceType.MANUFACTURE)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.stockInSheetDet.batch.batchSourceType.error", (Object[])[sheet], Locale.getDefault()))
                // 批號所屬製令須與單據關聯製令相同
                if (batch.manufactureOrder != sheet.manufactureOrder)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.stockInSheetDet.batch.manufactureOrder.error", (Object[])[sheet.manufactureOrder, batch.manufactureOrder], Locale.getDefault()))
                // 批號供應商應為null
                if (batch.manufacturer)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.stockInSheetDet.batch.manufacturer.should.be.null", (Object[])[sheet], Locale.getDefault()))
                if (batch.country != Country.TAIWAN)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.country.batch.country.not.equal", (Object[])[enumService.name(Country.TAIWAN).title, enumService.name(batch.country).title], Locale.getDefault()))
            }
            // 託外進貨批號
            if (sheet.instanceOf(OutSrcPurchaseSheetDet)) {
                // 批號來源類型必須為託外
                if (batch.batchSourceType != BatchSourceType.OUTSRCPURCHASE && batch.batchSourceType != BatchSourceType.OUTSRCMANUFACTURE)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.outSrcPurchaseSheetDet.batch.batchSourceType.error", (Object[])[sheet], Locale.getDefault()))
                // 批號所屬製令須與單據關聯製令相同
                if (batch.manufactureOrder != sheet.manufactureOrder)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.outSrcPurchaseSheetDet.batch.manufactureOrder.error", (Object[])[sheet.manufactureOrder, batch.manufactureOrder], Locale.getDefault()))
                // 單據之供應商(製造商)須與批號製造商一致
                if (batch.manufacturer != sheet.header.supplier.manufacturer)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.outSrcPurchaseSheetDet.header.supplier.manufacturer.batch.manufacturer.not.equal", (Object[])[sheet.header.supplier, batch.manufacturer], Locale.getDefault()))
                // 單據之供應商(製造商)國別須與批號產地一致
                if (batch.country != sheet.header.supplier.manufacturer.country)
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.country.batch.country.not.equal", (Object[])[enumService.name(sheet.header.supplier.manufacturer.country).title, enumService.name(batch.country).title], Locale.getDefault()))
            }
            //製令批號
            /*
             * 以目前domain與流程關聯，批號對應製令不會被其他使用，應不須檢查，若有更新則以新增方式。
             */
            // if (sheet.instanceOf(ManufactureOrder)) {

            //     if (batch.manufactureOrder != sheet)
            //     //批號來源類型必須為自製或託外
            //     if (batch.batchSourceType != BatchSourceType.MANUFACTURE && batch.batchSourceType != BatchSourceType.OUTSRCMANUFACTURE)
            //         return [success: false, batch: batch, message: messageSource.getMessage("${i18nType}.manufactureOrder.batch.batchSourceType.error", (Object[])[sheet], Locale.getDefault())]

            //     if (sheet.manufactureType == ManufactureType.OUTSRC) {
            //         // if (sheet.supplier.isManufacturer) {
            //             if (batch.manufacturer != sheet.supplier.manufacturer)
            //                 return [success: false, batch: batch, message: messageSource.getMessage("${i18nType}.manufactureOrder.supplier.manufacturer.batch.manufacturer.not.equal", (Object[])[sheet.supplier, batch.manufacturer], Locale.getDefault())]
            //             if (batch.country != sheet.supplier.manufacturer.country)
            //                 return [success: false, batch: batch, message: messageSource.getMessage("${i18nType}.sheet.country.batch.country.not.equal", (Object[])[enumService.name(sheet.supplier.manufacturer.country).title, enumService.name(batch.country).title], Locale.getDefault())]
            //         // }
            //         // else {
            //             // return [success: false, message: messageSource.getMessage("${i18nType}.manufactureOrder.supplier.isManufacturer.error", (Object[])[sheet], Locale.getDefault())]
            //         }
            //     }
            //     if (sheet.manufactureType == ManufactureType.FACTORY) {
            //         //自製批號不可存在製造商
            //         if (batch.manufacturer) {
            //             return [success: false, message: messageSource.getMessage("${i18nType}.batch.batchSourceType.manufacturer.should.be.null", (Object[])[sheetParams.batch.batchSourceType], Locale.getDefault())]
            //         }
            //         if (batch.country != Country.TAIWAN)
            //             return [success: false, batch: batch, message: messageSource.getMessage("${i18nType}.sheet.country.batch.country.not.equal", (Object[])[enumService.name(Country.TAIWAN).title, enumService.name(batch.country).title], Locale.getDefault())]
            //     }

            // }
            // 庫存異動單據
            if (sheet.instanceOf(InventoryTransactionSheetDet)) {
                if (BatchSourceType[sheetParams.batch.batchSourceType] != batch.batchSourceType) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryTransactionSheetDet.batchSourceType.batch.batchSourceType.not.equal", (Object[])[enumService.name(BatchSourceType[sheetParams.batch.batchSourceType]).title,  enumService.name(batch.batchSourceType).title], Locale.getDefault()))
                }
                // 自製、入庫批號不可存在製造商
                if (BatchSourceType[sheetParams.batch.batchSourceType] in [BatchSourceType.MANUFACTURE, BatchSourceType.STOCKIN] && sheetParams.batch.manufacturer?.id) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.batch.batchSourceType.manufacturer.should.be.null", (Object[])['manufacturer', Batch, Manufacturer.get(sheetParams.batch.manufacturer.id), enumService.name(BatchSourceType[sheetParams.batch.batchSourceType]).title], Locale.getDefault()))
                }
                // 非自製、入庫批號必須有製造商
                if (!(BatchSourceType[sheetParams.batch.batchSourceType] in [BatchSourceType.MANUFACTURE, BatchSourceType.STOCKIN]) && !sheetParams.batch.manufacturer?.id) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.batch.batchSourceType.manufacturer.nullable.error", (Object[])['manufacturer', Batch, Manufacturer.get(sheetParams.batch.manufacturer.id), enumService.name(BatchSourceType[sheetParams.batch.batchSourceType]).title], Locale.getDefault()))
                }

                def manufacturer = Manufacturer.get(sheetParams.batch.manufacturer.id)
                if (manufacturer != batch.manufacturer) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryTransactionSheetDet.manufacturer.batch.manufacturer.not.equal", (Object[])[Manufacturer.get(sheetParams.batch.manufacturer.id), batch.manufacturer], Locale.getDefault()))
                }
                if (manufacturer && (manufacturer.country != batch.country)) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryTransactionSheetDet.country.batch.country.not.equal", (Object[])[enumService.name(Country[sheetParams.batch.country]).title, enumService.name(batch.country).title], Locale.getDefault()))
                }
            }

        }// end if sheet

        return [success: true, data: batch]

    }

    @Transactional
    def createBatchInstanceByJson (sheetParams, sheet, siteId, params) {

        def site = Site.get(siteId)
        def sites = Site.findAllByActivationCode(site.activationCode)
        def batchCount = Batch.where {site in sites}.count()
        if (foodauthService.ping().success || Environment.current != Environment.DEVELOPMENT) {
            try {

                def validateBatchResult = foodauthService.validateBatch(site.activationCode, batchCount)

                if (!validateBatchResult.success) {
                    throw new DomainDataException(validateBatchResult.message)
                }

            }
            catch (e) {
                throw new DomainDataException("Could not validate your License, please contact us.")
            }
        }

        def batch = new Batch()

        batch.name = sheetParams["batch.name"]
        batch.item = Item.get(sheetParams.item.id)
        batch.site = site
        batch.creator = params.editor

        if (sheet) {
            // 採購批號
            if (sheet.instanceOf(PurchaseSheetDet)) {
                def manufacturer = Manufacturer.get(sheetParams.batch.manufacturer.id)
                batch.batchSourceType = BatchSourceType.PURCHASE
                batch.manufacturer = manufacturer
                batch.country = manufacturer.country

            }
            // 生產入庫批號
            if (sheet.instanceOf(StockInSheetDet)) {
                def manufactureBatch = Batch.get(sheetParams.manufactureBatch.id)
                if (sheet.manufactureOrder != manufactureBatch.manufactureOrder) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.stockInSheetDet.manufactureOrder.batch.manufactureBatch.not.equal", (Object[])[sheet.manufactureOrder, manufactureBatch.manufactureOrder], Locale.getDefault()))
                }
                batch.batchSourceType = BatchSourceType.STOCKIN
                batch.manufactureBatch = manufactureBatch
                batch.manufactureOrder = manufactureBatch.manufactureOrder
                batch.country = manufactureBatch.country
            }
            // 託外進貨批號
            if (sheet.instanceOf(OutSrcPurchaseSheetDet)) {
                def manufactureBatch = Batch.get(sheetParams.manufactureBatch.id)
                if (sheet.manufactureOrder != manufactureBatch.manufactureOrder) {
                    throw new DomainDataException(messageSource.getMessage("${i18nType}.outSrcPurchaseSheetDet.manufactureOrder.batch.manufactureBatch.not.equal", (Object[])[sheet.manufactureOrder, manufactureBatch.manufactureOrder], Locale.getDefault()))
                }
                batch.batchSourceType = BatchSourceType.OUTSRCPURCHASE
                batch.manufactureBatch = manufactureBatch
                batch.manufactureOrder = manufactureBatch.manufactureOrder
                batch.country = manufactureBatch.country
                batch.manufacturer = manufactureBatch.manufacturer
            }
            // 製令批號
            if (sheet.instanceOf(ManufactureOrder)) {
                if (sheet.manufactureType == ManufactureType.OUTSRC) {
                    batch.batchSourceType = BatchSourceType.OUTSRCMANUFACTURE
                    batch.manufacturer = sheet.supplier.manufacturer
                    batch.country = sheet.supplier.manufacturer.country
                }
                else if (sheet.manufactureType == ManufactureType.FACTORY) {
                    batch.batchSourceType = BatchSourceType.MANUFACTURE
                    batch.country = Country.TAIWAN // 暫先預設台灣
                }
                batch.manufactureOrder = sheet
                batch.expectQty = sheet.expectQty
            }
            // 庫存異動單據
            if (sheet.instanceOf(InventoryTransactionSheetDet)) {
                // few ways to convert string to enum
                // 1.Enum.valueOf(Country, "TAIWAN")
                // 2.Country.valueOf("TAIWAN")
                // 3."TAIWAN" as Country
                // 4.Country["TAIWAN"]
                // 5.(Country) "TAIWAN"

                batch.batchSourceType = (BatchSourceType) sheetParams.batch.batchSourceType

                // 自製、入庫批號不可存在製造商由validator判斷
                batch.manufacturer = Manufacturer.get(sheetParams.batch.manufacturer?.id)
                batch.country = batch.manufacturer?.country

                if (batch.batchSourceType in [BatchSourceType.MANUFACTURE, BatchSourceType.STOCKIN])
                    batch.country = Country.TAIWAN
            }
        }// end if sheet

        return domainService.save(batch)
    }

}
