package foodprint.erp

/**
 * Bill of Material
 * 物料結構表單頭
 */
class BillOfMaterial {

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
     * 修改日期：記錄bom、bomDet最後更新日期。
     */
    Date updatedDate = new Date()

    /**
     * 主件品號
     */
    Item item

    /**
     * 製令單別
     */
    TypeName manufactureOrderTypeName

    /**
     * 變更單
     */
    // CangeOrderDet changeOrderDet


    /**
     * 備註
     */
    String remark

    static hasMany = [
        /**
         * 物料結構表單身
         */
        details: BillOfMaterialDet
    ]

    static constraints = {
        editor nullable: true
        creator nullable: true
        item unique: ["site"]
        item validator: validator
        manufactureOrderTypeName nullable: true
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
        Object[] args = [BillOfMaterial]
        "${getMessageSource().getMessage("${i18nType}.billOfMaterial.label", args, Locale.getDefault())}: " +
        "${item.name}-${item.title}/${item.brand.name}-${item.brand.title}"
    }

    static validator = { col, obj ->

        // manufactureOrderTypeName 之 sheetType 應為 MANUFACTUREORDER
        if (obj.manufactureOrderTypeName && obj.manufactureOrderTypeName.sheetType != SheetType.MANUFACTUREORDER) {
            return ["billOfMaterial.manufactureOrderTypeName.sheetType.error", obj.enumService.name(obj.manufactureOrderTypeName.sheetType).title]
        }
    }

}
