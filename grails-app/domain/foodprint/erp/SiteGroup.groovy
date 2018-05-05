package foodprint.erp

import foodprint.authority.User
/**
 * 公司群組
 */
class SiteGroup {

    def grailsApplication
    def messageSource

    String importFlag = -1

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
     * 編號
     */
    String name = UUID.randomUUID().toString()

    /**
     * 名稱
     */
    String title

    static hasMany = [
        /**
         * 公司
         */
        sites: Site,

        /**
         * 使用者
         */
        users: User
    ]

    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: true, blank: false
    }

    Integer getUserCount() {
        users?.size() ?: 0
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [SiteGroup]
        "${getMessageSource().getMessage("${i18nType}.siteGroup.label", args, Locale.getDefault())}: "+
        "${name}-${title}"
    }
}
