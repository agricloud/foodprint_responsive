package foodprint.erp

/**
 * 職員
 */
class Employee {

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
     * 編號
     */
    String name
    /**
     *  姓名
     */
    String title
    /**
     *  職別
     */
    EmployeeType employeeType = EmployeeType.OPERATOR
    /**
     *  身分證號碼
     */
    String idNumber
    /**
     *  出生日期
     */
    Date birthDate
    /**
     *  現居電話
     */
    String tel
    /**
     *  行動電話
     */
    String mobile
    /**
     *  戶籍地址
     */
    String permanentAddress
    /**
     *  現居地址
     */
    String residentialAddress
    /**
     *  通訊地址
     */
    String correspondenceAddress

    String email

    /**
     *  緊急聯絡人
     */
    String contact

    /**
     *  緊急聯絡人電話
     */
    String contactPhoneNumber

    /**
     *  介紹
     */
    String introduction
    /**
     *  經驗、年資
     */
    String experience
    /**
     *  主要負責工作/項目
     */
    String mainWork
    /**
     *  工作區域
     */
    String area
    /**
     *  其他說明
     */
    String description
    /**
     *  備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*", validator: validator
        idNumber(unique: ["site"])
        email nullable: true, email: true
        introduction nullable: true
        experience nullable: true
        mainWork nullable: true
        area nullable: true
        description nullable: true
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
        Object[] args = [Employee]
        "${getMessageSource().getMessage("${i18nType}.employee.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

     static validator = { col, obj ->
        // 不static使用max，因為max所比較的date是固定值，為server啟動時當下的date，而非儲存user時重新執行new Date
        if (obj.birthDate >= new Date())
            return ["employee.birthDate.invalid"]

        if (obj.idNumber && !(obj.idNumber ==~ /^[A-Z][\d]{9}$/)) {
            return ["employee.idNumber.invalid"]
        }

        if (obj.tel && !(obj.tel ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
            return ["employee.tel.invalid"]
        }

        if (obj.mobile && !(obj.mobile ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
            return ["employee.mobile.invalid"]
        }

        if (obj.contactPhoneNumber && !(obj.contactPhoneNumber ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
            return ["employee.contactPhoneNumber.invalid"]
        }

        if (obj.email && !(obj.email ==~ /^[_a-zA-Z0-9-]+([.][_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+([.][a-zA-Z0-9-]+)*$/)) {
            return ["employee.email.invalid"]
        }
    }
}
