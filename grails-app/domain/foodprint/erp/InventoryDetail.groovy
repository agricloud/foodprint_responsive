package foodprint.erp
/**
 * 庫存細項
 */
class InventoryDetail {

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
     * 品項
     */
    Item item

    /**
     * 倉庫
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 批號
     */
    Batch batch

    /**
     * 數量
     */
    double qty = 0.0d

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable: true
        batch(unique: ["warehouse", "warehouseLocation", "item", "site"])
        batch validator: validator
        editor nullable: true
        creator nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [InventoryDetail]
        "${getMessageSource().getMessage("${i18nType}.inventoryDetail.label", args, Locale.getDefault())}: " +
        "${warehouse.name}-${warehouse.title}/${warehouseLocation.name}-${warehouseLocation.title}/" +
        "${item.name}-${item.title}-${item.brand.name}/${item.brand.title}/${batch.name}"
    }

    static validator = { col, obj ->

        // 儲位X不屬於倉庫X
        // note: warehouseLocation(col) 為 javassist class，直接使用會造成判斷錯誤。
        if (obj.warehouseLocation.warehouse != obj.warehouse) {
            return ["sheet.warehouseLocation.not.belong.to.warehouse", warehouseLocation, obj.warehouse]
        }
    }
}
