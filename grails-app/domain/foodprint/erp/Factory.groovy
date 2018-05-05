package foodprint.erp
/**
 * 廠別
 */
class Factory {

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
     * 電話
     */
    String tel

    /**
     * 傳真
     */
    String fax

    /**
     * 電子信箱
     */
    String email

    /**
     * 地址
     */
    String address

    /**
     * 備註
     */
    String remark

    static hasMany = [workstations: Workstation]

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
        tel nullable: true
        fax nullable: true
        email nullable: true, email: true
        address nullable: true
        remark nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Factory]
        "${getMessageSource().getMessage("${i18nType}.factory.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

    static validator = { col, obj ->
        if (obj.tel && !(obj.tel ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
        return ["factory.tel.invalid"]
        }

        if (obj.fax && !(obj.fax ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
        return ["factory.fax.invalid"]
        }

        if (obj.email && !(obj.email ==~ /^[_a-zA-Z0-9-]+([.][_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+([.][a-zA-Z0-9-]+)*$/)) {
        return ["factory.email.invalid"]
        }
    }
}
