package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Workstation
import foodprint.erp.Supplier
import foodprint.erp.Operation
/**
 * 履歷要收集的資料項目，其中可以設定項目要收集的對象
 */
class ReportParam {

    def grailsApplication
    def messageSource

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [report: Report]

    Param param
    Workstation workstation
    Supplier supplier
    Operation operation

    static constraints = {
        editor nullable: true
        creator nullable: true
        //當unique中有屬性為null時，會導致unique無法正確驗證，加入validator另行驗證。
        param unique: ["report", "operation", "workstation", "supplier", "site"],
              validator: validator
        workstation nullable: true
        supplier nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ReportParam]

        String ws = (workstation) ? workstation.name : ""
        String sp = (supplier) ? supplier.name : ""
        "${getMessageSource().getMessage("${i18nType}.reportParam.label", args, Locale.getDefault())}: " +
        "${report?.name}-${report?.title}/${param?.name}-${param?.title}/${operation?.name}-${operation?.title}/${ws}${sp}"
    }

    static validator = { col, obj ->
        def existRecord = ReportParam.findByIdNotEqualAndParamAndReportAndOperationAndWorkstationAndSupplierAndSite(
            obj.id, obj.param, obj.report, obj.operation, obj.workstation, obj.supplier, obj.site)
        
        if (existRecord) {
            return ["reportParam.unique.error", existRecord.id]
        }

        // 工作站/供應商須擇一
        if ((obj.workstation && obj.supplier)||(!obj.workstation && !obj.supplier))
            return ["reportParam.workstation.supplier.should.exist.one"]
    }
}
