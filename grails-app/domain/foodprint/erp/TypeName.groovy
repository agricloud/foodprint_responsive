package foodprint.erp

/**
 * 系統單據性質設定檔
 * 參考：CMSMQ
 */

class TypeName {

    def grailsApplication
    def messageSource
    def enumService

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
     * 單別
     */
    String name

    /**
     * 單據名稱
     */
    String title

    /**
     * 單據全名
     */
    String fullTitle

    /**
     * 單據性質
     *  1?:庫存、2?:訂單、3?:採購、4?:BOM、5?:製令、6?:應收、7?:應付、8?:票據、9?:會計、
     *  A?:進口、B?:出口、C?:固定資產、D?:製程、E?:維修、
     *  F?:模具、G?：保稅、H?:軍福品、I?:零用金、J?:人力資源、
     *  K?:海關合同、L?:IDL、M?:會計多帳本、N?:設備保養 
     */
    SheetType sheetType

    /**
     * 編碼方式
     */
    SheetFormatType sheetFormatType = SheetFormatType.DAY
    
    /**
     * 年碼數
     */
    int yearDigit = 4

    /**
     * 流水號碼數
     * note: 總編碼不可超過12碼
     */
    int runningDigit = 3

    /**
     * 異動類別
     *  1.入庫、2.銷貨、3.領用、4.轉撥、5.調整
     */
    TransactionType transactionType

    /**
     * 庫存影響
     * 1.增、-1.減，轉撥單只能為-1，其餘依單據性質，一般異動單據可自由設定。
     */
    Integer multiplier

    /**
     * 預設製令類別
     * 廠內or廠外
     */
    ManufactureType manufactureType

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
        sheetType updateable: false
        transactionType updateable: false
        multiplier updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: ["site"]
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        sheetType validator: validator
        title nullable: true
        fullTitle nullable: true
        yearDigit range: 0..4
        runningDigit min: 0
        transactionType nullable: true
        multiplier nullable: true, range: -1..1
        manufactureType nullable: true
        
    }

    def beforeSave() {
        if (sheetFormatType == SheetFormatType.MANUAL) {
            yearDigit = 0
            runningDigit = 0
        }
       
        if (sheetType != SheetType.TRANSACTION) {
            transactionType = getDefaultTransactionType()
        }

        if (sheetType != SheetType.TRANSACTION || !multiplier)
            multiplier = getDefaultMultiplier()

        if (sheetType != SheetType.MANUFACTUREORDER) {
            manufactureType = null
        }
    }

    def beforeInsert() {
        beforeSave()
    }

    def beforeUpdate() {
        beforeSave()
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [TypeName]
        "${getMessageSource().getMessage("${i18nType}.typeName.label", args, Locale.getDefault())}: " +
        "${name}-${title}"
    }

    static validator = { col, obj ->
        if (obj.yearDigit == 0 && !obj.sheetFormatType in [SheetFormatType.MANUAL, SheetFormatType.RUNNINGNUMBER]) {
            return ["must.be.greater.than.zero"]
        }
        if (obj.runningDigit == 0 && obj.sheetFormatType != SheetFormatType.MANUAL) {
            return ["must.be.greater.than.zero"]
        }
        //單號不可超過12碼
        // else if (obj.sheetFormatType == SheetFormatType.DAY) {
        //     if ((obj.yearDigit + 4 + obj.runningDigit) > 12)
        //         return ["sheet.name.digit.over.length"] //false
        // }
        // else if (obj.sheetFormatType == SheetFormatType.MONTH) {
        //     if ((obj.yearDigit + 2 + obj.runningDigit) > 12)
        //         return ["sheet.name.digit.over.length"] //false
        // }
        // else if (obj.sheetFormatType == SheetFormatType.RUNNINGNUMBER) {
        //     if (obj.runningDigit > 12)
        //         return ["sheet.name.digit.over.length"] //false
        // }
        //一般異動單據之異動類別不可為轉撥單
        if (obj.sheetType == SheetType.TRANSACTION) {
            if (obj.transactionType == TransactionType.TRANSFER)
                return ["error", obj.enumService.name(obj.sheetType).title, obj.enumService.name(obj.transactionType).title]
        }

        if (obj.sheetType == SheetType.MANUFACTUREORDER) {
            // 製令單別需設定製造性質
            if (!obj.manufactureType) {
                return ["typeName.manufactureType.nullable.error"]
            }
        }
    }

    def getDefaultTransactionType = {
        if (sheetType) {
            switch (sheetType) {
                case SheetType.SALESHEET:
                case SheetType.SALERETURNSHEET:
                    return TransactionType.SALE
                case SheetType.PURCHASESHEET:
                case SheetType.PURCHASERETURNSHEET:
                case SheetType.STOCKINSHEET:
                case SheetType.OUTSRCPURCHASESHEET:
                case SheetType.OUTSRCPURCHASERETURNSHEET:
                case SheetType.OPERATIONSTORAGE:
                case SheetType.TRANSACTION:
                    return TransactionType.STORAGE
                case SheetType.MATERIALSHEET:
                case SheetType.MATERIALRETURNSHEET:
                case SheetType.OUTSRCMATERIALSHEET:
                case SheetType.OUTSRCMATERIALRETURNSHEET:
                case SheetType.ORDERRELEASE:
                    return TransactionType.REQUISITION
                case SheetType.CUSTOMERORDER:
                case SheetType.MANUFACTUREORDER:
                // case SheetType.REWORKORDER:
                case SheetType.OPERATIONTRANSFER:
                case SheetType.WORKREPORT:
                    return null
            }
        }
    }
    def getDefaultMultiplier = {
        if (sheetType) {
            switch (sheetType) {
                case SheetType.CUSTOMERORDER:
                case SheetType.ORDERRELEASE:
                case SheetType.OPERATIONTRANSFER:
                case SheetType.OPERATIONSTORAGE:
                // case SheetType.REWORKORDER:
                case SheetType.WORKREPORT:
                    return 0
                case SheetType.SALERETURNSHEET:
                case SheetType.PURCHASESHEET:
                case SheetType.MANUFACTUREORDER:
                case SheetType.MATERIALRETURNSHEET:
                case SheetType.OUTSRCMATERIALRETURNSHEET:
                case SheetType.STOCKINSHEET:
                case SheetType.OUTSRCPURCHASESHEET:
                    return 1
                //轉撥單
                case SheetType.SALESHEET:
                case SheetType.PURCHASERETURNSHEET:
                case SheetType.MATERIALSHEET:
                case SheetType.OUTSRCMATERIALSHEET:
                case SheetType.OUTSRCPURCHASERETURNSHEET:
                    return -1
                case SheetType.TRANSACTION:
                    switch (transactionType) {
                        case TransactionType.STORAGE:
                        case TransactionType.ADJUSTMENT:
                            return 1
                        case TransactionType.SALE:
                        case TransactionType.REQUISITION:
                        case TransactionType.TRANSFER:
                            return -1
                    }
            }
        }
    }
}
