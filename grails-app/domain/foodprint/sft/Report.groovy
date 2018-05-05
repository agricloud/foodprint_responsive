package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Item

/**
 * 記錄各種履歷類型主檔
 */
class Report {

    def grailsApplication
    def messageSource

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
    String title
    Item item
    String description
    Date effectStartDate
    Date effectEndDate
    ReportType reportType = ReportType.OTHER

    static hasMany = [
        reportParams: ReportParam
    ]

    static mapping = {
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        description nullable: true
        effectStartDate nullable: true
        effectEndDate nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Report]
        "${getMessageSource().getMessage("${i18nType}.report.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
