package foodprint.erp.ChangeOrder

import foodprint.erp.Site
import foodprint.erp.TransactionType
import foodprint.erp.TypeName
import foodprint.erp.Item
import foodprint.erp.Batch
import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation
import foodprint.erp.ChangeOrder.InventoryTransactionSheet

/**
 * 庫存異動單單身
 * 參考：INVTB
 */
class InventoryTransactionSheetDet {

    def grailsApplication
    def messageSource

    String importFlag = -1

    /**
     * 公司
     */
    Site site

    /**
     * 修改者
     */
    String editor

    /**
     * 建立者
     */
    String creator

    /**
     * 建立日期（自動欄位）
     */
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated

    /**
     * 單別
     */
    TypeName typeName

    /**
     * 單號
     */
    String name

    /**
     * 序號
     */
    int sequence

    /**
     * 庫存異動單單頭
     */
    InventoryTransactionSheet header

    /**
     * 批號
     */
    Batch batch

    /**
     * 品項
     */
    Item item

    /**
     * 異動庫別/轉出庫別
     */
    Warehouse outWarehouse

    /**
     * 異動儲位/轉出儲位
     */
    WarehouseLocation outWarehouseLocation

    /**
     * 轉入庫別
     * note: 轉撥單時用
     */
    WarehouseLocation inWarehouse

    /**
     * 轉入儲位
     * note: 轉撥單時用
     */
    WarehouseLocation inWarehouseLocation

    /**
     * 數量
     */
    double qty

    /**
     * 單位
     */
    String unit

    /*
     * 備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
        sequence updateable: false
        header updateable: false
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        sequence(unique: ["name", "typeName", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        typeName validator: validator
        inWarehouse nullable: true
        inWarehouseLocation nullable: true
        qty notEqual: 0d
        remark nullable: true

    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [InventoryTransactionSheetDet]
        "${getMessageSource().getMessage("${i18nType}.inventoryTransactionSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName.name}-${name}-${sequence}"
    }

    static validator  = { col, obj ->
        // 「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.sheet.typeName.name.not.equal"]
        }

        // 單別、單號、序號一旦建立不允許變更
        if (obj.isDirty("typeName") || obj.isDirty("name") || obj.isDirty("sequence")) {
            return ["sheetDetail.typeName.name.sequence.cannot.change"]
        }

        if (obj.typeName.transactionType == TransactionType.TRANSFER) {
            // 轉撥單之轉入庫不可為空
            if (!inWarehouse)
                return ["inventoryTransactionSheetDet.inWarehouse.nullable.error"]
            if (!inWarehouseLocation)
                return ["inventoryTransactionSheetDet.inWarehouseLocation.nullable.error"]
            // 轉撥單之轉出庫與轉入庫不可相同
            if (obj.inWarehouse == obj.outWarehouse)
                return ["inventoryTransactionSheetDet.inWarehouse.outWarehouse.cannot.equal"]
            if (obj.inWarehouseLocation == obj.inWarehouseLocation)
                return ["inventoryTransactionSheetDet.inWarehouseLocation.outWarehouseLocation.cannot.equal"]
            //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
            if (obj.inWarehouse.factory != obj.header.factory) {
                return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.inWarehouse.factory, obj.header.factory]
            }
            //「單身倉庫」與「單身儲位所屬倉庫」不同不允許儲存
            if (obj.inWarehouseLocation.warehouse != obj.inWarehouse) {
                return ["sheetDetail.warechouse.warehouseLocation.warehouse.not.equal", obj.inWarehouse, obj.inWarehouseLocation.warehouse]
            }
        }
        else {
            // 非轉撥單不可有值
            if (obj.inWarehouse)
                return ["inventoryTransactionSheetDet.non.TRANSFER.inWarehouse.should.be.null"]
            if (obj.inWarehouseLocation)
                return ["inventoryTransactionSheetDet.non.TRANSFER.inWarehouseLocation.should.be.null"]
        }

        //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
        if (obj.outWarehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.outWarehouse.factory, obj.header.factory]
        }

        //「單身倉庫」與「單身儲位所屬倉庫」不同不允許儲存
        if (obj.outWarehouseLocation.warehouse != obj.outWarehouse) {
            return ["sheetDetail.warehouse.warehouseLocation.warehouse.not.equal", obj.warehouse, obj.outWarehouseLocation.warehouse]
        }
    }

}
