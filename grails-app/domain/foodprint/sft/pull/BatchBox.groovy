package foodprint.sft.pull

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.Factory
import foodprint.erp.Warehouse
/**
 * 批量成形箱
 *
 * 說明：用於記錄待生產品項的累計量及設定開立生產看板之基準
 */
class BatchBox {

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
     * 廠別
     */
    Factory factory

    /**
     * 倉庫
     */
    Warehouse warehouse 

    /**
     * 品項
     */
    Item item

    /**
     * 最近加入累積日
     */
    Date lastInDate

    /**
     * 最近成形批量日
     */
    Date lastFormDate

    /**
     * 總累積數量(待生產數量)
     */
    double qty

    /**
     * 單張看板數量
     */
    double kanbanQty = 0.0d

    /**
     *  單張看板累積數量
     */
    double accumulationQty = 0.0d

    /**
     * 批量成形水準(生產數量)
     */
    double formLevel

    /**
     * 自動拆批
     */
    boolean autoSplit = true

    /**
     * 拆批數量
     */ 
    double splitQty = 1.0d

    static hasMany = [batchBoxDets: BatchBoxDet]

    static constraints = {
        editor nullable: true
        creator nullable: true
        item unique: ["site"]
        lastInDate nullable: true
        lastFormDate nullable: true
        qty min: 0.0d
        formLevel min: 0.0d
        accumulationQty min: 0.0d
        kanbanQty min: 0.0d
        splitQty mid : 0.0d
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [BatchBox]
        "${getMessageSource().getMessage("${i18nType}.batchBox.label", args, Locale.getDefault())}: ${item}"
    }

}
