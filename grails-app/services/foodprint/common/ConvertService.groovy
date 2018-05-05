package foodprint.common

import foodprint.authority.*
import foodprint.erp.*
import foodprint.sft.*

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import grails.converters.JSON

class ConvertService {

    def grailsApplication
    def messageSource
    def enumService
    def dateService

    def i18nType

    def getDomainFields(domainClassName) {
            def fields = []
            def d = new DefaultGrailsDomainClass(domainClassName)
            d.persistentProperties.collect {
                fields << it.name
            }
            fields

    }

    def domainParseMap(domainObject) {
        log.info domainObject.class
        def result = [:]
        getDomainFields(domainObject.class).each { field ->
            result[field] = domainObject[field]
        }
        result.id = domainObject.id

        result
    }

    def processCommonParse(domain) {
        def result = [:]
        result.id = domain.id
        // result.dateCreated = dateService.formatWithISO8601(domain.dateCreated)
        // result.lastUpdated = dateService.formatWithISO8601(domain.lastUpdated)
        // result.creator = domain.creator
        // result.editor = domain.editor

        switch (domain.class) {
            case Role:
            case RoleGroup:
            case RoleGroupRole:
            case SiteGroup:
            case User:
            case UserRoleGroup:
            case Site:
                return result
                break;
        }

        if (domain.site) {
            result.site = domain.site
            result["site.id"] = domain.site.id
            result["site.name"] = domain.site.name
            result["site.title"] = domain.site.title
        }

        result
    }

    def roleParseJson(domain) {

        Object[] args = [domain]

        def result = processCommonParse(domain)

        result.authority = domain.authority
        result.title = messageSource.getMessage("${i18nType}.role.${domain.authority}.label", args, Locale.getDefault())
        result
    }

