package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.common.FieldType

/*
 * 使用者自訂領料單單身其他欄位
*/
class MaterialSheetDetCustomize {

    def grailsApplication
    def messageSource
    def enumService

    String importFlag = -1

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    Item item
    ItemCategoryLayer1 itemCategoryLayer1
    ItemCategoryLayer2 itemCategoryLayer2

    String title

    FieldType fieldType = FieldType.STRING //收集類型

    String defaultValue //預設值

    String unit

    String description

    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        //當unique中有屬性為null時，會導致unique無法正確驗證，加入validator另行驗證。
        title unique: ["item", "itemCategoryLayer1", "itemCategoryLayer2", "site"],
              validator: validator
        title blank: false
        item nullable: true
        itemCategoryLayer1 nullable: true
        itemCategoryLayer2 nullable: true

        defaultValue nullable: true
        unit nullable: true
        description nullable: true

    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [MaterialSheetDetCustomize]
        "${getMessageSource().getMessage("${i18nType}.materialSheetDetCustomize.label", args, Locale.getDefault())}: " +
        "${item?.name}-${item?.title}/${item?.brand?.name}-${item?.brand?.title}/" +
        "${itemCategoryLayer1?.name}-${itemCategoryLayer1?.title}/" +
        "${itemCategoryLayer2?.name}-${itemCategoryLayer2?.title}/" +
        "${title}"
    }

    static validator = { col, obj ->
        def existRecord = MaterialSheetDetCustomize.findByIdNotEqualAndTitleAndItemCategoryLayer1AndItemCategoryLayer2AndItemAndSite(
            obj.id, obj.title, obj.itemCategoryLayer1, obj.itemCategoryLayer2, obj.item, obj.site)
        
        if (existRecord) {
            return ["materialSheetDetCustomize.unique.error.label", existRecord.id]
        }

        if (obj.fieldType == FieldType.INTEGER) {
            if (obj.defaultValue && !(obj.defaultValue ==~ /^[+-]?\d+$/)) {
                return ["sheetCustomize.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.fieldType).title]
            }
        }

        if (obj.fieldType == FieldType.DOUBLE) {
            if (obj.defaultValue && !(obj.defaultValue ==~ /^[+-]?\d+([.]\d*)?$/)) {
                return ["sheetCustomize.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.fieldType).title]
            }
        }

        if (obj.fieldType == FieldType.BOOLEAN) {
            if (obj.defaultValue && obj.defaultValue!="true" && obj.defaultValue!="false") {
                return ["sheetCustomize.defaultValue.invalid", obj.defaultValue, obj.enumService.name(obj.fieldType).title+"(true/flase)"]
            }
        }
    }
}
