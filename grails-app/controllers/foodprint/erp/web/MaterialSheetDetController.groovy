package foodprint.erp.web

import foodprint.erp.MaterialSheet
import foodprint.erp.MaterialSheetDet
import foodprint.erp.ManufactureOrderStatus
import foodprint.erp.MaterialReturnSheetDet
import foodprint.sft.MaterialSheetDetCustomizeDet

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional

class MaterialSheetDetController {

    def grailsApplication
    def domainService
    def enumService
    def inventoryDetailService
    def sheetCustomizeService
    def materialSheetDetService

    def i18nType

    def index = {

        def sheet = MaterialSheet.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = MaterialSheetDet.createCriteria().list(params, params.criteria)
            render (contentType: 'application/json') {
                [data: list, total: list.totalCount]
            }

        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }

    }


    def show = {

        def sheetDet = MaterialSheetDet.get(params.id)


        if (sheetDet) {

            render (contentType: 'application/json') {
                [success: true, data: sheetDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }


    def create = {
        def result
        try {
            result = materialSheetDetService.create(params)
        }
        catch (DomainDataException e) {
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def save() {

        def result
        
        try {
            def sheetDet = new MaterialSheetDet(params)
            result = materialSheetDetService.save(sheetDet, getCustomizeParams(), params.site.id, params)
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

            def newSheetDet = new MaterialSheetDet(params)
            def oldSheetDet = MaterialSheetDet.get(params.id)

            // 退單已存在不允許更新
            if (MaterialReturnSheetDet.findByMaterialSheetDet(oldSheetDet)) {
                throw new DomainDataException(message(code: "${i18nType}.sheetDetail.returnSheetDetail.exists.cannot.update"))
            }
            
            inventoryDetailService.processInventoryByUpdate(oldSheetDet, newSheetDet, params.site.id, params)

            /*
             * 變更領料製令時
             * a.查詢變更前之製令，是否存有其他領料單，若有則「不變更製令狀態」，
             *   若無且製令狀態爲已發料，則將狀態還原爲「已發放未生產」。
             * b.變更後之製令，若狀態爲「未發放」、「已發放未生產」，則將狀態變更爲「已發料」。
             */
            if (newSheetDet.manufactureOrder != oldSheetDet.manufactureOrder) {
                def materialSheetDet = MaterialSheetDet.findByManufactureOrderAndTypeNameNotEqualAndNameNotEqualAndSequenceNotEqualAndSite(oldSheetDet.manufactureOrder, oldSheetDet.typeName, oldSheetDet.name, oldSheetDet.sequence, newSheetDet.site)
                if (!materialSheetDet && oldSheetDet.manufactureOrder.status == ManufactureOrderStatus.DISBURSED) {
                    def manufactureOrder = oldSheetDet.manufactureOrder
                    manufactureOrder.status = ManufactureOrderStatus.APPROVED
                    domainService.save(manufactureOrder)
                }
                if (newSheetDet.manufactureOrder.status == ManufactureOrderStatus.PENDING || newSheetDet.manufactureOrder.status == ManufactureOrderStatus.APPROVED) {
                    def manufactureOrder = newSheetDet.manufactureOrder
                    manufactureOrder.status = ManufactureOrderStatus.DISBURSED
                    domainService.save(manufactureOrder)
                }
            }

            oldSheetDet.properties = params

            result = domainService.save(oldSheetDet)

            def saveOrUpdateCustomizeResult = sheetCustomizeService.saveOrUpdate(oldSheetDet.id, getCustomizeParams(), MaterialSheetDetCustomizeDet, params.site.id, params)
            // 存在自訂參數情況
            if (saveOrUpdateCustomizeResult)
                result = saveOrUpdateCustomizeResult
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
        // 會將關聯的自訂參數(MaterialSheetDetCustomizeDet)一併刪除

        def result

        try {
            def sheetDet = MaterialSheetDet.get(params.id)

            //「製令」狀態爲完工或結案不允許刪除
            if (sheetDet.manufactureOrder.status in [ManufactureOrderStatus.FINISHED, ManufactureOrderStatus.ASSIGNEDFINISHED]) {
                throw new DomainDataException(message(code: "${i18nType}.materialSheetDet.manufactureOrder.status.finished.cannot.delete", args: [sheetDet.manufactureOrder, enumService.name(sheetDet.manufactureOrder.status).title]))
            }

            inventoryDetailService.processInventoryByDelete(sheetDet, params.site.id, params)

            result = domainService.delete(sheetDet)

            // 處理領料製令狀態，若沒有被其他單據領料則製令狀態退回至「已發放未生產」
            def materialSheetDet = MaterialSheetDet.findByManufactureOrderAndSite(sheetDet.manufactureOrder, sheetDet.site)
            def manufactureOrder = sheetDet.manufactureOrder
            if (!materialSheetDet && manufactureOrder.status == ManufactureOrderStatus.DISBURSED) {
                manufactureOrder.status = ManufactureOrderStatus.APPROVED
                domainService.save(manufactureOrder)
            }
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    private getCustomizeParams = {
        def paramsKeys = [
            'action', 'controller', 'criteria', 'timeZoneId',
            'foodpaintController', 'creator', 'editor',
            'site', 'site.id', 'site.name',
            'siteGroup', 'siteGroup.id', 'siteGroup.name',
            'id', 'typeName', 'typeName.id', 'name', 'sequence',
            'item', 'item.id','item.name','item.brand.name',
            'batch', 'batch.id', 'batch.name',
            'warehouse', 'warehouse.id',
            'warehouseLocation', 'warehouseLocation.id',
            'manufactureOrder', 'manufactureOrder.id', 'manufactureOrder.typeName', 'manufactureOrder.name',
            'batchOperation.id', 'batchOperation', 'releaseOrder',
            'header', 'header.id',
            'qty', 'unit', 'remark'
        ]

        def customizeParams = [:]

        params.each { param->
            if (!(param.key in paramsKeys))
                customizeParams << param
        }
        return customizeParams

    }
}
