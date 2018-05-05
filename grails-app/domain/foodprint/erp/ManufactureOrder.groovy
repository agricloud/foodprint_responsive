package foodprint.erp

import foodprint.common.DomainDataException
import java.util.Locale

/**
 * 製造命令
 */
class ManufactureOrder {

    def grailsApplication
    def messageSource
    def enumService

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
     * 製令類別
     * 廠內or廠外
     */
    ManufactureType manufactureType

    /**
     * 製令狀態
     */
    ManufactureOrderStatus status = ManufactureOrderStatus.PENDING
    /**
     * 廠別
     */
    Factory factory

    Workstation workstation
    Supplier supplier

    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet

    /**
     * 預計批號
     */
    String batchName

    /**
     * 品項編號
     */
    Item item

    /**
     * 預計數量
     */
    double expectQty

    static hasMany = [
        /**
         * 批號
         */
        batches: Batch
    ]


    /**
     * 是否有拆批
     */
    boolean isSplit = false

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
        batchName unique: true, blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        workstation nullable: true
        supplier nullable: true
        customerOrderDet nullable: true
        expectQty min: 0.0d
    }

    def beforeDelete() {
        // 試為何beforedelete可正常執行 update無法
        def i18nType = getGrailsApplication().config.grails.i18nType
        // 製令狀態為已發料、生產中、完工、結案者不可刪除
        if (!(status in [ManufactureOrderStatus.PENDING, ManufactureOrderStatus.APPROVED])) {
            throw new DomainDataException(getMessageSource().getMessage("${i18nType}.manufactureOrder.cannot.delete", (Object[])[enumService.name(status).title], Locale.getDefault()))
        }
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ManufactureOrder]
        "${getMessageSource().getMessage("${i18nType}.manufactureOrder.label", args, Locale.getDefault())}: " +
        "${typeName}-${name}"
    }

    static validator = { col, obj ->

        // 製令已發放，不可變更單據日期。
        if (obj.status != ManufactureOrderStatus.PENDING && obj.isDirty("executionDate")) {
            return ["manufactureOrder.status.executionDate.cannot.change", obj.enumService.name(obj.status).title]
        }

        // 廠內製令須輸入工作站
        if (obj.manufactureType == ManufactureType.FACTORY && !obj.workstation) {
            return ["manufactureOrder.manufactureType.FACTORY.workstation.nullable.error"]
        }
        // 廠內製令不可輸入供應商
        if (obj.manufactureType == ManufactureType.FACTORY && obj.supplier) {
            return ["manufactureOrder.manufactureType.FACTORY.supplier.should.be.null"]
        }
        // 託外製令須輸入供應商
        if (obj.manufactureType == ManufactureType.OUTSRC && !obj.supplier) {
            return ["manufactureOrder.manufactureType.OUTSRC.supplier.nullable.error"]
        }
        // 託外製令不可輸入工作站
        if (obj.manufactureType == ManufactureType.OUTSRC && obj.workstation) {
            return ["manufactureOrder.manufactureType.OUTSRC.workstation.should.be.null"]
        }

        // 廠別與工作站廠別不符
        if (obj.workstation && obj.factory!=obj.workstation.factory) {
            return ["manufactureOrder.factory.workstation.factory.not.equal", obj.factory, obj.workstation.factory]
        }

        // 供應商須為製造商
        if (obj.supplier && !obj.supplier.isManufacturer) {
            return ["manufactureOrder.supplier.isManufacturer.error"]
        }

        if (obj.expectQty <= 0) {
            return ["sheet.qty.min.error", obj]
        }
    }
}
