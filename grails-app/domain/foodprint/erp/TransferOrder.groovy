package foodprint.erp

/**
 * 移轉單單頭
 */
class TransferOrder{

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
     * 自訂單據執行日期
     * note: 前端傳入格式須為日期(不包含時間)，實際存入資料為filters轉換處理時區後的日期。
     */
    Date executionDate = new Date()

    /**
     * 公司
     */
    Site site

    /**
     * 單別
     */
    TypeName typeName

    /**
     * 單號
     */
    String name

    /**
     * 移轉日期 //是否與單據日期相同待確認
     */
    Date transferDate

    /**
     * 廠別
     */
    Factory factory

    /**
     * 移出類別
     * ex: 生產線別、託外廠商、倉庫
     */
    TransferType transferOutType

    /**
     * 移出部門：工作站
     */
    Workstation outWorkstation

    /**
     * 移出部門：託外廠商
     */
    Supplier outSupplier

    /**
     * 移出部門：倉庫
     */
    Warehouse outWarehouse

    /**
     * 移出部門：儲位
     */
    WarehouseLocation outWarehouseLocation

    /**
     * 移入類別
     * ex: 生產線別、託外廠商、倉庫
     */
    TransferType transferInType

    /**
     * 移入部門：工作站
     */
    Workstation inWorkstation

    /**
     * 移入部門：託外廠商
     */
    Supplier inSupplier

    /**
     * 移入部門：倉庫
     */
    Warehouse inWarehouse

    /**
     * 移入部門：儲位
     */
    WarehouseLocation inWarehouseLocation

    /**
     * 廠商單號：廠商自己的單號
     */
    String manufacturerOrderNo



    static hasMany = [details: TransferOrderDet]

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
    }
    
    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: ["typeName", "site"]
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        manufacturerOrderNo nullable: true
        outWorkstation nullable: true 
        outSupplier nullable: true 
        outWarehouse nullable: true 
        outWarehouseLocation nullable: true 
        inWorkstation nullable: true 
        inSupplier nullable: true 
        inWarehouse nullable: true 
        inWarehouseLocation nullable: true 
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    // public String toString() {
    //     def i18nType = getGrailsApplication().config.grails.i18nType
    //     Object[] args = [Batch]
    //     "${getMessageSource().getMessage("${i18nType}.batch.label", args, Locale.getDefault())}: ${name}"
    // }

}
