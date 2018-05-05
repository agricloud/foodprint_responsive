package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.BatchOperation
import foodprint.erp.Operation

/**
 * 批量即時生產記錄/批量資料檔
 *
 * 參考: LOT
 */
class BatchRealtimeRecord {

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
     * 發放日期
     */
    Date releaseDate

    /**
     * 生產數量
     */
    double qty


    /**
     * 狀態
     * 若為尚未加工/新增之批量，則此欄位不須輸入值
     * ex: 待進站, 待出站, 檢驗中, 批量已經完工, 指定完工, 待倉管入庫
     */
    BatchStatus status = BatchStatus.PENDING

    /**
     * 批號製程
     */
    BatchOperation batchOperation

    /**
     * 交期
     */
    Date dueDate

    /**
     * 優先值
     */
    int priority

    /**
     * 已加工時間
     *
     * 定義現場WIP批量報工時點批量進行整置/加工/後置整置狀態之已執行時間，
     * 此欄位對應LOT.TYPE & LOT.STATUS欄位之資訊，
     * 若為已RELEASE至現場之WIP(LOT.TYPE=0) & LOT.STATUS = 10, 20, 40，
     * LOT.OPERATEDTIME 欄位所輸入之值才有意義。
     * 舉例而言，LOT.STATUS = 20，LOT.OPERATEDTIME =300，
     * 則表示該LOT目前在加工中，且已加工300秒。LOT.OPERATEDTIME = NOW – CHECK IN 。
     * 時間單位為 “秒”。預設值為-1。
     */
    long operatedTime = -1

    /**
     * 剩餘作業時間
     * 定義現場WIP批量報工時點批量進行整置/加工/後置整置狀態之剩餘作業時間，
     * 此欄位與LOT.OPERATEDTIME，兩者擇一輸入即可。時間單位為 “秒”。預設值為-1。 
     */
    // long remainTime

    /**
     * 預計產出量
     */
    double expectQty

    /**
     * 實際產出量
     */
    double outputQty

    /**
     * 領料狀態
     * ex: 未領料, 部分領料, 完全領料
     */
    PickingStatus pickingStatus

    /**
     * 重工製程
     */
    Operation reworkOperation


    static constraints = {
        editor nullable: true
        creator nullable: true
        batch unique: ["batchOperation", "status", "site"]
        batchOperation nullable: true
        releaseDate nullable: true
        qty min: 0.0d
        expectQty min: 0.0d
        outputQty min: 0.0d
        reworkOperation nullable: true
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
