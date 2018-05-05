package foodprint.erp

import foodprint.sft.MaterialSheetDetCustomizeDet
/**
 * 領料單身
 */
class MaterialSheetDet {

    def grailsApplication
    def messageSource
    def enumService

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

    MaterialSheet header

    /**
     * 製令
     */
    ManufactureOrder manufactureOrder

    /** 
     * 領料製程 Batch.findByManufactureOrder(manufactureOrder).batchOperations*.operation
     */
    BatchOperation batchOperation

    /**
     * 投料順序
     */
    int releaseOrder = 1

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
     * 領料數量
     */
    double qty

    static hasMany = [materialSheetDetCustomizeDets: MaterialSheetDetCustomizeDet]

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
        releaseOrder min: 1
        qty min: 0.0d, notEqual: 0d
        remark nullable: true
        warehouseLocation nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [MaterialSheetDet]
        "${getMessageSource().getMessage("${i18nType}.materialSheetDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }

        //「製令」狀態爲完工或結案不允許儲存
        if (obj.manufactureOrder.status in [ManufactureOrderStatus.FINISHED, ManufactureOrderStatus.ASSIGNEDFINISHED]) {
            return ["materialSheetDet.manufactureOrder.status.finished", obj.manufactureOrder, obj.enumService.name(obj.manufactureOrder.status).title]
        }

        //「單頭廠別」與「製令廠別」不同不允許儲存
        if (obj.header.factory != obj.manufactureOrder.factory) {
            return ["materialSheetDet.header.factory.manufactureOrder.factory.not.equal", obj.header.factory, obj.manufactureOrder.factory]
        }

        //「單頭工作站、供應商」與「製令工作站、供應商」不同不允許儲存
        if (obj.header.workstation!=obj.manufactureOrder.workstation || obj.header.supplier!=obj.manufactureOrder.supplier) {
            return ["materialSheetDet.header.workstationOrSupplier.manufactureOrder.workstationOrSupplier.not.equal", obj.header.workstation, obj.header.supplier, obj.manufactureOrder.workstation, obj.manufactureOrder.supplier]
        }

        // 領料製程不存於批號製程不允許儲存
        // note: Batch.findByNameAndSite(obj.manufactureOrder.batchName, obj.site).batchOperations 在某些情況為 javassist class，
        // 使用 obj.batchOperation in Batch.findByNameAndSite(obj.manufactureOrder.batchName, obj.site).batchOperations 會造成判斷錯誤。
        if (!(obj.batchOperation.batch == Batch.findByNameAndSite(obj.manufactureOrder.batchName, obj.site))) {
            return ["materialSheetDet.batchOperation.error"]
        }

        //「單身品項」與「製令品項」相同不允許儲存
        if (obj.item == obj.manufactureOrder.item) {
            return ["materialSheetDet.item.manufactureOrder.item.cannot.equal", obj.item.name, obj.manufactureOrder.typeName, obj.manufactureOrder.name]
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
