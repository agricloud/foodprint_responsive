package foodprint.erp

/**
 * 移轉單單身
 */
class TransferOrderDet{

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
     * 單別
     */
    TypeName typeName

    /**
     * 單號
     */
    String name

    /**
     * 序號
     */
    int sequence

    TransferOrder header
    
    /**
     * 製令
     */
    ManufactureOrder manufactureOrder

    /**
     * 移出工序
     */
    int outSequence

    /**
     * 移出製程
     */
    Operation outOperation

    /**
     * 移入工序
     */
    int inSequence

    /**
     * 移入製程
     */
    Operation inOperation

    /**
     * 品項
     */
    Item item

    /**
     * 單位
     */
    String unit

    /**
     * 規格
     */
    String spec

    /**
     * 移轉數量
     */
    double qty

    /**
     * 報廢數量
     */
    double scrapQty

    /**
     * 批號
     */
    Batch batch

    /**
     * 使用人時(秒)
     */
    long laborHour

    /**
     * 使用機時(秒)
     */
    long equipmentHour

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        typeName updateable: false
        name updateable: false
        header updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        typeName validator: validator
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        sequence(unique: ["name", "typeName", "site"])
        qty min: 0.0d
        inOperation nullable: true 
        outOperation nullable: true 
        spec nullable: true 
        unit nullable: true 
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    // public String toString() {
    //     def i18nType = getGrailsApplication().config.grails.i18nType
    //     Object[] args = [Batch]
    //     "${getMessageSource().getMessage("${i18nType}.batch.label", args, Locale.getDefault())}: ${name}"
    // }

    static validator = { col, obj ->

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if (obj.typeName!=obj.header.typeName || obj.name!=obj.header.name) {
            return ["sheetDetail.typeName.name.header.typeName.name.not.equal"]
        }
    }

}
