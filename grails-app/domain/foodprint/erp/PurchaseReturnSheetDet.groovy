package foodprint.erp

/**
 * 退貨單身
 */
class PurchaseReturnSheetDet {

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
     * 訂單項次，取訂單編號最大單身項次 +1
     */
    int sequence

    PurchaseReturnSheet header

    /**
     * 進貨單單身
     */
    PurchaseSheetDet purchaseSheetDet

    /**
     * 品項編號
     */
    Item item

    /**
     * 單位
     */
    String unit

    /**
     * 批號
     */
    Batch batch

    /**
     * 庫別
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 退貨數量
     */
    double qty

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
        typeName validator: validator
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        sequence(unique: ["name", "typeName", "site"])
        qty min: 0.0d
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [PurchaseReturnSheetDet]
        "${getMessageSource().getMessage("${i18nType}.purchaseReturnSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        // 單頭單據日期不可小於來源單據單頭日期
        // ps.使用測試資料的purchaseSheetDet時，會出現此錯誤是正常的。
        if (obj.header.executionDate < obj.purchaseSheetDet.header.executionDate) {
            return ["returnSheetDetail.header.executionDate.min.error"]
        }

        //「單頭廠別」與「進貨單頭廠別」不同不允許儲存
        if (obj.header.factory != obj.purchaseSheetDet.header.factory) {
            return ["purchaseReturnSheetDet.header.factory.purchaseSheetDet.header.factory.not.equal", obj.header.factory, obj.purchaseSheetDet.header.factory]
        }

        //「單頭供應商」與「進貨單頭供應商」不同不允許儲存
        if (obj.purchaseSheetDet.header.supplier != obj.header.supplier) {
            return ["purchaseReturnSheetDet.header.supplier.purchaseSheetDet.header.supplier.not.equal", obj]
        }
        //「單身品項、批號」與「進貨單身品項、批號」不同不允許儲存
        if (obj.item!=obj.purchaseSheetDet.item || obj.batch!=obj.purchaseSheetDet.batch) {
            return ["purchaseReturnSheetDet.itemOrBatch.purchaseSheetDet.itemOrBatch.not.equal", obj.item, obj.batch, obj.purchaseSheetDet.item, obj.purchaseSheetDet.batch]
        }

        if (obj.qty <= 0) {
            return ["sheet.qty.min.error", obj]
        }

        // 退單數量不可大於來源單據數量
        if (obj.qty > obj.purchaseSheetDet.qty) {
            return ["returnSheetDetail.qty.max.error", obj.purchaseSheetDet.qty]
        }

        //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
        if (obj.warehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.warehouse.factory, obj.header.factory]
        }
    }
}
