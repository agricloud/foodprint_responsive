package foodprint.erp.ChangeOrder

import foodprint.erp.Site
import foodprint.erp.TypeName
import foodprint.erp.Factory

/**
 * 庫存異動單
 * 參考：INVTA
 */
class InventoryTransactionSheet {

    def grailsApplication
    def messageSource
    def sheetService

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
     * 庫存異動單單身
     */
    static hasMany = [details: InventoryTransactionSheetDet]

    /**
     * 部門
     */
    // Department department

    /*
     * 備註
     */
    String remark

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
        name(unique: ["typeName", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        typeName validator: validator
        remark nullable: true

    }

    def beforeUpdate() {
        //若變更單據日期，須連同變更庫存異動紀錄日期。
        sheetService.updateInventoryTransactionRecordExecutionDate(this, details)
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [InventoryTransactionSheet]
        "${getMessageSource().getMessage("${i18nType}.inventoryTransactionSheet.label", args, Locale.getDefault())}: " +
        "${typeName.name}-${name}"
    }

    static validator = { col, obj ->
        // 單身建立後不允許變更廠別
        if (obj.details && obj.isDirty("factory")) {
            return ["sheet.details.exists.factory.cannot.change", obj]
        }
    }
}
