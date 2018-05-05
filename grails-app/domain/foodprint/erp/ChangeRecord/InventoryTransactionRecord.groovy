package foodprint.erp.ChangeRecord

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.Batch
import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation
import foodprint.erp.TransactionType
import foodprint.erp.TypeName

/**
 * 庫存異動紀錄
 * 參考：INVLA
 */
class InventoryTransactionRecord {

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
     * 批號
     */
    Batch batch

    /**
     * 品項
     */
    Item item

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
     * 執行日期=單據日期
     */
    Date executionDate
   
    /**
     * 庫別
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 異動類別
     *  1.入庫、2.銷貨、3.領用、4.轉撥、5.調整
     */
    TransactionType transactionType

    /**
     * 出入別
     * 依單據性質之庫存影響:減 或 轉撥單性質的出寫入為-1，其餘為+1。
     */
    int multiplier

    /**
     * 異動數量
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
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        sequence(unique: ["multiplier", "name", "typeName", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        multiplier range: -1..1, notEqual: 0
        warehouse nullable: true
        warehouseLocation nullable: true
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
        Object[] args = [InventoryTransactionRecord]
        "${getMessageSource().getMessage("${i18nType}.inventoryTransactionRecord.label", args, Locale.getDefault())}: " +
        "${item.name}-${item.title}/${item.brand.name}-${item.brand.title}" +
        "/${typeName.name}-${name}-${sequence}/${multiplier}/${qty}"
    }
}
