package foodprint.erp

import foodprint.erp.Site
import foodprint.erp.Warehouse
import foodprint.erp.Item
import foodprint.erp.Inventory

import foodprint.common.DomainDataException

import org.springframework.transaction.annotation.Transactional

class InventoryService {

    def grailsApplication
    def messageSource
    def domainService

    def i18nType

    @Transactional
    def processInventory(item, warehouse, qty, site, params) {

        def inventory = Inventory.findByWarehouseAndItemAndSite(warehouse, item, site)

        if (inventory) {
            inventory.editor = params.editor
            inventory.qty += qty
        }
        else if (qty > 0) {
            inventory = new Inventory()
            inventory.creator = params.editor
            inventory.editor = params.editor
            inventory.warehouse = warehouse
            inventory.item = item
            inventory.qty = qty
            inventory.site = site
        }
        else if (qty < 0) {
            throw new DomainDataException(messageSource.getMessage("${i18nType}.inventory.not.found", [warehouse, item] as Object[], Locale.getDefault()))
        }

        def result = domainService.save(inventory)
        
        if (!result.success) 
            throw new DomainDataException(result.message, result.errors)

        return result
    }


}
