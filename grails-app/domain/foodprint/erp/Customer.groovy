package foodprint.erp

/**
 * 客戶
 */
class Customer {

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
     * 客戶編號 (Primary Key)
     */
    String name

    /**
     * 客戶名稱
     */
    String title

    /**
     * 電話
     */
    String tel

    /**
     * 傳真
     */
    String fax

    /**
     * 連絡人
     */
    String contact

    /**
     * 電子信箱
     */
    String email

    /**
     * 客戶地址
     */
    String address

    /**
     * 送貨地址
     */
    String shippingAddress

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
        tel nullable: true
        fax nullable: true
        contact nullable: true
        email nullable: true, email: true
        address nullable: true
        shippingAddress nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Customer]
        "${getMessageSource().getMessage("${i18nType}.customer.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

    static validator = { col, obj ->
        /*
        ^表示開頭

        第一段((\+\d+\-)|(\(\d+\)))?
        \+  符號"+" 出現一次
        \d+ 數字"0-9" 可出現一次或一次以上
        \-  符號"-" 出現一次
        \(  符號"(" 出現一次
        \)  符號")" 出現一次
        ((\+\d+\-)|(\(\d+\)))? 組合"\+\d+\-"或"\(\d+\)" 出現0次或一次

        第二段(\d+[-]?)+
        [-]?       符號"-" 出現0次或一次
        (\d+[-]?)+ 組合"\d+[-]?" 出現0次或一次

        第三段( [#]\d+)*
            空白符號 出現一次
        [#] 符號"#" 出現一次
        ( [#]\d+)* 組合" [#]\d+" 出現0次或一次以上

        $表示結尾

        example: +886-912-345-678 / (02)1234-5678 / (02)1234-5678 #123

        附註：
            \符號 ->把斜線後的特殊字元當作是一般是字元，例如"\\"等於一個"\"，"\["等於"["。
            [ab]  -> a或者b 出現一次
        */
        if (obj.tel && !(obj.tel ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
        return ["customer.tel.invalid"]
        }

        if (obj.fax && !(obj.fax ==~ /^((\+\d+\-)|(\(\d+\)))?(\d+[-]?)+( [#]\d+)*$/)) {
        return ["customer.fax.invalid"]
        }

        /*
        ^表示開頭
        [_a-zA-Z0-9-]+        符號"_"/小寫字母/大寫字母/數字"0-9"/符號"-" 出現0次或一次
        [.]                   符號"." 出現一次
        ([.][_a-zA-Z0-9-]+)*  組合"[.][_a-zA-Z0-9-]+" 出現0次或一次以上
        @                     符號"@" 出現一次
        $表示結尾

        example: abc.abc@international.com
        */
        if (obj.email && !(obj.email ==~ /^[_a-zA-Z0-9-]+([.][_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+([.][a-zA-Z0-9-]+)*$/)) {
        return ["customer.email.invalid"]
        }
    }
}
