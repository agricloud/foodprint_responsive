package foodprint.sft

import foodprint.erp.Site

/**
 * 收集項目定義
 */
class Param {

    def grailsApplication
    def messageSource
    def enumService

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
    String title
    String defaultValue //預設值
    ParamType paramType = ParamType.DOUBLE //收集類型
    String description
    String lower
    String upper
    String unit

    static mapping = {
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*", validator: validator
        title nullable: true
        defaultValue nullable: true
        description nullable: true
        lower nullable: true
        upper nullable: true
        unit nullable: true

    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Param]
        "${getMessageSource().getMessage("${i18nType}.param.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

    static validator = { col, obj ->
        if (obj.paramType == ParamType.INTEGER) {
            if (obj.defaultValue && !(obj.defaultValue ==~ /^[+-]?\d+$/)) {
                return ["param.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.paramType).title]
            }

            if (obj.upper && !(obj.upper ==~ /^[+-]?\d+$/)) {
                return ["param.upper.invalid", obj.upper, obj.enumService.name(obj.paramType).title]
            }

            if (obj.lower && !(obj.lower ==~ /^[+-]?\d+$/)) {
                return ["param.lower.invalid", obj.lower, obj.enumService.name(obj.paramType).title]
            }

            if (obj.upper && obj.lower && obj.upper.toInteger() < obj.lower.toInteger()) {
                return ["param.upper.min.error"]
            }

            if ( obj.defaultValue && obj.upper && obj.defaultValue.toInteger() > obj.upper.toInteger()) {
                return ["param.defaultValue.max.error"]
            }
            if ( obj.defaultValue && obj.lower && obj.defaultValue.toInteger() < obj.lower.toInteger()) {
                return ["param.defaultValue.min.error"]
            }

            
        }

        if (obj.paramType == ParamType.DOUBLE) {
            if (obj.defaultValue && !(obj.defaultValue ==~ /^[+-]?\d+([.]\d*)?$/)) {
                return ["param.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.paramType).title]
            }

            if (obj.upper && !(obj.upper ==~ /^[+-]?\d+([.]\d*)?$/)) {
                return ["param.upper.invalid", obj.upper, obj.enumService.name(obj.paramType).title]
            }

            if (obj.lower && !(obj.lower ==~ /^[+-]?\d+([.]\d*)?$/)) {
                return ["param.lower.invalid", obj.lower, obj.enumService.name(obj.paramType).title]
            }

            if (obj.upper && obj.lower && obj.upper.toDouble() < obj.lower.toDouble()) {
                return ["param.upper.min.error"]
            }

            if ( obj.defaultValue && obj.upper && obj.defaultValue.toDouble() > obj.upper.toDouble()) {
                return ["param.defaultValue.max.error"]
            }
            if ( obj.defaultValue && obj.lower && obj.defaultValue.toDouble() < obj.lower.toDouble()) {
                return ["param.defaultValue.min.error"]
            }

        }

        if (obj.paramType == ParamType.BOOLEAN) {
            if (obj.defaultValue && obj.defaultValue!="true" && obj.defaultValue!="false") {
                return ["param.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.paramType).title+"(true/flase)"]
            }

            if (obj.upper && obj.upper!="true" && obj.upper!="false") {
                return ["param.upper.invalid", obj.upper, obj.enumService.name(obj.paramType).title+"(true/flase)"]
            }

            if (obj.lower && obj.lower!="true" && obj.lower!="false") {
                return ["param.lower.invalid", obj.lower, obj.enumService.name(obj.paramType).title+"(true/flase)"]
            }
        }
    }
}
