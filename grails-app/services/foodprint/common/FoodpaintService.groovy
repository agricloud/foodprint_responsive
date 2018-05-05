package foodprint.common

import foodprint.erp.*
import foodprint.sft.*
import grails.plugins.rest.client.RestBuilder
import org.springframework.http.converter.StringHttpMessageConverter
import java.nio.charset.Charset
import grails.converters.*
import grails.util.Environment

class FoodpaintService {

    def grailsApplication
    def httpService
    def domainService
    def dateService

    static def serviceName = "foodpaint"

    /**
     * Ping to /api/ping to check service available
     */
    def ping() {

        return httpService.ping(serviceName)
    }

    def doCallService = { def controller, action, queryParams ->
        def result = httpService.doCallService serviceName, controller, action, queryParams
    }

    /**
     * Request data from Foodpaint /api/exportData
     */
    def doDataImport() {

        if (!ping().success) {
            log.error "foodpaint service not found: cannot import data"
            return [success: false]
        }

        // 原先寫法，保留供參考。
        // def rest = new RestBuilder()
        // rest.restTemplate.setMessageConverters([new StringHttpMessageConverter(Charset.forName("UTF-8"))])

        // def url = "${grailsApplication.config.grails.foodpaint.service.api.url}/exportData"
        // def resp = rest.get(url)

        // // log.debug resp.text
        // //進行資料匯入
        // importData(JSON.parse(resp.text))

        def result = doCallService "api", "exportData", null

        importData(result)

        log.debug "data import finished!"
        return [success: true]
    }

    def protected importData(records) {
        log.debug "FoodpaintService--importData"

        def importClassList = [
                'site',
                'itemCategoryLayer1',
                'itemCategoryLayer2',
                'manufacturer',
                'brand',
                'item',
                'factory',
                'workstation',
                'operation',
                'supplier',
                'customer',
                'itemRoute',
                'batch',
                'batchOperation'
            ]

        def timeZoneId = records.timeZone.ID

        importClassList.each{ importClass ->
            def className = importClass[0].toUpperCase() + importClass[1..-1]

            def domainClass = grailsApplication.getDomainClass("foodprint."+className)

            if (domainClass) {
                def fields = domainClass.persistentProperties.collect { it.name }
                records[importClass].each{ domainJson ->

                    def domain = getDomainInstance(importClass, domainJson, timeZoneId)
                    domainService.save(domain)
                }
            }

        }

    }


    def private processDomainJson(domainJson, timeZoneId) {

        domainJson.site = Site.findByName(domainJson.site?.name)
        domainJson.lastUpdated = dateService.parseUserISO8601ToUTC(domainJson.lastUpdated, timeZoneId)
        domainJson.dateCreated = dateService.parseUserISO8601ToUTC(domainJson.dateCreated, timeZoneId)
        domainJson.each{ key, value->
            if (value.toString()=='null') {
                domainJson[key]=null
            }
        }
        domainJson
    }

    def private getDomainInstance(className, domainJson, timeZoneId) {
        def domain

        domainJson = processDomainJson(domainJson, timeZoneId)

        if (className == "site")
            domain = getSiteInstance(domainJson)
        if (className == "itemCategoryLayer1")
            domain = getItemCategoryLayer1Instance(domainJson)
        if (className == "itemCategoryLayer2")
            domain = getItemCategoryLayer2Instance(domainJson)
        if (className == "manufacturer")
            domain = getManufacturerInstance(domainJson)
        if (className == "brand")
            domain = getBrandInstance(domainJson)
        if (className == "item")
            domain = getItemInstance(domainJson)
        if (className == "factory")
            domain = getFactoryInstance(domainJson)
        if (className == "workstation")
            domain = getWorkstationInstance(domainJson)
        if (className == "operation")
            domain = getOperationInstance(domainJson)
        if (className == "supplier")
            domain = getSupplierInstance(domainJson)
        if (className == "customer")
            domain = getCustomerInstance(domainJson)
        if (className == "itemRoute")
            domain = getItemRouteInstance(domainJson)
        if (className == "batch")
            domain = getBatchInstance(domainJson, timeZoneId)
        if (className == "batchOperation")
            domain = getBatchOperationInstance(domainJson, timeZoneId)

        domain
    }


