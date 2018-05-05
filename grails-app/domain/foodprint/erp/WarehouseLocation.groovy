package foodprint.erp

/**
 * 儲位
 */
class WarehouseLocation {

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

    static belongsTo = [
        /**
         * 所屬倉庫
         */
        warehouse: Warehouse
    ]

    /**
     * 編號
     */
    String name

    /**
     * 名稱
     */
    String title

    /**
     * 描述
     */
    String description

    /**
     *  容量
     */
    double capacity = Double.MAX_VALUE

    /**
     *  容量單位
     */
    String capacityUnit

    /**
     * 備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }
    
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        name(unique: ["warehouse", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        description nullable: true
        capacity min: 0.0d
        capacityUnit nullable: true
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
        Object[] args = [WarehouseLocation]
        "${getMessageSource().getMessage("${i18nType}.warehouseLocation.label", args, Locale.getDefault())}: " +
        "${warehouse.name}-${warehouse.title}/${name}-${title}"
    }
}
