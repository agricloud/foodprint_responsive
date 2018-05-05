package foodprint.erp
/**
 * 銷貨單單身
 */
class SaleSheetDet {

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

    SaleSheet header

    /**
     * 品項
     */
    Item item

    /**
     * 單位
     */
    String unit

    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet

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
     * 數量
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
        customerOrderDet nullable: true
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
        Object[] args = [SaleSheetDet]
        "${getMessageSource().getMessage("${i18nType}.saleSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        // 單頭單據日期不可小於來源單據單頭日期
        if (obj.customerOrderDet && obj.header.executionDate < obj.customerOrderDet.header.executionDate) {
            return ["sheetDetail.header.executionDate.min.error"]
        }

        //「單頭廠別」與「訂單單頭廠別」不同不允許儲存
        if (obj.customerOrderDet && obj.header.factory!=obj.customerOrderDet.header.factory) {
            return ["saleSheetDet.header.factory.customerOrderDet.header.factory.not.equal", obj.header.factory, obj.customerOrderDet.header.factory]
        }

        //「單頭客戶」與「訂單單頭客戶」不同不允許儲存
        if (obj.customerOrderDet && obj.header.customer!=obj.customerOrderDet.header.customer) {
            return ["saleSheetDet.header.customer.customerOrderDet.header.customer.not.equal", obj.header.customer, obj.customerOrderDet.header.customer]
        }

        //「單身品項」與「訂單單身品項」不同不允許儲存
        if (obj.customerOrderDet && obj.item!=obj.customerOrderDet.item) {
            return ["saleSheetDet.item.customerOrderDet.item.not.equal", obj.item, obj.customerOrderDet.item]
        }

        //「單身品項」與「批號品項」不同不允許儲存
        if (obj.item != obj.batch.item) {
            return ["sheet.item.batch.item.not.equal", obj.item, obj.batch.item]
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