    def private getSiteInstance(object) {
        def domain = Site.findByName(object.name)

        if (!domain) {
            domain = new Site(name:object.name)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.description = object.description
        domain.address = object.addr
        if (!domain.activationCode && object.activationCode) {
            domain.activationCode = object.activationCode
        }

        if (!domain.siteGroup)
            domain.siteGroup = SiteGroup.findByName("default")

        domain
    }

    def private getItemCategoryLayer1Instance(object) {

        def domain = ItemCategoryLayer1.findByNameAndSite(object.name, object.site)

        if (!domain) {
            domain = new ItemCategoryLayer1(name:object.name, site:object.site)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title

        domain
    }

    def private getItemCategoryLayer2Instance(object) {

        def itemCategoryLayer1 = ItemCategoryLayer1.findByNameAndSite(object.itemCategoryLayer1.name, object.site)

        def domain = ItemCategoryLayer2.findByNameAndItemCategoryLayer1AndSite(object.name, itemCategoryLayer1, object.site)

        if (!domain) {
            domain = new ItemCategoryLayer2(name:object.name, itemCategoryLayer1:itemCategoryLayer1, site:object.site)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title

        domain
    }

    def private getBrandInstance(object) {

        def domain = Brand.findByNameAndSite(object.name, object.site)

        if (!domain) {
            domain = new Brand(name:object.name, site:object.site)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title

        domain
    }

    def private getItemInstance(object) {

        def brand = Brand.findByNameAndSite(object.brand.name, object.site)
        def domain = Item.findByNameAndBrandAndSite(object.name, brand, object.site)

        if (!domain) {
            domain = new Item(name:object.name, brand:brand, site:object.site)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.description = object.description
        domain.spec = object.spec
        domain.unit = object.unit
        if (object.itemCategoryLayer1?.name) {
            def itemCategoryLayer1 = ItemCategoryLayer1.findByNameAndSite(object.itemCategoryLayer1.name, object.site)
            domain.itemCategoryLayer1 = itemCategoryLayer1

            if (object.itemCategoryLayer2?.name) {
                def itemCategoryLayer2 = ItemCategoryLayer2.findByNameAndItemCategoryLayer1AndSite(object.itemCategoryLayer2.name, itemCategoryLayer1, object.site)
                domain.itemCategoryLayer2 = itemCategoryLayer2
            }
        }

        if (object.defaultManufacturer?.name) {
            def defaultManufacturer = Manufacturer.findByNameAndSite(object.defaultManufacturer.name, object.site)
            domain.defaultManufacturer = defaultManufacturer
        }

        domain
    }

    def private getFactoryInstance(object) {

        def domain = Factory.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Factory(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.tel = object.tel
        domain.fax = object.fax
        domain.email = object.email
        domain.address = object.address
        domain.remark = object.remark

        domain
    }

    def private getWorkstationInstance(object) {

        def domain = Workstation.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Workstation(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.description = object.description

        def factory = Factory.findByNameAndSite(object.factory.name, object.site)
        domain.factory = factory

        domain
    }

    def private getOperationInstance(object) {

        def domain = Operation.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Operation(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.description = object.description

        domain
    }

    def private getSupplierInstance(object) {

        def domain = Supplier.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Supplier(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.country = object.country
        domain.tel = object.tel
        domain.fax = object.fax
        domain.contact = object.contact
        domain.email = object.email
        domain.address = object.address
        domain.isManufacturer = object.isManufacturer

        if (domain.isManufacturer == true) {
            def manufacturer = Manufacturer.findByNameAndSite(object.manufacturer?.name, object.site)
            domain.manufacturer = manufacturer
            manufacturer.supplier = domain
        }

        domain
    }

    def private getManufacturerInstance(object) {

        def domain = Manufacturer.findByNameAndSite(object.name, object.site)

        if (!domain) {
            domain = new Manufacturer(name:object.name, site:object.site)
        }

        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.country = object.country
        domain.tel = object.tel
        domain.fax = object.fax
        domain.contact = object.contact
        domain.email = object.email
        domain.address = object.address
        domain.isSupplier = object.isSupplier

        domain
    }

    def private getCustomerInstance(object) {

        def domain = Customer.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Customer(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated
        domain.title = object.title
        domain.tel = object.tel
        domain.fax = object.fax
        domain.contact = object.contact
        domain.email = object.email
        domain.address = object.address
        domain.shippingAddress = object.shippingAddress

        domain
    }

    def private getBatchInstance(object, timeZoneId) {

        def domain = Batch.findByNameAndSite(object.name, object.site)
        if (!domain) {
            domain = new Batch(name:object.name, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated

        def brand = Brand.findByNameAndSite(object.item.brand.name, object.site)
        domain.item = Item.findByNameAndBrandAndSite(object.item.name, brand, object.site)

        domain.expectQty = object.expectQty
        domain.batchType = object.batchType
        domain.batchSourceType = object.batchSourceType
        domain.country = object.country
        domain.manufacturer = Manufacturer.findByNameAndSite(object.manufacturer?.name, object.site)

        // log.debug "匯入前domain::batch-manufactureDate"+domain.manufactureDate
        // log.debug "傳入資料foodpaint::batch-manufactureDate"+object.manufactureDate
        if (object.manufactureDate && object.manufactureDate!=null) {
            domain.manufactureDate = dateService.parseUserISO8601ToUTC(object.manufactureDate, timeZoneId)
        }
        // log.debug "預計匯入domain::batch-manufactureDate"+domain.manufactureDate
        domain
    }

    def private getItemRouteInstance(object) {

        def brand = Brand.findByNameAndSite(object.item.brand.name, object.site)
        def item = Item.findByNameAndBrandAndSite(object.item.name, brand, object.site)

        def domain = ItemRoute.findByItemAndSequenceAndSite(item, object.sequence, object.site)
        if (!domain) {
            domain = new ItemRoute(item:item,sequence:object.sequence, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated

        domain.operation = Operation.findByNameAndSite(object.operation.name, object.site)
        domain.workstation = Workstation.findByNameAndSite(object.workstation?.name, object.site)
        domain.supplier = Supplier.findByNameAndSite(object.supplier?.name, object.site)

        domain
    }

    def private getBatchOperationInstance(object, timeZoneId) {

        def batch = Batch.findByNameAndSite(object.batch.name, object.site)

        def domain = BatchOperation.findByBatchAndSequenceAndSite(batch, object.sequence, object.site)
        if (!domain) {
            domain = new BatchOperation(batch:batch, sequence:object.sequence, site:object.site)
        }
        domain.editor = object.editor
        domain.creator = object.creator
        domain.dateCreated = object.dateCreated
        domain.lastUpdated = object.lastUpdated

        domain.operation = Operation.findByNameAndSite(object.operation.name, object.site)
        domain.workstation = Workstation.findByNameAndSite(object.workstation?.name, object.site)
        domain.supplier = Supplier.findByNameAndSite(object.supplier?.name, object.site)

        // log.debug "目前不匯入製程日期"
        // log.debug "匯入前domain::batchOperation-date"+domain.startDate+"/"+domain.endDate
        // log.debug "傳入資料foodpaint::batchOperation-date"+object.startDate+"/"+object.endDate

        //製程日期以print為主 若print無資料記錄 才匯入paint段
        if (object.startDate && !domain.startDate)
            domain.startDate = dateService.parseUserISO8601ToUTC(object.startDate, timeZoneId)

        if (object.endDate && !domain.endDate)
            domain.endDate = dateService.parseUserISO8601ToUTC(object.endDate, timeZoneId)

        // log.debug "預計匯入domain::batchOperation-date"+domain.startDate+"/"+domain.endDate

        domain
    }

}
