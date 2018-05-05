package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.BatchOperation

class BatchReportDet {

    def grailsApplication
    def messageSource
    def enumService

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    Batch batch

    static belongsTo = [batchOperation: BatchOperation]

    ReportParam reportParam
    String value

    static constraints = {
        reportParam(unique: ["batchOperation", "site"])
        editor nullable: true
        creator nullable: true
        batch validator: validator
        value nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [BatchReportDet]
        "${getMessageSource().getMessage("${i18nType}.batchReportDet.label", args, Locale.getDefault())}: " +
        "${batch.name}/${batchOperation.sequence}/${reportParam.param.title}/${reportParam.param.paramType}/${value}"
    }

    static validator = { col, obj ->
        if (obj.reportParam.param.paramType == ParamType.INTEGER) {
            if (obj.value && !(obj.value ==~ /^[+-]?\d+$/)) {
                return ["batchReportDet.value.invalid", obj.reportParam.param.title, obj.value, obj.enumService.name(obj.reportParam.param.paramType).title]
            }
        }

        if (obj.reportParam.param.paramType == ParamType.DOUBLE) {
            if (obj.value && !(obj.value ==~ /^[+-]?\d+([.]\d*)?$/)) {
                return ["batchReportDet.value.invalid", obj.reportParam.param.title, obj.value, obj.enumService.name(obj.reportParam.param.paramType).title]
            }
        }

        if (obj.reportParam.param.paramType == ParamType.BOOLEAN) {
            if (obj.value && obj.value!="true" && obj.value!="false") {
                return ["batchReportDet.value.invalid", obj.reportParam.param.title, obj.value, obj.enumService.name(obj.reportParam.param.paramType).title]
            }
        }
    }
}
