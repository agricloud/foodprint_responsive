package foodprint.erp

/**
 * 品項途程
 */
class ItemRoute {

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
     * 所屬品項
     */
    static belongsTo = [item: Item]

    /**
     * 工序
     */
    int sequence

    /**
     * 製程
     */
    Operation operation

    /**
     * 工作站
     */
    Workstation workstation

    /**
     * 供應商
     */
    Supplier supplier

    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        sequence(unique: ["item", "site"])
        sequence validator: validator
        workstation nullable: true
        supplier  nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ItemRoute]
        "${getMessageSource().getMessage("${i18nType}.itemRoute.label", args, Locale.getDefault())}: " +
        "${item.name}-${item.title}/${item.brand.name}-${item.brand.title}/${sequence}"
    }

    static validator = { col, obj ->
        // 工作站 & 供應商應存在其一
        if ((!obj.workstation && !obj.supplier)||(obj.workstation && obj.supplier)) {
            return ["itemRoute.workstation.supplier.should.exists.one"]
        }
    }
}
