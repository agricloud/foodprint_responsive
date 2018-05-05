package foodprint.sft.web

import foodprint.common.FieldType
import foodprint.erp.Site
import foodprint.sft.MaterialSheetDetCustomize
import foodprint.sft.MaterialSheetDetCustomizeDet

import foodprint.common.DomainDataException

import org.hibernate.criterion.CriteriaSpecification
import grails.transaction.Transactional

class MaterialSheetDetCustomizeController {

    def grailsApplication
    def domainService
    def enumService

    def i18nType

    def index = {

        params.criteria = params.criteria << {

            createAlias('itemCategoryLayer1', 'itemCategoryLayer1', CriteriaSpecification.LEFT_JOIN)
            createAlias('itemCategoryLayer2', 'itemCategoryLayer2', CriteriaSpecification.LEFT_JOIN)
            createAlias('item', 'item', CriteriaSpecification.LEFT_JOIN)
        }

        def list = MaterialSheetDetCustomize.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

     def show = {

        def materialSheetDetCustomize = MaterialSheetDetCustomize.get(params.id)

        if (materialSheetDetCustomize) {
            render (contentType: 'application/json') {
                [success: true, data: materialSheetDetCustomize]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
    def create = {

        def materialSheetDetCustomize = new MaterialSheetDetCustomize()

        render (contentType: 'application/json') {
            [success: true, data: materialSheetDetCustomize]
        }
    }
    @Transactional
    def save() {
        def result
        try {
            def materialSheetDetCustomize = new MaterialSheetDetCustomize(params)
            result = domainService.save(materialSheetDetCustomize)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def update() {
        def result
        try {
            def site = Site.get(params.site.id)
            def materialSheetDetCustomize = MaterialSheetDetCustomize.get(params.id)
            materialSheetDetCustomize.properties = params

            // 批號履歷參數已存在，不允許變更名稱、類型、單位。
            if (materialSheetDetCustomize.isDirty('title') || materialSheetDetCustomize.isDirty('fieldType')
                    || materialSheetDetCustomize.isDirty('unit')) {

                if (MaterialSheetDetCustomizeDet.findByMaterialSheetDetCustomizeAndSite(materialSheetDetCustomize, site)) {
                    throw new DomainDataException(message(code: "${i18nType}.materialSheetDetCustomize.materialSheetDetCustomizeDet.exists.cannot.change"))
                }
            }
            // 變更品項、品項類別一、品項類別二時，若已存在關聯自訂參數(MaterialSheetDetCustomizeDet)，須將關聯自訂參數全部刪除
            else if (materialSheetDetCustomize.isDirty('item') || materialSheetDetCustomize.isDirty('itemCategoryLayer1') ||
                    materialSheetDetCustomize.isDirty('itemCategoryLayer2')) {

                def materialSheetDetCustomizeDets = MaterialSheetDetCustomizeDet.findAllByMaterialSheetDetCustomizeAndSite(materialSheetDetCustomize, site)
                materialSheetDetCustomizeDets.each{
                    domainService.delete(it)
                }
            }

            

            result = domainService.save(materialSheetDetCustomize)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }


    @Transactional
    def delete() {

        def result

        try {
            def site = Site.get(params.site.id)
            def materialSheetDetCustomize = MaterialSheetDetCustomize.get(params.id)
            //刪除自訂參數設定時，同時把過去曾關聯產生的自訂參數(MaterialSheetDetCustomizeDet)刪除
            def materialSheetDetCustomizeDets = MaterialSheetDetCustomizeDet.findAllByMaterialSheetDetCustomizeAndSite(materialSheetDetCustomize, site)
            materialSheetDetCustomizeDets.each{
                domainService.delete(it)
            }

            result = domainService.delete(materialSheetDetCustomize)

        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    // 更新或刪除前檢查是否有關聯的自訂參數存在
    def existsMaterialSheetDetCustomizeDet = {
        def materialSheetDetCustomize = MaterialSheetDetCustomize.get(params.id)

        def materialSheetDetCustomizeDet = MaterialSheetDetCustomizeDet.findByMaterialSheetDetCustomizeAndSite(materialSheetDetCustomize, materialSheetDetCustomize.site)

        if (materialSheetDetCustomizeDet) {
            render (contentType: 'application/json') {
                [success: true, result: true]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: true, result: false]
            }
        }

    }

}
