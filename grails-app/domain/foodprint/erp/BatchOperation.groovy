package foodprint.erp

import foodprint.sft.ItemStage

/**
 * 批號製程
 */
class BatchOperation {

    def grailsApplication
    def messageSource
    
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
     * 批號
     */
    Batch batch

    /**
     * 工作站
     */
    Workstation workstation

    /**
     * 供應商
     */
    Supplier supplier
    
    /**
     * 製程
     */
    Operation operation

    /**
     * 工序
     */
    int sequence

    /**
     * 開始加工時間
     */
    Date startDate

    /**
     * 結束加工時間
     */
    Date endDate


    /**
     * 作業人員
     */
    Employee operator

    /**
     * 生產階段
     */
    ItemStage itemStage

    /**
     * 備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        sequence unique: ["batch", "site"]
        sequence validator: validator
        startDate nullable: true
        endDate nullable: true
        workstation nullable: true
        supplier nullable: true
        operator nullable: true
        itemStage nullable: true
        remark nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [BatchOperation]
        "${getMessageSource().getMessage("${i18nType}.batchOperation.label", args, Locale.getDefault())}: " +
        "${batch.name}-${sequence}"
    }

    static validator = { col, obj ->
        if (obj.batch.batchSourceType == BatchSourceType.PURCHASE)
            return ["batchOperation.batch.batchSourceType.error"]
        // 工作站 & 供應商應存在其一
        if ((!obj.workstation && !obj.supplier)||(obj.workstation && obj.supplier)) {
            return ["batchOperation.workstation.supplier.should.exists.one"]
        }
        // 職員類型需為作業員
        if (obj.operator && obj.operator.employeeType != EmployeeType.OPERATOR) {
            return ["batchOperation.operator.employeeType.error", obj.operator, obj.messageSource.getMessage("${obj.getGrailsApplication().config.grails.i18nType}.employeeType.OPERATOR.label",(Object[]) [], Locale.getDefault())]
        }
    }
}
