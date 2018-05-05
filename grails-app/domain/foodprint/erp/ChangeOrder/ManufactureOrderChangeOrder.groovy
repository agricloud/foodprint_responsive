package foodprint.erp.ChangeOrder

import foodprint.erp.*

/**
 * 製造命令變更單
 */
class ManufactureOrderChangeOrder {

    def grailsApplication
    def messageSource
    def dateService

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

    String originVersion
    String originEditor
    String originCreator
    Date originDateCreated
    Date originLastUpdated

    ManufactureOrder manufactureOrder
    /**
     * 單別
     */
    TypeName typeName

    /**
     * 單號
     */
    String name

    /**
     * 變更版次
     */
    int sequence

    /*
     * 變更原因
     */
    String reason

    /**
     * 自訂單據日期：變更日期
     * note: 前端傳入格式須為日期(不包含時間)，實際存入資料為filters轉換處理時區後的日期。
     */
    Date executionDate

    /**
     * 製令狀態
     */
    ManufactureOrderStatus status
    ManufactureOrderStatus originStatus
    /**
     * 廠別
     */
    Factory factory
    Integer originFactoryId

    Workstation workstation
    Integer originWorkstationId //可能為null，因此須使用Interger類。

    Supplier supplier
    Integer originSupplierId

    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet
    Integer originCustomerOrderDetId

    /**
     * 品項編號
     */
    Item item
    String originItemId

    /**
     * 生產量
     */
    double expectQty
    double originExpectQty

    /**
     * 預計批號
     */
    String batchName
    String originBatchName

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
        executionDate updateable: false
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        originEditor nullable: true
        originCreator nullable: true
        typeName validator: validator
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        sequence(unique: ["typeName", "name", "site"])
        reason nullable: true
        workstation nullable: true
        originWorkstationId nullable: true
        supplier nullable: true
        originSupplierId nullable: true
        customerOrderDet nullable: true
        originCustomerOrderDetId nullable: true
        expectQty min: 0.0d
        originExpectQty min: 0.0d
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ManufactureOrderChangeOrder]
        "${getMessageSource().getMessage("${i18nType}.manufactureOrderChangeOrder.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}-${sequence}"
    }

    static validator = { col, obj ->
        if (obj.executionDate > obj.manufactureOrder.executionDate) {
            return ["manufactureOrderChangeOrder.executionDate.error", obj.dateService.formatWithISO8601(obj.executionDate), obj.dateService.formatWithISO8601(obj.manufactureOrder.dateCreated)]
        }
    }
}
