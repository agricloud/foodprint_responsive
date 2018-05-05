package foodprint.erp

import foodprint.sft.DispatchType
import foodprint.sft.WorkstationStatus

/**
 * 工作站/生產線
 */
class Workstation {

    def grailsApplication
    def messageSource
    def domainService

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
     * 編號
     */
    String name

    /**
     * 名稱
     */
    String title

    /**
     * 追蹤模式
     * ex: 派工、非派工、追蹤機台加工時間但不派工
     */
    DispatchType dispatchType = DispatchType.NONDISPATCH



    static belongsTo = [
        /**
         * 所屬廠別
         */
        factory: Factory
    ]

    WorkstationStatus status = WorkstationStatus.IDLE
    
    /**
     * 描述
     */
    String description

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        description nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Workstation]
        "${getMessageSource().getMessage("${i18nType}.workstation.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }
}
