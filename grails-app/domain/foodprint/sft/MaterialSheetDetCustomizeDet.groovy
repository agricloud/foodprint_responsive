package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.MaterialSheetDet
/*
 * 使用者自訂領料單單身其他欄位之實際資料值
*/

class MaterialSheetDetCustomizeDet {

    def grailsApplication
    def messageSource

    String importFlag = -1

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [materialSheetDet: MaterialSheetDet]

    MaterialSheetDetCustomize materialSheetDetCustomize

    String value

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable: true
        editor nullable: true
        creator nullable: true
        materialSheetDetCustomize(unique: ["materialSheetDet", "site"])
        value nullable: true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [MaterialSheetDetCustomizeDet]

        "${getMessageSource().getMessage("${i18nType}.materialSheetDetCustomizeDet.label", args, Locale.getDefault())}: " +
        "${materialSheetDet?.typeName}-${materialSheetDet?.name}-${materialSheetDet?.sequence}/" +
        "${materialSheetDetCustomize.title}"
    }
}
