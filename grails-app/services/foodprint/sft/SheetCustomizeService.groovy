package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Item
import foodprint.erp.MaterialSheetDet
import foodprint.common.FieldType
import foodprint.sft.MaterialSheetDetCustomize
import foodprint.sft.MaterialSheetDetCustomizeDet

import foodprint.common.DomainDataException

class SheetCustomizeService {

    def grailsApplication
    def messageSource
    def domainService
    def enumService

    def i18nType

    //called from MaterialSheetDetCustomizeDetController
    def index = { sheetDetId, itemId, customizeDetClass, siteId ->

        def sheetCustomizeDets

        if (customizeDetClass == MaterialSheetDetCustomizeDet)
            sheetCustomizeDets = getMaterialSheetDetCustomizeDets(sheetDetId, itemId, siteId)

        [success: true, data: sheetCustomizeDets, total: sheetCustomizeDets.size()]

    }

    def getSheetCustomizes = { itemId, customizeClass, siteId ->

        def site = Site.get(siteId)
        def item = Item.get(itemId)
        def itemCategoryLayer1 = item?.itemCategoryLayer1
        def itemCategoryLayer2 = item?.itemCategoryLayer2

        // service layer 無法使用此寫法
        // def sheetCustomizes = customizeClass.findAll{
        //     ((item==item && itemCategoryLayer1==null && itemCategoryLayer2==null) ||
        //      (item==item && itemCategoryLayer1==itemCategoryLayer1 && itemCategoryLayer2==null) ||
        //      (item==item && itemCategoryLayer1==itemCategoryLayer1 && itemCategoryLayer2==itemCategoryLayer2) ||
        //      (item==null && itemCategoryLayer1==null && itemCategoryLayer2==null) ||
        //      (item==null && itemCategoryLayer1==itemCategoryLayer1 && itemCategoryLayer2==null) ||
        //      (item==null && itemCategoryLayer1==itemCategoryLayer1 && itemCategoryLayer2==itemCategoryLayer2)
        //     ) && site==site
        // }
        def sheetCustomizes = customizeClass.findAll {
            and {
                eq('site.id', site.id)
                or {
                    and {
                        eq('item.id', item?.id)
                        isNull('itemCategoryLayer1.id')
                        isNull('itemCategoryLayer2.id')
                    }
                    and {
                        eq('item.id', item?.id)
                        eq('itemCategoryLayer1.id', itemCategoryLayer1?.id)
                        isNull('itemCategoryLayer2.id')
                    }
                    and {
                        eq('item.id', item?.id)
                        eq('itemCategoryLayer1.id', itemCategoryLayer1?.id)
                        eq('itemCategoryLayer2.id', itemCategoryLayer2?.id)
                    }
                    and {
                        isNull('item.id')
                        isNull('itemCategoryLayer1.id')
                        isNull('itemCategoryLayer2.id')
                    }
                    and {
                        isNull('item.id')
                        eq('itemCategoryLayer1.id', itemCategoryLayer1?.id)
                        eq('itemCategoryLayer2.id', itemCategoryLayer2?.id)
                    }
                    and {
                        isNull('item.id')
                        eq('itemCategoryLayer1.id', itemCategoryLayer1?.id)
                        isNull('itemCategoryLayer2.id')
                    }
                }
            }
        }// end findAll
        sheetCustomizes
    }

    def getMaterialSheetDetCustomizeDets = { sheetDetId, itemId, siteId->

        def site = Site.get(siteId)
        def sheet = MaterialSheetDet.get(sheetDetId)

        def sheetCustomizes = getSheetCustomizes(itemId, MaterialSheetDetCustomize, siteId)

        def sheetCustomizeDets = []

        if (!sheet) {

            sheetCustomizes.each {
                sheetCustomizeDets << new MaterialSheetDetCustomizeDet(materialSheetDetCustomize:it, value: it.defaultValue, site:site)
            }

        }
        else {

            sheetCustomizes.each {
                def sheetCustomizeDet = MaterialSheetDetCustomizeDet.findByMaterialSheetDetAndMaterialSheetDetCustomizeAndSite(sheet,it,site)

                if (!sheetCustomizeDet) {
                    sheetCustomizeDet = new MaterialSheetDetCustomizeDet(materialSheetDetCustomize:it, value: it.defaultValue, site:site)
                }

                sheetCustomizeDets << sheetCustomizeDet
            }

        }

        sheetCustomizeDets
    }

