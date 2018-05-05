package foodprint.erp

/**
 * 品牌
 * 品牌不等於製造商
 * 若未來需要製造商資訊
 * 須另外設計製造商domain
 * 一品牌可有多製造商生產 一製造商也可以生產多種品牌
 * 因此製造商應同供應商 跟隨進貨單單身紀錄
 * 製造商可紀錄country資訊 則進貨單可不必輸入產地 改由製造商自動帶入
 */
class Brand {

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
     * 品牌編號 (Primary Key)
     */
    String name

    /**
     * 品牌名稱
     */
    String title

    /**
     * 品牌描述
     */
    String description

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
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Brand]
        "${getMessageSource().getMessage("${i18nType}.brand.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
