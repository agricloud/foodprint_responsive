package foodprint.erp.web

import foodprint.erp.Site
import foodprint.erp.Warehouse
import foodprint.erp.Item
import foodprint.erp.Inventory
import foodprint.erp.InventoryDetail

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class InventoryController {

    def grailsApplication
    def domainService
    def inventoryService

    def i18nType

    def index = {

        def list = Inventory.createCriteria().list(params, params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }

    }

    def indexByWarehouseAndItem = {
        def warehouse = Warehouse.get(params.warehouse.id)
        def item = Item.get(params.item.id)

        params.criteria = {
            if (warehouse)
                eq('warehouse', warehouse)
            if (item)
                eq('item', item)
        } >> params.criteria

        def list = Inventory.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def show = {

        def inventory = Inventory.get(params.id);
        if (inventory) {
            render (contentType: 'application/json') {
                [success: true, data: inventory]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {

        def inventory = new Inventory()
        render (contentType: 'application/json') {
            [success: true, data: inventory]
        }
    }

    @Transactional
    def delete() {
        def result
        try {
            def site = Site.get(params.site.id)
            def inventory = Inventory.get(params.id)
            if (!InventoryDetail.findByWarehouseAndItemAndSite(inventory.warehouse, inventory.item, site)) {
                result = domainService.delete(inventory)
            }
            else {
                throw new DomainDataException(message(code: "${i18nType}.inventory.inventoryDetail.has.existed", args: [inventory, e]))
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

}
