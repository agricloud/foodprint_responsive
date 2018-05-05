package foodprint.sft

import foodprint.erp.Site

/**
 * 製程類別 第一層
 */
class OperationCategoryLayer1 {

    def grailsApplication
    def messageSource

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
    String title

    static mapping = {
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
        Object[] args = [OperationCategoryLayer1]
        "${getMessageSource().getMessage("${i18nType}.operationCategoryLayer1.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
