import common.*
import foodprint.*
import foodprint.authority.*
import foodprint.common.*
import foodprint.erp.*
import foodprint.erp.ChangeOrder.*
import foodprint.sft.*
import foodprint.sft.pull.*
import grails.converters.JSON

import grails.plugin.springsecurity.SecurityFilterPosition 
import grails.plugin.springsecurity.SpringSecurityUtils 

class BootStrap {
    def convertService
    def grailsApplication
    def messageSource
    def defaultDataService
    def dateService
    def testMFGService

    def concurrentSessionController 
    def securityContextPersistenceFilter 

    def init = { servletContext ->

        // 預設時區，避免 json 轉換自動扣除 8 小時(台灣 +8:00)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        jsonParseDefine()

        // 同一帳號不可同時登錄，後登錄的會把先前的session踢出。
        securityContextPersistenceFilter.forceEagerSessionCreation = true 
        SpringSecurityUtils.clientRegisterFilter('concurrentSessionFilter', SecurityFilterPosition.CONCURRENT_SESSION_FILTER) 

        def siteGroup = SiteGroup.findByName('default') ?: new SiteGroup(name: 'default', title: 'default').save(failOnError: true, flush: true)

        environments {

            development {

                // acticationCode
                // 10008a5e-c5d3-42d4-a8b3-61099671452f //365天/User/3人
                // 41c6c3e5-e4a0-41b4-98f9-9f0df9339e8e //過期/User/3人
                // 15fd2da2-dd5a-446c-bd55-70047298ebed //permanent/User/10000000人
                // 59477ef6-2c5a-4fb9-aab5-7d88307823e0 //365天/Admin/3人
                // bf9765db-8c69-4e9e-aa6f-6e008c111eed //過期/Admin/3人
                // 485bf424-86f2-4e24-8988-45fba90cedf1 //permanent/Admin/10000000人
                // fce481ac-1c43-4064-ac1c-0028d9580842 //365天/5 batch
                // 6ef9e8f8-ae33-4876-8d61-c9ceda208192 //過期/5 batch
                // b2fdbfd9-c465-4600-a794-9f3bdd58bce6 //permanent/1000000 batch

                grailsApplication.config.grails.dev.user.activationCode = "15fd2da2-dd5a-446c-bd55-70047298ebeds"
                grailsApplication.config.grails.dev.admin.activationCode = "485bf424-86f2-4e24-8988-45fba90cedf1"
                grailsApplication.config.grails.dev.site.activationCode = "b2fdbfd9-c465-4600-a794-9f3bdd58bce6"

                
                boolean generateTestData = false
                // def specifiedData = ["agri", "mfg"]
                def specifiedData = ["agri"]
                boolean createLoopData = false
                boolean useAuthority = grailsApplication.config.grails.useAuthority //是否啟用權限控管

                if (useAuthority) {
                    defaultDataService.generateRoleData()
                }
                if (generateTestData) {
                    def domain = [
                        ItemCategoryLayer1,
                        ItemCategoryLayer2,
                        Manufacturer,
                        Brand,
                        Factory,
                        Workstation,
                        Item,
                        ItemStage,
                        ItemRegisteredNum,
                        Supplier,
                        Customer,
                        Employee,
                        OperationCategoryLayer1,
                        Operation,
                        ItemRoute,
                        BillOfMaterial,
                        BillOfMaterialDet,
                        Warehouse,
                        WarehouseLocation,
                        TypeName,
                        CustomerOrder,
                        CustomerOrderDet,
                        ManufactureOrder,
                        ManufactureOrderDet,
                        Batch,
                        BatchOperation,
                        Inventory,
                        InventoryDetail,
                        InventoryTransactionSheet,
                        InventoryTransactionSheetDet,
                        PurchaseSheet,
                        PurchaseSheetDet,
                        PurchaseReturnSheet,
                        PurchaseReturnSheetDet,
                        MaterialSheet,
                        MaterialSheetDet,
                        MaterialSheetDetCustomize,
                        MaterialSheetDetCustomizeDet,
                        MaterialReturnSheet,
                        MaterialReturnSheetDet,
                        StockInSheet,
                        StockInSheetDet,
                        OutSrcPurchaseSheet,
                        OutSrcPurchaseSheetDet,
                        OutSrcPurchaseReturnSheet,
                        OutSrcPurchaseReturnSheetDet,
                        SaleSheet,
                        SaleSheetDet,
                        SaleReturnSheet,
                        SaleReturnSheetDet,
                        Param,
                        Report,
                        ReportParam,
                        BatchReportDet,

                        //SFT test
                        BatchBox,
                        //BatchBoxDet,
                        DeliveryKanban,
                        TransferOrder,
                        TransferOrderDet
                    ]

                    def testService = new TestService(grailsApplication: grailsApplication, messageSource: messageSource, dateService: dateService, testMFGService: testMFGService)
                    testService.createTestData(domain, specifiedData, createLoopData, false)
                }

                
                def site = Site.findByName('site1') ?: new Site(name: 'site1', title: 'site1', siteGroup: siteGroup, activationCode: grailsApplication.config.grails.dev.site.activationCode).save(failOnError: true, flush: true)

                // def role1 = Role.findOrSaveByAuthority('ROLE_ADMIN')

                def user = User.findByUsername('admin') ?: new User(userType: UserType.ADMIN, username: 'admin', fullName:'admin', password: 'admin', lastLoginSite: site, enabled: true, siteGroup: site.siteGroup, activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError:true, flush:true)
                
                UserSite.create(user, site)
                // UserRole.create(user, role1)

                if (useAuthority) {
                    RoleGroup.getAll().each { roleGroup ->
                        if (roleGroup.name.startsWith("DEFAULT_"))
                            UserRoleGroup.create(user, roleGroup)
                    }
                }
                
            }

            production {
                // 產生預設權限資料
                defaultDataService.generateRoleData()
            }
        }
    }
    def destroy = {

    }



