package foodprint.erp
/**
 * 庫存
 */
class Inventory {

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
     * 倉庫
     */
    Warehouse warehouse

    /**
     * 品項
     */
    Item item

    /**
     * 數量
     */
    double qty = 0.0d

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable: true
        item(unique: ["warehouse", "site"])
        editor nullable: true
        creator nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Inventory]
        "${getMessageSource().getMessage("${i18nType}.inventory.label", args, Locale.getDefault())}: " +
        "${warehouse.name}-${warehouse.title}/${item.name}-${item.title}/${item.brand.name}-${item.brand.title}"
    }
}
