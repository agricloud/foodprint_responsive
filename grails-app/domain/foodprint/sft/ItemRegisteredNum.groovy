package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.Manufacturer
import foodprint.common.Country

/**
 * 品項登記字號
 */
class ItemRegisteredNum {

    def grailsApplication
    def messageSource
    def enumService

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [item: Item]
    Manufacturer manufacturer
    Country country = Country.TAIWAN
    String registeredNum

    static constraints = {
        editor nullable: true
        creator nullable: true
        //當unique中有屬性為null時，會導致unique無法正確驗證，加入validator另行驗證。
        item unique: ["manufacturer", "country", "site"], validator: validator
        manufacturer nullable:true
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ItemRegisteredNum]
        "${getMessageSource().getMessage("${i18nType}.itemRegisteredNum.label", args, Locale.getDefault())}: " +
        "${item.name}-${item.title}/${manufacturer.name}-${manufacturer.title}/${enumService.name(country).title}/${registeredNum}"
    }

    static validator = { col, obj ->

        def existRecord = ItemRegisteredNum.findByIdNotEqualAndItemAndCountryAndManufacturerAndSite(
            obj.id, obj.item, obj.country, obj.manufacturer, obj.site)
        
        if (existRecord) {
            return ["itemRegisteredNum.unique.error.label", existRecord.id]
        }
    }
}
