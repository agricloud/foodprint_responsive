package foodprint.erp

import foodprint.common.Country
import foodprint.sft.BatchReportDet
import foodprint.sft.BatchRealtimeRecord

/**
 * 批號
 */
class Batch {

    def grailsApplication
    def messageSource
    def enumService

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
     * 品項
     */
    Item item

    static hasMany = [
        /**
         * 批號途程
         * 說明: batchSourceType 為自製或託外製造才有此資料
         */
        batchOperations: BatchOperation,
        /**
         * 批號製程參數/批號履歷參數
         * 說明: batchSourceType 為自製或託外製造才有此資料
         */
        batchReportDets: BatchReportDet,
        /**
         * 批量即時生產記錄
         * 說明: batchSourceType 為自製或託外製造才有此資料
         */
        batchRealtimeRecords: BatchRealtimeRecord
    ]

    /**
     * 批號
     */
    String name

    /**
     * 預計數量
     *
     * note: ERP無此欄位
     */
    double expectQty = 0.0d

    /**
     * 失效日
     */
    Date effectEndDate

    /**
     * 製造完成日期
     */
    Date manufactureDate

    /**
     * 有效期限
     */
    Date expirationDate

    /**
     * 批號類型
     * ex: 產品、原料 ...
     *
     * note: 無 erp 時使用
     */
    BatchType batchType = BatchType.UNDEFINED

    /**
     * 批號來源類型
     * ex: 自製、託外製造、採購
     */
    BatchSourceType batchSourceType

    /**
     * 製令
     * 由庫存異動單據所新增的自製、託外、入庫、託外進貨批號不會有製令。
     */
    ManufactureOrder manufactureOrder

    /**
     * 製造商
     */
    Manufacturer manufacturer


    /**
     * 製造批號
     * 當批號來源為入庫批號時 ex: STOCKIN、OUTSRCPURCHASE
     * 須記錄原始之生產批號
     */
    Batch manufactureBatch

    /**
     * 採購品項之實際產地，託外品項之託外廠商國家，自製品項之生產線所屬國家
     */
    Country country

    /**
     * 備註
     */
    String remark

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: true, blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        batchSourceType validator: validator
        expectQty min: 0.0d
        effectEndDate nullable: true
        manufactureDate nullable: true
        expirationDate nullable: true
        manufactureOrder nullable: true
        manufacturer nullable: true
        manufactureBatch nullable: true
        remark nullable: true
        uuid nullable: true
    }

    /*
     * 唯一識別碼
     */
    String uuid

    def beforeInsert() {
        // optionally, replace the dashes by adding .replaceAll('-','')
        uuid = UUID.randomUUID().toString()
    }

    static mapping = {
        sort name: "asc"
        batchOperations sort: "sequence"
        name updateable: false
        batchSourceType updateable: false
        manufactureOrder updateable: false
        manufacturer updateable: false
        country updateable: false
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Batch]
        "${getMessageSource().getMessage("${i18nType}.batch.label", args, Locale.getDefault())}: ${name}"
    }

    static validator  = { col, obj ->

        // 自製、入庫批號不可存在製造商
        if ((obj.batchSourceType==BatchSourceType.MANUFACTURE || obj.batchSourceType==BatchSourceType.STOCKIN) && obj.manufacturer)
            return ["batch.batchSourceType.manufacturer.should.be.null", obj.enumService.name(obj.batchSourceType).title]
        // 非自製、入庫批號必須有製造商
        if ((obj.batchSourceType!=BatchSourceType.MANUFACTURE && obj.batchSourceType!=BatchSourceType.STOCKIN) && !obj.manufacturer)
            return ["batch.batchSourceType.manufacturer.nullable.error", obj.enumService.name(obj.batchSourceType).title]

        // note: 由庫存異動單建立的自製、入庫批號不會有製令
        if (obj.manufactureOrder) {
            // 進貨批號不可有製令
            if (obj.batchSourceType==BatchSourceType.PURCHASE)
                return ["batch.manufactureOrder.should.be.null"]
            if (obj.manufactureOrder.manufactureType==ManufactureType.FACTORY) {
                if (!(obj.batchSourceType in [BatchSourceType.MANUFACTURE, BatchSourceType.STOCKIN]))
                    return 
                // 自製製令，批號不可有供應商
                if (obj.manufacturer)
                    return false

            }
            else if (obj.manufactureOrder.manufactureType==ManufactureType.OUTSRC && obj.batchSourceType!=BatchSourceType.OUTSRCMANUFACTURE) {
                if (!(obj.batchSourceType in [BatchSourceType.OUTSRCMANUFACTURE, BatchSourceType.OUTSRCPURCHASE]))
                    return false
                if (!obj.manufacturer)
                    return false
                if (obj.manufacturer != obj.manufactureOrder.supplier.manufacturer)
                    return false
            }
        }

        // 由庫存異動單建立的入庫、託外進貨不會有製造批號
        if (obj.manufactureBatch) {

            // 非入庫、託外進貨之批號不可有製造批號
            if (obj.batchSourceType!=BatchSourceType.STOCKIN && obj.batchSourceType!=BatchSourceType.OUTSRCPURCHASE)
                return ["batch.batchSourceType.manufactureBatch.should.be.null", obj.enumService.name(obj.batchSourceType).title]
            // 入庫批號之製造批號必為自製
            if (obj.batchSourceType==BatchSourceType.STOCKIN && obj.manufactureBatch.batchSourceType!=BatchSourceType.MANUFACTURE)
                return ["batch.batchSourceType.STOCKIN.manufactureBatch.batchSourceType.must.be.MANUFACTURE", obj.enumService.name(obj.batchSourceType).title, obj.enumService.name(obj.manufactureBatch.batchSourceType).title]
            // 託外進貨之製造批號必為託外製造
            if (obj.batchSourceType==BatchSourceType.OUTSRCPURCHASE && obj.manufactureBatch.batchSourceType!=BatchSourceType.OUTSRCMANUFACTURE)
                return ["batch.batchSourceType.OUTSRCPURCHASE.manufactureBatch.batchSourceType.must.be.OUTSRCMANUFACTURE", obj.enumService.name(obj.batchSourceType).title, obj.enumService.name(obj.manufactureBatch.batchSourceType).title]
            // 批號製令與製造批號之製令須相同
            if (obj.manufactureOrder != obj.manufactureBatch.manufactureOrder)
                return ["batch.manufactureOrder.manufactureBatch.manufactureOrder.not.equal", obj.manufactureOrder, obj.manufactureBatch.manufactureOrder]
            // 批號製造商與製造批號之製造商須相同
            if (obj.manufactureBatch.manufacturer != obj.manufacturer)
                return ["batch.manufacturer.manufactureBatch.manufacturer.not.equal", obj.manufacturer, obj.manufactureBatch.manufacturer]
            // 批號產地與製造批號之產地須相同
            if (obj.manufactureBatch.country != obj.country)
                return ["batch.country.manufactureBatch.country.not.equal", obj.enumService.name(obj.country).title, obj.enumService.name(obj.manufactureBatch.country).title]
        }

        if (obj.manufacturer) {
            // 批號產地與製造商國別不符
            if (obj.country != obj.manufacturer.country)
                return ["batch.country.manufacturer.country.not.equal", obj.enumService.name(obj.country).title, obj.enumService.name(obj.manufacturer.country).title]
        }
    }

}
