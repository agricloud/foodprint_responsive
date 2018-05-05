package foodprint.erp

import foodprint.authority.UserSite

/**
 * 公司
 */
class Site {

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
     * 地址
     */
    String address

    /**
     * 註冊碼
     */
    String activationCode

    /**
     * 公司群組
     */
    SiteGroup siteGroup

    static hasMany = [
        /**
         * 使用者
         */
        userSites: UserSite
    ]

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
        siteGroup updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: true, blank: false, matches: "[a-zA-Z][_a-zA-Z0-9-]{3,}"
        description nullable: true
        address nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Site]
        "${getMessageSource().getMessage("${i18nType}.site.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
