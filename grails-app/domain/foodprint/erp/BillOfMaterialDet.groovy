package foodprint.erp

/**
 * Bill of Material Details
 * 物料結構表單身
 */
class BillOfMaterialDet {

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
     * 物料結構表單頭
     */
    static belongsTo = [header: BillOfMaterial]

    /**
     * 序號
     */
    int sequence

    /**
     * 元件品號
     */
    Item item

    /**
     * 組成用量
     */
    double qty = 1

    /**
     * 底數
     */
    int denominator = 1

    /**
     * 製程
     * ERP定義：
     * 表示該元件於生產時被使用到的順序（使用在哪一道製程）。
     * 新增時，製程的預設值為「****」。
     * 可開窗帶入，其資料來源為「製程代號建立作業」中所建立的製程代號。
     * 可自行輸入，其代號不需要存在於「製程代號資料」中，因其代表的為投料順序，並不一定要完全搭配實際的生產製程。
     * 輸入領料單時，可利用此製程代號來篩選要領取的料件。
     */
    Operation operation

    /**
     * 投料順序
     */
    int releaseOrder = 1

    /**
     * 生效日
     */
    Date effectStartDate

    /**
     * 失效日
     */
    Date effectEndDate


    static constraints = {
        editor nullable: true
        creator nullable: true
        sequence unique: ["header", "site"]
        header validator: validator
        qty min: 0.0d
        operation nullable: true
        releaseOrder min: 1
        effectStartDate nullable: true
        effectEndDate nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [BillOfMaterialDet]
        "${getMessageSource().getMessage("${i18nType}.billOfMaterialDet.label", args, Locale.getDefault())}: " +
        "${header.item.name}-${header.item.title}/${header.item.brand.name}-${header.item.brand.title}/${sequence}"//+
        //"${item.name}-${item.title}/${item.brand.name}-${item.brand.title}/${sequence}"
    }

    static validator = { col, obj ->

        // 元件品號與主件品號不可相同
        if (obj.item == obj.header.item) {
            return ["billOfMaterialDet.item.header.item.cannot.equal", item, billOfMaterial.item]
        }
    }

}
