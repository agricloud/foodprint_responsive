package foodprint.sft

import foodprint.erp.Site

/**
 * 品項類別 第二層
 */
class ItemCategoryLayer2 {

    def grailsApplication
    def messageSource

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [itemCategoryLayer1: ItemCategoryLayer1]
    String name
    String title

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["itemCategoryLayer1", "site"])
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
        Object[] args = [ItemCategoryLayer2]
        "${getMessageSource().getMessage("${i18nType}.itemCategoryLayer2.label", args, Locale.getDefault())}: " +
        "${itemCategoryLayer1.name}-${itemCategoryLayer1.title}/${name}-${title}"
    }
}
