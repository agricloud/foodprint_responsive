package foodprint.erp
/**
 * 進貨單身
 */
class PurchaseSheetDet {

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

    PurchaseSheet header
    
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
     * 進貨數量
     */
    double qty

    /**
     * 單位進價
     */
    double price = 0.0d

    /**
     * 進貨總額
     */
    double totalPrice = 0.0d

    static hasMany = [purchaseReturnSheetDets: PurchaseReturnSheetDet]

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
        typeName validator: validator
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        sequence(unique: ["name", "typeName", "site"])
        qty min: 0.0d
        price min: 0.0d
        totalPrice min: 0.0d
        remark nullable: true
    }

    def beforeSave() {
        this.totalPrice = this.qty*this.price
        this.header.totalPrice += this.totalPrice
    }

    def beforeInsert() {
        beforeSave()
    }

    def beforeUpdate() {
        // 重新計算單身進貨總額、單頭進貨總額
        if (this.getPersistentValue('qty')!=this.qty || this.getPersistentValue('price')!=this.price) {
            this.header.totalPrice-=this.getPersistentValue('totalPrice')
            beforeSave()
        }
        
    }

    def beforeDelete() {
        this.header.totalPrice-=this.totalPrice
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [PurchaseSheetDet]
        "${getMessageSource().getMessage("${i18nType}.purchaseSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        if (obj.qty <= 0) {
            return ["sheet.qty.min.error", obj]
        }

        if (obj.warehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.warehouse.factory, obj.header.factory]
        }
    }
}
