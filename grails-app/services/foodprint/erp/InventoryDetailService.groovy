package foodprint.erp

import foodprint.erp.ChangeOrder.InventoryTransactionSheetDet
import foodprint.erp.ChangeRecord.InventoryTransactionRecord

import foodprint.common.DomainDataException

import org.springframework.transaction.annotation.Transactional

class InventoryDetailService {

    def grailsApplication
    def messageSource
    def domainService
    def inventoryService
    def batchService

    def i18nType

    def indexByBatchAndGroupByWarehouse(batchId, siteId) {

        def site = Site.get(siteId)
        def batch = Batch.get(batchId)

        def inventoryDetails = InventoryDetail.executeQuery("SELECT warehouse.id, warehouse.name, warehouse.title, item.id, item.name, item.title, batch.id, batch.name, SUM(qty) FROM InventoryDetail WHERE batch.id = ? AND site.id = ? GROUP BY warehouse.id",[batch.id, site.id])

        return inventoryDetails
    }

    /*
     * note:: service中params僅使用editor、creator等一定是由前端傳入之資訊，其餘需另外傳送參數，
     *        以確保在不同caller呼叫時，前端之params內容並不一定可直接使用。
     */
    @Transactional
    def processInventoryByCreate(sheet, siteId, params) {

        processSheetAndInventory(sheet, sheet.batch, 'create', siteId, params)
        
        return [success: true]
    }

    @Transactional
    def processInventoryByUpdate(oldSheet, newSheet, siteId, params) {
        if (oldSheet.qty!=newSheet.qty || oldSheet.batch!=newSheet.batch || oldSheet.item!=newSheet.item
            || (oldSheet.hasProperty("outWarehouse") && oldSheet.outWarehouse==newSheet.outWarehouse)
            || (oldSheet.hasProperty("outWarehouseLocation") && oldSheet.outWarehouseLocation==newSheet.outWarehouseLocation)
            || (oldSheet.hasProperty("inWarehouse") && oldSheet.inWarehouse==newSheet.inWarehouse)
            || (oldSheet.hasProperty("inWarehouseLocation") && oldSheet.inWarehouseLocation==newSheet.inWarehouseLocation)) {

            processSheetAndInventory(oldSheet, oldSheet.batch, 'recovery', siteId, params)
       
            processSheetAndInventory(newSheet, newSheet.batch, 'update', siteId, params)
        }

        return [success: true]
    }

    @Transactional
    def processInventoryByDelete(sheet, siteId, params) {

        processSheetAndInventory(sheet, sheet.batch, 'delete', siteId, params)
        
        return [success: true]
    }

    @Transactional
    private processSheetAndInventory(sheet, batch, action, siteId, params) {

        def site = Site.get(siteId)
        def warehouse, warehouseLocation
        def item = sheet.item

        // 待新增單位轉換功能後，須調整。
        if (sheet.unit != item.unit)
            throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryDetail.unit.error!!", [sheet] as Object[], Locale.getDefault()))
        if (sheet.qty == 0)
            throw new DomainDataException(messageSource.getMessage("${i18nType}.qty.cannot.be.zero", ['qty', InventoryDetail, sheet.qty, ''] as Object[], Locale.getDefault()))

        def multiplier = sheet.typeName.multiplier
        def qty = multiplier*(sheet.qty)*(action=="recovery" || action=="delete" ? -1 : 1)

        if (sheet instanceof InventoryTransactionSheetDet) {
            if (sheet.typeName.transactionType != TransactionType.TRANSFER) {
                warehouse = sheet.outWarehouse
                warehouseLocation = sheet.outWarehouseLocation
            }
            if (sheet.typeName.transactionType == TransactionType.TRANSFER) {
                warehouse = sheet.inWarehouse
                warehouseLocation = sheet.inWarehouseLocation
                processInventory(item, batch, outWarehouse, outWarehouseLocation, -qty, site, params)
                if (action == "delete")
                    deleteInventoryTransactionRecord(sheet, -multiplier, item, batch, outWarehouse, outWarehouseLocation, sheet.qty, site, params)
                else if (action == "recovery")
                    updateOrCreateInventoryTransactionRecord(sheet, -multiplier, item, batch, outWarehouse, outWarehouseLocation, 0, site, params)
                else
                    updateOrCreateInventoryTransactionRecord(sheet, -multiplier, item, batch, outWarehouse, outWarehouseLocation, sheet.qty, site, params)
            }
        }
        else {
            warehouse = sheet.warehouse
            warehouseLocation = sheet.warehouseLocation
        }

        processInventory(item, batch, warehouse, warehouseLocation, qty, site, params)
        if (action == "delete")
            deleteInventoryTransactionRecord(sheet, multiplier, item, batch, warehouse, warehouseLocation, sheet.qty, site, params)
        else if (action == "recovery")
            updateOrCreateInventoryTransactionRecord(sheet, multiplier, item, batch, warehouse, warehouseLocation, 0, site, params)
        else
            updateOrCreateInventoryTransactionRecord(sheet, multiplier, item, batch, warehouse, warehouseLocation, sheet.qty, site, params)
    }

