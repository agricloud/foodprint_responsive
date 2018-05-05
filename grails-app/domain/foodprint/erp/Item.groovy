package foodprint.erp

import foodprint.sft.ItemCategoryLayer1
import foodprint.sft.ItemCategoryLayer2
import foodprint.sft.ItemStage
import foodprint.sft.ItemRegisteredNum
import foodprint.sft.WorkFlowType

/**
 * 品項
 */
class Item {

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
     * 名稱
     */
    String title

    /**
     * 品牌
     */
    Brand brand

    /**
     * 品號屬性
     * P:採購件、M:自製件、S:託外加工件、Y:虛設品號、F:Feature件、O:Option件
     */
    ItemType itemType = ItemType.PURCHASE

    /**
     * 描述
     */
    String description
    
    /**
     * 規格
     */
    String spec
    
    /**
     * 庫存單位
     */
    String unit
    
    /**
     * 記錄期限多少天數
     */
    Long dueDays

    /**
     * 有效起始日期
     */
    Date effectStartDate

    /**
     * 有效結束日期
     */
    Date effectEndDate

    /**
     * 生產作業流
     */
    WorkFlowType workFlowType = WorkFlowType.PUSH

    /**
     * 預設工作站
     */
    Workstation defaultWorkstation

    /**
     *  標準作業時間(秒)
     */
    double cycleTime = -1

    static hasMany = [
        /**
         * 品項途程
         */
        itemRoutes: ItemRoute,
        /**
         * 品項生產階段
         */
        itemStages: ItemStage,
        /**
         * 品項登記字號
         */
        itemRegisteredNums: ItemRegisteredNum
    ]

    /**
     * 品項類別(一)
     */
    ItemCategoryLayer1 itemCategoryLayer1

    /**
     * 品項類別(二)
     */
    ItemCategoryLayer2 itemCategoryLayer2

    /*
     * 製造商預設值
     * 用以提供進貨單自動帶入製造商
     */
    Manufacturer defaultManufacturer

    static mapping = {
        importFlag  defaultValue: -1
        // 設置 updateable 後，將不會被標記 dirty flag。
        name updateable: false
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name(unique: ["brand", "site"])
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        description nullable: true
        spec nullable: true
        dueDays nullable: true
        effectStartDate nullable: true
        effectEndDate nullable: true
        itemCategoryLayer1 nullable: true
        itemCategoryLayer2 nullable: true
        defaultManufacturer nullable: true
        defaultWorkstation nullable: true
        cycleTime min: -1d
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Item]
        "${getMessageSource().getMessage("${i18nType}.item.label", args, Locale.getDefault())}: " +
        "${name}-${title}/${brand.name}-${brand.title}"
    }
}
