package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.BatchOperation
import foodprint.erp.TransferOrderDet
/**
 * 批號製程即時生產紀錄
 * 說明: 用於紀錄實際加工之過程
 * 參考: SFT_OP_REALRUN
 */
class BatchOperationRealtimeRecord {

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
     * 批量
     */
    Batch batch

    /**
     * 批號製程
     */
    BatchOperation batchOperation

    /**
     * 資料紀錄的類型
     * 記錄途程單顯示的資料(BATCHOPERATION)；或記錄報工(WORKREPORT)的資料。
     * sft欄位：項次
     */
    BatchOperationRealtimeRecordType recordType

    /**
     * 進站數量
     */
    double checkInQty

    /**
     * 出站數量
     */
    double checkOutQty

    /**
     * 進站時間
     */
    Date checkInDate

    /**
     * 出站時間
     */
    Date checkOutDate

    /**
     * 報廢數量
     */
    double scrapQty

    /**
     * 重工數量
     */
    double reworkQty

    /**
     * 短少數量
     */
    double shortQty

    /**
     * 多餘數量
     */
    double surplusQty
    

    /**
     * 移轉單單身
     */
    TransferOrderDet transferOrderDet


    static constraints = {
        editor nullable: true
        creator nullable: true
        batchOperation unique: ["batch", "recordType", "site"]
        checkInQty min: 0.0d
        checkOutQty min: 0.0d
        checkInDate nullable: true
        checkOutDate nullable: true
        scrapQty min: 0.0d
        reworkQty min: 0.0d
        shortQty min: 0.0d
        surplusQty min: 0.0d
        transferOrderDet nullable: true
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
