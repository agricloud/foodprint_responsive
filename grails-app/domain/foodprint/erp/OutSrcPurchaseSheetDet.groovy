package foodprint.erp

class OutSrcPurchaseSheetDet{

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

    /**
     * 託外進貨單單頭
     */
    OutSrcPurchaseSheet header
    
    /**
     * 品項編號
     */
    Item item

    /**
     * 單位
     */
    String unit

    /**
     * 庫別
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 進貨數量
     */
    double qty

    /**
     * 批號
     */
    Batch batch

    /**
     * 製令
     */
    ManufactureOrder manufactureOrder


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
        Object[] args = [OutSrcPurchaseSheetDet]
        "${getMessageSource().getMessage("${i18nType}.outSrcPurchaseSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        //「單頭廠別」與「製令廠別」不同不允許儲存
        if (obj.header.factory != obj.manufactureOrder.factory) {
            return ["outSrcPurchaseSheetDet.header.factory.manufactureOrder.factory.not.equal", obj.header.factory, obj.manufactureOrder.factory]
        }

        //「單頭供應商」與「製令供應商」不同不允許儲存
        if (obj.header.supplier != obj.manufactureOrder.supplier) {
            return ["outSrcPurchaseSheetDet.header.supplier.manufactureOrder.supplier.not.equal", obj.header.supplier, obj.manufactureOrder.supplier]
        }

        if (obj.qty <= 0) {
            return ["sheet.qty.min.error", obj]
        }

        //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
        if (obj.warehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.warehouse.factory, obj.header.factory]
        }
    }
}
