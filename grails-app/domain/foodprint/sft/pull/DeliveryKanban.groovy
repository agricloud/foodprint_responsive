package foodprint.sft.pull

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.CustomerOrderDet
import foodprint.erp.TypeName
/**
 * 顧客交貨看板
 */
class DeliveryKanban{

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
     * 看板類別
     */
    TypeName typeName

    /**
     * 看板編號
     */
    String name

    /**
     * 項次
     */
    int sequence

    /**
     * 品項
     */
    Item item

    /**
     * 數量
     */
    double qty

    /**
     * 預計出貨開始
     */
    Date expectShippingDateStart

    /**
     *  預計出貨結束
     */
    Date expectShippingDateEnd

    /**
     * 實際出貨日
     */
    Date shippingDate

    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: ["typeName", "sequence", "site"]
        name blank: false, matches: "[a-zA-Z0-9][_a-zA-Z0-9-]*"
        qty min: 0.0d
        expectShippingDateStart nullable: true
        expectShippingDateEnd nullable: true
        shippingDate nullable: true
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

}