    private jsonParseDefine() {
        JSON.registerObjectMarshaller(Role) {
            convertService.roleParseJson(it)
        }
        JSON.registerObjectMarshaller(RoleGroup) {
            convertService.roleGroupParseJson(it)
        }
        JSON.registerObjectMarshaller(RoleGroupRole) {
            convertService.roleGroupRoleParseJson(it)
        }
        JSON.registerObjectMarshaller(SiteGroup) {
            convertService.siteGroupParseJson(it)
        }
        JSON.registerObjectMarshaller(Site) {
            convertService.siteParseJson(it)
        }
        JSON.registerObjectMarshaller(UserType) {
            convertService.userTypeParseJson(it)
        }
        JSON.registerObjectMarshaller(User) {
            convertService.userParseJson(it)
        }
        JSON.registerObjectMarshaller(UserRoleGroup) {
            convertService.userRoleGroupParseJson(it)
        }
        JSON.registerObjectMarshaller(UserSite) {
            convertService.userSiteParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemCategoryLayer1) {
            convertService.itemCategoryLayer1ParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemCategoryLayer2) {
            convertService.itemCategoryLayer2ParseJson(it)
        }
        JSON.registerObjectMarshaller(Brand) {
            convertService.brandParseJson(it)
        }
        JSON.registerObjectMarshaller(Item) {
            convertService.itemParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemStage) {
            convertService.itemStageParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemRegisteredNum) {
            convertService.itemRegisteredNumParseJson(it)
        }
        JSON.registerObjectMarshaller(Factory) {
            convertService.factoryParseJson(it)
        }
        JSON.registerObjectMarshaller(Customer) {
            convertService.customerParseJson(it)
        }
        JSON.registerObjectMarshaller(Manufacturer) {
            convertService.manufacturerParseJson(it)
        }
        JSON.registerObjectMarshaller(Supplier) {
            convertService.supplierParseJson(it)
        }
        JSON.registerObjectMarshaller(Workstation) {
            convertService.workstationParseJson(it)
        }
        JSON.registerObjectMarshaller(Employee) {
            convertService.employeeParseJson(it)
        }
        JSON.registerObjectMarshaller(OperationCategoryLayer1) {
            convertService.operationCategoryLayer1ParseJson(it)
        }
        JSON.registerObjectMarshaller(Operation) {
            convertService.operationParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemRoute) {
            convertService.itemRouteParseJson(it)
        }
        JSON.registerObjectMarshaller(Batch) {
            convertService.batchParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchOperation) {
            convertService.batchOperationParseJson(it)
        }
        JSON.registerObjectMarshaller(BillOfMaterial) {
            convertService.billOfMaterialParseJson(it)
        }
        JSON.registerObjectMarshaller(BillOfMaterialDet) {
            convertService.billOfMaterialDetParseJson(it)
        }
        JSON.registerObjectMarshaller(Warehouse) {
            convertService.warehouseParseJson(it)
        }
        JSON.registerObjectMarshaller(WarehouseLocation) {
            convertService.warehouseLocationParseJson(it)
        }
        JSON.registerObjectMarshaller(Inventory) {
            convertService.inventoryParseJson(it)
        }
        JSON.registerObjectMarshaller(InventoryDetail) {
            convertService.inventoryDetailParseJson(it)
        }
        JSON.registerObjectMarshaller(TypeName) {
            convertService.typeNameParseJson(it)
        }
        // sheets
        JSON.registerObjectMarshaller(InventoryTransactionSheet) {
            convertService.inventoryTransactionSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(InventoryTransactionSheetDet) {
            convertService.inventoryTransactionSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(CustomerOrder) {
            convertService.customerOrderParseJson(it)
        }
        JSON.registerObjectMarshaller(CustomerOrderDet) {
            convertService.customerOrderDetParseJson(it)
        }
        JSON.registerObjectMarshaller(ManufactureOrder) {
            convertService.manufactureOrderParseJson(it)
        }
        JSON.registerObjectMarshaller(ManufactureOrderChangeOrder) {
            convertService.manufactureOrderChangeOrderParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheet) {
            convertService.materialSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheetDet) {
            convertService.materialSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheetDetCustomize) {
            convertService.materialSheetDetCustomizeParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheetDetCustomizeDet) {
            convertService.materialSheetDetCustomizeDetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseSheet) {
            convertService.purchaseSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseSheetDet) {
            convertService.purchaseSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(StockInSheet) {
            convertService.stockInSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(StockInSheetDet) {
            convertService.stockInSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseSheet) {
            convertService.outSrcPurchaseSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseSheetDet) {
            convertService.outSrcPurchaseSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(SaleSheet) {
            convertService.saleSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(SaleSheetDet) {
            convertService.saleSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialReturnSheet) {
            convertService.materialReturnSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialReturnSheetDet) {
            convertService.materialReturnSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseReturnSheet) {
            convertService.purchaseReturnSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseReturnSheetDet) {
            convertService.purchaseReturnSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseReturnSheet) {
            convertService.outSrcPurchaseReturnSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseReturnSheetDet) {
            convertService.outSrcPurchaseReturnSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(SaleReturnSheet) {
            convertService.saleReturnSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(SaleReturnSheetDet) {
            convertService.saleReturnSheetDetParseJson(it)
        }
        // sft
        JSON.registerObjectMarshaller(Param) {
            convertService.paramParseJson(it)
        }
        JSON.registerObjectMarshaller(Report) {
            convertService.reportParseJson(it)
        }
        JSON.registerObjectMarshaller(ReportParam) {
            convertService.reportParamParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchReportDet) {
            convertService.batchReportDetParseJson(it)
        }
        JSON.registerObjectMarshaller(DeliveryKanban) {
            convertService.deliveryKanbanParseJson(it)
        }
        JSON.registerObjectMarshaller(ManufactureOrderDet) {
            convertService.manufactureOrderDetParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchRealtimeRecord) {
            convertService.batchRealtimeRecordParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchBox) {
            convertService.batchBoxParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchBoxDet) {
            convertService.batchBoxDetParseJson(it)
        }
    }


}
