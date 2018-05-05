package foodprint.erp

import foodprint.common.Country

/**
 * 製造商
 * 採購的廠商為供應商，實際生產產品的為製造商。
 * 若採購的廠商即為實際的製造商，則須同時建置供應商與製造商，ex:託外製造。
 */
class Manufacturer {

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
     * 編號
     */
    String name

    /**
     * 名稱
     */
    String title

    /**
     * 是否為供應商
     */
    Boolean isSupplier = false

    static belongsTo = [
        /**
         * 對應供應商
         */
        supplier: Supplier
    ]

    /**
     * 國別
     */
    Country country

    /**
     * 電話
     */
    String tel

    /**
     * 傳真
     */
    String fax

    /**
     * 連絡人
     */
    String contact
    
    /**
     * 電子信箱
     */
    String email
    
    /**
     * 地址
     */
    String address

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*", validator: validator
        supplier nullable: true
        tel nullable: true
        fax nullable: true
        contact nullable: true
        email nullable: true, email: true
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
        Object[] args = [Manufacturer]
        "${getMessageSource().getMessage("${i18nType}.manufacturer.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

    static validator = { col, obj ->

        if (obj.tel && !(obj.tel ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
            return ["manufacturer.tel.invalid"]
        }

        if (obj.fax && !(obj.fax ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
            return ["manufacturer.fax.invalid"]
        }

        if (obj.email && !(obj.email ==~ /^[_a-zA-Z0-9-]+([.][_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+([.][a-zA-Z0-9-]+)*$/)) {
            return ["manufacturer.email.invalid"]
        }
    }
}
