package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.BatchOperation
import foodprint.erp.TransferOrderDet

/**
 * 製令進出站記錄檔
 * 說明: 用於紀錄生產線活動狀態
 * 參考: SFT_WS_RUN
 */
class WorkstationRealtimeRecord{

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
     * 項次
     */
    int sequence

    /**
     * 執行日期
     */
    Date executeDate

    /**
     * 生產數量
     */
    double qty


    /**
     * 生產線執行的類型
     */
    WorkstationRealtimeRecordType recordType

    /**
     * 批號製程
     */
    BatchOperation batchOperation

    /**
     * 派工項次
     * 對應EQUIPMENT_RUN.UNIID, 無派工為-1。
     */
    int dispatchSequence = -1


    /**
     * 移轉單單身
     */
    TransferOrderDet transferOrderDet

    static constraints = {
        editor nullable: true
        creator nullable: true
        batch unique: ["sequence", "site"]
        batchOperation nullable: true
        qty min: 0.0d
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
