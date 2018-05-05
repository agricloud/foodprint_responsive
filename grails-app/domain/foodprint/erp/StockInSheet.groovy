package foodprint.erp
/**
 * 入庫單
 */
class StockInSheet {

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
     * 單別
     */
    TypeName typeName

    /**
     * 單號
     */
    String name

    /**
     * 自訂單據日期
     * note: 前端傳入格式須為日期(不包含時間)，實際存入資料為filters轉換處理時區後的日期。
     */
    Date executionDate = new Date()

    /**
     * 廠別
     */
    Factory factory

    /**
     * 生產線別
     */
    Workstation workstation

    static hasMany = [details: StockInSheetDet]

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        typeName validator: validator
        name(unique: ["typeName", "site"])
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
        Object[] args = [StockInSheet]
        "${getMessageSource().getMessage("${i18nType}.stockInSheet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}"
    }

    static validator = { col, obj ->

        // 廠別與工作站廠別不符
        if (obj.workstation && obj.factory!=obj.workstation.factory) {
            return ["stockInSheet.factory.workstation.factory.not.equal", obj.factory, obj.workstation.factory]
        }
    }
}
