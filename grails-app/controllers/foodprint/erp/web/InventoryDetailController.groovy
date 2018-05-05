package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.Factory
import foodprint.erp.Warehouse
import foodprint.erp.WarehouseLocation
import foodprint.erp.Item
import foodprint.erp.Batch
import foodprint.erp.InventoryDetail
import foodprint.erp.PurchaseSheetDet
import foodprint.erp.ManufactureOrder
import foodprint.erp.OutSrcPurchaseSheetDet

import grails.transaction.Transactional

class InventoryDetailController {

    def grailsApplication
    def domainService
    def batchService

    def i18nType

    def index = {

        def list = InventoryDetail.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByWarehouseFactoryAndManufactureOrder = {
        def site = Site.get(params.site.id)
        def factory = Factory.get(params.warehouse.factory.id)
        def warehouses = Warehouse.findAllByFactoryAndSite(factory, site)
        
        def batches, list

        if (params.manufactureOrder.id) {
            def manufactureOrder = ManufactureOrder.get(params.manufactureOrder.id)
            batches = Batch.findAllByManufactureOrderAndSite(manufactureOrder, site)
            list = InventoryDetail.where{
                warehouse in warehouses && batch in batches && site==site
            }.list(params)
        }
        else {
            batches = Batch.findAllByManufactureOrderAndSite(null, site)
            list = InventoryDetail.where{
                warehouse in warehouses && batch in batches && site==site
            }.list(params)
        }
        // def batches = Batch.findAllByManufactureOrderAndSite(manufactureOrder, site)
        // def list = InventoryDetail.where{
        //     warehouse in warehouses && batch in batches && site==site
        // }.list(params)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWarehouseFactory = {
        def site = Site.get(params.site.id)
        def factory = Factory.get(params.warehouse.factory.id)
        def warehouses = Warehouse.findAllByFactoryAndSite(factory, site)
        def list = InventoryDetail.where{
            warehouse in warehouses && site==site
        }.list(params)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWarehouseFactoryAndItem = {
        def site = Site.get(params.site.id)
        def factory = Factory.get(params.warehouse.factory.id)
        def warehouses = Warehouse.findAllByFactoryAndSite(factory, site)
        def item = Item.get(params.item.id)
        def list = InventoryDetail.where{
            warehouse in warehouses && item==item && site==site
        }.list(params)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWarehouseAndWarehouseLocationAndItemAndBatch = {

        def warehouse = Warehouse.get(params.warehouse.id)
        def warehouseLocation = WarehouseLocation.get(params.warehouseLocation.id)
        def item = Item.get(params.item.id)
        def batch = Batch.get(params.batch.id)

        params.criteria = {
            if (warehouse)
                eq('warehouse', warehouse)
            if (warehouseLocation)
                eq('warehouseLocation', warehouseLocation)
            if (item)
                eq('item', item)
            if (batch)
                eq('batch', batch)
        } >> params.criteria

        def list = InventoryDetail.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByItem = {

        def item = Item.get(params.item.id)

        params.criteria = {
                eq('item', item)
                gt('qty', 0.0d)
        } >> params.criteria

        def list = InventoryDetail.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def show = {

        def inventoryDetail = InventoryDetail.get(params.id)
        if (inventoryDetail) {
            render (contentType: 'application/json') {
                [success: true, data: inventoryDetail]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }
}
