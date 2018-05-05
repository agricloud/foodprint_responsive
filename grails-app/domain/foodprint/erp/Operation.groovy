package foodprint.erp   

import foodprint.sft.OperationCategoryLayer1

/**
 * 製程
 */
class Operation {

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
     * 製程編號
     */
    String name

    /**
     * 製程名稱
     */
    String title

    /**
     * 製程敘述
     */
    String description

    /**
     * 製程類別(一)
     */
    OperationCategoryLayer1 operationCategoryLayer1

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
        description nullable: true
        operationCategoryLayer1 nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Operation]
        "${getMessageSource().getMessage("${i18nType}.operation.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
