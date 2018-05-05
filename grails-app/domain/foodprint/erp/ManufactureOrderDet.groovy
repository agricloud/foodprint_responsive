package foodprint.erp

/**
 * 製造命令單身
 * 說明：依bom表展算出須領料量，或由使用者自行帶入須領材料及數量，並記錄實際領料狀況。
 * reference: ERP MOCTB
 * note: 對應運搬看板
 */
class ManufactureOrderDet {

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
     * 製令
     */
    ManufactureOrder header
    
    /**
     * 製令單別
     */
    TypeName typeName

    /**
     * 製令單號
     */
    String name

    Item item

    Batch batch

    /**
     * 須領用量
     * 依據BOM材料用量建立的組成用量關係自動展算。
     * 亦可自行輸入。
     */

    double expectQty

    /**
     * 已領用量
     * 領料單、退料單之領料數量回寫。
     */
    double qty

    /** 
     * 投料製程
     */
    Operation operation

    /**
     * 投料順序
     */
    int releaseOrder = 1

    /**
     * 庫別
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 預計領料日
     * 如果是依照BOM用量資料展單身材料品號者，則預計領料日＝預計開工日＋BOM用量資料建立作業的投料時距。
     * 亦可自行輸入。
     */
    Date expectPickingDate

    /**
     * 實際領料日
     */
    Date pickingDate

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
        header updateable: false
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        typeName validator: validator
        name(unique: ["typeName", "item", "operation", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        batch nullable: true        
        warehouse nullable: true        
        warehouseLocation nullable: true        
        expectQty min: 0.0d
        qty min: 0.0d
        expectPickingDate nullable: true
        pickingDate nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ManufactureOrderDet]
        "${getMessageSource().getMessage("${i18nType}.manufactureOrderDet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${operation.name}-${item.name}"
    }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }
    }
}
