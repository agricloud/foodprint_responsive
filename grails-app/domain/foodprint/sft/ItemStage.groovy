package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Item

/**
 * 品項生產階段
 */
class ItemStage {

    def grailsApplication
    def messageSource

    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    Site site

    static belongsTo = [
        item: Item
	]
    int sequence
    String title
    String description


    static constraints = {
        editor nullable: true
        creator nullable: true
        sequence(unique: ["item", "site"])
        description nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ItemStage]
        "${getMessageSource().getMessage("${i18nType}.itemStage.label", args, Locale.getDefault())}: " +
        "${item.name}-${item.title}/${sequence}-${title}"
    }
}