    //called from MaterialSheetDetController
    def saveOrUpdate = { sheetDetId, customizeParams, customizeDetClass, siteId, params ->

        def failure = []
        def success = []
        def msg = []
        Object[] args = []

        //更新參數值
        customizeParams.each { param->
            def result
            if (customizeDetClass == MaterialSheetDetCustomizeDet) {
                result = getMaterialSheetDetCustomizeDet(sheetDetId, param, siteId, params)
            }
            if (result.success) {

                if (domainService.save(result.sheetCustomizeDet).success)
                    success << result.sheetCustomize.title
                else
                    failure << result.sheetCustomize.title
            }
            else {
                msg << result.message
            }


        }// end customizeParams.each

        if (success.size() > 0) {
            args = [success.join(' , ')]
            msg << messageSource.getMessage("${i18nType}.default.message.update.success", args, Locale.getDefault())
        }
        if (failure.size() > 0) {
            args = [failure.join(' , ')]
            msg << messageSource.getMessage("${i18nType}.default.message.update.failed", args, Locale.getDefault())

            throw new DomainDataException(msg.join('<br>'))

        }
        else {
            if (success.size() > 0)
                return [success: true, message: msg.join('<br>')]

            if (success.size()==0 && failure.size()==0 && msg!=[])
                throw new DomainDataException(msg.join('<br>'))
            if (success.size()==0 && failure.size()==0 && msg==[]) {
                return null
            }

        }
    }

    def getMaterialSheetDetCustomizeDet = { sheetDetId, param, siteId, params->

        def site = Site.get(siteId)
        def sheet = MaterialSheetDet.get(sheetDetId)

        def sheetCustomize = MaterialSheetDetCustomize.get(param.key)

        def result = validateValueType(param.value, sheetCustomize)

        if (result.success) {
            def sheetCustomizeDet = MaterialSheetDetCustomizeDet.findByMaterialSheetDetAndMaterialSheetDetCustomizeAndSite(sheet, sheetCustomize, site)

            if (!sheetCustomizeDet) {
                sheetCustomizeDet = new MaterialSheetDetCustomizeDet(materialSheetDet: sheet, materialSheetDetCustomize: sheetCustomize, value: param.value, site: site, creator: params.editor)
            }
            else {
                sheetCustomizeDet.value = param.value
            }

            return [success: true, sheetCustomize: sheetCustomize, sheetCustomizeDet: sheetCustomizeDet]
        }
        else {
            return result
        }

    }

    def validateValueType = { value, sheetCustomize->

        def typeError = false
        Object[] args

        if (sheetCustomize.fieldType==FieldType.INTEGER) {
            if (value && !(value ==~ /^[+-]?\d+$/)) {
                typeError = true
            }
        }

        if (sheetCustomize.fieldType==FieldType.DOUBLE) {
            if (value && !(value ==~ /^[+-]?\d+([.]\d*)?$/)) {
                typeError = true
            }
        }

        if (sheetCustomize.fieldType==FieldType.BOOLEAN) {
            if (value && value!="true" && value!="false") {
                typeError = true
            }
        }
        if (typeError) {
            args = [sheetCustomize.title, value, enumService.name(sheetCustomize.fieldType).title]
            throw new DomainDataException(messageSource.getMessage("${i18nType}.sheetCustomizeDet.value.invalid", args, Locale.getDefault()))

        }
        else
            return [success: true]
    }

    //當領料單單身刪除 領料單單身自訂收集資料自動被刪除 (因materialSheetDetCustomizeDet belongsTo=[MaterialSheetDet])
    // def delete = { params, customizeDetClass ->

    //     def sheetCustomizeDets
    //     if (customizeDetClass == MaterialSheetDetCustomizeDet)
    //         sheetCustomizeDets = findAllMaterialSheetDetCustomizeDetByMaterialSheetDetAndSite(params)

    //     def result
    //     try {
    //         sheetCustomizeDets.each{
    //             result = domainService.delete(it)
    //             if (!result.success)
    //                 return result
    //         }

    //     }
        // catch (DomainDataException e) {
        //     result = e.getResult()
        // }

    //     return result
    // }

    // def findAllMaterialSheetDetCustomizeDetByMaterialSheetDetAndSite = { params ->
    //     def sheet = MaterialSheetDet.get(params.id)
    //     def sheetCustomizeDets = MaterialSheetDetCustomizeDet.findByMaterialSheetDetAndSite(sheet, site)

    //     sheetCustomizeDets
    // }
}
