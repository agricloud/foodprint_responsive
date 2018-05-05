package foodprint.common

import foodprint.authority.*
import foodprint.erp.Site
import foodprint.sft.Param
import foodprint.sft.ParamType

import grails.transaction.Transactional


class DefaultDataService {

    def grailsApplication
    def domainService
    def messageSource

    def i18nType
    def creator = "DefaultDataService"

    def generateRoleData = {

        def roleData = roleData()
        roleData.each { group ->
            def roleGroup = RoleGroup.findOrSaveByNameAndTitleAndCreatorAndSiteGroup(group.roleGroupName, group.roleGroupTitle, creator, null)
            // roleGroup.save(failOnError: true, flush: true)

            group.roles.each { rolename ->
                def role = Role.findOrSaveByAuthority(rolename)
                RoleGroupRole.findOrSaveByRoleGroupAndRoleAndCreatorAndSiteGroup(roleGroup, role, creator, roleGroup.siteGroup)
            }
        }

        requestMapData.each { data ->
            RequestMap.findOrSaveByUrlAndConfigAttribute(data.url, data.configAttribute)
        }

    }

    def generateParamData = { Site site ->
        
        paramData.each { it ->
            domainService.save(new Param(creator: creator, name:it[0], title:it[1], unit:it[2], paramType:it[3], defaultValue:it[4], lower:it[5], upper:it[6], description:it[7] , site:site))
        }

        return [success: true]
    }

    @Transactional
    def deleteParamData(Site site) {

        def result

        paramData.each { it ->
            def param = Param.findByCreatorAndNameAndSite(creator, it[0], site)
            if (param) {
                result = domainService.delete(param)
            }
            else {
                result = [success: true, message: 'param is not existed']
            }
        } 
        return result
    }

