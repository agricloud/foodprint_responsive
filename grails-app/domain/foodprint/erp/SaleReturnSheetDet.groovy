package foodprint.erp
/**
 * 銷退單單身
 */
class SaleReturnSheetDet {

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

    
    SaleReturnSheet header
    
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
     * 銷貨單單身
     */
    SaleSheetDet saleSheetDet

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
        Object[] args = [SaleReturnSheetDet]
        "${getMessageSource().getMessage("${i18nType}.saleReturnSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }
    
    static validator  = { col, obj ->

        // 「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.sheet.typeName.name.not.equal"]
        }
        
        // 單頭單據日期不可小於來源單據單頭日期
        if (obj.header.executionDate < obj.saleSheetDet.header.executionDate) {
            return ["returnSheetDetail.header.executionDate.min.error"]
        }

        //「單頭廠別」與「銷貨單頭廠別」不同不允許儲存
        if (obj.header.factory != obj.saleSheetDet.header.factory) {
            return ["saleReturnSheetDet.header.factory.saleSheetDet.header.factory.not.equal", obj.header.factory, obj.saleSheetDet.header.factory]
        }

        //「單頭客戶」與「銷貨單頭客戶」不同不允許儲存
        if (obj.header.customer != obj.saleSheetDet.header.customer) {
            return ["saleReturnSheetDet.header.customer.saleSheetDet.header.customer.not.equal", obj.header.customer, obj.saleSheetDet.header.customer]
        }

        //「單身訂單」與「銷貨單身訂單」不同不允許儲存
        if (obj.customerOrderDet != obj.saleSheetDet.customerOrderDet) {
            return ["saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal", obj.customerOrderDet, obj.saleSheetDet.customerOrderDet]
        }

        //「單身品項、批號」與「銷貨單身品項、批號」不同不允許儲存
        if (obj.item!=obj.saleSheetDet.item || obj.batch!=obj.saleSheetDet.batch) {
            return ["saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal", obj.item, obj.batch, obj.saleSheetDet.item, obj.saleSheetDet.batch]
        }

        if (obj.qty <= 0) {
            return ["sheet.qty.min.error", obj]
        }

        // 退單數量不可大於來源單據數量
        if (obj.qty > obj.saleSheetDet.qty) {
            return ["returnSheetDetail.qty.max.error", obj.saleSheetDet.qty]
        }

        //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
        if (obj.warehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.warehouse.factory, obj.header.factory]
        }
    }
}