    def roleGroupParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        result
    }

    def roleGroupRoleParseJson(domain) {
        Object[] args = [domain]
        
        def result = processCommonParse(domain)

        result.roleGroup = domain.roleGroup
        result["roleGroup.id"] = domain.roleGroup.id
        result["roleGroup.name"] = domain.roleGroup.name
        result["roleGroup.title"] = domain.roleGroup.title

        if (result.roleGroup.siteGroup) {
            result.roleGroup.siteGroup = domain.roleGroup.siteGroup
            result["roleGroup.siteGroup.id"] = domain.roleGroup.siteGroup.id
            result["roleGroup.siteGroup.name"] = domain.roleGroup.siteGroup.name
            result["roleGroup.siteGroup.title"] = domain.roleGroup.siteGroup.title
        }

        result.role = domain.role
        result["role.id"] = domain.role.id
        result["role.authority"] = domain.role.authority
        result["role.title"] = messageSource.getMessage("${i18nType}.role.${domain.role.authority}.label", args, Locale.getDefault())


        result
    }

    def siteGroupParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        result
    }

    def siteParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.description = domain.description
        result.address = domain.address

        if (domain.activationCode)
            result.activationCode = "${domain.activationCode.getAt(0)}************************${domain.activationCode.getAt(-3)}*${domain.activationCode.getAt(-1)}"

        if (domain.siteGroup) {
            result.siteGroup = domain.siteGroup
            result["siteGroup.id"] = domain.siteGroup.id
            result["siteGroup.name"] = domain.siteGroup.name
            result["siteGroup.title"] = domain.siteGroup.title
        }
        result
    }

    def userTypeParseJson(domain) {
        def userType = enumService.name(domain)
        userType.name
    }

    def userParseJson(domain) {
        def result = processCommonParse(domain)

        def userType = enumService.name(domain.userType)
        result.userType = userType.name
        result.userTypeTitle = userType.title
        result.username = domain.username
        result.fullName = domain.fullName
        if (domain.password)
            result.password = "***************************************"
        result.enabled = domain.enabled
        result.accountExpired = domain.accountExpired
        result.accountLocked = domain.accountLocked
        result.passwordExpired = domain.passwordExpired
        result.email = domain.email

        if (domain.activationCode)
            result.activationCode = "${domain.activationCode.getAt(0)}************************${domain.activationCode.getAt(-3)}*${domain.activationCode.getAt(-1)}"
        
        if (domain.lastLoginSite) {
            result.lastLoginSite = domain.lastLoginSite
            result["lastLoginSite.id"] = domain.lastLoginSite.id
            result["lastLoginSite.name"] = domain.lastLoginSite.name
            result["lastLoginSite.title"] = domain.lastLoginSite.title
        }

        if (domain.siteGroup) {
            result.siteGroup = domain.siteGroup
            result["siteGroup.id"] = domain.siteGroup.id
            result["siteGroup.name"] = domain.siteGroup.name
            result["siteGroup.title"] = domain.siteGroup.title
        }

        result
    }

    def userRoleGroupParseJson(domain) {
        def result = processCommonParse(domain)

        result.user.id = domain.user.id
        result.user.username = domain.user.username

        result.roleGroup.id = domain.roleGroup.id
        result.roleGroup.name = domain.roleGroup.name
        result.roleGroup.title = domain.roleGroup.title

        result
    }

    def userSiteParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.user) {
            result.user = domain.user
            result["user.id"] = domain.user.id
            result["user.username"] = domain.user.username
            result["user.fullName"] = domain.user.fullName

            def userType = enumService.name(domain.user.userType)
            result["user.userType"] = userType.name
            result["user.userTypeTitle"] = userType.title
        }

        result
    }

    def typeNameParseJson(domain) {
        def result = processCommonParse(domain)

        result.id        = domain.id
        result.name  = domain.name
        result.title     = domain.title
        result.fullTitle = domain.fullTitle
        result.yearDigit = domain.yearDigit
        result.runningDigit = domain.runningDigit
        result.multiplier = domain.multiplier

        if (domain.sheetType) {
            def sheetType = enumService.name(domain.sheetType)
            result["sheetType"] = sheetType.name
            result["sheetTypeTitle"] = sheetType.title
        }
        if (domain.sheetFormatType) {
            def sheetFormatType = enumService.name(domain.sheetFormatType)
            result["sheetFormatType"] = sheetFormatType.name
            result["sheetFormatTypeTitle"] = sheetFormatType.title
        }
        if (domain.transactionType) {
            def transactionType = enumService.name(domain.transactionType)
            result["transactionType"] = transactionType.name
            result["transactionTypeTitle"] = transactionType.title
        }

        if (domain.manufactureType) {
            def manufactureType = enumService.name(domain.manufactureType)
            result["manufactureType"] = manufactureType.name
            result["manufactureTypeTitle"] = manufactureType.title
        }


        result
    }

    def itemCategoryLayer1ParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        result
    }

    def itemCategoryLayer2ParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.itemCategoryLayer1 = domain.itemCategoryLayer1
        result["itemCategoryLayer1.id"] = domain.itemCategoryLayer1.id
        result["itemCategoryLayer1.name"] = domain.itemCategoryLayer1.name
        result["itemCategoryLayer1.title"] = domain.itemCategoryLayer1.title

        result
    }

    def brandParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.description = domain.description

        result
    }

    def itemParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        def itemType = enumService.name(domain.itemType)
        result.itemType = itemType.name
        result.itemTypeTitle = itemType.title
        result.spec = domain.spec
        result.unit = domain.unit
        result.description = domain.description
        result.dueDays = domain.dueDays
        result.effectStartDate = dateService.formatWithISO8601(domain.effectStartDate)
        result.effectEndDate = dateService.formatWithISO8601(domain.effectEndDate)

        def workFlowType = enumService.name(domain.workFlowType)
        result.workFlowType = workFlowType.name
        result.workFlowTypeTitle = workFlowType.title
        result.cycleTime = domain.cycleTime

        if (domain.brand) {
            result.brand = domain.brand
            result["brand.id"] = domain.brand.id
            result["brand.name"] = domain.brand.name
            result["brand.title"] = domain.brand.title
        }

        if (domain.itemCategoryLayer1) {
            result.itemCategoryLayer1 = domain.itemCategoryLayer1
            result["itemCategoryLayer1.id"] = domain.itemCategoryLayer1.id
            result["itemCategoryLayer1.name"] = domain.itemCategoryLayer1.name
            result["itemCategoryLayer1.title"] = domain.itemCategoryLayer1.title
            if (domain.itemCategoryLayer2) {
                result.itemCategoryLayer2 = domain.itemCategoryLayer2
                result["itemCategoryLayer2.id"] = domain.itemCategoryLayer2.id
                result["itemCategoryLayer2.name"] = domain.itemCategoryLayer2.name
                result["itemCategoryLayer2.title"] = domain.itemCategoryLayer2.title
            }
        }

        if (domain.defaultManufacturer) {
            result.defaultManufacturer = domain.defaultManufacturer
            result["defaultManufacturer.id"] = domain.defaultManufacturer.id
            result["defaultManufacturer.name"] = domain.defaultManufacturer.name
            result["defaultManufacturer.title"] = domain.defaultManufacturer.title
            def country = enumService.name(domain.defaultManufacturer.country)
            result["defaultManufacturer.country"] = country.name
            result["defaultManufacturer.countryTitle"] = country.title
        }

        if (domain.defaultWorkstation){
            result.defaultWorkstation = domain.defaultWorkstation
            result["defaultWorkstation.id"] = domain.defaultWorkstation.id
            result["defaultWorkstation.name"] = domain.defaultWorkstation.name
            result["defaultWorkstation.title"] = domain.defaultWorkstation.title
        }

        result
    }

    def itemStageParseJson(domain) {
        def result = processCommonParse(domain)

        result.sequence = domain.sequence
        result.title = domain.title
        result.description = domain.description

        result.item = domain.item
        result["item.id"] = domain.item.id
        result["item.name"] = domain.item.name
        result["item.title"] = domain.item.title
        result["item.brand.id"] = domain.item.brand.id
        result["item.brand.name"] = domain.item.brand.name
        result["item.brand.title"] = domain.item.brand.title
        result["item.spec"] = domain.item.spec
        result["item.unit"] = domain.item.unit

        result
    }

    def itemRegisteredNumParseJson(domain) {
        def result = processCommonParse(domain)

        result.registeredNum = domain.registeredNum
        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }
        if (domain.manufacturer) {
            result.manufacturer = domain.manufacturer
            result["manufacturer.id"] = domain.manufacturer.id
            result["manufacturer.name"] = domain.manufacturer.name
            result["manufacturer.title"] = domain.manufacturer.title
        }
        if (domain.country) {
            def country = enumService.name(domain.country)
            result["country"] = country.name
            result["countryTitle"] = country.title
        }

        result
    }

    def factoryParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        result.tel = domain.tel
        result.fax = domain.fax
        result.email = domain.email
        result.address = domain.address
        result.remark = domain.remark

        result
    }

    def operationCategoryLayer1ParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        result
    }

    def operationParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.description = domain.description

        if (domain.operationCategoryLayer1) {
            result.operationCategoryLayer1 = domain.operationCategoryLayer1
            result["operationCategoryLayer1.id"] = domain.operationCategoryLayer1.id
            result["operationCategoryLayer1.name"] = domain.operationCategoryLayer1.name
            result["operationCategoryLayer1.title"] = domain.operationCategoryLayer1.title
        }

        result
    }

    def workstationParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.description = domain.description

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        result
    }

    def supplierParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.isManufacturer = domain.isManufacturer
        if (domain.manufacturer) {
            //為了避免convert json形成nested loop(manufacturer有supplier)
            //此處不用result.manufacturer = supplier.manufacturer
            //改以手動設定map
            result.manufacturer = [:]
            result.manufacturer.id = domain.manufacturer.id
            result.manufacturer.name = domain.manufacturer.name
            result.manufacturer.title = domain.manufacturer.title
            result["manufacturer.id"] = domain.manufacturer.id
            result["manufacturer.name"] = domain.manufacturer.name
            result["manufacturer.title"] = domain.manufacturer.title
        }
        if (domain.country) {
            def country = enumService.name(domain.country)
            result.country = country.name
            result.countryTitle = country.title
        }
        result.tel = domain.tel
        result.fax = domain.fax
        result.contact = domain.contact
        result.email = domain.email
        result.address = domain.address

        result
    }

    def manufacturerParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.isSupplier = domain.isSupplier
        if (domain.supplier) {
            //為了避免convert json形成nested loop(supplier有manufacturer)
            //此處不用result.supplier = manufacturer.supplier
            //改以手動設定map
            result.supplier = [:]
            result.supplier.id = domain.supplier.id
            result.supplier.name = domain.supplier.name
            result.supplier.title = domain.supplier.title
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }
        if (domain.country) {
            def country = enumService.name(domain.country)
            result.country = country.name
            result.countryTitle = country.title
        }
        result.tel = domain.tel
        result.fax = domain.fax
        result.contact = domain.contact
        result.email = domain.email
        result.address = domain.address

        result
    }

    def customerParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.tel = domain.tel
        result.fax = domain.fax
        result.contact = domain.contact
        result.email = domain.email
        result.address = domain.address
        result.shippingAddress = domain.shippingAddress

        result
    }

    def employeeParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        def employeeType = enumService.name(domain.employeeType)
        result.employeeType = employeeType.name
        result.employeeTypeTitle = employeeType.title
        result.idNumber = domain.idNumber
        result.birthDate = dateService.formatWithISO8601(domain.birthDate)
        result.tel = domain.tel
        result.mobile = domain.mobile
        result.permanentAddress = domain.permanentAddress
        result.residentialAddress = domain.residentialAddress
        result.correspondenceAddress = domain.correspondenceAddress
        result.email = domain.email
        result.contact = domain.contact
        result.contactPhoneNumber = domain.contactPhoneNumber
        result.introduction = domain.introduction
        result.experience = domain.experience
        result.mainWork = domain.mainWork
        result.area = domain.area
        result.description = domain.description
        result.remark = domain.remark

        result
    }

    def itemRouteParseJson(domain) {
        def result = processCommonParse(domain)

        result.sequence = domain.sequence

        result.item = domain.item
        result["item.id"] = domain.item.id
        result["item.name"] = domain.item.name
        result["item.title"] = domain.item.title
        result["item.brand.id"] = domain.item.brand.id
        result["item.brand.name"] = domain.item.brand.name
        result["item.brand.title"] = domain.item.brand.title

        if (domain.operation) {
            result.operation = domain.operation
            result["operation.id"] = domain.operation.id
            result["operation.name"] = domain.operation.name
            result["operation.title"] = domain.operation.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result
    }

    def batchParseJson(domain) {
        def result = processCommonParse(domain)

        result.id = domain.id
        result.name = domain.name

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        result.effectEndDate = dateService.formatWithISO8601(domain.effectEndDate)
        result.expectQty = domain.expectQty
        result.manufactureDate = dateService.formatWithISO8601(domain.manufactureDate)
        result.expirationDate = dateService.formatWithISO8601(domain.expirationDate)
        result.remark = domain.remark

        if (domain.batchType) {
            def batchType = enumService.name(domain.batchType)
            result["batchType"] = batchType.name
            result["batchTypeTitle"] = batchType.title
        }
        if (domain.batchSourceType) {
            def batchSourceType = enumService.name(domain.batchSourceType)
            result["batchSourceType"] = batchSourceType.name
            result["batchSourceTypeTitle"] = batchSourceType.title
        }

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.factory.id"] = domain.manufactureOrder.factory.id
            result["manufactureOrder.factory.name"] = domain.manufactureOrder.factory.name
            result["manufactureOrder.factory.title"] = domain.manufactureOrder.factory.title
            result["manufactureOrder.workstation.id"] = domain.manufactureOrder.workstation?.id
            result["manufactureOrder.workstation.name"] = domain.manufactureOrder.workstation?.name
            result["manufactureOrder.workstation.title"] = domain.manufactureOrder.workstation?.title
            result["manufactureOrder.supplier.id"] = domain.manufactureOrder.supplier?.id
            result["manufactureOrder.supplier.name"] = domain.manufactureOrder.supplier?.name
            result["manufactureOrder.supplier.title"] = domain.manufactureOrder.supplier?.title
        }

        if (domain.manufactureBatch) {
            result.manufactureBatch = domain.manufactureBatch
            result["manufactureBatch.id"] = domain.manufactureBatch.id
            result["manufactureBatch.name"] = domain.manufactureBatch.name
            def batchSourceType = enumService.name(domain.manufactureBatch.batchSourceType)
            result["manufactureBatch.batchSourceType"] = batchSourceType.name
            result["manufactureBatch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.manufactureBatch.country)
            result["manufactureBatch.country"] = country.name
            result["manufactureBatch.countryTitle"] = country.title
            result["manufactureBatch.manufacturer.id"] = domain.manufactureBatch.manufacturer?.id
            result["manufactureBatch.manufacturer.name"] = domain.manufactureBatch.manufacturer?.name
            result["manufactureBatch.manufacturer.title"] = domain.manufactureBatch.manufacturer?.title
        }

        if (domain.manufacturer) {
            result.manufacturer = domain.manufacturer
            result["manufacturer.id"] = domain.manufacturer.id
            result["manufacturer.name"] = domain.manufacturer.name
            result["manufacturer.title"] = domain.manufacturer.title
        }

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.status"] = domain.manufactureOrder.status
        }
        
        if (domain.country) {
            def country = enumService.name(domain.country)
            result["country"] = country.name
            result["countryTitle"] = country.title
        }        

        result
    }

    def batchOperationParseJson(domain) {
        def result = processCommonParse(domain)

        result.sequence = domain.sequence
        result.startDate = dateService.formatWithISO8601(domain.startDate)
        result.endDate = dateService.formatWithISO8601(domain.endDate)
        result.batch = domain.batch
        result["batch.id"] = domain.batch.id
        result["batch.name"] = domain.batch.name
        result["batch.item.id"] = domain.batch.item.id

        result.remark = domain.remark

        if (domain.operation) {
            result.operation = domain.operation
            result["operation.id"] = domain.operation.id
            result["operation.name"] = domain.operation.name
            result["operation.title"] = domain.operation.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        if (domain.operator) {
            result.operator = domain.operator
            result["operator.id"] = domain.operator.id
            result["operator.name"] = domain.operator.name
            result["operator.title"] = domain.operator.title
        }

        if (domain.itemStage) {
            result.itemStage = domain.itemStage
            result["itemStage.id"] = domain.itemStage.id
            result["itemStage.sequence"] = domain.itemStage.sequence
            result["itemStage.title"] = domain.itemStage.title
        }

        result
    }

    def billOfMaterialParseJson(domain) {
        def result = processCommonParse(domain)

        result.updatedDate = dateService.formatWithISO8601(domain.updatedDate)
        
        result.id = domain.id

        if(domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            def itemType = enumService.name(domain.item.itemType)
            result["item.itemType"] = itemType.name
            result["item.itemTypeTitle"] = itemType.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.manufactureOrderTypeName) {
            result.manufactureOrderTypeName = domain.manufactureOrderTypeName
            result["manufactureOrderTypeName.id"] = domain.manufactureOrderTypeName.id
            result["manufactureOrderTypeName.name"] = domain.manufactureOrderTypeName.name
            result["manufactureOrderTypeName.title"] = domain.manufactureOrderTypeName.title
            def sheetType = enumService.name(domain.manufactureOrderTypeName.sheetType)
            result["manufactureOrderTypeName.sheetType"] = sheetType.name
            result["manufactureOrderTypeName.sheetTypeTitle"] = sheetType.title
        }

        result.remark = domain.remark

        result
    }

    def billOfMaterialDetParseJson(domain) {
        def result = processCommonParse(domain)

        result["header.id"] = domain.header.id
        result.sequence = domain.sequence
        result.qty = domain.qty
        result.denominator = domain.denominator
        result.releaseOrder = domain.releaseOrder
        result.effectStartDate = dateService.formatWithISO8601(domain.effectStartDate)
        result.effectEndDate = dateService.formatWithISO8601(domain.effectEndDate)

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.operation) {
            result.operation = domain.operation
            result["operation.id"] = domain.operation.id
            result["operation.name"] = domain.operation.name
            result["operation.title"] = domain.operation.title
        }

        result
    }

    def warehouseParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        result.capacity = domain.capacity
        result.capacityUnit = domain.capacityUnit
        result.remark = domain.remark

        result
    }

    def warehouseLocationParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.capacity = domain.capacity
        result.capacityUnit = domain.capacityUnit
        result.description = domain.description
        result.remark = domain.remark

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        result
    }

    def inventoryParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }
        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.unit"] = domain.item.unit
        }
        result.qty = domain.qty

        result
    }

    def inventoryDetailParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }
        if  (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }
        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.unit"] = domain.item.unit
        }
        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title
        }

        result.qty = domain.qty

        result
    }

     def inventoryTransactionSheetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title


        result.name = domain.name

        result.executionDate = dateService.formatWithISO8601(domain.executionDate)
        
        result.remark = domain.remark

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        result
    }

    def inventoryTransactionSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence
        result.qty = domain.qty
        result.unit = domain.unit
        result.remark = domain.remark

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            result["batch.item.name"] = domain.batch.item.name
            result["batch.item.title"] = domain.batch.item.title
            result["batch.item.brand.name"] = domain.batch.item.brand.name
            result["batch.item.brand.title"] = domain.batch.item.brand.title
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            if (domain.batch.manufacturer) {
                result["batch.manufacturer.id"] = domain.batch.manufacturer.id
                result["batch.manufacturer.name"] = domain.batch.manufacturer.name
                result["batch.manufacturer.title"] = domain.batch.manufacturer.title
            }
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.outWarehouse) {
            result.outWarehouse = domain.outWarehouse
            result["outWarehouse.id"] = domain.outWarehouse.id
            result["outWarehouse.name"] = domain.outWarehouse.name
            result["outWarehouse.title"] = domain.outWarehouse.title
        }

        if (domain.outWarehouseLocation) {
            result.outWarehouseLocation = domain.outWarehouseLocation
            result["outWarehouseLocation.id"] = domain.outWarehouseLocation.id
            result["outWarehouseLocation.name"] = domain.outWarehouseLocation.name
            result["outWarehouseLocation.title"] = domain.outWarehouseLocation.title
        }

        if (domain.inWarehouse) {
            result.inWarehouse = domain.inWarehouse
            result["inWarehouse.id"] = domain.inWarehouse.id
            result["inWarehouse.name"] = domain.inWarehouse.name
            result["inWarehouse.title"] = domain.inWarehouse.title
        }

        if (domain.inWarehouseLocation) {
            result.inWarehouseLocation = domain.inWarehouseLocation
            result["inWarehouseLocation.id"] = domain.inWarehouseLocation.id
            result["inWarehouseLocation.name"] = domain.inWarehouseLocation.name
            result["inWarehouseLocation.title"] = domain.inWarehouseLocation.title
        }

        result
    }

    def customerOrderParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }

        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)
        result.dueDate = dateService.formatWithISO8601(domain.dueDate)
        result.shippingAddress = domain.shippingAddress


        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.customer) {
            result.customer = domain.customer
            result["customer.id"] = domain.customer.id
            result["customer.name"] = domain.customer.name
            result["customer.title"] = domain.customer.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def customerOrderDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id
        result["header.typeName"] = domain.header.typeName
        result["header.typeName.id"] = domain.header.typeName.id
        result["header.typeName.name"] = domain.header.typeName.name
        result["header.typeName.title"] = domain.header.typeName.title
        def headerSheetType = enumService.name(domain.header.typeName.sheetType)
        result["header.typeName.sheetType"] = headerSheetType.name
        result["header.typeName.sheetTypeTitle"] = headerSheetType.title
        result["header.name"] = domain.header.name

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        result
    }

    def manufactureOrderParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }

        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        result.expectQty = domain.expectQty

        def manufactureType = enumService.name(domain.manufactureType)
        result["manufactureType"] = manufactureType.name
        result["manufactureTypeTitle"] = manufactureType.title

        def status = enumService.name(domain.status)
        result["status"] = status.name
        result["statusTitle"] = status.title

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
            result["supplier.manufacturer.id"] = domain.supplier.manufacturer.id
            result["supplier.manufacturer.name"] = domain.supplier.manufacturer.name
            result["supplier.manufacturer.title"] = domain.supplier.manufacturer.title
            def country = enumService.name(domain.supplier.manufacturer.country)
            result["supplier.manufacturer.country"] = country.name
            result["supplier.manufacturer.countryTitle"] = country.title
        }

        result.batchName = domain.batchName

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.customerOrderDet) {
            result.customerOrderDet = domain.customerOrderDet
            result["customerOrderDet.id"] = domain.customerOrderDet.id
            result["customerOrderDet.name"] = domain.customerOrderDet.name
            result["customerOrderDet.typeName"] = domain.customerOrderDet.typeName
            result["customerOrderDet.typeName.id"] = domain.customerOrderDet.typeName.id
            result["customerOrderDet.typeName.name"] = domain.customerOrderDet.typeName.name
            result["customerOrderDet.typeName.title"] = domain.customerOrderDet.typeName.title
            def customerOrderDetSheetType = enumService.name(domain.customerOrderDet.typeName.sheetType)
            result["customerOrderDet.typeName.sheetType"] = customerOrderDetSheetType.name
            result["customerOrderDet.typeName.sheetTypeTitle"] = customerOrderDetSheetType.title

            result["customerOrderDet.sequence"] = domain.customerOrderDet.sequence
        }

        result.isSplit = domain.isSplit

        result
    }

    def manufactureOrderChangeOrderParseJson(domain) {
        def result = processCommonParse(domain)

        result.originVersion = domain.originVersion
        result.originEditor = domain.originEditor
        result.originCreator = domain.originCreator
        result.originDateCreated = dateService.formatWithISO8601(domain.originDateCreated)
        result.originLastUpdated = dateService.formatWithISO8601(domain.originLastUpdated)

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
        }

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        result.reason = domain.reason
        
        if (domain.status) {
            def status = enumService.name(domain.status)
            result["status"] = status.name
            result["statusTitle"] = status.title
        }

        if (domain.originStatus) {
            def originStatus = enumService.name(domain.originStatus)
            result["originStatus"] = originStatus.name
            result["originStatusTitle"] = originStatus.title
        }

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }
        result.originFactoryId = domain.originFactoryId

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        result.originWorkstationId = domain.originWorkstationId

        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }
        result.originSupplierId = domain.originSupplierId

        if (domain.customerOrderDet) {
            result.customerOrderDet = domain.customerOrderDet
            result["customerOrderDet.id"] = domain.customerOrderDet.id
            result["customerOrderDet.typeName"] = domain.customerOrderDet.typeName
            result["customerOrderDet.typeName.id"] = domain.customerOrderDet.typeName.id
            result["customerOrderDet.typeName.name"] = domain.customerOrderDet.typeName.name
            result["customerOrderDet.typeName.title"] = domain.customerOrderDet.typeName.title
            def customerOrderDetSheetType = enumService.name(domain.customerOrderDet.typeName.sheetType)
            result["customerOrderDet.typeName.sheetType"] = customerOrderDetSheetType.name
            result["customerOrderDet.typeName.sheetTypeTitle"] = customerOrderDetSheetType.title
            result["customerOrderDet.name"] = domain.customerOrderDet.name
            result["customerOrderDet.sequence"] = domain.customerOrderDet.sequence
        }
        result.originCustomerOrderDetId = domain.originCustomerOrderDetId

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }
        result.originItemId = domain.originItemId

        result.expectQty = domain.expectQty
        result.originExpectQty = domain.originExpectQty

        result.batchName = domain.batchName
        result.originBatchName = domain.originBatchName

        result
    }

    def purchaseSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title

        }

        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)
        result.totalPrice = domain.totalPrice

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def purchaseSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.header = domain.header
        result["header.id"] = domain.header.id

        result.qty = domain.qty
        result.unit = domain.unit
        result.price = domain.price
        result.totalPrice = domain.totalPrice
        result.remark = domain.remark

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer.title
        }


        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def purchaseReturnSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def purchaseReturnSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.purchaseSheetDet) {
            result.purchaseSheetDet = domain.purchaseSheetDet
            result["purchaseSheetDet.id"] = domain.purchaseSheetDet.id
            result["purchaseSheetDet.typeName"] = domain.purchaseSheetDet.typeName
            result["purchaseSheetDet.typeName.id"] = domain.purchaseSheetDet.typeName.id
            result["purchaseSheetDet.typeName.name"] = domain.purchaseSheetDet.typeName.name
            result["purchaseSheetDet.typeName.title"] = domain.purchaseSheetDet.typeName.title
            def purchaseSheetDetSheetType = enumService.name(domain.purchaseSheetDet.typeName.sheetType)
            result["purchaseSheetDet.typeName.sheetType"] = purchaseSheetDetSheetType.name
            result["purchaseSheetDet.typeName.sheetTypeTitle"] = purchaseSheetDetSheetType.title
            result["purchaseSheetDet.name"] = domain.purchaseSheetDet.name
            result["purchaseSheetDet.sequence"] = domain.purchaseSheetDet.sequence
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer.title
        }


        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def materialSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def materialSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit
        result.remark = domain.remark

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
            result["manufactureOrder.batchName"] = domain.manufactureOrder.batchName
        }

        if (domain.batchOperation) {
            result.batchOperation = domain.batchOperation
            result["batchOperation.id"] = domain.batchOperation.id
            result["batchOperation.sequence"] = domain.batchOperation.sequence

            if (domain.batchOperation.workstation) {
                result["batchOperation.workstation"] = domain.batchOperation.workstation
                result["batchOperation.workstation.id"] = domain.batchOperation.workstation.id
                result["batchOperation.workstation.name"] = domain.batchOperation.workstation.name
                result["batchOperation.workstation.title"] = domain.batchOperation.workstation.title
            }

            if (domain.batchOperation.supplier) {
                result["batchOperation.supplier"] = domain.batchOperation.supplier
                result["batchOperation.supplier.id"] = domain.batchOperation.supplier.id
                result["batchOperation.supplier.name"] = domain.batchOperation.supplier.name
                result["batchOperation.supplier.title"] = domain.batchOperation.supplier.title
            }

            result["batchOperation.operation"] = domain.batchOperation.operation
            result["batchOperation.operation.id"] = domain.batchOperation.operation.id
            result["batchOperation.operation.name"] = domain.batchOperation.operation.name
            result["batchOperation.operation.title"] = domain.batchOperation.operation.title

        }

        result.releaseOrder = domain.releaseOrder

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def materialSheetDetCustomizeParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.itemCategoryLayer1) {
            result.itemCategoryLayer1 = domain.itemCategoryLayer1
            result["itemCategoryLayer1.id"] = domain.itemCategoryLayer1.id
            result["itemCategoryLayer1.name"] = domain.itemCategoryLayer1.name
            result["itemCategoryLayer1.title"] = domain.itemCategoryLayer1.title
            if (domain.itemCategoryLayer2) {
                result.itemCategoryLayer2 = domain.itemCategoryLayer2
                result["itemCategoryLayer2.id"] = domain.itemCategoryLayer2.id
                result["itemCategoryLayer2.name"] = domain.itemCategoryLayer2.name
                result["itemCategoryLayer2.title"] = domain.itemCategoryLayer2.title
            }
        }

        result.title = domain.title
        result.defaultValue = domain.defaultValue
        def fieldType = enumService.name(domain.fieldType)
        result.fieldType = fieldType.name
        result.fieldTypeTitle = fieldType.title
        result.description = domain.description
        result.unit = domain.unit

        result
    }

    def materialSheetDetCustomizeDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.materialSheetDet = domain.materialSheetDet
        result["materialSheetDet.id"] = domain.materialSheetDet?.id

        result.materialSheetDetCustomize = domain.materialSheetDetCustomize
        result["materialSheetDetCustomize.id"] = domain.materialSheetDetCustomize.id
        result["materialSheetDetCustomize.title"] = domain.materialSheetDetCustomize.title
        result["materialSheetDetCustomize.defaultValue"] = domain.materialSheetDetCustomize.defaultValue
        def fieldType = enumService.name(domain.materialSheetDetCustomize.fieldType)
        result["materialSheetDetCustomize.fieldType"] = fieldType.name
        result["materialSheetDetCustomize.fieldTypeTitle"] = fieldType.title

        result.value = domain.value

        result
    }

    def materialReturnSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }

        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def materialReturnSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.materialSheetDet) {
            result.materialSheetDet = domain.materialSheetDet
            result["materialSheetDet.id"] = domain.materialSheetDet.id
            result["materialSheetDet.name"] = domain.materialSheetDet.name
            result["materialSheetDet.typeName"] = domain.materialSheetDet.typeName
            result["materialSheetDet.typeName.id"] = domain.materialSheetDet.typeName.id
            result["materialSheetDet.typeName.name"] = domain.materialSheetDet.typeName.name
            result["materialSheetDet.typeName.title"] = domain.materialSheetDet.typeName.title
            def materialSheetDetSheetType = enumService.name(domain.materialSheetDet.typeName.sheetType)
            result["materialSheetDet.typeName.sheetType"] = materialSheetDetSheetType.name
            result["materialSheetDet.typeName.sheetTypeTitle"] = materialSheetDetSheetType.title
            result["materialSheetDet.sequence"] = domain.materialSheetDet.sequence
        }

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def stockInSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def stockInSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title

            if (domain.batch.manufactureBatch) {
                result.manufactureBatch = domain.batch.manufactureBatch
                result["manufactureBatch.id"] = domain.batch.manufactureBatch.id
                result["manufactureBatch.name"] = domain.batch.manufactureBatch.name
            }
            else {
                result.manufactureBatch = domain.batch
                result["manufactureBatch.id"] = domain.batch.id
                result["manufactureBatch.name"] = domain.batch.name
            }
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }
        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def outSrcPurchaseSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def outSrcPurchaseSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer.title

            if (domain.batch.manufactureBatch) {
                result.manufactureBatch = domain.batch.manufactureBatch
                result["manufactureBatch.id"] = domain.batch.manufactureBatch.id
                result["manufactureBatch.name"] = domain.batch.manufactureBatch.name
            }
            else {
                result.manufactureBatch = domain.batch
                result["manufactureBatch.id"] = domain.batch.id
                result["manufactureBatch.name"] = domain.batch.name
            }
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def outSrcPurchaseReturnSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def outSrcPurchaseReturnSheetDetParseJson(domain) {

        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.manufactureOrder) {
            result.manufactureOrder = domain.manufactureOrder
            result["manufactureOrder.id"] = domain.manufactureOrder.id
            result["manufactureOrder.name"] = domain.manufactureOrder.name
            result["manufactureOrder.typeName"] = domain.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
        }

        if (domain.outSrcPurchaseSheetDet) {
            result.outSrcPurchaseSheetDet = domain.outSrcPurchaseSheetDet
            result["outSrcPurchaseSheetDet.id"] = domain.outSrcPurchaseSheetDet.id
            result["outSrcPurchaseSheetDet.name"] = domain.outSrcPurchaseSheetDet.name
            result["outSrcPurchaseSheetDet.typeName"] = domain.outSrcPurchaseSheetDet.typeName
            result["outSrcPurchaseSheetDet.typeName.id"] = domain.outSrcPurchaseSheetDet.typeName.id
            result["outSrcPurchaseSheetDet.typeName.name"] = domain.outSrcPurchaseSheetDet.typeName.name
            result["outSrcPurchaseSheetDet.typeName.title"] = domain.outSrcPurchaseSheetDet.typeName.title
            def outSrcPurchaseSheetDetSheetType = enumService.name(domain.outSrcPurchaseSheetDet.typeName.sheetType)
            result["outSrcPurchaseSheetDet.typeName.sheetType"] = outSrcPurchaseSheetDetSheetType.name
            result["outSrcPurchaseSheetDet.typeName.sheetTypeTitle"] = outSrcPurchaseSheetDetSheetType.title
            result["outSrcPurchaseSheetDet.sequence"] = domain.outSrcPurchaseSheetDet.sequence
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer.title
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def saleSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)
        result.shippingAddress = domain.shippingAddress

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.customer) {
            result.customer = domain.customer
            result["customer.id"] = domain.customer.id
            result["customer.name"] = domain.customer.name
            result["customer.title"] = domain.customer.title
            result["customer.tel"] = domain.customer.tel
            result["customer.fax"] = domain.customer.fax
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def saleSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.customerOrderDet) {
            result.customerOrderDet = domain.customerOrderDet
            result["customerOrderDet.id"] = domain.customerOrderDet.id
            result["customerOrderDet.name"] = domain.customerOrderDet.name
            result["customerOrderDet.typeName"] = domain.customerOrderDet.typeName
            result["customerOrderDet.typeName.id"] = domain.customerOrderDet.typeName.id
            result["customerOrderDet.typeName.name"] = domain.customerOrderDet.typeName.name
            result["customerOrderDet.typeName.title"] = domain.customerOrderDet.typeName.title
            def customerOrderDetSheetType = enumService.name(domain.customerOrderDet.typeName.sheetType)
            result["customerOrderDet.typeName.sheetType"] = customerOrderDetSheetType.name
            result["customerOrderDet.typeName.sheetTypeTitle"] = customerOrderDetSheetType.title
            result["customerOrderDet.sequence"] = domain.customerOrderDet.sequence
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def saleReturnSheetParseJson(domain) {
        def result = processCommonParse(domain)

        if (domain.typeName) {
            result.typeName = domain.typeName
            result["typeName.id"] = domain.typeName.id
            result["typeName.name"] = domain.typeName.name
            result["typeName.title"] = domain.typeName.title
            def sheetType = enumService.name(domain.typeName.sheetType)
            result["typeName.sheetType"] = sheetType.name
            result["typeName.sheetTypeTitle"] = sheetType.title
        }
        result.name = domain.name
        result.executionDate = dateService.formatWithISO8601(domain.executionDate)
        result.pickUpAddress = domain.pickUpAddress

        if (domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        if (domain.customer) {
            result.customer = domain.customer
            result["customer.id"] = domain.customer.id
            result["customer.name"] = domain.customer.name
            result["customer.title"] = domain.customer.title
            result["customer.tel"] = domain.customer.tel
            result["customer.fax"] = domain.customer.fax
        }

        result["existDets"] = false
        if (domain.details) {
            result["existDets"] = true
        }

        result
    }

    def saleReturnSheetDetParseJson(domain) {
        def result = processCommonParse(domain)

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        def sheetType = enumService.name(domain.typeName.sheetType)
        result["typeName.sheetType"] = sheetType.name
        result["typeName.sheetTypeTitle"] = sheetType.title

        result.name = domain.name
        result.sequence = domain.sequence

        result.qty = domain.qty
        result.unit = domain.unit

        result.header = domain.header
        result["header.id"] = domain.header.id

        if (domain.customerOrderDet) {
            result.customerOrderDet = domain.customerOrderDet
            result["customerOrderDet.id"] = domain.customerOrderDet.id
            result["customerOrderDet.name"] = domain.customerOrderDet.name
            result["customerOrderDet.typeName"] = domain.customerOrderDet.typeName
            result["customerOrderDet.typeName.id"] = domain.customerOrderDet.typeName.id
            result["customerOrderDet.typeName.name"] = domain.customerOrderDet.typeName.name
            result["customerOrderDet.typeName.title"] = domain.customerOrderDet.typeName.title
            def customerOrderDetSheetType = enumService.name(domain.customerOrderDet.typeName.sheetType)
            result["customerOrderDet.typeName.sheetType"] = customerOrderDetSheetType.name
            result["customerOrderDet.typeName.sheetTypeTitle"] = customerOrderDetSheetType.title
            result["customerOrderDet.sequence"] = domain.customerOrderDet.sequence
        }

        if (domain.saleSheetDet) {
            result.saleSheetDet = domain.saleSheetDet
            result["saleSheetDet.id"] = domain.saleSheetDet.id
            result["saleSheetDet.name"] = domain.saleSheetDet.name
            result["saleSheetDet.typeName"] = domain.saleSheetDet.typeName
            result["saleSheetDet.typeName.id"] = domain.saleSheetDet.typeName.id
            result["saleSheetDet.typeName.name"] = domain.saleSheetDet.typeName.name
            result["saleSheetDet.typeName.title"] = domain.saleSheetDet.typeName.title
            def saleSheetDetSheetType = enumService.name(domain.saleSheetDet.typeName.sheetType)
            result["saleSheetDet.typeName.sheetType"] = saleSheetDetSheetType.name
            result["saleSheetDet.typeName.sheetTypeTitle"] = saleSheetDetSheetType.title
            result["saleSheetDet.sequence"] = domain.saleSheetDet.sequence
        }

        if (domain.batch) {
            result.batch = domain.batch
            result["batch.id"] = domain.batch.id
            result["batch.name"] = domain.batch.name
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batch.batchSourceType"] = batchSourceType.name
            result["batch.batchSourceTypeTitle"] = batchSourceType.title
            def country = enumService.name(domain.batch.country)
            result["batch.country"] = country.name
            result["batch.countryTitle"] = country.title
            result["batch.manufacturer.id"] = domain.batch.manufacturer?.id
            result["batch.manufacturer.name"] = domain.batch.manufacturer?.name
            result["batch.manufacturer.title"] = domain.batch.manufacturer?.title
        }

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }

        if (domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if (domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def paramParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        result.defaultValue = domain.defaultValue
        def paramType = enumService.name(domain.paramType)
        result.paramType = paramType.name
        result.paramTypeTitle = paramType.title
        result.description = domain.description
        result.lower = domain.lower
        result.upper = domain.upper
        result.unit = domain.unit
        result
    }

    def reportParseJson(domain) {
        def result = processCommonParse(domain)

        result.name = domain.name
        result.title = domain.title
        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
        }
        def reportType = enumService.name(domain.reportType)
        result.reportType = reportType.name
        result.reportTypeTitle = reportType.title
        result.description = domain.description
        result.effectStartDate = dateService.formatWithISO8601(domain.effectStartDate)
        result.effectEndDate = dateService.formatWithISO8601(domain.effectEndDate)
        result
    }

    def reportParamParseJson(domain) {
        def result = processCommonParse(domain)
        result.id = domain.id
        result.report = domain.report
        result["report.id"] = domain.report.id
        result["report.name"] = domain.report.name
        result["report.title"] = domain.report.title

        if (domain.param) {
            result.param = domain.param
            result["param.id"] = domain.param.id
            result["param.name"] = domain.param.name
            result["param.title"] = domain.param.title
        }

        if (domain.operation) {
            result.operation = domain.operation
            result["operation.id"] = domain.operation.id
            result["operation.name"] = domain.operation.name
            result["operation.title"] = domain.operation.title
        }

        if (domain.workstation) {
            result.workstation = domain.workstation
            result["workstation.id"] = domain.workstation.id
            result["workstation.name"] = domain.workstation.name
            result["workstation.title"] = domain.workstation.title
        }
        if (domain.supplier) {
            result.supplier = domain.supplier
            result["supplier.id"] = domain.supplier.id
            result["supplier.name"] = domain.supplier.name
            result["supplier.title"] = domain.supplier.title
        }

        result
    }

    def batchReportDetParseJson(domain) {
        def result = processCommonParse(domain)
        result.id = domain.id
        result.value = domain.value

        result.reportParam = domain.reportParam
        result["reportParam.id"] = domain.reportParam.id
        result.reportParam.param = domain.reportParam.param
        result["reportParam.param.id"] = domain.reportParam.param.id
        result["reportParam.param.name"] = domain.reportParam.param.name
        result["reportParam.param.title"] = domain.reportParam.param.title
        result["reportParam.param.defaultValue"] = domain.reportParam.param.defaultValue
        def paramType = enumService.name(domain.reportParam.param.paramType)
        result["reportParam.param.paramType"] = paramType.name
        result["reportParam.param.paramTypeTitle"] = paramType.title

        result
    }

    def deliveryKanbanParseJson(domain) {
        def result = processCommonParse(domain)
        result.id = domain.id
        result.name = domain.name

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        
        result.sequence = domain.sequence

        result.expectShippingDateStart = dateService.formatWithISO8601(domain.expectShippingDateStart)
        result.expectShippingDateEnd = dateService.formatWithISO8601(domain.expectShippingDateEnd)

        result.qty = domain.qty
        if(domain.customerOrderDet) {
            result.customerOrderDet = domain.customerOrderDet
            result["customerOrderDet.id"] = domain.customerOrderDet.id
            result["customerOrderDet.name"] = domain.customerOrderDet.name
            result["customerOrderDet.typeName"] = domain.customerOrderDet.typeName
        }
        if(domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }
        result
    }
    def manufactureOrderDetParseJson(domain) {
        def result = processCommonParse(domain)
        result.id = domain.id
        result.name = domain.name

        result.typeName = domain.typeName
        result["typeName.id"] = domain.typeName.id
        result["typeName.name"] = domain.typeName.name
        result["typeName.title"] = domain.typeName.title
        
        result.expectQty = domain.expectQty
        result.qty = domain.qty
        result.expectPickingDate = domain.expectPickingDate
        result.pickingDate = domain.pickingDate

        if(domain.header) {
            result["manufactureOrder.id"] = domain.header.id
            result["manufactureOrder.name"] = domain.header.name
            result["manufactureOrder.typeName"] = domain.header.typeName
        }
        if(domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }
        if(domain.operation) {
            result.operation = domain.operation
            result["operation.id"] = domain.operation.id
            result["operation.name"] = domain.operation.name
            result["operation.title"] = domain.operation.title
        }
        if(domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }
        if(domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }

    def batchRealtimeRecordParseJson(domain) {
        def result = processCommonParse(domain)
        result.id = domain.id
        result.qty = domain.qty
        result.expectQty = domain.expectQty
        result.outputQty = domain.outputQty
        result.releaseDate = dateService.formatWithISO8601(domain.releaseDate)
        result.dueDate = dateService.formatWithISO8601(domain.dueDate)

        if(domain.status) {
            def status = enumService.name(domain.status)
            result["batchStatus"] = status.name
            result["batchStatusTitle"] = status.title
        }

        result.batch = domain.batch
        result["batch.id"] = domain.batch.id
        result["batch.name"] = domain.batch.name

        result.item = domain.batch.item
        result["item.id"] = domain.batch.item.id
        result["item.name"] = domain.batch.item.name
        result["item.title"] = domain.batch.item.title
        result["item.brand.id"] = domain.batch.item.brand.id
        result["item.brand.name"] = domain.batch.item.brand.name
        result["item.brand.title"] = domain.batch.item.brand.title
        result["item.spec"] = domain.batch.item.spec
        result["item.unit"] = domain.batch.item.unit
        result["item.description"] = domain.batch.item.description

        result.batchOperation = domain.batchOperation
        result["batchOperation.id"] = domain.batchOperation.id
        result["batchOperation.sequence"] = domain.batchOperation.sequence
        result["batchOperation.operation.name"] = domain.batchOperation.operation.name
        result["batchOperation.operation.title"] = domain.batchOperation.operation.title

        if (domain.batch.manufactureOrder) {
            result.manufactureOrder = domain.batch.manufactureOrder
            result["manufactureOrder.id"] = domain.batch.manufactureOrder.id
            result["manufactureOrder.typeName"] = domain.batch.manufactureOrder.typeName
            result["manufactureOrder.typeName.id"] = domain.batch.manufactureOrder.typeName.id
            result["manufactureOrder.typeName.name"] = domain.batch.manufactureOrder.typeName.name
            result["manufactureOrder.typeName.title"] = domain.batch.manufactureOrder.typeName.title
            def manufactureOrderSheetType = enumService.name(domain.batch.manufactureOrder.typeName.sheetType)
            result["manufactureOrder.typeName.sheetType"] = manufactureOrderSheetType.name
            result["manufactureOrder.typeName.sheetTypeTitle"] = manufactureOrderSheetType.title
            result["manufactureOrder.name"] = domain.batch.manufactureOrder.name
            result["manufactureOrder.factory.id"] = domain.batch.manufactureOrder.factory.id
            result["manufactureOrder.factory.name"] = domain.batch.manufactureOrder.factory.name
            result["manufactureOrder.factory.title"] = domain.batch.manufactureOrder.factory.title
            result["manufactureOrder.workstation.id"] = domain.batch.manufactureOrder.workstation?.id
            result["manufactureOrder.workstation.name"] = domain.batch.manufactureOrder.workstation?.name
            result["manufactureOrder.workstation.title"] = domain.batch.manufactureOrder.workstation?.title
            result["manufactureOrder.supplier.id"] = domain.batch.manufactureOrder.supplier?.id
            result["manufactureOrder.supplier.name"] = domain.batch.manufactureOrder.supplier?.name
            result["manufactureOrder.supplier.title"] = domain.batch.manufactureOrder.supplier?.title
        }

        if(domain.batch.batchType) {
            def batchType = enumService.name(domain.batch.batchType)
            result["batchType"] = batchType.name
            result["batchTypeTitle"] = batchType.title
        }
        if(domain.batch.batchSourceType) {
            def batchSourceType = enumService.name(domain.batch.batchSourceType)
            result["batchSourceType"] = batchSourceType.name
            result["batchSourceTypeTitle"] = batchSourceType.title
        }


        result
    }

    def batchBoxDetParseJson(domain){
        def result = processCommonParse(domain)
        result.id = domain.id
        result.qty = domain.qty
        result.sequence = domain.sequence
        result.status = domain.status
        result.formLevel = domain.batchBox.formLevel
        result.total = domain.batchBox.qty
        result.kanbanQty = domain.batchBox.kanbanQty

        result.item = domain.batchBox.item
        result["item.id"] = domain.batchBox.item.id
        result["item.name"] = domain.batchBox.item.name
        result["item.title"] = domain.batchBox.item.title
        result["item.brand.id"] = domain.batchBox.item.brand.id
        result["item.brand.name"] = domain.batchBox.item.brand.name
        result["item.brand.title"] = domain.batchBox.item.brand.title
        result["item.spec"] = domain.batchBox.item.spec
        result["item.unit"] = domain.batchBox.item.unit
        result["item.description"] = domain.batchBox.item.description

        if(domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }
        if(domain.warehouseLocation) {
            result.warehouseLocation = domain.warehouseLocation
            result["warehouseLocation.id"] = domain.warehouseLocation.id
            result["warehouseLocation.name"] = domain.warehouseLocation.name
            result["warehouseLocation.title"] = domain.warehouseLocation.title
        }

        result
    }
    def batchBoxParseJson(domain){
        def result = processCommonParse(domain)
        result.id = domain.id
        result.formLevel = domain.formLevel
        result.total = domain.qty
        result.kanbanQty = domain.kanbanQty
        result.accumulationQty = domain.accumulationQty
        result.autoSplit = domain.autoSplit
        result.splitQty = domain.splitQty

        if (domain.item) {
            result.item = domain.item
            result["item.id"] = domain.item.id
            result["item.name"] = domain.item.name
            result["item.title"] = domain.item.title
            result["item.brand.id"] = domain.item.brand.id
            result["item.brand.name"] = domain.item.brand.name
            result["item.brand.title"] = domain.item.brand.title
            result["item.spec"] = domain.item.spec
            result["item.unit"] = domain.item.unit
            result["item.description"] = domain.item.description
        }
        

        if(domain.warehouse) {
            result.warehouse = domain.warehouse
            result["warehouse.id"] = domain.warehouse.id
            result["warehouse.name"] = domain.warehouse.name
            result["warehouse.title"] = domain.warehouse.title
        }

        if(domain.factory) {
            result.factory = domain.factory
            result["factory.id"] = domain.factory.id
            result["factory.name"] = domain.factory.name
            result["factory.title"] = domain.factory.title
        }

        result
    }
}