    def roleData = {
        Object[] args 

        [[//預設權限管理
            roleGroupName: "DEFAULT_AUTHORITY",          roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.authority.label", args, Locale.getDefault()),
            roles: [
                "ROLE_SITEGROUP_MAINTAIN",               "ROLE_SITE_MAINTAIN",                
                "ROLE_USERUSER_MAINTAIN",                "ROLE_ADMINUSER_MAINTAIN",
                "ROLE_USERUSERSITES_MAINTAIN",           "ROLE_ADMINUSERSITES_MAINTAIN",
                "ROLE_ROLEGROUPROLE_MAINTAIN",           "ROLE_USERUSERROLEGROUP_MAINTAIN",   "ROLE_ADMINUSERROLEGROUP_MAINTAIN"
        ]],[//預設基本資料
            roleGroupName: "DEFAULT_BASICMAINTAIN",          roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.basicMaintain.label", args, Locale.getDefault()),
            roles: [//基本資料維護
                "ROLE_USER_MAINTAIN",
                "ROLE_ITEMCATEGORYLAYER1_MAINTAIN",      "ROLE_ITEMCATEGORYLAYER2_MAINTAIN",  "ROLE_BRAND_MAINTAIN",
                "ROLE_ITEM_MAINTAIN",                    "ROLE_ITEMSTAGE_MAINTAIN",           "ROLE_ITEMREGISTEREDNUM_MAINTAIN",
                "ROLE_ITEMROUTE_MAINTAIN",               "ROLE_FACTORY_MAINTAIN",
                "ROLE_OPERATIONCATEGORYLAYER1_MAINTAIN", "ROLE_OPERATION_MAINTAIN",           "ROLE_WORKSTATION_MAINTAIN",
                "ROLE_SUPPLIER_MAINTAIN",                "ROLE_MANUFACTURER_MAINTAIN",        "ROLE_CUSTOMER_MAINTAIN",
                "ROLE_EMPLOYEE_MAINTAIN",                "ROLE_BATCH_MAINTAIN",
                "ROLE_PARAM_MAINTAIN",                   "ROLE_REPORT_MAINTAIN",              "ROLE_REPORTPARAM_MAINTAIN",
                "ROLE_WAREHOUSE_MAINTAIN",               "ROLE_WAREHOUSELOCATION_MAINTAIN"
        ]],[//預設庫存資料維護
            roleGroupName: "DEFAULT_BASICINVENTORYMAINTAIN",          roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.basicInventoryMaintain.label", args, Locale.getDefault()),
            roles: [//基本資料維護
                "ROLE_INVENTORY_MAINTAIN",              "ROLE_INVENTORYDETAIL_MAINTAIN",
                "ROLE_INVENTORYTYPENAME_MAINTAIN",      "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN"
        ]],[//預設物料結構維護
            roleGroupName: "DEFAULT_PRODUCTSTRUCTUREMAINTAIN",      roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.productStructureMaintain.label", args, Locale.getDefault()),
            roles: [
                "ROLE_BILLOFMATERIAL_MAINTAIN"
        ]],[//預設單據維護
            roleGroupName: "DEFAULT_SHEETMAINTAIN",      roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.sheetMaintain.label", args, Locale.getDefault()),
            roles: [
                "ROLE_NONINVENTORYTYPENAME_MAINTAIN",
                "ROLE_CUSTOMERORDER_MAINTAIN",     "ROLE_PURCHASESHEET_MAINTAIN",               "ROLE_PURCHASERETURNSHEET_MAINTAIN",
                "ROLE_MANUFACTUREORDER_MAINTAIN",  "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN", "ROLE_SALESHEET_MAINTAIN",
                "ROLE_SALERETURNSHEET_MAINTAIN",   "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ]],[//預設製令管理
            roleGroupName: "DEFAULT_BATCHMANAGE", roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.batchManage.label", args, Locale.getDefault()),
            roles: [
                "ROLE_BATCH_SPLIT",        "ROLE_BATCH_RELEASE"
        ]],[//預設看板管理
            roleGroupName: "DEFAULT_KANBANMANAGE", roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.kanbanManage.label", args, Locale.getDefault()),
            roles: [
                "ROLE_CUSTOMERKANBAN_MAINTAIN",    "ROLE_CUSTOMERKANBAN_RELEASE", "ROLE_CUSTOMERKANBAN_SHIPPING",
                "ROLE_PRODUCTIONKANBAN_RELEASE",    "ROLE_BATCHBOX_MAINTAIN", "ROLE_BATCHBOX_VIEW", "ROLE_ITEMSTORE_VIEW"
        ]],[//預設製令追蹤
            roleGroupName: "DEFAULT_BATCHTRACKING", roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.batchTracking.label", args, Locale.getDefault()),
            roles: [
                "ROLE_BATCH_CHECKIN",        "ROLE_BATCH_CHECKOUT"
        ]],[//預設看板追蹤
            roleGroupName: "DEFAULT_KANBANTRACKING", roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.kanbanTracking.label", args, Locale.getDefault()),
            roles: [
                "ROLE_PRODUCTIONKANBAN_CHECKIN",       "ROLE_PRODUCTIONKANBAN_CHECKOUT",
                "ROLE_WITHDRAWALKANBAN_MOVEOUT",       "ROLE_WITHDRAWALKANBAN_MOVEIN"
        ]],[//預設生產/栽種作業
            roleGroupName: "DEFAULT_MANUFACTUREOPERATE", roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.manufactureOperate.label", args, Locale.getDefault()),
            roles: [
                "ROLE_BATCHOPERATION_MAINTAIN",    "ROLE_MATERIALSHEET_MAINTAIN",             "ROLE_MATERIALRETURNSHEET_MAINTAIN",
                "ROLE_STOCKINSHEET_MAINTAIN",      "ROLE_OUTSRCPURCHASESHEET_MAINTAIN",       "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ]],[//預設履歷收集
            roleGroupName: "DEFAULT_REPORTCOLLECT",      roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.reportCollect.label", args, Locale.getDefault()),
            roles: [
                "ROLE_BATCHREPORTDET_MAINTAIN"
        ]],[//預設回溯追蹤
            roleGroupName: "DEFAULT_TRACE",              roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.trace.label", args, Locale.getDefault()),
            roles: [
                "ROLE_FORWARDTRACE",  "ROLE_BACKWARDTRACE"
        ]],[//預設驗證管理文件
            roleGroupName: "DEFAULT_CERTIFICATEREPORT",  roleGroupTitle: messageSource.getMessage("${i18nType}.roleGroup.default.certificateReport.label", args, Locale.getDefault()),
            roles: [
                "ROLE_MATERIALAPPLYREPORT",        "ROLE_MATERIALPURCHASEREPORT",               "ROLE_BATCHROUTEREPORT"
        ]]]
    }
    def requestMapData = [[
            url: '/enum/indexUserType',
            configAttribute: "permitAll"
        ],[
            url: '/enum/**',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/attachment/list/**',
            configAttribute: "permitAll()"
        ],[
            url: '/attachment/show/**',
            configAttribute: "permitAll()"
        ],[
            url: '/attachment/showPdf/**',
            configAttribute: "permitAll()"
        ],[
            url: '/attachment/**',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/siteGroup/**',
            configAttribute: "ROLE_SITEGROUP_MAINTAIN"
        ],[
            url: '/site',
            configAttribute: "ROLE_SITE_MAINTAIN,ROLE_USERUSERSITES_MAINTAIN,ROLE_ADMINUSERSITES_MAINTAIN"
        ],[
            url: '/site/**',
            configAttribute: "ROLE_SITE_MAINTAIN"
        ],[
            url: '/user',
            configAttribute: "ROLE_USER_MAINTAIN"
        ],[
            url: '/user/indexByUserType',
            configAttribute: "ROLE_USERUSER_MAINTAIN,ROLE_ADMINUSER_MAINTAIN,ROLE_USERUSERSITES_MAINTAIN,ROLE_ADMINUSERSITES_MAINTAIN" +
                ",ROLE_USERUSERROLEGROUP_MAINTAIN,ROLE_ADMINUSERROLEGROUP_MAINTAIN"
        ],[
            url: '/user/switchSite',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/user/switchPanel',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/user/switchWorkstation',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/user/create',
            configAttribute: "permitAll"
        ],[
            url: '/user/save',
            configAttribute: "permitAll"
        ],[
            url: '/user/update',
            configAttribute: "ROLE_USER_MAINTAIN,ROLE_USERUSER_MAINTAIN,ROLE_ADMINUSER_MAINTAIN"
        ],[
            url: '/user/show/**',
            configAttribute: "ROLE_USER_MAINTAIN,ROLE_USERUSER_MAINTAIN,ROLE_ADMINUSER_MAINTAIN"
        ],[
            url: '/user/**',
            configAttribute: "ROLE_USERUSER_MAINTAIN,ROLE_ADMINUSER_MAINTAIN"
        ],[
            url: '/userSite/indexSitesByUser',
            configAttribute: "isAuthenticated()"
        ],[
            url: '/userSite/**',
            configAttribute: "ROLE_USERUSERSITES_MAINTAIN,ROLE_ADMINUSERSITES_MAINTAIN"
        ],[
            url: '/role/**',
            configAttribute: "ROLE_ROLEGROUPROLE_MAINTAIN"
        ],[
            url: '/roleGroup',
            configAttribute: "ROLE_ROLEGROUPROLE_MAINTAIN,ROLE_ADMINUSERROLEGROUP_MAINTAIN"
        ],[
            url: '/roleGroup/indexWithoutAuthority',
            configAttribute: "ROLE_ROLEGROUPROLE_MAINTAIN,ROLE_USERUSERROLEGROUP_MAINTAIN"
        ],[
            url: '/roleGroup/**',
            configAttribute: "ROLE_ROLEGROUPROLE_MAINTAIN"
        ],[
            url: '/roleGroupRole/**',
            configAttribute: "ROLE_ROLEGROUPROLE_MAINTAIN"
        ],[
            url: '/userRoleGroup/**',
            configAttribute: "ROLE_USERUSERROLEGROUP_MAINTAIN,ROLE_ADMINUSERROLEGROUP_MAINTAIN"
        ],[
            url: '/itemCategoryLayer1',
            configAttribute: "ROLE_ITEMCATEGORYLAYER1_MAINTAIN,ROLE_ITEMCATEGORYLAYER2_MAINTAIN,ROLE_ITEM_MAINTAIN" +
                ",ROLE_MATERIALAPPLYREPORT,ROLE_MATERIALPURCHASEREPORT,ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/itemCategoryLayer1/**',
            configAttribute: "ROLE_ITEMCATEGORYLAYER1_MAINTAIN"
        ],[
            url: '/itemCategoryLayer2',
            configAttribute: "ROLE_ITEMCATEGORYLAYER2_MAINTAIN,ROLE_ITEM_MAINTAIN,ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/itemCategoryLayer2/**',
            configAttribute: "ROLE_ITEMCATEGORYLAYER2_MAINTAIN"
        ],[
            url: '/brand',
            configAttribute: "ROLE_BRAND_MAINTAIN,ROLE_ITEM_MAINTAIN"
        ],[
            url: '/brand/**',
            configAttribute: "ROLE_BRAND_MAINTAIN"
        ],[
            url: '/item',
            configAttribute: "ROLE_ITEM_MAINTAIN,ROLE_ITEMSTAGE_MAINTAIN,ROLE_ITEMREGISTEREDNUM_MAINTAIN" +
                ",ROLE_BATCH_MAINTAIN,ROLE_ITEMROUTE_MAINTAIN" +
                ",ROLE_INVENTORY_MAINTAIN,ROLE_INVENTORYDETAIL_MAINTAIN" +
                ",ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN,ROLE_BILLOFMATERIAL_MAINTAIN" +
                ",ROLE_CUSTOMERORDER_MAINTAIN,ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_REPORT_MAINTAIN" +
                ",ROLE_MATERIALAPPLYREPORT,ROLE_MATERIALPURCHASEREPORT,ROLE_BATCHROUTEREPORT" +
                ",ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/item/indexByWorkFlowType',
            configAttribute: "ROLE_BATCHBOX_MAINTAIN,ROLE_BATCHBOX_VIEW,ROLE_ITEMSTORE_VIEW"
        ],[
            url: '/item/**',
            configAttribute: "ROLE_ITEM_MAINTAIN"
        ],[
            url: '/itemStage',
            configAttribute: "ROLE_ITEMSTAGE_MAINTAIN,ROLE_BATCHREPORTDET_MAINTAIN"
        ],[
            url: '/itemStage/**',
            configAttribute: "ROLE_ITEMSTAGE_MAINTAIN"
        ],[
            url: '/itemRegisteredNum',
            configAttribute: "ROLE_ITEMREGISTEREDNUM_MAINTAIN"
        ],[
            url: '/itemRegisteredNum/**',
            configAttribute: "ROLE_ITEMREGISTEREDNUM_MAINTAIN"
        ],[
            url: '/factory',
            configAttribute: "ROLE_FACTORY_MAINTAIN,ROLE_WAREHOUSE_MAINTAIN,ROLE_WORKSTATION_MAINTAIN" +
                ",ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN" +
                ",ROLE_CUSTOMERORDER_MAINTAIN,ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_BATCHBOX_MAINTAIN" +
                ",ROLE_MATERIALPURCHASEREPORT"
        ],[
            url: '/factory/**',
            configAttribute: "ROLE_FACTORY_MAINTAIN"
        ],[
            url: '/operationCategoryLayer1',
            configAttribute: "ROLE_OPERATIONCATEGORYLAYER1_MAINTAIN,ROLE_OPERATION_MAINTAIN"
        ],[
            url: '/operationCategoryLayer1/**',
            configAttribute: "ROLE_OPERATIONCATEGORYLAYER1_MAINTAIN"
        ],[
            url: '/operation',
            configAttribute: "ROLE_OPERATION_MAINTAIN,ROLE_ITEMROUTE_MAINTAIN,ROLE_BATCHOPERATION_MAINTAIN" +
                ",ROLE_BILLOFMATERIAL_MAINTAIN,ROLE_REPORTPARAM_MAINTAIN" +
                ",ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/operation/**',
            configAttribute: "ROLE_OPERATION_MAINTAIN"
        ],[
            url: '/workstation',
            configAttribute: "ROLE_WORKSTATION_MAINTAIN,ROLE_ITEMROUTE_MAINTAIN,ROLE_BATCHOPERATION_MAINTAIN" +
                ",ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN" +
                ",ROLE_REPORTPARAM_MAINTAIN" +
                ",ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/workstation/indexByFactory',
            configAttribute: "ROLE_WORKSTATION_MAINTAIN,ROLE_MANUFACTUREORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/workstation/**',
            configAttribute: "ROLE_WORKSTATION_MAINTAIN"
        ],[
            url: '/supplier',
            configAttribute: "ROLE_SUPPLIER_MAINTAIN,ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/supplier/indexByIsManufacturer',
            configAttribute: "ROLE_SUPPLIER_MAINTAIN,ROLE_ITEMROUTE_MAINTAIN,ROLE_BATCHOPERATION_MAINTAIN" +
                ",ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_REPORTPARAM_MAINTAIN"
        ],[
            url: '/supplier/**',
            configAttribute: "ROLE_SUPPLIER_MAINTAIN"
        ],[
            url: '/manufacturer',
            configAttribute: "ROLE_MANUFACTURER_MAINTAIN,ROLE_ITEM_MAINTAIN,ROLE_ITEMREGISTEREDNUM_MAINTAIN" +
                ",ROLE_BATCH_MAINTAIN,ROLE_INVENTORYDETAIL_MAINTAIN" +
                ",ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN" +
                ",ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/manufacturer/**',
            configAttribute: "ROLE_MANUFACTURER_MAINTAIN"
        ],[
            url: '/customer',
            configAttribute: "ROLE_CUSTOMER_MAINTAIN,ROLE_CUSTOMERORDER_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/customer/**',
            configAttribute: "ROLE_CUSTOMER_MAINTAIN"
        ],[
            url: '/employee/indexByEmployeeType',
            configAttribute: "ROLE_EMPLOYEE_MAINTAIN,ROLE_BATCHREPORTDET_MAINTAIN" +
                ",ROLE_MATERIALAPPLYREPORT,ROLE_MATERIALPURCHASEREPORT,ROLE_BATCHROUTEREPORT"
        ],[
            url: '/employee/**',
            configAttribute: "ROLE_EMPLOYEE_MAINTAIN"
        ],[
            url: '/batch/typeahead',
            configAttribute: "permitAll"
        ],[
            url: '/batch',
            configAttribute: "ROLE_BATCH_MAINTAIN,ROLE_INVENTORYDETAIL_MAINTAIN" +
                ",ROLE_PURCHASERETURNSHEET_MAINTAIN,ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_FORWARDTRACE,ROLE_BACKWARDTRACE"
        ],[
            url: '/batch/indexByName',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/batch/indexByNonPurchaseBatchSourceType',
            configAttribute: "ROLE_BATCH_MAINTAIN,ROLE_BATCHOPERATION_MAINTAIN,ROLE_BATCHREPORTDET_MAINTAIN" +
                ",ROLE_MATERIALAPPLYREPORT,ROLE_MATERIALPURCHASEREPORT,ROLE_BATCHROUTEREPORT"
        ],[
            url: '/batch/indexByManufactureOrderNonFinishedStatusAndFactoryAndWorkstationOrSupplier',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN"
        ],[
            url: '/batch/indexByBatchSourceTypeAndManufactureOrderStatusAndWorkFlowType',
            configAttribute: "ROLE_BATCH_SPLIT,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/batch/show/**',
            configAttribute: "ROLE_BATCH_MAINTAIN,ROLE_BATCH_SPLIT,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/batch/create',
            configAttribute: "ROLE_BATCH_MAINTAIN"
        ],[
            url: '/batch/save',
            configAttribute: "ROLE_BATCH_MAINTAIN"
        ],[
            url: '/batch/update',
            configAttribute: "ROLE_BATCH_MAINTAIN"
        ],[
            url: '/batch/delete',
            configAttribute: "ROLE_BATCH_MAINTAIN"
        ],[
            url: '/batch/batchSplit',
            configAttribute: "ROLE_BATCH_SPLIT"
        ],[
            url: '/batch/batchSplitSave',
            configAttribute: "ROLE_BATCH_SPLIT"
        ],[
            url: '/batch/release',
            configAttribute: "ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/itemRoute/**',
            configAttribute: "ROLE_ITEMROUTE_MAINTAIN"
        ],[
            url: '/batchOperation',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN,ROLE_MATERIALSHEET_MAINTAIN" +
            ",ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE,ROLE_BATCHREPORTDET_MAINTAIN"
        ],[
            url: '/batchOperation/show/**',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN" +
                ",ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE,ROLE_BATCHREPORTDET_MAINTAIN"
        ],[
            url: '/batchOperation/create',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/batchOperation/save',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/batchOperation/update',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/batchOperation/delete',
            configAttribute: "ROLE_BATCHOPERATION_MAINTAIN,ROLE_BATCH_RELEASE,ROLE_PRODUCTIONKANBAN_RELEASE"
        ],[
            url: '/warehouse',
            configAttribute: "ROLE_WAREHOUSE_MAINTAIN,ROLE_WAREHOUSELOCATION_MAINTAIN" +
                ",ROLE_INVENTORY_MAINTAIN,ROLE_INVENTORYDETAIL_MAINTAIN" +
                ",ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN" +
                ",ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_BATCHBOX_MAINTAIN"
        ],[
            url: '/warehouse/indexByFactory',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN,ROLE_PURCHASESHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN" +
                ",ROLE_BATCH_CHECKOUT"
        ],[
            url: '/warehouse/**',
            configAttribute: "ROLE_WAREHOUSE_MAINTAIN"
        ],[
            url: '/warehouseLocation',
            configAttribute: "ROLE_WAREHOUSELOCATION_MAINTAIN,ROLE_INVENTORYDETAIL_MAINTAIN" +
                ",ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_BATCH_CHECKOUT"
        ],[
            url: '/warehouseLocation/**',
            configAttribute: "ROLE_WAREHOUSELOCATION_MAINTAIN"
        ],[
            url: '/inventory',
            configAttribute: "ROLE_INVENTORY_MAINTAIN"
        ],[
            url: '/inventory/**',
            configAttribute: "ROLE_INVENTORY_MAINTAIN"
        ],[
            url: '/inventoryDetail',
            configAttribute: "ROLE_INVENTORYDETAIL_MAINTAIN,ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN" +
                ",ROLE_PURCHASESHEET_MAINTAIN,ROLE_MATERIALSHEET_MAINTAIN,ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/inventoryDetail/indexByWarehouseFactory',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/inventoryDetail/indexByItem',
            configAttribute: "ROLE_ITEMSTORE_VIEW"
        ],[
            url: '/inventoryDetail/indexByWarehouseFactoryAndItem',
            configAttribute: "ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/inventoryDetail/indexByWarehouseFactoryAndManufactureOrder',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN"
        ],[
            url: '/inventoryDetail/**',
            configAttribute: "ROLE_INVENTORYDETAIL_MAINTAIN"
        ],[
            url: '/batchBox',
            configAttribute: "ROLE_BATCHBOX_MAINTAIN"
        ],[
            url: '/batchBox/**',
            configAttribute: "ROLE_BATCHBOX_MAINTAIN"
        ],[
            url: '/typeName',
            configAttribute: "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN"
        ],[
            url: '/typeName/indexBySheetTypeGroup',
            configAttribute: "ROLE_INVENTORYTYPENAME_MAINTAIN"
        ],[
            url: '/typeName/indexNonInventorySheetTypeGroup',
            configAttribute: "ROLE_NONINVENTORYTYPENAME_MAINTAIN"
        ],[
            url: '/typeName/indexBySheetTypes',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN,ROLE_BILLOFMATERIAL_MAINTAIN" +
                ",ROLE_CUSTOMERORDER_MAINTAIN,ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN,ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN" +
                ",ROLE_STOCKINSHEET_MAINTAIN,ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" +
                ",ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/typeName/**',
            configAttribute: "ROLE_INVENTORYTYPENAME_MAINTAIN, ROLE_NONINVENTORYTYPENAME_MAINTAIN"
        ],[
            url: '/inventoryTransactionSheet',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN"
        ],[
            url: '/inventoryTransactionSheet/**',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN"
        ],[
            url: '/inventoryTransactionSheetDet',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN"
        ],[
            url: '/inventoryTransactionSheetDet/**',
            configAttribute: "ROLE_INVENTORYTRANSACTIONSHEET_MAINTAIN"
        ],[
            url: '/billOfMaterial',
            configAttribute: "ROLE_BILLOFMATERIAL_MAINTAIN"
        ],[
            url: '/billOfMaterial/**',
            configAttribute: "ROLE_BILLOFMATERIAL_MAINTAIN"
        ],[
            url: '/billOfMaterialDet',
            configAttribute: "ROLE_BILLOFMATERIAL_MAINTAIN"
        ],[
            url: '/billOfMaterialDet/**',
            configAttribute: "ROLE_BILLOFMATERIAL_MAINTAIN"
        ],[
            url: '/customerOrder',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN,ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN" +
                ",ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/customerOrder/indexByFactoryAndCustomer',
            configAttribute: "ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/customerOrder/show/**',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN,ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/customerOrder/create',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrder/save',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrder/update',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrder/delete',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrder/print',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrderDet',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN,ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/customerOrderDet/indexByConvertToDeliveryKanban',
            configAttribute: "ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/customerOrderDet/show/**',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN,ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/customerOrderDet/create',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrderDet/save',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrderDet/update',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/customerOrderDet/delete',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/purchaseSheet',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseSheet/indexByFactoryAndSupplier',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseSheet/**',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN"
        ],[
            url: '/purchaseSheetDet',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN,ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseSheetDet/**',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN"
        ],[
            url: '/purchaseReturnSheet',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseReturnSheet/**',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseReturnSheetDet',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/purchaseReturnSheetDet/**',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/manufactureOrder',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN" +
                ",ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN" +
                ",ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/manufactureOrder/indexByNonFinishedStatus',
            configAttribute: "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN"
        ],[
            url: '/manufactureOrder/indexByNonFinishedStatusAndFactoryAndWorkstationOrSupplier',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/manufactureOrder/**',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet/indexByManufactureOrderStatusAndWorkstationAndMoveType',
            configAttribute: "ROLE_WITHDRAWALKANBAN_MOVEOUT,ROLE_WITHDRAWALKANBAN_MOVEIN"
        ],[
            url: '/manufactureOrderDet/show/**',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN,ROLE_WITHDRAWALKANBAN_MOVEOUT,ROLE_WITHDRAWALKANBAN_MOVEIN"
        ],[
            url: '/manufactureOrderDet/create',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet/save',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet/update',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet/delete',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderDet/moveOut',
            configAttribute: "ROLE_WITHDRAWALKANBAN_MOVEOUT"
        ],[
            url: '/manufactureOrderDet/moveIn',
            configAttribute: "ROLE_WITHDRAWALKANBAN_MOVEIN"
        ],[
            url: '/manufactureOrderChangeOrder',
            configAttribute: "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN"
        ],[
            url: '/manufactureOrderChangeOrder/**',
            configAttribute: "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN"
        ],[
            url: '/materialSheet',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialSheet/indexByFactoryAndWorkstationOrSupplier',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialSheet/**',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/materialSheetDet',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN,ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialSheetDet/**',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/materialReturnSheet',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialReturnSheet/**',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialReturnSheetDet',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialReturnSheetDet/**',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/materialSheetDetCustomize',
            configAttribute: "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/materialSheetDetCustomize/**',
            configAttribute: "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/materialSheetDetCustomizeDet',
            configAttribute: "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN,ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/materialSheetDetCustomizeDet/**',
            configAttribute: "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ],[
            url: '/stockInSheet',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/stockInSheet/**',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/stockInSheetDet',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/stockInSheetDet/**',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseSheet',
            configAttribute: "ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseSheet/indexByFactoryAndSupplier',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseSheet/**',
            configAttribute: "ROLE_OUTSRCPURCHASESHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseSheetDet',
            configAttribute: "ROLE_OUTSRCPURCHASESHEET_MAINTAIN,ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseSheetDet/**',
            configAttribute: "ROLE_OUTSRCPURCHASESHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseReturnSheet',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseReturnSheet/**',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseReturnSheetDet',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/outSrcPurchaseReturnSheetDet/**',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleSheet',
            configAttribute: "ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleSheet/indexByFactoryAndCustomer',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleSheet/**',
            configAttribute: "ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/saleSheetDet',
            configAttribute: "ROLE_SALESHEET_MAINTAIN,ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleSheetDet/**',
            configAttribute: "ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/saleReturnSheet',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleReturnSheet/**',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleReturnSheetDet',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/saleReturnSheetDet/**',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/param',
            configAttribute: "ROLE_PARAM_MAINTAIN,ROLE_REPORTPARAM_MAINTAIN"
        ],[
            url: '/param/**',
            configAttribute: "ROLE_PARAM_MAINTAIN"
        ],[
            url: '/report',
            configAttribute: "ROLE_REPORT_MAINTAIN,ROLE_REPORTPARAM_MAINTAIN"
        ],[
            url: '/report/**',
            configAttribute: "ROLE_REPORT_MAINTAIN"
        ],[
            url: '/reportParam/**',
            configAttribute: "ROLE_REPORTPARAM_MAINTAIN"
        ],[
            url: '/batchReportDet/**',
            configAttribute: "ROLE_BATCHREPORTDET_MAINTAIN"
        ],[
            url: '/batchExecution/indexByBatchSourceTypeAndManufactureOrderStatusAndWorkstationAndWorkFlowType',
            configAttribute: "ROLE_BATCH_CHECKIN,ROLE_BATCH_CHECKOUT" +
                ",ROLE_PRODUCTIONKANBAN_CHECKIN,ROLE_PRODUCTIONKANBAN_CHECKOUT"
        ],[
            url: '/batchExecution/checkIn',
            configAttribute: "ROLE_BATCH_CHECKIN,ROLE_PRODUCTIONKANBAN_CHECKIN"
        ],[
            url: '/batchExecution/checkOut',
            configAttribute: "ROLE_BATCH_CHECKOUT,ROLE_PRODUCTIONKANBAN_CHECKOUT"
        ],[
            url: '/deliveryKanban',
            configAttribute: "ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/deliveryKanban/indexByNotAssignExpectShippingDate',
            configAttribute: "ROLE_CUSTOMERKANBAN_RELEASE"
        ],[
            url: '/deliveryKanban/indexByExpectShippingDate',
            configAttribute: "ROLE_CUSTOMERKANBAN_RELEASE,ROLE_CUSTOMERKANBAN_SHIPPING"
        ],[
            url: '/deliveryKanban/orderChangeToDeliverKanban',
            configAttribute: "ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/deliveryKanban/orderChangeToDeliverKanbanAndSave',
            configAttribute: "ROLE_CUSTOMERKANBAN_MAINTAIN"
        ],[
            url: '/deliveryKanban/release',
            configAttribute: "ROLE_CUSTOMERKANBAN_RELEASE"
        ],[
            url: '/trace/backwardTraceRoot',
            configAttribute: "ROLE_BACKWARDTRACE"
        ],[
            url: '/trace/backwardTracePrint',
            configAttribute: "ROLE_BACKWARDTRACE"
        ],[
            url: '/trace/backwardTrace',
            configAttribute: "ROLE_BACKWARDTRACE"
        ],[
            url: '/trace/forwardTraceRoot',
            configAttribute: "ROLE_FORWARDTRACE"
        ],[
            url: '/trace/forwardTrace',
            configAttribute: "ROLE_FORWARDTRACE"
        ],[
            url: '/trace/forwardTracePrint',
            configAttribute: "ROLE_FORWARDTRACE"
        ],[
            url: '/foodpaint/**',
            configAttribute: "isAuthenticated()"
        ]/*,[
            url: '/warehouse?foodpaintController=warehouse',
            configAttribute: "ROLE_WAREHOUSE_MAINTAIN,ROLE_WAREHOUSELOCATION_MAINTAIN"
        ],[
            url: '/foodpaint?foodpaintController=warehouse',
            configAttribute: "ROLE_WAREHOUSE_MAINTAIN"
        ],[
            url: '/foodpaint?foodpaintController=warehouseLocation',
            configAttribute: "ROLE_WAREHOUSELOCATION_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_INVENTORY_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_INVENTORYDETAIL_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_CUSTOMERORDER_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_PURCHASESHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_PURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_MANUFACTUREORDER_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_MANUFACTUREORDERCHANGEORDER_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_MATERIALSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_MATERIALRETURNSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_STOCKINSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_OUTSRCPURCHASESHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_SALESHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_SALERETURNSHEET_MAINTAIN"
        ],[
            url: '/foodpaint/**',
            configAttribute: "ROLE_MATERIALSHEETDETCUSTOMIZE_MAINTAIN"
        ]*/,[
            url: '/certificateReport/generateMaterialApplyReport',
            configAttribute: "ROLE_MATERIALAPPLYREPORT"
        ],[
            url: '/certificateReport/generateMaterialPurchaseReport',
            configAttribute: "ROLE_MATERIALPURCHASEREPORT"
        ],[
            url: '/certificateReport/generateBatchRouteReport',
            configAttribute: "ROLE_BATCHROUTEREPORT"
        ],
    ]

    private def paramData = [
        ["3-keto-Carbofuran", "3-酮基加保伕", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//1
        ["3-OH-Carbofuran", "3-羥基加保伕", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//2
        ["Abamectin", "阿巴汀", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//3
        ["Acephate", "毆殺松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//4
        ["Acetamiprid", "亞滅培", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//5
        ["Acetochlor", "Acetochlor", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//6
        ["Acrinathrin", "阿納寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//7
        ["Alachlor", "拉草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//8
        ["Aldicarb", "得滅克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//9
        ["AldicarbSulfone", "得滅克颯", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//10

        ["AldicarbSulfoxide", "得滅克亞颯", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//11
        ["Aldrin", "阿特寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//12
        ["Allethrin", "亞烈寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.02", null],//13
        ["Alloxydim-sodium", "立汰草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//14
        ["Alphacypermethrin", "亞滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//15
        ["Ametryn", "草殺淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//16
        ["Amisulbrom", "安美速", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//17
        ["Atrazine", "草脫淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//18
        ["Azinphos-methyl", "谷速松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//19
        ["Azoxystrobin", "亞托敏", "ppm", ParamType.DOUBLE, "0.0", "0.0", "10", null],//20

        ["Benalaxyl", "本達樂", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//21
        ["Bendiocarb", "免敵克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//22
        ["Benfluralin", "倍尼芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//23
        ["Benfuracarb", "免扶克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//24
        ["BensulfuronMethyl", "免速隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//25
        ["Bentazone", "本達隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//26
        ["Benthiazole", "佈生", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//27
        ["alpha-BHC", "α-蟲必死", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//28
        ["beta-BHC", "β-蟲必死", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//29
        ["delta-BHC", "δ-蟲必死", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//30

        ["Bifenazate", "必芬蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//31
        ["Bifenox", "必芬諾", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//32
        ["Bifenthrin", "畢芬寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//33
        ["Bitertanol", "比多農", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//34
        ["Boscalid", "白克列", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//35
        ["Bromacil", "克草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//36
        ["Bromophos-ethyl", "乙基溴磷松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//37
        ["Bromophos-methyl", "甲基溴磷松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//38
        ["Bromopropylate", "新殺蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "3.0", null],//39
        ["Bromuconazole", "溴克座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//40

        ["Bupirimate", "布瑞莫", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//41
        ["Buprofezin", "布芬淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//42
        ["Butachlor", "丁基拉草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//43
        ["Butocarboxim", "佈嘉信", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//44
        ["Butralin", "比達寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//45
        ["Carbaryl", "加保利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//46
        ["Carbendazim", "貝芬替", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//47
        ["Carbofuran", "加保扶", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//48
        ["Carbophenothion", "加保松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0", null],//49
        ["Carbosulfan", "丁基加保扶", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//50

        ["Carpropamid", "加普胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//51
        ["Chinomethionat", "蟎離丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//52
        ["Chlorantraniliprole", "剋安勃", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//53
        ["cis-Chlordane", "cis-可氯丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//54
        ["trans-Chlordane", "trans-可氯丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//55
        ["Chlorfenapyr", "克凡派", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//56
        ["Chlorfluazuron", "克福隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.05", null],//57
        ["Chloropropylate", "克氯蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//58
        ["Chlorothalonil", "四氯異苯腈", "ppm", ParamType.DOUBLE, "0.0", "0.0", "3.0", null],//59
        ["Chlorpropham", "Chlorpropham", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//60

        ["Chlorpyrifos", "陶斯松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//61
        ["Chlorpyrifos-methyl", "甲基陶斯松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//62
        ["Chlorthal-dimethyl", "大克草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//63
        ["Chlozolinate", "克氯得", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//64
        ["Chromafenozide", "可芬諾", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//65
        ["Cinosulfuron", "西速隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//66
        ["Clofentezine", "克芬蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//67
        ["Clomazone", "可滅蹤", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//68
        ["Clomeprop", "克普草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//69
        ["Clothianidin", "可尼丁", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//70

        ["Cyanofenphos", "施力松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//71
        ["Cyazofamid", "賽座滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "5.0", null],//72
        ["Cyclosulfamuron", "環磺隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//73
        ["Cyflumetofen", "賽芬蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//74
        ["Cyfluthrin", "賽扶寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.3", null],//75
        ["Cyhalofop-butyl", "丁基賽伏草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//76
        ["Cyhalothrin", "賽洛寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//77
        ["Cymoxanil", "克絕", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//78
        ["Cypermethrin", "賽滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//79
        ["Cyproconazole", "環克座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//80

        ["Cyprodinil", "賽普洛", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//81
        ["op-DDD", "o,p\'-DDD", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//82
        ["op-DDE", "o,p\'-DDE", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//83
        ["op-DDT", "o,p\'-DDT", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//84
        ["pp-DDD", "p,p\'-DDD", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//85
        ["pp-DDE", "p,p\'-DDE", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//86
        ["pp-DDT", "p,p\'-DDT", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//87
        ["Deltamethrin", "第滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.02", null],//88
        ["Demeton-s-methyl", "滅賜松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//89
        ["Diazinon", "大利松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//90

        ["Dichlorvos", "二氯松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//91
        ["Dicloran", "大克爛", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//92
        ["Dicofol", "大克蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//93
        ["Dicrotophos", "雙特松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//94
        ["Dieldrin", "地特靈", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//95
        ["Difenoconazole", "待克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//96
        ["Diflubenzuron", "二福隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//97
        ["Dimethenamid", "汰草滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//98
        ["Dimethoate", "大滅松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//99
        ["Dimethomorph", "達滅芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//100

        ["Diniconazole", "達克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//101
        ["Dinitramine", "撻乃安", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//102
        ["Dinotefuran", "達特南", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//103
        ["Diphenamid", "大芬滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//104
        ["Disulfoton", "二硫松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//105
        ["Ditalimfos", "普得松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//106
        ["Diuron", "達有龍", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//107
        ["Dymron", "汰草龍", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//108
        ["Edifenphos", "護粒松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//109
        ["alpha-Endosulfan", "α-安殺番", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//110

        ["beta-Endosulfan", "β-安殺番", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//111
        ["Endosulfan", "安殺番硫酸鹽", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//112
        ["Endrin", "安特靈", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//113
        ["EPN", "一品松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//114
        ["Epoxiconazole", "依普座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//115
        ["Esfenvalerate", "益化利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//116
        ["Ethion", "愛殺松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "3.0", null],//117
        ["Ethiprole", "益斯普", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//118
        ["Ethirimol", "依瑞莫", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//119
        ["Ethoprophos", "普伏松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.02", null],//120

        ["Etofenprox", "依芬寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//121
        ["Etoxazole", "依殺蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.7", null],//122
        ["Etrimfos", "益多松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//123
        ["Famoxadone", "凡殺同", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//124
        ["Fenamiphos", "芬滅松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.1", null],//125
        ["Fenarimol", "芬瑞莫", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//126
        ["Fenazaquin", "芬殺蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//127
        ["Fenbuconazole", "芬克座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//128
        ["Fenbutatin-oxide", "芬佈賜", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//129
        ["Fenhexamid", "Fenhexamid", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//130

        ["Fenitrothion", "撲滅松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//131
        ["Fenobucarb", "丁基滅必蝨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//132
        ["Fenothiocarb", "芬硫克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//133
        ["Fenoxaprop-ethyl", "芬殺草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//134
        ["Fenoxycarb", "芬諾克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//135
        ["Fenpropathrin", "芬普寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//136
        ["Fenpropimorph", "芬普福", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//137
        ["Fenpyroximate", "芬普蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//138
        ["Fensulfothion", "繁褔松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//139
        ["Fenthion", "芬殺松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//140

        ["Fenvalerate", "芬化利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//141
        ["Fipronil", "芬普尼", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.001", null],//142
        ["Flazasulfuron", "伏速隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//143
        ["Flonicamid", "氟尼胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//144
        ["Fluazifop-butyl", "伏寄普", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//145
        ["Fluazinam", "扶吉胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.2", null],//146
        ["Flucythrinate", "護賽寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//147
        ["Fludioxonil", "護汰寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "7.0", null],//148
        ["Flufenoxuron", "氟芬隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//149
        ["Fluopicolide", "氟比來", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//150

        ["Flusilazole", "褔矽得", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//151
        ["Flutolanil", "福多寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//152
        ["Flutriafol", "護汰芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//153
        ["Fluvalinate", "福化利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//154
        ["Fonofos", "大褔松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//155
        ["Formetanate", "覆滅蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.5", null],//156
        ["Formothion", "褔木松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//157
        ["Fthalide", "熱必斯", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//158
        ["Furametpyr", "褔拉比", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//159
        ["Halfenprox", "合芬寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//160

        ["Haloxyfop-methyl", "甲基合氯氟", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.1", null],//161
        ["Heptachlor", "飛佈達", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//162
        ["Heptachlor-epoxide", "環氧飛佈達", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//163
        ["Heptenophos", "飛達松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//164
        ["Hexaconazole", "菲克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//165
        ["Hexaflumuron", "六伏隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.05", null],//166
        ["Hexazinone", "菲殺淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//167
        ["Hexythiazox", "合賽多", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//168
        ["Imazalil", "依滅列", "ppm", ParamType.DOUBLE, "0.0", "0.0", "5.0", null],//169
        ["Imibenconazole", "易胺座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//170

        ["Imidacloprid", "益達胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//171
        ["Indoxacarb", "因得克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//172
        ["Iprobenfos", "丙基喜樂松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//173
        ["Iprodione", "依普同", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//174
        ["Isazofos", "依殺松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//175
        ["Isofenphos", "亞芬松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//176
        ["Isoprocarb", "滅必蝨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//177
        ["Isoprothiolane", "亞賜圃", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//178
        ["IsouronC", "愛速隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//179
        ["Isoxathion", "加福松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//180

        ["Kresoxim-methyl", "克收欣", "ppm", ParamType.DOUBLE, "0.0", "0.0", "5.0", null],//181
        ["Lindane", "靈丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//182
        ["Linuron", "理有龍", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//183
        ["Lufenuron", "祿芬隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//184
        ["Malathion", "馬拉松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//185
        ["Mecarbam", "滅加松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//186
        ["Mefenacet", "滅芬草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//187
        ["Mepanipyrim", "滅派林", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//188
        ["Mephosfolan", "美褔松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//189
        ["Mepronil", "滅普寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//190

        ["Metaflumizone", "美氟綜", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//191
        ["Metalaxyl", "滅達樂", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//192
        ["Metazachlor", "滅草胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//193
        ["Metconazole", "滅特座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//194
        ["Methacrifos", "滅克松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//195
        ["Methamidophos", "達馬松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//196
        ["Methidathion", "滅大松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//197
        ["Methiocarb", "滅賜克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//198
        ["Methomyl", "納乃得", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//199
        ["Methoxyfenozide", "滅芬諾", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//200

        ["Methyl-pentachiorophenyl-sulfide", "五氯苯基甲基硫化物", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//201
        ["Metobromuron", "撲多草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//202
        ["Metolachlor", "莫多草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//203
        ["Metolcarb", "治滅蝨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//204
        ["Metrafenon", "滅芬農", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//205
        ["Metribuzin", "滅必淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//206
        ["Mevinphos", "美文松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//207
        ["Mirex", "滅蟻樂", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//208
        ["Molinate", "稻得壯", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//209
        ["Monocrotophos", "亞素靈", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//210

        ["Myclobutanil", "邁克尼", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//211
        ["Napropamide", "滅落脫", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//212
        ["Norflurazon", "Norflurazon", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.2", null],//213
        ["Novaluron", "諾伐隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//214
        ["Nuarimol", "尼瑞莫", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//215
        ["Omethoate", "歐滅松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//216
        ["Oxadiazon", "樂滅草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//217
        ["Oxadixyl", "毆殺斯", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//218
        ["Oxamyl", "毆殺滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//219
        ["Oxycarboxin", "嘉保信", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//220

        ["Oxyfluorfen", "復祿芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//221
        ["Paclobutrazol", "巴克素", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//222
        ["Parathion", "巴拉松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//223
        ["Parathion-methyl", "甲基巴拉松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//224
        ["Penconazole", "平克座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//225
        ["Pencycuron", "賓克隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//226
        ["Pendimethalin", "施得圃", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//227
        ["Penoxsulam", "平速爛", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//228
        ["Pentachloroaniline", "五氯苯胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//229
        ["Permethrin", "百滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//230

        ["Phenthoate", "賽達松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.2", null],//231
        ["Phorate", "福瑞松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//232
        ["Phosalone", "裕必松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//233
        ["Phosmet", "益滅松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//234
        ["Phosphamidon", "福賜米松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//235
        ["Phoxim", "巴賽松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//236
        ["Pirimicarb", "比加普", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//237
        ["Pirimiphos-ethyl", "乙基亞特松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//238
        ["Pirimiphos-methyl", "亞特松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//239
        ["Pretilachlor", "普拉草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//240

        ["Prochloraz", "撲克拉", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//241
        ["Procymidone", "撲滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//242
        ["Profenophos", "佈飛松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//243
        ["Promecarb", "普滅克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//244
        ["Prometryn", "佈滅淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//245
        ["Propamocarb-hydrochloride", "普拔克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//246
        ["Propanil", "除草靈", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//247
        ["Propaphos", "加護松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//248
        ["Propargit", "毆蟎多", "ppm", ParamType.DOUBLE, "0.0", "0.0", "5.0", null],//249
        ["Propiconazole", "普克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//250

        ["Propoxur", "安丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//251
        ["Prothiofos", "普硫松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//252
        ["Pymetrozine", "派滅淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//253
        ["Pyraclofos", "白克松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//254
        ["Pyraclostrobin", "百克敏", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.0", null],//255
        ["Pyrazophos", "白粉松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//256
        ["Pyridaben", "畢達本", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//257
        ["Pyridaphenthion", "必芬松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "2.0", null],//258
        ["Pyridate", "必汰草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//259
        ["Pyrifenox", "比芬諾", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//260

        ["Pyrimethanil", "派美尼", "ppm", ParamType.DOUBLE, "0.0", "0.0", "7.0", null],//261
        ["Pyrimidifen", "畢汰芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//262
        ["Pyriproxyfen", "百利普芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//263
        ["Pyroquilon", "百快隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//264
        ["Quinalphos", "拜裕松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//265
        ["Quinoxyfen", "快諾芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//266
        ["Quintozene-PCNB", "五氯硝笨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//267
        ["Quizalofop-ethyl", "快伏草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//268
        ["Salithion", "殺力松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//269
        ["Silafluofen", "矽護芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//270

        ["Simazine", "草滅淨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//271
        ["Spinetoram-J", "賜諾特 J", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.2", null],//272
        ["Spinetoram-L", "賜諾特 L", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.2", null],//273
        ["Spinosad-A", "賜諾殺 A", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.3", null],//274
        ["Spinosad-D", "賜諾殺 D", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.3", null],//275
        ["Spirodiclofen", "賜派芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//276
        ["Spirotetramat", "賜派滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//277
        ["Tebuconazole", "得克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//278
        ["Tebufenozide", "得芬諾", "ppm", ParamType.DOUBLE, "0.0", "0.0", "1.5", null],//279
        ["Tebufenpyrad", "得芬瑞", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//280

        ["Teflubenzuron", "得福隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//281
        ["Tepraloxydim", "得殺草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//282
        ["Terbufos", "托福松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//283
        ["Tetraconazole", "四克利", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//284
        ["Tetradifon", "得脫蟎", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//285
        ["Tetramethrin", "治滅寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//286
        ["Thenylchlor", "欣克草", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//287
        ["Thiabendazole", "腐絕", "ppm", ParamType.DOUBLE, "0.0", "0.0", "10.0", null],//288
        ["Thiacloprid", "賽果培", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//289
        ["Thiamethoxam", "賽速安", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//290

        ["Thifluzamide", "賽伏滅", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//291
        ["Thiobencarb", "殺丹", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//292
        ["Thiodicarb", "硫敵克", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//293
        ["Tolclofos-methyl", "脫克松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//294
        ["Tolfenpyrad", "脫芬瑞", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//295
        ["Triadimefon", "三泰芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//296
        ["Triadimenol", "三泰隆", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//297
        ["Triazophos", "三落松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//298
        ["Trichlorfon", "三氯松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//299
        ["Tricyclazole", "三賽唑", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//300

        ["Tridiphane", "三地芬", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0"],//301
        ["Trifloxystrobin", "三氟敏", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.5", null],//302
        ["Triflumizole", "賽福座", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//303
        ["Trifluralin", "三福林", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.05", null],//304
        ["Triforine", "賽福寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//305
        ["Vamidothion", "繁米松", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//306
        ["Vinclozolin", "免克寧", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.01", null],//307
        ["XMC", "滅克蝨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//308
        ["Xylylcarb", "滅爾蝨", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//309
        ["Zoxamide", "座賽胺", "ppm", ParamType.DOUBLE, "0.0", "0.0", "0.0", null],//310

        ["R01", "水分", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//311
        ["R02", "蛋白質", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//312
        ["R03", "脂肪", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//313
        ["R04", "碳水化合物", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//314
        ["R05", "熱量", "Kcal", ParamType.DOUBLE, null, null, null, "每100g含量"],//315
        ["R06", "粗纖維", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//316
        ["R07", "鈣", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//317
        ["R08", "磷", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//318
        ["R09", "鐵", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//319
        ["R10", "胡蘿蔔素", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//320

        ["R11", "硫酸素", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//321
        ["R12", "核黃素", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//322
        ["R13", "尼克酸", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//323
        ["R14", "抗壞血酸", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//324
        ["R15", "膳食纖維", "g", ParamType.DOUBLE, null, null, null, "每100g含量"],//325
        ["R16", "菸鹼酸", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//326
        ["R17", "維生素C", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//327
        ["R18", "鉀", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//328
        ["R19", "鎂", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//329
        ["R20", "維生素B1", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//330
        ["R21", "維生素B2", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//331
        ["R22", "維生素B6", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"],//332
        ["R23", "維生素E", "mg", ParamType.DOUBLE, null, null, null, "每100g含量"]//333
    ]

    /*
    //id, version, creator, dateCreated, defaultValue, description, editor, lastUpdated, lower, name, paramType, siteId, title, unit, upper
    '781','0',NULL,'2015-08-14 05:29:37','0',NULL,'agricloud-douliu','2015-08-14 05:29:37','0','3-keto Carbofuran','DOUBLE','12','3-酮基加保伕','ppm','0'
    '782','0',NULL,'2015-08-14 05:41:09','0',NULL,'agricloud-douliu','2015-08-14 05:41:09','0','3-OH carbofuran','DOUBLE','12','3-羥基加保伕','ppm','0'
    '783','0',NULL,'2015-08-14 05:42:16','0',NULL,'agricloud-douliu','2015-08-14 05:42:16','0','Abamectin','DOUBLE','12','阿巴汀','ppm','0.01'
    '784','0',NULL,'2015-08-14 05:43:04','0',NULL,'agricloud-douliu','2015-08-14 05:43:04','0','Acephate','DOUBLE','12','毆殺松','ppm','0.01'
    '785','0',NULL,'2015-08-14 05:43:40','0',NULL,'agricloud-douliu','2015-08-14 05:43:40','0','Acetamiprid','DOUBLE','12','亞滅培','ppm','0.5'
    '786','1',NULL,'2015-08-14 05:44:26','0',NULL,'r85306474','2016-05-04 17:58:38','0','Acetochlor','DOUBLE','12','Acetochlor','ppm','0'
    '787','0',NULL,'2015-08-14 05:45:15','0',NULL,'agricloud-douliu','2015-08-14 05:45:15','0','Acrinathrin','DOUBLE','12','阿納寧','ppm','0'
    '788','0',NULL,'2015-08-14 05:45:43','0',NULL,'agricloud-douliu','2015-08-14 05:45:43','0','Alachlor','DOUBLE','12','拉草','ppm','0.01'
    '789','0',NULL,'2015-08-14 05:46:45','0',NULL,'agricloud-douliu','2015-08-14 05:46:45','0','Aldicarb','DOUBLE','12','得滅克','ppm','0'
    '790','0',NULL,'2015-08-14 05:47:18','0',NULL,'agricloud-douliu','2015-08-14 05:47:18','0','Aldicarb sulfone','DOUBLE','12','得滅克颯','ppm','0'
    '791','0',NULL,'2015-08-14 05:47:47','0',NULL,'agricloud-douliu','2015-08-14 05:47:47','0','Aldicarb sulfoxide','DOUBLE','12','得滅克亞颯','ppm','0'
    '792','0',NULL,'2015-08-14 05:48:20','0',NULL,'agricloud-douliu','2015-08-14 05:48:20','0','Aldrin','DOUBLE','12','阿特寧','ppm','0'
    '793','0',NULL,'2015-08-14 05:49:08','0',NULL,'agricloud-douliu','2015-08-14 05:49:08','0','Allethrin','DOUBLE','12','亞烈寧','ppm','0.02'
    '794','0',NULL,'2015-08-14 05:50:21','0',NULL,'agricloud-douliu','2015-08-14 05:50:21','0','Alloxydim(sodium)','DOUBLE','12','立汰草','ppm','0'
    '795','0',NULL,'2015-08-14 05:50:53','0',NULL,'agricloud-douliu','2015-08-14 05:50:53','0','Alphacypermethrin','DOUBLE','12','亞滅寧','ppm','0'
    '796','0',NULL,'2015-08-14 05:51:26','0',NULL,'agricloud-douliu','2015-08-14 05:51:26','0','Ametryn','DOUBLE','12','草殺淨','ppm','0'
    '797','1',NULL,'2015-08-14 05:52:07','0',NULL,'agricloud-douliu','2015-08-14 05:52:21','0','Amisulbrom','DOUBLE','12','安美速','ppm','2.0'
    '798','0',NULL,'2015-08-14 05:53:33','0',NULL,'agricloud-douliu','2015-08-14 05:53:33','0','Atrazine','DOUBLE','12','草脫淨','ppm','0'
    '799','0',NULL,'2015-08-14 07:56:23','0',NULL,'agricloud-douliu','2015-08-14 07:56:23','0','Azinphos-methyl','DOUBLE','12','谷速松','ppm','0'
    '800','1',NULL,'2015-08-14 07:56:40','0',NULL,'agricloud-douliu','2015-08-14 07:58:52','0','Azoxystrobin','DOUBLE','12','亞托敏','ppm','10'
    '801','0',NULL,'2015-08-14 07:56:59','0',NULL,'agricloud-douliu','2015-08-14 07:56:59','0','Benalaxyl','DOUBLE','12','本達樂','ppm','0'
    '802','0',NULL,'2015-08-14 07:57:28','0',NULL,'agricloud-douliu','2015-08-14 07:57:28','0','Bendiocarb','DOUBLE','12','免敵克','ppm','0.01'
    '803','0',NULL,'2015-08-14 07:58:01','0',NULL,'agricloud-douliu','2015-08-14 07:58:01','0','Benfluralin','DOUBLE','12','倍尼芬','ppm','0.01'
    '804','0',NULL,'2015-08-14 07:58:24','0',NULL,'agricloud-douliu','2015-08-14 07:58:24','0','Benfuracarb','DOUBLE','12','免扶克','ppm','1'
    '805','0',NULL,'2015-08-14 07:58:44','0',NULL,'agricloud-douliu','2015-08-14 07:58:44','0','Bensulfuron-methyl','DOUBLE','12','免速隆','ppm','0.01'
    '806','0',NULL,'2015-08-14 07:59:17','0',NULL,'agricloud-douliu','2015-08-14 07:59:17','0','Bentazone','DOUBLE','12','本達隆','ppm','0.01'
    '807','0',NULL,'2015-08-14 07:59:35','0',NULL,'agricloud-douliu','2015-08-14 07:59:35','0','Benthiazole','DOUBLE','12','佈生','ppm','0.01'
    '808','0',NULL,'2015-08-14 07:59:51','0',NULL,'agricloud-douliu','2015-08-14 07:59:51','0','α-BHC','DOUBLE','12','α-蟲必死','ppm','0'
    '809','0',NULL,'2015-08-14 08:00:08','0',NULL,'agricloud-douliu','2015-08-14 08:00:08','0','β-BHC','DOUBLE','12','β-蟲必死','ppm','0'
    '810','0',NULL,'2015-08-14 08:00:21','0',NULL,'agricloud-douliu','2015-08-14 08:00:21','0','δ-BHC','DOUBLE','12','δ-蟲必死','ppm','0'
    '811','0',NULL,'2015-08-14 08:00:42','0',NULL,'agricloud-douliu','2015-08-14 08:00:42','0','Bifenazate','DOUBLE','12','必芬蟎','ppm','0'
    '812','0',NULL,'2015-08-14 08:01:00','0',NULL,'agricloud-douliu','2015-08-14 08:01:00','0','Bifenox','DOUBLE','12','必芬諾','ppm','0.01'
    '813','0',NULL,'2015-08-14 08:01:21','0',NULL,'agricloud-douliu','2015-08-14 08:01:21','0','Bifenthrin','DOUBLE','12','畢芬寧','ppm','0'
    '814','0',NULL,'2015-08-14 08:02:20','0',NULL,'agricloud-douliu','2015-08-14 08:02:20','0','Bitertanol','DOUBLE','12','比多農','ppm','0.01'
    '815','0',NULL,'2015-08-14 13:37:00','0',NULL,'agricloud-douliu','2015-08-14 13:37:00','0','Boscalid','DOUBLE','12','白克列','ppm','0.01'
    '816','0',NULL,'2015-08-14 13:37:18','0',NULL,'agricloud-douliu','2015-08-14 13:37:18','0','Bromacil','DOUBLE','12','克草','ppm','0.5'
    '817','0',NULL,'2015-08-14 13:37:35','0',NULL,'agricloud-douliu','2015-08-14 13:37:35','0','Bromophos-ethyl','DOUBLE','12','乙基溴磷松','ppm','0'
    '818','0',NULL,'2015-08-14 13:37:51','0',NULL,'agricloud-douliu','2015-08-14 13:37:51','0','Bromophos-methyl','DOUBLE','12','甲基溴磷松','ppm','0'
    '819','0',NULL,'2015-08-14 13:38:08','0',NULL,'agricloud-douliu','2015-08-14 13:38:08','0','Bromopropylate','DOUBLE','12','新殺蟎','ppm','3.0'
    '820','0',NULL,'2015-08-14 13:38:21','0',NULL,'agricloud-douliu','2015-08-14 13:38:21','0','Bromuconazole','DOUBLE','12','溴克座','ppm','0'
    '821','0',NULL,'2015-08-14 13:38:37','0',NULL,'agricloud-douliu','2015-08-14 13:38:37','0','Bupirimate','DOUBLE','12','布瑞莫','ppm','0.01'
    '822','0',NULL,'2015-08-14 13:38:53','0',NULL,'agricloud-douliu','2015-08-14 13:38:53','0','Buprofezin','DOUBLE','12','布芬淨','ppm','0.5'
    '823','0',NULL,'2015-08-14 13:39:12','0',NULL,'agricloud-douliu','2015-08-14 13:39:12','0','Butachlor','DOUBLE','12','丁基拉草','ppm','0.01'
    '824','0',NULL,'2015-08-14 13:40:04','0',NULL,'agricloud-douliu','2015-08-14 13:40:04','0','Butocarboxim','DOUBLE','12','佈嘉信','ppm','0.01'
    '825','0',NULL,'2015-08-14 13:40:38','0',NULL,'agricloud-douliu','2015-08-14 13:40:38','0','Butralin','DOUBLE','12','比達寧','ppm','0.01'
    '826','0',NULL,'2015-08-14 13:40:53','0',NULL,'agricloud-douliu','2015-08-14 13:40:53','0','Carbaryl','DOUBLE','12','加保利','ppm','2.0'
    '827','0',NULL,'2015-08-14 13:41:11','0',NULL,'agricloud-douliu','2015-08-14 13:41:11','0','Carbendazim','DOUBLE','12','貝芬替','ppm','0.01'
    '828','0',NULL,'2015-08-14 13:41:27','0',NULL,'agricloud-douliu','2015-08-14 13:41:27','0','Carbofuran','DOUBLE','12','加保扶','ppm','2.0'
    '829','0',NULL,'2015-08-14 13:41:44','0',NULL,'agricloud-douliu','2015-08-14 13:41:44','0','Carbophenothion','DOUBLE','12','加保松','ppm','0'
    '830','0',NULL,'2015-08-14 13:42:36','0',NULL,'agricloud-douliu','2015-08-14 13:42:36','0','Carbosulfan','DOUBLE','12','丁基加保扶','ppm','2.0'
    '831','0',NULL,'2015-08-14 13:42:53','0',NULL,'agricloud-douliu','2015-08-14 13:42:53','0','Carpropamid','DOUBLE','12','加普胺','ppm','0.01'
    '832','0',NULL,'2015-08-14 13:43:16','0',NULL,'agricloud-douliu','2015-08-14 13:43:16','0','Chinomethionat','DOUBLE','12','蟎離丹','ppm','0.5'
    '833','0',NULL,'2015-08-14 13:44:11','0',NULL,'agricloud-douliu','2015-08-14 13:44:11','0','Chlorantraniliprole','DOUBLE','12','剋安勃','ppm','0.5'
    '834','0',NULL,'2015-08-14 13:44:25','0',NULL,'agricloud-douliu','2015-08-14 13:44:25','0','cis-Chlordane','DOUBLE','12','cis-可氯丹','ppm','0'
    '835','0',NULL,'2015-08-14 13:44:38','0',NULL,'agricloud-douliu','2015-08-14 13:44:38','0','trans-Chlordane','DOUBLE','12','trans-可氯丹','ppm','0'
    '836','0',NULL,'2015-08-14 13:44:57','0',NULL,'agricloud-douliu','2015-08-14 13:44:57','0','Chlorfenapyr','DOUBLE','12','克凡派','ppm','1.0'
    '837','0',NULL,'2015-08-14 13:45:15','0',NULL,'agricloud-douliu','2015-08-14 13:45:15','0','Chlorfluazuron','DOUBLE','12','克福隆','ppm','0.05'
    '838','0',NULL,'2015-08-14 13:47:25','0',NULL,'agricloud-douliu','2015-08-14 13:47:25','0','Chloropropylate','DOUBLE','12','克氯蟎','ppm','0.01'
    '839','0',NULL,'2015-08-14 13:47:50','0',NULL,'agricloud-douliu','2015-08-14 13:47:50','0','Chlorothalonil','DOUBLE','12','四氯異苯腈','ppm','3.0'
    '840','0',NULL,'2015-08-14 13:48:30','0',NULL,'agricloud-douliu','2015-08-14 13:48:30','0','Chlorpropham','DOUBLE','12','Chlorpropham','ppm','0'
    '841','0',NULL,'2015-08-14 13:48:46','0',NULL,'agricloud-douliu','2015-08-14 13:48:46','0','Chlorpyrifos','DOUBLE','12','陶斯松','ppm','1.0'
    '842','0',NULL,'2015-08-14 13:49:03','0',NULL,'agricloud-douliu','2015-08-14 13:49:03','0','Chlorpyrifos-methyl','DOUBLE','12','甲基陶斯松','ppm','0.01'
    '843','0',NULL,'2015-08-14 13:49:19','0',NULL,'agricloud-douliu','2015-08-14 13:49:19','0','Chlorthal-dimethyl','DOUBLE','12','大克草','ppm','0'
    '844','0',NULL,'2015-08-14 13:49:32','0',NULL,'agricloud-douliu','2015-08-14 13:49:32','0','Chlozolinate','DOUBLE','12','克氯得','ppm','0'
    '845','0',NULL,'2015-08-14 13:49:48','0',NULL,'agricloud-douliu','2015-08-14 13:49:48','0','Chromafenozide','DOUBLE','12','可芬諾','ppm','0'
    '846','0',NULL,'2015-08-14 13:50:05','0',NULL,'agricloud-douliu','2015-08-14 13:50:05','0','Cinosulfuron','DOUBLE','12','西速隆','ppm','0.01'
    '847','0',NULL,'2015-08-14 13:50:19','0',NULL,'agricloud-douliu','2015-08-14 13:50:19','0','Clofentezine','DOUBLE','12','克芬蟎','ppm','2.0'
    '848','0',NULL,'2015-08-14 13:50:39','0',NULL,'agricloud-douliu','2015-08-14 13:50:39','0','Clomazone','DOUBLE','12','可滅蹤','ppm','0.01'
    '849','0',NULL,'2015-08-14 13:50:56','0',NULL,'agricloud-douliu','2015-08-14 13:50:56','0','Clomeprop','DOUBLE','12','克普草','ppm','0'
    '850','0',NULL,'2015-08-14 13:51:22','0',NULL,'agricloud-douliu','2015-08-14 13:51:22','0','Clothianidin','DOUBLE','12','可尼丁','ppm','1.0'
    '851','0',NULL,'2015-08-14 13:51:36','0',NULL,'agricloud-douliu','2015-08-14 13:51:36','0','Cyanofenphos','DOUBLE','12','施力松','ppm','0'
    '852','0',NULL,'2015-08-14 13:51:50','0',NULL,'agricloud-douliu','2015-08-14 13:51:50','0','Cyazofamid','DOUBLE','12','賽座滅','ppm','5.0'
    '853','0',NULL,'2015-08-14 13:52:06','0',NULL,'agricloud-douliu','2015-08-14 13:52:06','0','Cyclosulfamuron','DOUBLE','12','環磺隆','ppm','0.01'
    '854','0',NULL,'2015-08-14 13:52:26','0',NULL,'agricloud-douliu','2015-08-14 13:52:26','0','Cyflumetofen','DOUBLE','12','賽芬蟎','ppm','1.0'
    '855','0',NULL,'2015-08-14 13:53:02','0',NULL,'agricloud-douliu','2015-08-14 13:53:02','0','Cyfluthrin','DOUBLE','12','賽扶寧','ppm','0.3'
    '856','0',NULL,'2015-08-14 13:53:18','0',NULL,'agricloud-douliu','2015-08-14 13:53:18','0','Cyhalofop-butyl','DOUBLE','12','丁基賽伏草','ppm','0'
    '857','0',NULL,'2015-08-14 13:53:36','0',NULL,'agricloud-douliu','2015-08-14 13:53:36','0','Cyhalothrin','DOUBLE','12','賽洛寧','ppm','1.0'
    '858','0',NULL,'2015-08-14 13:53:52','0',NULL,'agricloud-douliu','2015-08-14 13:53:52','0','Cymoxanil','DOUBLE','12','克絕','ppm','0.01'
    '859','0',NULL,'2015-08-14 13:54:08','0',NULL,'agricloud-douliu','2015-08-14 13:54:08','0','Cypermethrin','DOUBLE','12','賽滅寧','ppm','2.0'
    '860','0',NULL,'2015-08-14 13:54:22','0',NULL,'agricloud-douliu','2015-08-14 13:54:22','0','Cyproconazole','DOUBLE','12','環克座','ppm','0.01'
    '861','0',NULL,'2015-08-14 13:54:40','0',NULL,'agricloud-douliu','2015-08-14 13:54:40','0','Cyprodinil','DOUBLE','12','賽普洛','ppm','0.01'
    '862','0',NULL,'2015-08-14 13:55:09','0',NULL,'agricloud-douliu','2015-08-14 13:55:09','0','o,p\'-DDD','DOUBLE','12','o,p\'-滴滴滴','ppm','0'
    '863','0',NULL,'2015-08-14 13:55:26','0',NULL,'agricloud-douliu','2015-08-14 13:55:26','0','o,p\'-DDE','DOUBLE','12','o,p\'-滴滴易','ppm','0'
    '864','0',NULL,'2015-08-14 13:55:41','0',NULL,'agricloud-douliu','2015-08-14 13:55:41','0','o,p\'-DDT','DOUBLE','12','o,p\'-滴滴涕','ppm','0'
    '865','0',NULL,'2015-08-14 13:55:54','0',NULL,'agricloud-douliu','2015-08-14 13:55:54','0','p,p\'-DDD','DOUBLE','12','p,p\'-滴滴滴','ppm','0'
    '866','0',NULL,'2015-08-14 13:56:06','0',NULL,'agricloud-douliu','2015-08-14 13:56:06','0','p,p\'-DDE','DOUBLE','12','p,p\'-滴滴易','ppm','0'
    '867','0',NULL,'2015-08-14 13:56:17','0',NULL,'agricloud-douliu','2015-08-14 13:56:17','0','p,p\'-DDT','DOUBLE','12','p,p\'-滴滴涕','ppm','0'
    '868','0',NULL,'2015-08-14 13:56:40','0',NULL,'agricloud-douliu','2015-08-14 13:56:40','0','Deltamethrin','DOUBLE','12','第滅寧','ppm','0.02'
    '869','0',NULL,'2015-08-14 13:56:57','0',NULL,'agricloud-douliu','2015-08-14 13:56:57','0','Demeton-s-methyl','DOUBLE','12','滅賜松','ppm','0.5'
    '870','0',NULL,'2015-08-14 13:57:11','0',NULL,'agricloud-douliu','2015-08-14 13:57:11','0','Diazinon','DOUBLE','12','大利松','ppm','0'
    '871','0',NULL,'2015-08-14 13:57:33','0',NULL,'agricloud-douliu','2015-08-14 13:57:33','0','Dichlorvos','DOUBLE','12','二氯松','ppm','0'
    '872','0',NULL,'2015-08-14 13:57:46','0',NULL,'agricloud-douliu','2015-08-14 13:57:46','0','Dicloran','DOUBLE','12','大克爛','ppm','0.01'
    '873','0',NULL,'2015-08-14 13:58:01','0',NULL,'agricloud-douliu','2015-08-14 13:58:01','0','Dicofol','DOUBLE','12','大克蟎','ppm','1.0'
    '874','0',NULL,'2015-08-14 13:58:15','0',NULL,'agricloud-douliu','2015-08-14 13:58:15','0','Dicrotophos','DOUBLE','12','雙特松','ppm','0'
    '875','0',NULL,'2015-08-14 13:58:27','0',NULL,'agricloud-douliu','2015-08-14 13:58:27','0','Dieldrin','DOUBLE','12','地特靈','ppm','0'
    '876','0',NULL,'2015-08-14 13:58:43','0',NULL,'agricloud-douliu','2015-08-14 13:58:43','0','Difenoconazole','DOUBLE','12','待克利','ppm','0.01'
    '877','0',NULL,'2015-08-14 13:58:55','0',NULL,'agricloud-douliu','2015-08-14 13:58:55','0','Diflubenzuron','DOUBLE','12','二福隆','ppm','1.0'
    '878','0',NULL,'2015-08-14 13:59:33','0',NULL,'agricloud-douliu','2015-08-14 13:59:33','0','Dimethenamid','DOUBLE','12','汰草滅','ppm','0.01'
    '879','0',NULL,'2015-08-14 13:59:49','0',NULL,'agricloud-douliu','2015-08-14 13:59:49','0','Dimethoate','DOUBLE','12','大滅松','ppm','2.0'
    '880','0',NULL,'2015-08-14 14:00:05','0',NULL,'agricloud-douliu','2015-08-14 14:00:05','0','Dimethomorph','DOUBLE','12','達滅芬','ppm','0.01'
    '881','0',NULL,'2015-08-14 14:00:28','0',NULL,'agricloud-douliu','2015-08-14 14:00:28','0','Diniconazole','DOUBLE','12','達克利','ppm','0'
    '882','0',NULL,'2015-08-14 14:00:44','0',NULL,'agricloud-douliu','2015-08-14 14:00:44','0','Dinitramine','DOUBLE','12','撻乃安','ppm','0.01'
    '883','0',NULL,'2015-08-14 14:00:55','0',NULL,'agricloud-douliu','2015-08-14 14:00:55','0','Dinotefuran','DOUBLE','12','達特南','ppm','0'
    '884','0',NULL,'2015-08-14 14:01:08','0',NULL,'agricloud-douliu','2015-08-14 14:01:08','0','Diphenamid','DOUBLE','12','大芬滅','ppm','0.01'
    '885','0',NULL,'2015-08-14 14:01:24','0',NULL,'agricloud-douliu','2015-08-14 14:01:24','0','Disulfoton','DOUBLE','12','二硫松','ppm','0'
    '886','0',NULL,'2015-08-14 14:01:36','0',NULL,'agricloud-douliu','2015-08-14 14:01:36','0','Ditalimfos','DOUBLE','12','普得松','ppm','0'
    '887','0',NULL,'2015-08-14 14:01:46','0',NULL,'agricloud-douliu','2015-08-14 14:01:46','0','Diuron','DOUBLE','12','達有龍','ppm','0'
    '888','0',NULL,'2015-08-14 14:02:26','0',NULL,'agricloud-douliu','2015-08-14 14:02:26','0','Dymron','DOUBLE','12','汰草龍','ppm','0'
    '889','0',NULL,'2015-08-14 14:02:41','0',NULL,'agricloud-douliu','2015-08-14 14:02:41','0','Edifenphos','DOUBLE','12','護粒松','ppm','0.01'
    '890','0',NULL,'2015-08-14 14:03:03','0',NULL,'agricloud-douliu','2015-08-14 14:03:03','0','α-Endosulfan','DOUBLE','12','α-安殺番','ppm','0'
    '891','0',NULL,'2015-08-14 14:03:17','0',NULL,'agricloud-douliu','2015-08-14 14:03:17','0','β-Endosulfan','DOUBLE','12','β-安殺番','ppm','0'
    '892','0',NULL,'2015-08-14 14:03:29','0',NULL,'agricloud-douliu','2015-08-14 14:03:29','0','Endosulfan','DOUBLE','12','安殺番硫酸鹽','ppm','0'
    '893','0',NULL,'2015-08-14 14:03:43','0',NULL,'agricloud-douliu','2015-08-14 14:03:43','0','Endrin','DOUBLE','12','安特靈','ppm','0'
    '894','0',NULL,'2015-08-14 14:03:57','0',NULL,'agricloud-douliu','2015-08-14 14:03:57','0','EPN','DOUBLE','12','一品松','ppm','0'
    '895','0',NULL,'2015-08-14 14:04:19','0',NULL,'agricloud-douliu','2015-08-14 14:04:19','0','Epoxiconazole','DOUBLE','12','依普座','ppm','0.01'
    '896','0',NULL,'2015-08-14 14:04:39','0',NULL,'agricloud-douliu','2015-08-14 14:04:39','0','Esfenvalerate','DOUBLE','12','益化利','ppm','0'
    '897','0',NULL,'2015-08-14 14:04:53','0',NULL,'agricloud-douliu','2015-08-14 14:04:53','0','Ethion','DOUBLE','12','愛殺松','ppm','3.0'
    '898','0',NULL,'2015-08-14 14:05:04','0',NULL,'agricloud-douliu','2015-08-14 14:05:04','0','Ethiprole','DOUBLE','12','益斯普','ppm','0'
    '899','0',NULL,'2015-08-14 14:05:22','0',NULL,'agricloud-douliu','2015-08-14 14:05:22','0','Ethirimol','DOUBLE','12','依瑞莫','ppm','0.01'
    '900','0',NULL,'2015-08-14 14:05:41','0',NULL,'agricloud-douliu','2015-08-14 14:05:41','0','Ethoprophos','DOUBLE','12','普伏松','ppm','0.02'
    '901','0',NULL,'2015-08-14 14:08:40','0',NULL,'agricloud-douliu','2015-08-14 14:08:40','0','Etofenprox','DOUBLE','12','依芬寧','ppm','0'
    '902','1',NULL,'2015-08-14 14:08:52','0',NULL,'agricloud-douliu','2015-08-14 14:09:12','0','Etoxazole','DOUBLE','12','依殺蟎','ppm','0.7'
    '903','0',NULL,'2015-08-14 14:09:24','0',NULL,'agricloud-douliu','2015-08-14 14:09:24','0','Etrimfos','DOUBLE','12','益多松','ppm','0'
    '904','0',NULL,'2015-08-14 14:09:48','0',NULL,'agricloud-douliu','2015-08-14 14:09:48','0','Famoxadone','DOUBLE','12','凡殺同','ppm','0.01'
    '905','0',NULL,'2015-08-14 14:10:05','0',NULL,'agricloud-douliu','2015-08-14 14:10:05','0','Fenamiphos','DOUBLE','12','芬滅松','ppm','0.1'
    '906','0',NULL,'2015-08-14 14:13:17','0',NULL,'agricloud-douliu','2015-08-14 14:13:17','0','Fenarimol','DOUBLE','12','芬瑞莫','ppm','0.01'
    '907','0',NULL,'2015-08-14 14:13:34','0',NULL,'agricloud-douliu','2015-08-14 14:13:34','0','Fenazaquin','DOUBLE','12','芬殺蟎','ppm','0.5'
    '908','0',NULL,'2015-08-14 14:14:00','0',NULL,'agricloud-douliu','2015-08-14 14:14:00','0','Fenbuconazole','DOUBLE','12','芬克座','ppm','0.01'
    '909','0',NULL,'2015-08-14 14:14:14','0',NULL,'agricloud-douliu','2015-08-14 14:14:14','0','Fenbutatin-oxide','DOUBLE','12','芬佈賜','ppm','2.0'
    '910','0',NULL,'2015-08-14 14:14:28','0',NULL,'agricloud-douliu','2015-08-14 14:14:28','0','Fenhexamid','DOUBLE','12','Fenhexamid','ppm','0'
    '911','0',NULL,'2015-08-14 14:14:46','0',NULL,'agricloud-douliu','2015-08-14 14:14:46','0','Fenitrothion','DOUBLE','12','撲滅松','ppm','0.5'
    '912','0',NULL,'2015-08-14 14:15:04','0',NULL,'agricloud-douliu','2015-08-14 14:15:04','0','Fenobucarb','DOUBLE','12','丁基滅必蝨','ppm','0.01'
    '913','0',NULL,'2015-08-14 14:15:17','0',NULL,'agricloud-douliu','2015-08-14 14:15:17','0','Fenothiocarb','DOUBLE','12','芬硫克','ppm','1.0'
    '914','0',NULL,'2015-08-14 14:15:45','0',NULL,'agricloud-douliu','2015-08-14 14:15:45','0','Fenoxaprop-ethyl','DOUBLE','12','芬殺草','ppm','0'
    '915','0',NULL,'2015-08-14 14:15:59','0',NULL,'agricloud-douliu','2015-08-14 14:15:59','0','Fenoxycarb','DOUBLE','12','芬諾克','ppm','1.0'
    '916','0',NULL,'2015-08-14 14:16:15','0',NULL,'agricloud-douliu','2015-08-14 14:16:15','0','Fenpropathrin','DOUBLE','12','芬普寧','ppm','0.5'
    '917','0',NULL,'2015-08-14 14:16:32','0',NULL,'agricloud-douliu','2015-08-14 14:16:32','0','Fenpropimorph','DOUBLE','12','芬普福','ppm','0.01'
    '918','0',NULL,'2015-08-14 14:16:45','0',NULL,'agricloud-douliu','2015-08-14 14:16:45','0','Fenpyroximate','DOUBLE','12','芬普蟎','ppm','0.5'
    '919','0',NULL,'2015-08-14 14:17:00','0',NULL,'agricloud-douliu','2015-08-14 14:17:00','0','Fensulfothion','DOUBLE','12','繁褔松','ppm','0'
    '920','0',NULL,'2015-08-14 14:17:13','0',NULL,'agricloud-douliu','2015-08-14 14:17:13','0','Fenthion','DOUBLE','12','芬殺松','ppm','0.5'
    '921','0',NULL,'2015-08-14 14:18:05','0',NULL,'agricloud-douliu','2015-08-14 14:18:05','0','Fenvalerate','DOUBLE','12','芬化利','ppm','2.0'
    '922','0',NULL,'2015-08-14 14:18:30','0',NULL,'agricloud-douliu','2015-08-14 14:18:30','0','Fipronil','DOUBLE','12','芬普尼','ppm','0.001'
    '923','0',NULL,'2015-08-14 14:21:50','0',NULL,'agricloud-douliu','2015-08-14 14:21:50','0','Flazasulfuron','DOUBLE','12','伏速隆','ppm','0.01'
    '924','0',NULL,'2015-08-14 14:22:05','0',NULL,'agricloud-douliu','2015-08-14 14:22:05','0','Flonicamid','DOUBLE','12','氟尼胺','ppm','0.01'
    '925','0',NULL,'2015-08-14 14:25:20','0',NULL,'agricloud-douliu','2015-08-14 14:25:20','0','Fluazifop-butyl','DOUBLE','12','伏寄普','ppm','0.01'
    '926','0',NULL,'2015-08-14 14:25:37','0',NULL,'agricloud-douliu','2015-08-14 14:25:37','0','Fluazinam','DOUBLE','12','扶吉胺','ppm','0.2'
    '927','0',NULL,'2015-08-14 14:25:50','0',NULL,'agricloud-douliu','2015-08-14 14:25:50','0','Flucythrinate','DOUBLE','12','護賽寧','ppm','1.0'
    '928','0',NULL,'2015-08-14 14:26:04','0',NULL,'agricloud-douliu','2015-08-14 14:26:04','0','Fludioxonil','DOUBLE','12','護汰寧','ppm','7.0'
    '929','0',NULL,'2015-08-14 14:26:19','0',NULL,'agricloud-douliu','2015-08-14 14:26:19','0','Flufenoxuron','DOUBLE','12','氟芬隆','ppm','0.01'
    '930','0',NULL,'2015-08-14 14:26:34','0',NULL,'agricloud-douliu','2015-08-14 14:26:34','0','Fluopicolide','DOUBLE','12','氟比來','ppm','0.01'
    '931','0',NULL,'2015-08-14 14:26:46','0',NULL,'agricloud-douliu','2015-08-14 14:26:46','0','Flusilazole','DOUBLE','12','褔矽得','ppm','0'
    '932','0',NULL,'2015-08-14 14:27:03','0',NULL,'agricloud-douliu','2015-08-14 14:27:03','0','Flutolanil','DOUBLE','12','福多寧','ppm','0.01'
    '933','0',NULL,'2015-08-14 14:27:33','0',NULL,'agricloud-douliu','2015-08-14 14:27:33','0','Flutriafol','DOUBLE','12','護汰芬','ppm','0.01'
    '934','0',NULL,'2015-08-14 14:27:54','0',NULL,'agricloud-douliu','2015-08-14 14:27:54','0','Fluvalinate','DOUBLE','12','福化利','ppm','1.0'
    '935','0',NULL,'2015-08-14 14:28:15','0',NULL,'agricloud-douliu','2015-08-14 14:28:15','0','Fonofos','DOUBLE','12','大褔松','ppm','0'
    '936','0',NULL,'2015-08-14 14:28:30','0',NULL,'agricloud-douliu','2015-08-14 14:28:30','0','Formetanate','DOUBLE','12','覆滅蟎','ppm','1.5'
    '937','0',NULL,'2015-08-14 14:28:43','0',NULL,'agricloud-douliu','2015-08-14 14:28:43','0','Formothion','DOUBLE','12','褔木松','ppm','0'
    '938','0',NULL,'2015-08-14 14:29:09','0',NULL,'agricloud-douliu','2015-08-14 14:29:09','0','Fthalide','DOUBLE','12','熱必斯','ppm','0.01'
    '939','0',NULL,'2015-08-14 14:29:21','0',NULL,'agricloud-douliu','2015-08-14 14:29:21','0','Furametpyr','DOUBLE','12','褔拉比','ppm','0'
    '940','0',NULL,'2015-08-14 14:29:35','0',NULL,'agricloud-douliu','2015-08-14 14:29:35','0','Halfenprox','DOUBLE','12','合芬寧','ppm','0.01'
    '941','0',NULL,'2015-08-14 14:29:51','0',NULL,'agricloud-douliu','2015-08-14 14:29:51','0','Haloxyfop-methyl','DOUBLE','12','甲基合氯氟','ppm','0.1'
    '942','0',NULL,'2015-08-14 14:30:08','0',NULL,'agricloud-douliu','2015-08-14 14:30:08','0','Heptachlor','DOUBLE','12','飛佈達','ppm','0'
    '943','0',NULL,'2015-08-14 14:30:59','0',NULL,'agricloud-douliu','2015-08-14 14:30:59','0','Heptachlor epoxide','DOUBLE','12','環氧飛佈達','ppm','0'
    '944','0',NULL,'2015-08-14 14:31:13','0',NULL,'agricloud-douliu','2015-08-14 14:31:13','0','Heptenophos','DOUBLE','12','飛達松','ppm','0.01'
    '945','0',NULL,'2015-08-14 14:31:27','0',NULL,'agricloud-douliu','2015-08-14 14:31:27','0','Hexaconazole','DOUBLE','12','菲克利','ppm','0.01'
    '946','0',NULL,'2015-08-14 14:32:45','0',NULL,'agricloud-douliu','2015-08-14 14:32:45','0','Hexaflumuron','DOUBLE','12','六伏隆','ppm','0.05'
    '947','0',NULL,'2015-08-14 14:33:00','0',NULL,'agricloud-douliu','2015-08-14 14:33:00','0','Hexazinone','DOUBLE','12','菲殺淨','ppm','0.01'
    '948','0',NULL,'2015-08-14 14:33:12','0',NULL,'agricloud-douliu','2015-08-14 14:33:12','0','Hexythiazox','DOUBLE','12','合賽多','ppm','1.0'
    '949','0',NULL,'2015-08-14 14:33:30','0',NULL,'agricloud-douliu','2015-08-14 14:33:30','0','Imazalil','DOUBLE','12','依滅列','ppm','5.0'
    '950','0',NULL,'2015-08-14 14:33:48','0',NULL,'agricloud-douliu','2015-08-14 14:33:48','0','Imibenconazole','DOUBLE','12','易胺座','ppm','0.5'
    '951','0',NULL,'2015-08-14 14:34:01','0',NULL,'agricloud-douliu','2015-08-14 14:34:01','0','Imidacloprid','DOUBLE','12','益達胺','ppm','1.0'
    '952','0',NULL,'2015-08-14 14:34:16','0',NULL,'agricloud-douliu','2015-08-14 14:34:16','0','Indoxacarb','DOUBLE','12','因得克','ppm','0.01'
    '953','0',NULL,'2015-08-14 14:34:31','0',NULL,'agricloud-douliu','2015-08-14 14:34:31','0','Iprobenfos','DOUBLE','12','丙基喜樂松','ppm','0.01'
    '954','0',NULL,'2015-08-14 14:34:44','0',NULL,'agricloud-douliu','2015-08-14 14:34:44','0','Iprodione','DOUBLE','12','依普同','ppm','0.01'
    '955','0',NULL,'2015-08-14 14:34:55','0',NULL,'agricloud-douliu','2015-08-14 14:34:55','0','Isazofos','DOUBLE','12','依殺松','ppm','0'
    '956','0',NULL,'2015-08-14 14:35:12','0',NULL,'agricloud-douliu','2015-08-14 14:35:12','0','Isofenphos','DOUBLE','12','亞芬松','ppm','0.01'
    '957','0',NULL,'2015-08-14 14:35:29','0',NULL,'agricloud-douliu','2015-08-14 14:35:29','0','Isoprocarb','DOUBLE','12','滅必蝨','ppm','2.0'
    '958','0',NULL,'2015-08-14 14:35:46','0',NULL,'agricloud-douliu','2015-08-14 14:35:46','0','Isoprothiolane','DOUBLE','12','亞賜圃','ppm','0.01'
    '959','0',NULL,'2015-08-14 14:36:20','0',NULL,'agricloud-douliu','2015-08-14 14:36:20','0','Isouron','DOUBLE','12','愛速隆','ppm','0'
    '960','0',NULL,'2015-08-14 14:36:58','0',NULL,'agricloud-douliu','2015-08-14 14:36:58','0','Isoxathion','DOUBLE','12','加福松','ppm','1.0'
    '961','0',NULL,'2015-08-14 14:37:13','0',NULL,'agricloud-douliu','2015-08-14 14:37:13','0','Kresoxim-methyl','DOUBLE','12','克收欣','ppm','5.0'
    '962','0',NULL,'2015-08-14 14:37:25','0',NULL,'agricloud-douliu','2015-08-14 14:37:25','0','Lindane','DOUBLE','12','靈丹','ppm','0'
    '963','0',NULL,'2015-08-14 14:37:38','0',NULL,'agricloud-douliu','2015-08-14 14:37:38','0','Linuron','DOUBLE','12','理有龍','ppm','0'
    '964','0',NULL,'2015-08-14 14:37:50','0',NULL,'agricloud-douliu','2015-08-14 14:37:50','0','Lufenuron','DOUBLE','12','祿芬隆','ppm','0'
    '965','0',NULL,'2015-08-14 14:38:02','0',NULL,'agricloud-douliu','2015-08-14 14:38:02','0','Malathion','DOUBLE','12','馬拉松','ppm','2.0'
    '966','0',NULL,'2015-08-14 14:38:13','0',NULL,'agricloud-douliu','2015-08-14 14:38:13','0','Mecarbam','DOUBLE','12','滅加松','ppm','0'
    '967','0',NULL,'2015-08-14 14:38:28','0',NULL,'agricloud-douliu','2015-08-14 14:38:28','0','Mefenacet','DOUBLE','12','滅芬草','ppm','0.01'
    '968','0',NULL,'2015-08-14 14:38:39','0',NULL,'agricloud-douliu','2015-08-14 14:38:39','0','Mepanipyrim','DOUBLE','12','滅派林','ppm','0'
    '969','0',NULL,'2015-08-14 14:38:51','0',NULL,'agricloud-douliu','2015-08-14 14:38:51','0','Mephosfolan','DOUBLE','12','美褔松','ppm','0'
    '970','0',NULL,'2015-08-14 14:39:07','0',NULL,'agricloud-douliu','2015-08-14 14:39:07','0','Mepronil','DOUBLE','12','滅普寧','ppm','0.01'
    '971','0',NULL,'2015-08-14 14:39:41','0',NULL,'agricloud-douliu','2015-08-14 14:39:41','0','Metaflumizone','DOUBLE','12','美氟綜','ppm','0.01'
    '972','0',NULL,'2015-08-14 14:39:59','0',NULL,'agricloud-douliu','2015-08-14 14:39:59','0','Metalaxyl','DOUBLE','12','滅達樂','ppm','0.5'
    '973','0',NULL,'2015-08-14 14:40:37','0',NULL,'agricloud-douliu','2015-08-14 14:40:37','0','Metazachlor','DOUBLE','12','滅草胺','ppm','0'
    '974','0',NULL,'2015-08-14 14:40:50','0',NULL,'agricloud-douliu','2015-08-14 14:40:50','0','Metconazole','DOUBLE','12','滅特座','ppm','0.01'
    '975','0',NULL,'2015-08-14 14:41:02','0',NULL,'agricloud-douliu','2015-08-14 14:41:02','0','Methacrifos','DOUBLE','12','滅克松','ppm','0'
    '976','0',NULL,'2015-08-14 14:41:14','0',NULL,'agricloud-douliu','2015-08-14 14:41:14','0','Methamidophos','DOUBLE','12','達馬松','ppm','0.5'
    '977','0',NULL,'2015-08-14 14:41:27','0',NULL,'agricloud-douliu','2015-08-14 14:41:27','0','Methidathion','DOUBLE','12','滅大松','ppm','1.0'
    '978','0',NULL,'2015-08-14 14:41:41','0',NULL,'agricloud-douliu','2015-08-14 14:41:41','0','Methiocarb','DOUBLE','12','滅賜克','ppm','0.01'
    '979','0',NULL,'2015-08-14 14:41:53','0',NULL,'agricloud-douliu','2015-08-14 14:41:53','0','Methomyl','DOUBLE','12','納乃得','ppm','1.0'
    '980','0',NULL,'2015-08-14 14:42:08','0',NULL,'agricloud-douliu','2015-08-14 14:42:08','0','Methoxyfenozide','DOUBLE','12','滅芬諾','ppm','2.0'
    '981','0',NULL,'2015-08-14 14:42:22','0',NULL,'agricloud-douliu','2015-08-14 14:42:22','0','Methyl pentachiorophenyl sulfide','DOUBLE','12','五氯苯基甲基硫化物','ppm','0'
    '982','0',NULL,'2015-08-14 14:42:34','0',NULL,'agricloud-douliu','2015-08-14 14:42:34','0','Metobromuron','DOUBLE','12','撲多草','ppm','0'
    '983','0',NULL,'2015-08-14 14:42:47','0',NULL,'agricloud-douliu','2015-08-14 14:42:47','0','Metolachlor','DOUBLE','12','莫多草','ppm','0.01'
    '984','0',NULL,'2015-08-14 14:43:02','0',NULL,'agricloud-douliu','2015-08-14 14:43:02','0','Metolcarb','DOUBLE','12','治滅蝨','ppm','0.01'
    '985','0',NULL,'2015-08-14 14:43:47','0',NULL,'agricloud-douliu','2015-08-14 14:43:47','0','Metrafenone','DOUBLE','12','滅芬農','ppm','0'
    '986','0',NULL,'2015-08-14 14:44:01','0',NULL,'agricloud-douliu','2015-08-14 14:44:01','0','Metribuzin','DOUBLE','12','滅必淨','ppm','0.01'
    '987','0',NULL,'2015-08-14 14:44:19','0',NULL,'agricloud-douliu','2015-08-14 14:44:19','0','Mevinphos','DOUBLE','12','美文松','ppm','0'
    '988','0',NULL,'2015-08-14 14:45:11','0',NULL,'agricloud-douliu','2015-08-14 14:45:11','0','Mirex','DOUBLE','12','滅蟻樂','ppm','0'
    '989','0',NULL,'2015-08-14 14:45:24','0',NULL,'agricloud-douliu','2015-08-14 14:45:24','0','Molinate','DOUBLE','12','稻得壯','ppm','0'
    '990','0',NULL,'2015-08-14 14:45:37','0',NULL,'agricloud-douliu','2015-08-14 14:45:37','0','Monocrotophos','DOUBLE','12','亞素靈','ppm','0'
    '991','0',NULL,'2015-08-14 14:45:50','0',NULL,'agricloud-douliu','2015-08-14 14:45:50','0','Myclobutanil','DOUBLE','12','邁克尼','ppm','0.01'
    '992','0',NULL,'2015-08-14 14:46:09','0',NULL,'agricloud-douliu','2015-08-14 14:46:09','0','Napropamide','DOUBLE','12','滅落脫','ppm','0.01'
    '993','0',NULL,'2015-08-14 14:47:42','0',NULL,'agricloud-douliu','2015-08-14 14:47:42','0','Norflurazon','DOUBLE','12','Norflurazon','ppm','0.2'
    '994','0',NULL,'2015-08-14 14:47:56','0',NULL,'agricloud-douliu','2015-08-14 14:47:56','0','Novaluron','DOUBLE','12','諾伐隆','ppm','0'
    '995','0',NULL,'2015-08-14 14:48:09','0',NULL,'agricloud-douliu','2015-08-14 14:48:09','0','Nuarimol','DOUBLE','12','尼瑞莫','ppm','0.01'
    '996','0',NULL,'2015-08-14 14:48:23','0',NULL,'agricloud-douliu','2015-08-14 14:48:23','0','Omethoate','DOUBLE','12','歐滅松','ppm','1.0'
    '997','0',NULL,'2015-08-14 14:49:01','0',NULL,'agricloud-douliu','2015-08-14 14:49:01','0','Oxadiazon','DOUBLE','12','樂滅草','ppm','0.01'
    '998','0',NULL,'2015-08-14 14:49:15','0',NULL,'agricloud-douliu','2015-08-14 14:49:15','0','Oxadixyl','DOUBLE','12','毆殺斯','ppm','0.01'
    '999','0',NULL,'2015-08-14 14:49:30','0',NULL,'agricloud-douliu','2015-08-14 14:49:30','0','Oxamyl','DOUBLE','12','毆殺滅','ppm','0.01'
    '1000','0',NULL,'2015-08-14 14:49:43','0',NULL,'agricloud-douliu','2015-08-14 14:49:43','0','Oxycarboxin','DOUBLE','12','嘉保信','ppm','0.01'
    '1001','0',NULL,'2015-08-14 14:49:56','0',NULL,'agricloud-douliu','2015-08-14 14:49:56','0','Oxyfluorfen','DOUBLE','12','復祿芬','ppm','0.01'
    '1002','0',NULL,'2015-08-14 14:50:09','0',NULL,'agricloud-douliu','2015-08-14 14:50:09','0','Paclobutrazol','DOUBLE','12','巴克素','ppm','0.01'
    '1003','0',NULL,'2015-08-14 14:50:30','0',NULL,'agricloud-douliu','2015-08-14 14:50:30','0','Parathion','DOUBLE','12','巴拉松','ppm','0'
    '1004','0',NULL,'2015-08-14 14:50:45','0',NULL,'agricloud-douliu','2015-08-14 14:50:45','0','Parathion-methyl','DOUBLE','12','甲基巴拉松','ppm','0.01'
    '1005','0',NULL,'2015-08-14 14:51:19','0',NULL,'agricloud-douliu','2015-08-14 14:51:19','0','Penconazole','DOUBLE','12','平克座','ppm','0.01'
    '1006','0',NULL,'2015-08-14 14:51:33','0',NULL,'agricloud-douliu','2015-08-14 14:51:33','0','Pencycuron','DOUBLE','12','賓克隆','ppm','0.01'
    '1007','0',NULL,'2015-08-14 14:51:51','0',NULL,'agricloud-douliu','2015-08-14 14:51:51','0','Pendimethalin','DOUBLE','12','施得圃','ppm','0.01'
    '1008','0',NULL,'2015-08-14 14:53:32','0',NULL,'agricloud-douliu','2015-08-14 14:53:32','0','Penoxsulam','DOUBLE','12','平速爛','ppm','0'
    '1009','0',NULL,'2015-08-14 15:03:04','0',NULL,'agricloud-douliu','2015-08-14 15:03:04','0','Pentachloroaniline','DOUBLE','12','五氯苯胺','ppm','0'
    '1010','0',NULL,'2015-08-14 15:03:20','0',NULL,'agricloud-douliu','2015-08-14 15:03:20','0','Permethrin','DOUBLE','12','百滅寧','ppm','2.0'
    '1011','0',NULL,'2015-08-14 15:03:36','0',NULL,'agricloud-douliu','2015-08-14 15:03:36','0','Phenthoate','DOUBLE','12','賽達松','ppm','0.2'
    '1012','0',NULL,'2015-08-14 15:03:49','0',NULL,'agricloud-douliu','2015-08-14 15:03:49','0','Phorate','DOUBLE','12','福瑞松','ppm','0'
    '1013','0',NULL,'2015-08-14 15:04:02','0',NULL,'agricloud-douliu','2015-08-14 15:04:02','0','Phosalone','DOUBLE','12','裕必松','ppm','0'
    '1014','0',NULL,'2015-08-14 15:04:27','0',NULL,'agricloud-douliu','2015-08-14 15:04:27','0','Phosmet','DOUBLE','12','益滅松','ppm','1.0'
    '1015','0',NULL,'2015-08-14 15:04:43','0',NULL,'agricloud-douliu','2015-08-14 15:04:43','0','Phosphamidon','DOUBLE','12','福賜米松','ppm','0.5'
    '1016','0',NULL,'2015-08-14 15:04:54','0',NULL,'agricloud-douliu','2015-08-14 15:04:54','0','Phoxim','DOUBLE','12','巴賽松','ppm','0'
    '1017','0',NULL,'2015-08-14 15:05:05','0',NULL,'agricloud-douliu','2015-08-14 15:05:05','0','Pirimicarb','DOUBLE','12','比加普','ppm','0'
    '1018','0',NULL,'2015-08-14 15:05:19','0',NULL,'agricloud-douliu','2015-08-14 15:05:19','0','Pirimiphos-ethyl','DOUBLE','12','乙基亞特松','ppm','0'
    '1019','0',NULL,'2015-08-14 15:05:34','0',NULL,'agricloud-douliu','2015-08-14 15:05:34','0','Pirimiphos-methyl','DOUBLE','12','亞特松','ppm','0.01'
    '1020','0',NULL,'2015-08-14 15:07:57','0',NULL,'agricloud-douliu','2015-08-14 15:07:57','0','Pretilachlor','DOUBLE','12','普拉草','ppm','0.01'
    '1021','0',NULL,'2015-08-14 15:08:12','0',NULL,'agricloud-douliu','2015-08-14 15:08:12','0','Prochloraz','DOUBLE','12','撲克拉','ppm','0.01'
    '1022','0',NULL,'2015-08-14 15:08:27','0',NULL,'agricloud-douliu','2015-08-14 15:08:27','0','Procymidone','DOUBLE','12','撲滅寧','ppm','0.01'
    '1023','0',NULL,'2015-08-14 15:08:44','0',NULL,'agricloud-douliu','2015-08-14 15:08:44','0','Profenophos','DOUBLE','12','佈飛松','ppm','1.0'
    '1024','0',NULL,'2015-08-14 15:10:10','0',NULL,'agricloud-douliu','2015-08-14 15:10:10','0','Promecarb','DOUBLE','12','普滅克','ppm','0'
    '1025','0',NULL,'2015-08-14 15:10:23','0',NULL,'agricloud-douliu','2015-08-14 15:10:23','0','Prometryn','DOUBLE','12','佈滅淨','ppm','0'
    '1026','1',NULL,'2015-08-14 15:10:33','0',NULL,'agricloud-douliu','2015-08-14 15:11:03','0','Propamocarb hydrochloride','DOUBLE','12','普拔克','ppm','0.01'
    '1027','0',NULL,'2015-08-14 15:11:16','0',NULL,'agricloud-douliu','2015-08-14 15:11:16','0','Propanil','DOUBLE','12','除草靈','ppm','0.01'
    '1028','0',NULL,'2015-08-14 15:11:28','0',NULL,'agricloud-douliu','2015-08-14 15:11:28','0','Propaphos','DOUBLE','12','加護松','ppm','0.01'
    '1029','0',NULL,'2015-08-14 15:11:41','0',NULL,'agricloud-douliu','2015-08-14 15:11:41','0','Propargite','DOUBLE','12','毆蟎多','ppm','5.0'
    '1030','0',NULL,'2015-08-14 15:11:53','0',NULL,'agricloud-douliu','2015-08-14 15:11:53','0','Propiconazole','DOUBLE','12','普克利','ppm','0.01'
    '1031','0',NULL,'2015-08-14 15:12:06','0',NULL,'agricloud-douliu','2015-08-14 15:12:06','0','Propoxur','DOUBLE','12','安丹','ppm','0.01'
    '1032','0',NULL,'2015-08-14 15:12:20','0',NULL,'agricloud-douliu','2015-08-14 15:12:20','0','Prothiofos','DOUBLE','12','普硫松','ppm','0'
    '1033','0',NULL,'2015-08-14 15:12:33','0',NULL,'agricloud-douliu','2015-08-14 15:12:33','0','Pymetrozine','DOUBLE','12','派滅淨','ppm','0.01'
    '1034','0',NULL,'2015-08-14 15:12:52','0',NULL,'agricloud-douliu','2015-08-14 15:12:52','0','Pyraclofos','DOUBLE','12','白克松','ppm','0'
    '1035','0',NULL,'2015-08-14 15:13:04','0',NULL,'agricloud-douliu','2015-08-14 15:13:04','0','Pyraclostrobin','DOUBLE','12','百克敏','ppm','1.0'
    '1036','0',NULL,'2015-08-14 15:13:16','0',NULL,'agricloud-douliu','2015-08-14 15:13:16','0','Pyrazophos','DOUBLE','12','白粉松','ppm','0.01'
    '1037','0',NULL,'2015-08-14 15:13:29','0',NULL,'agricloud-douliu','2015-08-14 15:13:29','0','Pyridaben','DOUBLE','12','畢達本','ppm','2.0'
    '1038','0',NULL,'2015-08-14 15:13:44','0',NULL,'agricloud-douliu','2015-08-14 15:13:44','0','Pyridaphenthion','DOUBLE','12','必芬松','ppm','2.0'
    '1039','0',NULL,'2015-08-14 15:13:55','0',NULL,'agricloud-douliu','2015-08-14 15:13:55','0','Pyridate','DOUBLE','12','必汰草','ppm','0'
    '1040','0',NULL,'2015-08-14 15:14:08','0',NULL,'agricloud-douliu','2015-08-14 15:14:08','0','Pyrifenox','DOUBLE','12','比芬諾','ppm','0.01'
    '1041','0',NULL,'2015-08-14 15:14:24','0',NULL,'agricloud-douliu','2015-08-14 15:14:24','0','Pyrimethanil','DOUBLE','12','派美尼','ppm','7.0'
    '1042','0',NULL,'2015-08-14 15:14:38','0',NULL,'agricloud-douliu','2015-08-14 15:14:38','0','Pyrimidifen','DOUBLE','12','畢汰芬','ppm','0.5'
    '1043','0',NULL,'2015-08-14 15:16:15','0',NULL,'agricloud-douliu','2015-08-14 15:16:15','0','Pyriproxyfen','DOUBLE','12','百利普芬','ppm','0.01'
    '1044','0',NULL,'2015-08-14 15:16:30','0',NULL,'agricloud-douliu','2015-08-14 15:16:30','0','Pyroquilon','DOUBLE','12','百快隆','ppm','0.01'
    '1045','0',NULL,'2015-08-14 15:17:09','0',NULL,'agricloud-douliu','2015-08-14 15:17:09','0','Quinalphos','DOUBLE','12','拜裕松','ppm','0'
    '1046','0',NULL,'2015-08-14 15:17:23','0',NULL,'agricloud-douliu','2015-08-14 15:17:23','0','Quinoxyfen','DOUBLE','12','快諾芬','ppm','0.01'
    '1047','0',NULL,'2015-08-14 15:17:37','0',NULL,'agricloud-douliu','2015-08-14 15:17:37','0','Quintozene(PCNB)','DOUBLE','12','五氯硝笨','ppm','0'
    '1048','0',NULL,'2015-08-14 15:17:50','0',NULL,'agricloud-douliu','2015-08-14 15:17:50','0','Quizalofop-ethyl','DOUBLE','12','快伏草','ppm','0.01'
    '1049','0',NULL,'2015-08-14 15:18:06','0',NULL,'agricloud-douliu','2015-08-14 15:18:06','0','Salithion','DOUBLE','12','殺力松','ppm','0'
    '1050','0',NULL,'2015-08-14 15:18:21','0',NULL,'agricloud-douliu','2015-08-14 15:18:21','0','Silafluofen','DOUBLE','12','矽護芬','ppm','0'
    '1051','0',NULL,'2015-08-14 15:18:37','0',NULL,'agricloud-douliu','2015-08-14 15:18:37','0','Simazine','DOUBLE','12','草滅淨','ppm','0'
    '1052','0',NULL,'2015-08-14 15:18:53','0',NULL,'agricloud-douliu','2015-08-14 15:18:53','0','Spinetoram J','DOUBLE','12','賜諾特 J','ppm','0.2'
    '1053','0',NULL,'2015-08-14 15:19:06','0',NULL,'agricloud-douliu','2015-08-14 15:19:06','0','Spinetoram L','DOUBLE','12','賜諾特 L','ppm','0.2'
    '1054','0',NULL,'2015-08-14 15:19:20','0',NULL,'agricloud-douliu','2015-08-14 15:19:20','0','Spinosad A','DOUBLE','12','賜諾殺 A','ppm','0.3'
    '1055','0',NULL,'2015-08-14 15:19:47','0',NULL,'agricloud-douliu','2015-08-14 15:19:47','0','Spinosad D','DOUBLE','12','賜諾殺 D','ppm','0.3'
    '1056','0',NULL,'2015-08-14 15:20:10','0',NULL,'agricloud-douliu','2015-08-14 15:20:10','0','Spirodiclofen','DOUBLE','12','賜派芬','ppm','0.5'
    '1057','0',NULL,'2015-08-14 15:20:25','0',NULL,'agricloud-douliu','2015-08-14 15:20:25','0','Spirotetramat','DOUBLE','12','賜派滅','ppm','0.5'
    '1058','0',NULL,'2015-08-14 15:20:39','0',NULL,'agricloud-douliu','2015-08-14 15:20:39','0','Tebuconazole','DOUBLE','12','得克利','ppm','0.01'
    '1059','0',NULL,'2015-08-14 15:20:59','0',NULL,'agricloud-douliu','2015-08-14 15:20:59','0','Tebufenozide','DOUBLE','12','得芬諾','ppm','1.5'
    '1060','0',NULL,'2015-08-14 15:21:15','0',NULL,'agricloud-douliu','2015-08-14 15:21:15','0','Tebufenpyrad','DOUBLE','12','得芬瑞','ppm','0.5'
    '1061','0',NULL,'2015-08-14 15:21:27','0',NULL,'agricloud-douliu','2015-08-14 15:21:27','0','Teflubenzuron','DOUBLE','12','得福隆','ppm','0.01'
    '1062','0',NULL,'2015-08-14 15:21:41','0',NULL,'agricloud-douliu','2015-08-14 15:21:41','0','Tepraloxydim','DOUBLE','12','得殺草','ppm','0.01'
    '1063','0',NULL,'2015-08-14 15:21:54','0',NULL,'agricloud-douliu','2015-08-14 15:21:54','0','Terbufos','DOUBLE','12','托福松','ppm','0.01'
    '1064','0',NULL,'2015-08-14 15:22:07','0',NULL,'agricloud-douliu','2015-08-14 15:22:07','0','Tetraconazole','DOUBLE','12','四克利','ppm','0.01'
    '1065','0',NULL,'2015-08-14 15:22:39','0',NULL,'agricloud-douliu','2015-08-14 15:22:39','0','Tetradifon','DOUBLE','12','得脫蟎','ppm','0'
    '1066','0',NULL,'2015-08-14 15:22:57','0',NULL,'agricloud-douliu','2015-08-14 15:22:57','0','Tetramethrin','DOUBLE','12','治滅寧','ppm','0.01'
    '1067','0',NULL,'2015-08-14 15:23:08','0',NULL,'agricloud-douliu','2015-08-14 15:23:08','0','Thenylchlor','DOUBLE','12','欣克草','ppm','0'
    '1068','0',NULL,'2015-08-14 15:23:21','0',NULL,'agricloud-douliu','2015-08-14 15:23:21','0','Thiabendazole','DOUBLE','12','腐絕','ppm','10.0'
    '1069','0',NULL,'2015-08-14 15:23:34','0',NULL,'agricloud-douliu','2015-08-14 15:23:34','0','Thiacloprid','DOUBLE','12','賽果培','ppm','0.01'
    '1070','0',NULL,'2015-08-14 15:23:48','0',NULL,'agricloud-douliu','2015-08-14 15:23:48','0','Thiamethoxam','DOUBLE','12','賽速安','ppm','0.01'
    '1071','0',NULL,'2015-08-14 15:23:58','0',NULL,'agricloud-douliu','2015-08-14 15:23:58','0','Thifluzamide','DOUBLE','12','賽伏滅','ppm','0'
    '1072','0',NULL,'2015-08-14 15:24:12','0',NULL,'agricloud-douliu','2015-08-14 15:24:12','0','Thiobencarb','DOUBLE','12','殺丹','ppm','0.01'
    '1073','0',NULL,'2015-08-14 15:24:25','0',NULL,'agricloud-douliu','2015-08-14 15:24:25','0','Thiodicarb','DOUBLE','12','硫敵克','ppm','0.01'
    '1074','0',NULL,'2015-08-14 15:24:37','0',NULL,'agricloud-douliu','2015-08-14 15:24:37','0','Tolclofos-methyl','DOUBLE','12','脫克松','ppm','0'
    '1075','0',NULL,'2015-08-14 15:24:53','0',NULL,'agricloud-douliu','2015-08-14 15:24:53','0','Tolfenpyrad','DOUBLE','12','脫芬瑞','ppm','0.01'
    '1076','0',NULL,'2015-08-14 15:25:07','0',NULL,'agricloud-douliu','2015-08-14 15:25:07','0','Triadimefon','DOUBLE','12','三泰芬','ppm','0.01'
    '1077','0',NULL,'2015-08-14 15:25:20','0',NULL,'agricloud-douliu','2015-08-14 15:25:20','0','Triadimenol','DOUBLE','12','三泰隆','ppm','0.01'
    '1078','0',NULL,'2015-08-14 15:25:32','0',NULL,'agricloud-douliu','2015-08-14 15:25:32','0','Triazophos','DOUBLE','12','三落松','ppm','0.01'
    '1079','0',NULL,'2015-08-14 15:25:45','0',NULL,'agricloud-douliu','2015-08-14 15:25:45','0','Trichlorfon','DOUBLE','12','三氯松','ppm','0.5'
    '1080','0',NULL,'2015-08-14 15:25:59','0',NULL,'agricloud-douliu','2015-08-14 15:25:59','0','Tricyclazole','DOUBLE','12','三賽唑','ppm','0'
    '1081','0',NULL,'2015-08-14 15:26:13','0',NULL,'agricloud-douliu','2015-08-14 15:26:13','0','Tridiphane','DOUBLE','12','三地芬','ppm','0'
    '1082','0',NULL,'2015-08-14 15:26:26','0',NULL,'agricloud-douliu','2015-08-14 15:26:26','0','Trifloxystrobin','DOUBLE','12','三氟敏','ppm','0.5'
    '1083','0',NULL,'2015-08-14 15:26:41','0',NULL,'agricloud-douliu','2015-08-14 15:26:41','0','Triflumizole','DOUBLE','12','賽福座','ppm','0.01'
    '1084','0',NULL,'2015-08-14 15:27:42','0',NULL,'agricloud-douliu','2015-08-14 15:27:42','0','Trifluralin','DOUBLE','12','三福林','ppm','0.05'
    '1085','0',NULL,'2015-08-14 15:27:55','0',NULL,'agricloud-douliu','2015-08-14 15:27:55','0','Triforine','DOUBLE','12','賽福寧','ppm','0.01'
    '1086','0',NULL,'2015-08-14 15:28:12','0',NULL,'agricloud-douliu','2015-08-14 15:28:12','0','Vamidothion','DOUBLE','12','繁米松','ppm','0.01'
    '1087','0',NULL,'2015-08-14 15:28:24','0',NULL,'agricloud-douliu','2015-08-14 15:28:24','0','Vinclozolin','DOUBLE','12','免克寧','ppm','0.01'
    '1088','0',NULL,'2015-08-14 15:28:34','0',NULL,'agricloud-douliu','2015-08-14 15:28:34','0','XMC','DOUBLE','12','滅克蝨','ppm','0'
    '1089','0',NULL,'2015-08-14 15:28:45','0',NULL,'agricloud-douliu','2015-08-14 15:28:45','0','Xylylcarb','DOUBLE','12','滅爾蝨','ppm','0'
    '1090','0',NULL,'2015-08-14 15:28:55','0',NULL,'agricloud-douliu','2015-08-14 15:28:55','0','Zoxamide','DOUBLE','12','座賽胺','ppm','0'
    '1091','1',NULL,'2015-08-17 10:49:07',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:04',NULL,'R01','DOUBLE','12','水分','g',NULL
    '1092','1',NULL,'2015-08-17 10:49:39',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:08',NULL,'R02','DOUBLE','12','蛋白質','g',NULL
    '1093','1',NULL,'2015-08-17 10:49:58',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:12',NULL,'R03','DOUBLE','12','脂肪','g',NULL
    '1094','1',NULL,'2015-08-17 10:50:14',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:17',NULL,'R04','DOUBLE','12','碳水化合物','g',NULL
    '1095','1',NULL,'2015-08-17 10:50:49',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:23',NULL,'R05','DOUBLE','12','熱量','Kcal',NULL
    '1096','1',NULL,'2015-08-17 10:51:02',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:27',NULL,'R06','DOUBLE','12','粗纖維','g',NULL
    '1097','1',NULL,'2015-08-17 10:51:26',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:32',NULL,'R07','DOUBLE','12','鈣','mg',NULL
    '1098','1',NULL,'2015-08-17 10:51:45',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:37',NULL,'R08','DOUBLE','12','磷','mg',NULL
    '1099','1',NULL,'2015-08-17 10:52:02',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:43',NULL,'R09','DOUBLE','12','鐵','mg',NULL
    '1100','1',NULL,'2015-08-17 10:52:20',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:47',NULL,'R10','DOUBLE','12','胡蘿蔔素','mg',NULL
    '1101','1',NULL,'2015-08-17 10:52:38',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:51',NULL,'R11','DOUBLE','12','硫酸素','mg',NULL
    '1102','1',NULL,'2015-08-17 10:52:55',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:01:55',NULL,'R12','DOUBLE','12','核黃素','mg',NULL
    '1103','1',NULL,'2015-08-17 10:53:14',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:02:00',NULL,'R13','DOUBLE','12','尼克酸','mg',NULL
    '1104','1',NULL,'2015-08-17 10:53:27',NULL,'每100g含量','agricloud-douliu','2015-08-17 11:02:04',NULL,'R14','DOUBLE','12','抗壞血酸','mg',NULL
    '1111','3',NULL,'2015-08-19 13:49:45',NULL,'每100g含量','agricloud-douliu','2015-08-19 14:04:25',NULL,'R15','DOUBLE','12','膳食纖維','g',NULL
    '1112','1',NULL,'2015-08-19 13:51:23',NULL,'每100g含量','agricloud-douliu','2015-08-19 14:04:33',NULL,'R16','DOUBLE','12','菸鹼酸','mg',NULL
    '1113','1',NULL,'2015-08-19 13:51:51',NULL,'每100g含量','agricloud-douliu','2015-08-19 14:04:39',NULL,'R17','DOUBLE','12','維生素C','mg',NULL
    '1114','1',NULL,'2015-08-19 13:52:30',NULL,'每100g含量','agricloud-douliu','2015-08-19 14:04:46',NULL,'R18','DOUBLE','12','鉀','mg',NULL
    '1115','1',NULL,'2015-08-19 13:52:48',NULL,'每100g含量','agricloud-douliu','2015-08-19 14:04:53',NULL,'R19','DOUBLE','12','鎂','mg',NULL
    '1116','2',NULL,'2015-08-22 02:15:03',NULL,'每100g含量','yanting','2015-08-22 02:45:12',NULL,'R20','DOUBLE','12','維生素B1','mg',NULL
    '1117','1',NULL,'2015-08-22 02:15:28',NULL,'每100g含量','yanting','2015-08-22 02:45:17',NULL,'R21','DOUBLE','12','維生素B2','mg',NULL
    '1118','1',NULL,'2015-08-22 02:16:06',NULL,'每100g含量','yanting','2015-08-22 02:45:23',NULL,'R22','DOUBLE','12','維生素B6','mg',NULL
    '1119','1','lofangyu','2015-10-26 17:11:07',NULL,'每100g含量','lofangyu','2015-10-26 17:11:24',NULL,'R23','DOUBLE','12','維生素E','mg',NULL

    */
}