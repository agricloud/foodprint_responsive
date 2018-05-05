package foodprint.erp

/**
 * 領退單身
 */
class MaterialReturnSheetDet {

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

    MaterialReturnSheet header

    /**
     * 製令
     */
    ManufactureOrder manufactureOrder

    MaterialSheetDet materialSheetDet

    /**
     * 批號
     */
    Batch batch
    
    /**
     * 品項編號，材料編號
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
     * 退料數量
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
        Object[] args = [MaterialReturnSheetDet]
        "${getMessageSource().getMessage("${i18nType}.materialReturnSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        // 單頭單據日期不可小於來源單據單頭日期
        if (obj.header.executionDate < obj.materialSheetDet.header.executionDate) {
            return ["returnSheetDetail.header.executionDate.min.error"]
        }

        //「單頭廠別」與「領料單頭廠別」不同不允許儲存
        if (obj.header.factory != obj.materialSheetDet.header.factory) {
            return ["materialReturnSheetDet.header.factory.materialSheetDet.header.factory.not.equal", obj.header.factory, obj.materialSheetDet.header.factory]
        }

        //「單頭工作站、供應商」與「領料單頭工作站、供應商」不同不允許儲存
        if (obj.header.workstation!=obj.materialSheetDet.header.workstation || obj.header.supplier!=obj.materialSheetDet.header.supplier) {
            return ["materialReturnSheetDet.header.workstationOrSupplier.materialSheetDet.header.workstationOrSupplier.not.equal", obj.header.workstation, obj.header.supplier, obj.materialSheetDet.header.workstation, obj.materialSheetDet.header.supplier]
        }

        //「單身製令」與「領料單身製令」不同不允許儲存
        if (obj.manufactureOrder != obj.materialSheetDet.manufactureOrder) {
            return ["materialReturnSheetDet.manufactureOrder.materialSheetDet.manufactureOrder.not.equal", obj.manufactureOrder, obj.materialSheetDet.manufactureOrder]
        }

        //「單身品項、批號」與「領料單身品項、批號」不同不允許儲存
        if (obj.item!=obj.materialSheetDet.item || obj.batch!=obj.materialSheetDet.batch) {
            return ["materialReturnSheetDet.itemOrBatch.materialSheetDet.itemOrBatch.not.equal", obj.item, obj.batch, obj.materialSheetDet.item, obj.materialSheetDet.batch]
        }

        if (obj.qty <= 0) {
            return ["sheet.qty.min.error", obj]
        }

        // 退單數量不可大於來源單據數量
        if (obj.qty > obj.materialSheetDet.qty) {
            return ["returnSheetDetail.qty.max.error", obj.materialSheetDet.qty]
        }

        //「單身倉庫廠別」與「單頭廠別」不同不允許儲存
        if (obj.warehouse.factory != obj.header.factory) {
            return ["sheetDetail.header.factory.warehouse.factory.not.equal", obj.warehouse.factory, obj.header.factory]
        }
    }
}