    @Transactional
    private processInventory(item, batch, warehouse, warehouseLocation, qty, site, params) {

        inventoryService.processInventory(item, warehouse, qty, site, params)
        
        def inventoryDetail = InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatchAndSite(warehouse, warehouseLocation, item, batch, site)
        if (inventoryDetail || qty > 0) {
            if (inventoryDetail) {
                inventoryDetail.editor = params.editor
                inventoryDetail.qty += qty
            }
            else if (qty > 0) {
                inventoryDetail = new InventoryDetail()
                inventoryDetail.creator = params.editor
                inventoryDetail.editor = params.editor
                inventoryDetail.warehouse = warehouse
                inventoryDetail.warehouseLocation = warehouseLocation
                inventoryDetail.item = item
                inventoryDetail.batch = batch
                inventoryDetail.qty = qty
                inventoryDetail.site = site
            }
            //if (BatchBoxDet.findByManufactureOrder(batch.manufactureOrder))
            //    BatchBoxDet.findByManufactureOrder(batch.manufactureOrder).qty += qty
        }
        else if (qty < 0) {
            throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryDetail.not.found", [warehouse, warehouseLocation, item, batch] as Object[], Locale.getDefault()))
            //庫存不足
            // throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryDetail.quantity.not.enough", [warehouse, warehouseLocation, item, batch] as Object[], Locale.getDefault()))
        }

        def result = domainService.save(inventoryDetail)
        
        if (!result.success) 
            throw new DomainDataException(result.message, result.errors)

        return result
    }

    @Transactional
    private updateOrCreateInventoryTransactionRecord(sheet, multiplier, item, batch, warehouse, warehouseLocation, qty, site, params) {

        def record = InventoryTransactionRecord.findByTypeNameAndNameAndSequenceAndMultiplierAndSite(sheet.typeName, sheet.name, sheet.sequence, multiplier, site)
        if (!record) {
            record = new InventoryTransactionRecord()
            record.creator = params.editor
            record.editor = params.editor
            record.site = site
            record.typeName = sheet.typeName
            record.name = sheet.name
            record.sequence = sheet.sequence
            record.executionDate = sheet.header.executionDate
            record.transactionType = sheet.typeName.transactionType
            record.multiplier = multiplier
        }
        
        record.batch = batch
        record.item = item
        record.warehouse = warehouse
        record.warehouseLocation = warehouseLocation
        record.qty = qty
        record.unit = sheet.unit

        //領料單、移轉單remark寫入製令
        //進貨單remark寫入supplier
        if (sheet instanceof MaterialSheetDet || sheet instanceof MaterialReturnSheetDet)
            record.remark = "${sheet.manufactureOrder.typeName.name}-${sheet.manufactureOrder.name}"
        else if (sheet instanceof PurchaseSheetDet)
            record.remark = "${sheet.header.supplier.name}-${sheet.header.supplier.title}"
        else if (sheet instanceof PurchaseReturnSheetDet)
            record.remark = "${sheet.header.supplier.name}-${sheet.header.supplier.title}"
        else if (sheet instanceof OutSrcPurchaseSheetDet)
            record.remark = "${sheet.header.supplier.name}-${sheet.header.supplier.title}"
        else if (sheet instanceof OutSrcPurchaseReturnSheetDet)
            record.remark = "${sheet.header.supplier.name}-${sheet.header.supplier.title}"
        else if (sheet instanceof StockInSheetDet)
            record.remark = "${sheet.header.workstation.name}-${sheet.header.workstation.title}"
        else if (sheet instanceof SaleSheetDet)
            record.remark = "${sheet.header.customer.name}-${sheet.header.customer.title}"
        else if (sheet instanceof SaleReturnSheetDet)
            record.remark = "${sheet.header.customer.name}-${sheet.header.customer.title}"

        def result = domainService.save(record)

        if (!result.success) 
            throw new DomainDataException(result.message, result.errors)

        return result
    }

    @Transactional
    private deleteInventoryTransactionRecord(sheet, multiplier, item, batch, warehouse, warehouseLocation, qty, site, params) {

        def record = InventoryTransactionRecord.findByTypeNameAndNameAndSequenceAndMultiplierAndSite(sheet.typeName, sheet.name, sheet.sequence, multiplier, site)

        if (record.item == item && record.batch == batch && record.warehouse == warehouse && record.warehouseLocation == warehouseLocation && record.qty == qty) {
            return domainService.delete(record)
        }
        else {
            throw new DomainDataException(messageSource.getMessage("${i18nType}.inventoryTransactionRecord.not.found", [] as Object[], Locale.getDefault()))
        }
    }
}
