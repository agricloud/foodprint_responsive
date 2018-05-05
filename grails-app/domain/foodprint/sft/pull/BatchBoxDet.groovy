package foodprint.sft.pull

import foodprint.erp.Site
import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation
/**
 * 批量成形箱細項
 *
 * 說明：用於累積已被領取待補充生產的數量
 */
class BatchBoxDet {

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
     * 公司
     */
    Site site

    /**
     * 單頭
     */
    BatchBox batchBox

    int sequence


    /**
     * 類型
     * ex: 一般、緊急(補充用)、緊急(重工報廢用)
     */
    BatchBoxDetType batchBoxDetType = BatchBoxDetType.NORMAL

    /**
     * 倉庫
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation

    /**
     * 數量
     */
    double qty

    /**
     * 轉換生產看板日期
     */
    Date formDate

    /**
     * 批量成形狀態碼
     * ex: 尚未轉換, 已轉換完全數量, 部分數量被轉換
     */
    BatchFormStatus status = BatchFormStatus.PENDING

    static constraints = {
        editor nullable: true
        creator nullable: true
        sequence unique: ["batchBox", "site"]
        qty min: 0.0d
        formDate nullable: true
        warehouseLocation nullable:true
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