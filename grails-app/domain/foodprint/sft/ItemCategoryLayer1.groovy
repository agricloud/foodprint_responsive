package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.*

/**
 * 品項類別 第一層
 */
class ItemCategoryLayer1 {

    def grailsApplication
    def messageSource

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
    String title

    static hasMany = [itemCategoryLayer2: ItemCategoryLayer2]

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"

    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ItemCategoryLayer1]
        "${getMessageSource().getMessage("${i18nType}.itemCategoryLayer1.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
