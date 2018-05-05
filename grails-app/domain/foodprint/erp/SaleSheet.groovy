package foodprint.erp
/**
 * 銷貨單單頭
 */
class SaleSheet {

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
     * 出貨廠別
     */
    Factory factory

    /**
     * 客戶
     */
    Customer customer

    static hasMany = [details: SaleSheetDet]

    /**
     * 送貨地址
     */
    String shippingAddress

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
        shippingAddress nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [SaleSheet]
        "${getMessageSource().getMessage("${i18nType}.saleSheet.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}"
    }

    static validator = { col, obj ->

        
        if (obj.details) {
            def result = false
            def sourceSheetResult = false
            obj.details.find { detail ->
                // 單據日期不可小於來源單據日期。
                if (obj.executionDate < detail.customerOrderDet?.header.executionDate) {
                    sourceSheetResult = true
                    return true
                }
                // 單據日期不可大於退單單據日期。
                def returnDetails = SaleReturnSheetDet.findAllBySaleSheetDetAndSite(detail, obj.site)
                returnDetails.find { returnDetail ->
                    if (obj.executionDate > returnDetail.header.executionDate) {
                        result = true 
                    }
                    return result // true: break; false: keep looping;
                }
                return result // true: break; false: keep looping;
            }
            if (sourceSheetResult) return ["sheet.executionDate.min.error"]
            if (result) return ["sheet.executionDate.max.error"]
        }
    }
}
