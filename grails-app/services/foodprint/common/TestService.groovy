package foodprint.common

import foodprint.authority.*
import foodprint.erp.*
import foodprint.erp.ChangeOrder.*
import foodprint.erp.ChangeRecord.*
import foodprint.sft.*
import foodprint.sft.pull.*

class TestService {

    def grailsApplication
    def messageSource
    def dateService
    def testMFGService

    def siteGroup1, site1, user1

    def itemCategoryLayer11, itemCategoryLayer12, itemCategoryLayer1Other
    def itemCategoryLayer211, itemCategoryLayer212, itemCategoryLayer221, itemCategoryLayer222

    def productBrand1, materialBrand1, materialBrand2

    def manufacturerTAIWAN, manufacturerJAPAN, manufacturerHONGKONG, manufacturerMEXICO, manufacturerCHINA,
        manufacturerITALY, manufacturerUNITEDKINGDOM, manufacturerInspect

    def product1ProductBrand1,
        semiProduct1ProductBrand1, semiProduct2ProductBrand1,
        material1MaterialBrand1, material2MaterialBrand1, material2MaterialBrand2, material3MaterialBrand1,
        material4MaterialBrand1, material5MaterialBrand2, material6MaterialBrand2, material7MaterialBrand2
    
    def factory1, factory2
    def customer1, customer2
    def operator1, operator2, accountant1
    def supplier1, supplier2, supplierInspect
    def workstation11, workstation12, workstation21
    def operationCategoryLayer11, operationCategoryLayer12, operationCategoryLayer13, operationCategoryLayer1Other
    def operation1, operation2, operation3, operation4, operationInspect

    def product1ProductBrand1Stage1, product1ProductBrand1Stage2, product1ProductBrand1Stage3, product1ProductBrand1Stage4

    def semiProduct1ProductBrand1RigisteredNumTW,
        material1MaterialBrand1RigisteredNumTW, material1MaterialBrand1RigisteredNumJP, material1MaterialBrand1RigisteredNumHK,
        material2MaterialBrand1RigisteredNumTW, material2MaterialBrand2RigisteredNumTW, material3MaterialBrand1RigisteredNumTW,
        material5MaterialBrand2RigisteredNumTW, material5MaterialBrand2RigisteredNumUK, material5MaterialBrand2RigisteredNumMC,
        material6MaterialBrand2RigisteredNumTW, material7MaterialBrand2RigisteredNumTW

    def materialWarehouse11, productWarehouse11, materialWarehouse21, productWarehouse21

    def materialWarehouseLocation111, materialWarehouseLocation112, productWarehouseLocation111, productWarehouseLocation112,
        materialWarehouseLocation211, materialWarehouseLocation212, productWarehouseLocation211, productWarehouseLocation212

    def typeNameTransactionSTORAGE, typeNameReturnTransactionSTORAGE,
        typeNameTransactionSALE, typeNameTransactionREQUISITION, typeNameTransactionADJUSTMENT,
        typeNameCustomerOrder, typeNameSaleSheet, typeNameSaleReturnSheet,
        typeNamePurchaseSheet, typeNamePurchaseReturnSheet,
        typeNameManufactureOrder, typeNameOutSrcManufactureOrder,
        // typeNameManufactureOrderRework,
        typeNameMaterialSheet, typeNameMaterialReturnSheet,
        typeNameOutSrcMaterialSheet, typeNameOutSrcMaterialReturnSheet,
        typeNameStockInSheet, typeNameOutSrcPurchaseSheet, typeNameOutSrcPurchaseReturnSheet,
        typeNameTransferOrderRELEASE, typeNameTransferOrderTRANSFER, typeNameTransferOrderSTORAGE,
        typeNameOutSrcTransferOrderRELEASE, typeNameOutSrcTransferOrderTRANSFER, typeNameOutSrcTransferOrderSTORAGE
        // typeNameWorkReport,

    def customerOrder1, customerOrder2
    def customerOrderDet11
    def manufactureOrder1, manufactureOrder2, manufactureOrder3, manufactureOrder4,
        manufactureOrder5, manufactureOrder6, manufactureOrderChangeOrder
    
    def product1ProductBrand1Batch1, product1ProductBrand1Batch2, product1ProductBrand1Batch3, product1ProductBrand1Batch4,
        semiProduct1ProductBrand1Batch1, semiProduct1ProductBrand1Batch2,
        material1MaterialBrand1Batch1, material2MaterialBrand1Batch1, material2MaterialBrand2Batch1,
        material3MaterialBrand1Batch1, material4MaterialBrand1Batch1, material5MaterialBrand2Batch1,
        material6MaterialBrand2Batch1, material7MaterialBrand2Batch1,
        transactionBatch11, transactionBatch21, transactionBatch31, transactionBatch41

    def product1ProductBrand1ItemRoute1, product1ProductBrand1ItemRoute2, product1ProductBrand1ItemRoute3,
        semiProduct1ProductBrand1ItemRoute1, semiProduct1ProductBrand1ItemRoute2
    def product1ProductBrand1Batch1Operation1, product1ProductBrand1Batch1Operation2,
        product1ProductBrand1Batch1Operation3, product1ProductBrand1Batch1Operation4,
        product1ProductBrand1Batch1Operation5,
        semiProduct1ProductBrand1Batch1Operation1, semiProduct1ProductBrand1Batch1Operation2,
        product1ProductBrand1Batch3Operation1, product1ProductBrand1Batch3Operation2,
        product1ProductBrand1Batch3Operation3, product1ProductBrand1Batch3Operation4,
        product1ProductBrand1Batch3Operation5,
        semiProduct1ProductBrand1Batch2Operation1, semiProduct1ProductBrand1Batch2Operation2

    def inventory1, inventory2, inventory3, inventory4, inventory5, inventory6,
        inventory7, inventory8, inventory9

    def inventoryDetail11, inventoryDetail21, inventoryDetail31, inventoryDetail41, inventoryDetail51,
        inventoryDetail61, inventoryDetail71, inventoryDetail81, inventoryDetail91, inventoryDetail101,
        inventoryDetail501, inventoryDetail502, inventoryDetail503, inventoryDetail504
    
    def inventoryTransactionSheet1, inventoryTransactionSheet2, inventoryTransactionSheet3,
        inventoryTransactionSheet4//, inventoryTransactionSheet5
    def inventoryTransactionSheetDet11, inventoryTransactionSheetDet21, inventoryTransactionSheetDet31,
        inventoryTransactionSheetDet41

    def purchaseSheet1, purchaseSheet2
    def purchaseSheetDet11, purchaseSheetDet12, purchaseSheetDet13, purchaseSheetDet14,
        purchaseSheetDet21, purchaseSheetDet22, purchaseSheetDet23
    
    def materialSheet1, materialSheet2, materialSheet3
    def materialSheetDet11, materialSheetDet12, materialSheetDet13,
        materialSheetDet21, materialSheetDet22, materialSheetDet23, materialSheetDet24, materialSheetDet25,
        materialSheetDet31, materialSheetDet32, materialSheetDet33
    
    def materialSheetDetCustomize1,  material5MaterialSheetDetCustomize1,
        itemCategoryLayer12MaterialSheetDetCustomize1, itemCategoryLayer221MaterialSheetDetCustomize1,
        material6ItemCategoryLayer12ItemCategoryLayer221MaterialSheetDetCustomize1

    def materialSheetDet31MaterialSheetDetCustomizeDet1,
        materialSheetDet31Material5MaterialSheetDetCustomize1,
        materialSheetDet31itemCategoryLayer12MaterialSheetDetCustomize1,
        materialSheetDet32itemCategoryLayer221MaterialSheetDetCustomize1,
        materialSheetDet32material6ItemCategoryLayer12ItemCategoryLayer221MaterialSheetDetCustomize1

    def stockInSheet1, stockInSheet2
    def stockInSheetDet11, stockInSheetDet21
    def outSrcPurchaseSheet1
    def outSrcPurchaseSheetDet11

    def saleSheet1, saleSheet2
    def saleSheetDet11, saleSheetDet21
    def purchaseReturnSheet1, purchaseReturnSheet2
    def purchaseReturnSheetDet13, purchaseReturnSheetDet14, purchaseReturnSheetDet23
    def materialReturnSheet1, materialReturnSheet2, materialReturnSheet3
    def materialReturnSheetDet25, materialReturnSheetDet31, materialReturnSheetDet32, materialReturnSheetDet33
    def outSrcPurchaseReturnSheet1
    def outSrcPurchaseReturnSheetDet11
    def saleReturnSheet1, saleReturnSheet2
    def saleReturnSheetDet11

    def paramInspect1, paramInspect2, paramInspect3, paramInspect4, paramInspect5,
        paramOther1, paramNutrition1, paramNutrition2, paramNutrition3
    def reportInspect1, reportOther1, reportNutrition1
    def reportParamInspect1, reportParamInspect2, reportParamInspect3, reportParamInspect4, reportParamInspect5,
        reportParamOther1, reportParamNutrition1, reportParamNutrition2, reportParamNutrition3
    def brdInspect1, brdInspect2, brdOther1, brdNutrition1

    def loopsite1,
        loopbrand1,
        loopitemcategorylayer11, loopitemcategorylayer211,
        loopitem1, loopitem2,
        loopfactory1,
        loopcustomer1,
        loopoperator1,
        loopmanufacturer1,
        loopmanufacturer_issupplier1,
        loopsupplier1,
        loopsupplier_ismanufacturer1,
        loopworkstation1,
        loopoperationcategory1,
        loopoperation1,
        loopwarehouse1,
        loopwarehouselocation1,
        looptypenameTRANSACTION1, looptypenameCUSTOMERORDER1,
        looptypenameSALESHEET1, looptypenameSALERETURNSHEET1,
        looptypenamePURCHASESHEET1, looptypenamePURCHASERETURNSHEET1,
        looptypenameMANUFACTUREORDER1, looptypenameOUTSRCMANUFACTUREORDER1,
        looptypenameMATERIALSHEET1, looptypenameMATERIALRETURNSHEET1,
        looptypenameOUTSRCMATERIALSHEET1, looptypenameOUTSRCMATERIALRETURNSHEET1,
        looptypenameSTOCKINSHEET1,
        looptypenameOUTSRCPURCHASESHEET1, looptypenameOUTSRCPURCHASERETURNSHEET1,
        loopcustomerorder1, loopcustomerorderdet1,
        loopmanufactureorder1, loopmanufactureorder51,
        loopbatch1, /* use for inventoryTransactionSheetDet*/loopbatch2, loopbatch51, loopbatch52,
        loopbatchoperation1,
        loopinventory1, loopinventorydetail1,
        loopinventorytransactionsheet1, loopinventorytransactionsheetdet1,
        looppurchasesheet1, looppurchasesheetdet1,
        loopmaterialsheet1, loopmaterialsheetdet1,
        loopmaterialsheetdetcustomize1,
        loopstockinsheet1, loopstockinsheetdet1,
        loopoutsrcpurchasesheet1, loopoutsrcpurchasesheetdet1,
        loopsalesheet1, loopsalesheetdet1,
        looppurchasereturnsheet1, looppurchasereturnsheetdet1,
        loopmaterialreturnsheet1, loopmaterialreturnsheetdet1,
        loopoutsrcpurchasereturnsheet1, loopoutsrcpurchasereturnsheetdet1, 
        loopsalereturnsheet1, loopsalereturnsheetdet1,
        loopparam1, loopreport1, loopreportparam1

    def createTestData = { domain, specifiedData, createLoopData, processMetaClass ->
        //標準測試資料，作為驗證以及 unit test 用
        domain = [Site] + domain
        if (specifiedData) {
            domain.each {
                if (processMetaClass == true) {
                    it.metaClass.getGrailsApplication = {
                        return grailsApplication
                    }
                    it.metaClass.getMessageSource = {
                        return messageSource
                    }
                }

                if ("agri" in specifiedData) {
                    log.debug "create${it.getSimpleName()}"
                    "create${it.getSimpleName()}"(createLoopData)
                }
                if ("mfg" in specifiedData) {
                    log.debug  "testMFGService.create${it.getSimpleName()}"
                    testMFGService."create${it.getSimpleName()}"()
                }
            }//end of each
        }
    }

    def createSite = { createLoopData ->
        siteGroup1 = SiteGroup.findByName("default") ?: new SiteGroup(name: "default", title: "default").save(failOnError: true, flush: true)

        site1 = Site.findByName("site1") ?: new Site(name: "site1", title: "測試公司1", siteGroup: siteGroup1,
            activationCode: grailsApplication.config.grails.dev.site.activationCode).save(failOnError: true, flush: true)
        user1 = User.findByUsername("admin") ?: new User(userType: UserType.ADMIN, username: "admin",
            fullName: "admin", password: "admin", lastLoginSite: site1, enabled: true, siteGroup: siteGroup1,
            activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
        UserSite.create(user1, site1)

        if (createLoopData) {
            (1..90).each {
                //loopstite_#siteGroup_#流水號
                def site = Site.findByName("loopsite1_${it}") ?: new Site(name: "loopsite1_${it}", title: "公司1_${it}", siteGroup: siteGroup1,
                    activationCode: grailsApplication.config.grails.dev.site.activationCode).save(failOnError: true, flush: true)

                if (it==1) {
                    loopsite1 = Site.findByName("loopsite1_1")
                    //ADMIN permanent
                    (1..50).each { index ->
                        def user = new User(userType: UserType.ADMIN, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }
                    //ADMIN 一年
                    (51..54).each { index ->
                        def user = new User(userType: UserType.ADMIN, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: "59477ef6-2c5a-4fb9-aab5-7d88307823e0").save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }
                    //ADMIN 過期
                    (61..64).each { index ->
                        def user = new User(userType: UserType.ADMIN, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: "59477ef6-2c5a-4fb9-aab5-7d88307823e0").save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }


                    //USER permanent
                    (101..150).each { index ->
                        def user = new User(userType: UserType.USER, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: grailsApplication.config.grails.dev.user.activationCode).save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }
                    //USER 一年
                    (151..154).each { index ->
                        def user = new User(userType: UserType.USER, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: "10008a5e-c5d3-42d4-a8b3-61099671452f").save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }
                    //USER 過期
                    (161..164).each { index ->
                        def user = new User(userType: UserType.USER, username: "loopuser1_${it}_${index}", fullName: "loopuser1_${it}_${index}",
                            password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                            activationCode: "41c6c3e5-e4a0-41b4-98f9-9f0df9339e8e").save(failOnError: true, flush: true)
                        UserSite.create(user, site)
                    }
                }
                else {
                    def user = new User(userType: UserType.ADMIN, username: "loopuser1_${it}_1", fullName: "loopuser1_${it}_1",
                        password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                        activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
                    UserSite.create(user, site)
                }
            }
            (91..100).each { //site過期
                    def site = Site.findByName("loopsite1_${it}") ?: new Site(name: "loopsite1_${it}", title: "公司1_${it}", siteGroup: siteGroup1, activationCode: "6ef9e8f8-ae33-4876-8d61-c9ceda208192").save(failOnError: true, flush: true)
                    def user = new User(userType: UserType.ADMIN, username: "loopuser1_${it}_1", fullName: "loopuser1_${it}_1",
                        password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup1,
                        activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
                    UserSite.create(user, site)
            }

            def siteGroup2 = new SiteGroup(name: "siteGroup2", title: "測試群組2").save(failOnError: true, flush: true)
            (1..10).each {
                    def site = Site.findByName("loopsite2_${it}") ?: new Site(name: "loopsite2_${it}", title: "公司2_${it}", siteGroup: siteGroup2, activationCode: grailsApplication.config.grails.dev.site.activationCode).save(failOnError: true, flush: true)
                    def user = new User(userType: UserType.ADMIN, username: "loopuser2_${it}_1", fullName: "loopuser2_${it}_1",
                        password: "0000", lastLoginSite: site, enabled: true, siteGroup: siteGroup2,
                        activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
                    UserSite.create(user, site)
            }
        }
    }

    def createItemCategoryLayer1 = { createLoopData ->

        itemCategoryLayer11 = new ItemCategoryLayer1(name: "ca11", title: "肥料", site: site1).save(failOnError: true, flush: true)
        itemCategoryLayer12 = new ItemCategoryLayer1(name: "ca12", title: "病蟲草害防治", site: site1).save(failOnError: true, flush: true)
        itemCategoryLayer1Other = new ItemCategoryLayer1(name: "other", title: "其他", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopitemcategorylayer1 = new ItemCategoryLayer1(name: "loopca1_${it}", title: "類1_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopitemcategorylayer11 = loopitemcategorylayer1)
            }
        }
    }

    def createItemCategoryLayer2 = { createLoopData ->

        itemCategoryLayer211 = new ItemCategoryLayer2(name: "ca211", title: "基肥", itemCategoryLayer1: itemCategoryLayer11, site: site1).save(failOnError: true, flush: true)
        itemCategoryLayer212 = new ItemCategoryLayer2(name: "ca212", title: "禮肥", itemCategoryLayer1: itemCategoryLayer11, site: site1).save(failOnError: true, flush: true)

        itemCategoryLayer221 = new ItemCategoryLayer2(name: "ca221", title: "農藥", itemCategoryLayer1: itemCategoryLayer12, site: site1).save(failOnError: true, flush: true)
        itemCategoryLayer222 = new ItemCategoryLayer2(name: "ca222", title: "非農藥", itemCategoryLayer1: itemCategoryLayer12, site: site1).save(failOnError: true, flush: true)
   
        if (createLoopData) {
            (1..100).each {
                def loopitemcategorylayer2 = new ItemCategoryLayer2(name: "loopca2_1${it}", title: "類2_1${it}", itemCategoryLayer1: loopitemcategorylayer11, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopitemcategorylayer211 = loopitemcategorylayer2)
            }
        }
    }

    def createBrand = { createLoopData ->

        productBrand1 = new Brand(name: "productBrand1", title: "製造品品牌", site: site1).save(failOnError: true, flush: true)
        materialBrand1 = new Brand(name: "materialBrand1", title: "品牌A", site: site1).save(failOnError: true, flush: true)
        materialBrand2 = new Brand(name: "materialBrand2", title: "品牌B", site: site1).save(failOnError: true, flush: true)
        
        if (createLoopData) {
            (1..100).each {
                def loopbrand = new Brand(name: "loopbrand_${it}", title: "品牌_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopbrand1 = loopbrand)

            }
        }
    }

    def createItem = { createLoopData ->

        product1ProductBrand1 = new Item(name: "product1", brand: productBrand1, title: "產品-玉米", itemType: ItemType.MANUFACTURE, spec: "甜玉米，高糖分、皮薄", unit: "kg", description: "非基因轉殖品種", site: site1).save(failOnError: true, flush: true)

        semiProduct1ProductBrand1 = new Item(name: "semiProduct1", brand: productBrand1, title: "自製半成品", itemType: ItemType.MANUFACTURE, spec: "半成品", unit: "kg", description: "非基因轉殖品種", itemCategoryLayer1: itemCategoryLayer1Other, site: site1).save(failOnError: true, flush: true)
        
        semiProduct2ProductBrand1 = new Item(name: "semiProduct2", brand: productBrand1, title: "託外製造半成品", itemType: ItemType.OUTSRCMANUFACTURE, spec: "半成品", unit: "kg", description: "非基因轉殖品種", itemCategoryLayer1: itemCategoryLayer1Other, site: site1).save(failOnError: true, flush: true)

        material1MaterialBrand1 = new Item(name: "material1", brand: materialBrand1, title: "原料1品牌1-無", itemType: ItemType.PURCHASE, spec: "A", unit: "kg", description: "非基因", site: site1).save(failOnError: true, flush: true)

        material2MaterialBrand1 = new Item(name: "material2", brand: materialBrand1, title: "原料2品牌1-肥料-無", itemType: ItemType.PURCHASE, spec: "B", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer11, defaultManufacturer: manufacturerTAIWAN, site: site1).save(failOnError: true, flush: true)
        material2MaterialBrand2 = new Item(name: "material2", brand: materialBrand2, title: "原料2品牌2-肥料-無", itemType: ItemType.PURCHASE, spec: "B", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer11, defaultManufacturer: manufacturerJAPAN, site: site1).save(failOnError: true, flush: true)
        material3MaterialBrand1 = new Item(name: "material3", brand: materialBrand1, title: "原料3品牌1-肥料-基肥", itemType: ItemType.PURCHASE, spec: "C", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer11, itemCategoryLayer2: itemCategoryLayer211, defaultManufacturer: manufacturerTAIWAN, site: site1).save(failOnError: true, flush: true)
        material4MaterialBrand1 = new Item(name: "material4", brand: materialBrand1, title: "原料4品牌1-肥料-禮肥", itemType: ItemType.PURCHASE, spec: "D", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer11, itemCategoryLayer2: itemCategoryLayer212, defaultManufacturer: manufacturerTAIWAN, site: site1).save(failOnError: true, flush: true)

        material5MaterialBrand2 = new Item(name: "material5", brand: materialBrand2, title: "原料5品牌2-病蟲草害-無", itemType: ItemType.PURCHASE, spec: "E", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer12, defaultManufacturer: manufacturerJAPAN, site: site1).save(failOnError: true, flush: true)
        material6MaterialBrand2 = new Item(name: "material6", brand: materialBrand2, title: "原料6品牌2-病蟲草害-農藥", itemType: ItemType.PURCHASE, spec: "F", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer12, itemCategoryLayer2: itemCategoryLayer221, defaultManufacturer: manufacturerJAPAN, site: site1).save(failOnError: true, flush: true)
        material7MaterialBrand2 = new Item(name: "material7", brand: materialBrand2, title: "原料7品牌2-病蟲草害-非農藥", itemType: ItemType.PURCHASE, spec: "G", unit: "kg", description: "非基因", itemCategoryLayer1: itemCategoryLayer12, itemCategoryLayer2: itemCategoryLayer222, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopitem = new Item(name: "loopitem_${it}", brand: loopbrand1, title: "品項_${it}", itemType: ItemType.MANUFACTURE, spec: "loop_${it}", unit: "loop_${it}", description: "loop_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopitem1 = loopitem)
                it != 2 ?: (loopitem2 = loopitem)
            }
        }

    }

    def createFactory = { createLoopData ->
        factory1 = new Factory(name: "fct1", title: "工廠1", tel: "02-333-111-1", email: "AAA@xx.com", address: "台北市新生南路333號", site: site1).save(failOnError: true, flush: true)
        factory2 = new Factory(name: "fct2", title: "工廠2", tel: "02-333-111-2", email: "BBB@xx.com", address: "台北市忠孝東路49號", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopfactory = new Factory(name: "loopfactory_${it}", title: "工廠_${it}", tel: "02-333-111-${it}", email: "loop_${it}@xx.com", address: "台北市忠孝東路${it}號", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopfactory1 = loopfactory)
            }
        }
    }

    def createCustomer = { createLoopData ->

        customer1 = new Customer(name: "customer1", title: "客戶陳小姐", site: site1).save(failOnError: true, flush: true)
        customer2 = new Customer(name: "customer2", title: "客戶王先生", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopcustomer = new Customer(name: "loopcustomer_${it}", title: "客戶_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopcustomer1 = loopcustomer)
            }
        }
    }

    def createEmployee = { createLoopData ->

        operator1  = new Employee(name: "opr1", title: "op陳小明", employeeType: EmployeeType.OPERATOR, idNumber: "F123456789", birthDate: new Date(100, 8, 23, 16, 0),
                tel: "02-1234-5678", mobile: "0910-123-456", permanentAddress: "台北市中華路三段155號", residentialAddress: "台北市市民大道二段1號", correspondenceAddress: "台北市市民大道二段1號", contact: "陳小美", contactPhoneNumber: "0911-111-111", site: site1).save(failOnError: true, flush: true)
        operator2  = new Employee(name: "opr2", title: "op劉大雄", employeeType: EmployeeType.OPERATOR, idNumber: "H123456789", birthDate: new Date(105, 8, 23, 16, 0),
                tel: "04-1234-5678", mobile: "0983-123-456", permanentAddress: "台中市台灣大道四段99號", residentialAddress: "台中市台灣大道四段99號", correspondenceAddress: "台中市台灣大道四段99號", contact: "劉香香", contactPhoneNumber: "0970-111-111", site: site1).save(failOnError: true, flush: true)
        accountant1 = new Employee(name: "act1", title: "ac許美美", employeeType: EmployeeType.ACCOUNTANT, idNumber: "A223456789", birthDate: new Date(110, 1, 2, 9, 0),
                tel: "03-1234-5678", mobile: "0933-123-456", permanentAddress: "桃園市中原路21號之1", residentialAddress: "桃園市中原路21號之1", correspondenceAddress: "桃園市中原路21號之5", contact: "許恩恩", contactPhoneNumber: "0935-111-111", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopoperator = new Employee(name: "loopemployee_${it}", title: "職員_${it}", employeeType: EmployeeType.OPERATOR, idNumber: "A11111111${it}", birthDate: (new Date()).previous(),
                        tel: "03-1234-5678", mobile: "0933-123-456", permanentAddress: "桃園市中原路100巷${it}號", residentialAddress: "桃園市中原路100巷${it}號", correspondenceAddress: "桃園市中原路100巷${it}號", contact: "聯絡人_${it}", contactPhoneNumber: "0935-111-12${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopoperator1 = loopoperator)
            }
        }
    }

    def createManufacturer = { createLoopData ->
        manufacturerTAIWAN = new Manufacturer(name: "manufacturerTAIWAN", title: "製造商TAIWAN", email: "A@xx.com", address: "高雄市鹽埕區中華路99號", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        manufacturerJAPAN = new Manufacturer(name: "manufacturerJAPAN", title: "製造商JAPAN", email: "B@xx.com", address: "彰化縣員林鎮三民路7號", country: Country.JAPAN, site: site1).save(failOnError: true, flush: true)
        manufacturerHONGKONG = new Manufacturer(name: "manufacturerHONGKONG", title: "製造商HONGKONG", email: "HONGKONG@xx.com", address: "彰化縣員林鎮三民路7號", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        manufacturerMEXICO = new Manufacturer(name: "manufacturerMEXICO", title: "製造商MEXICO", email: "MEXICO@xx.com", address: "MEXICO", country: Country.MEXICO, site: site1).save(failOnError: true, flush: true)
        manufacturerCHINA = new Manufacturer(name: "manufacturerCHINA", title: "製造商CHINA", email: "CHINA@xx.com", address: "CHINA", country: Country.CHINA, site: site1).save(failOnError: true, flush: true)
        manufacturerITALY = new Manufacturer(name: "manufacturerITALY", title: "製造商ITALY", email: "ITALY@xx.com", address: "ITALY", country: Country.ITALY, site: site1).save(failOnError: true, flush: true)
        manufacturerUNITEDKINGDOM = new Manufacturer(name: "manufacturerUNITEDKINGDOM", title: "製造商UNITEDKINGDOM", email: "UNITEDKINGDOM@xx.com", address: "UNITEDKINGDOM", country: Country.UNITEDKINGDOM, site: site1).save(failOnError: true, flush: true)
        manufacturerInspect = new Manufacturer(name: "manufacturerInspectCreatedBySupplier", title: "檢驗商", email: "ABB@xx.com", address: "台北市羅斯福路1號", country: Country.TAIWAN, isSupplier: true, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopmanufacturer = new Manufacturer(name: "loopmanufacturer_${it}", title: "製造商_${it}", email: "ma_${it}@xx.com", address: "彰化縣員林鎮三民路${it}號", country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopmanufacturer1 = loopmanufacturer)
            }
            loopmanufacturer_issupplier1 = new Manufacturer(name: "loopmanufacturer_issupplier1", title: "供應製造商_1", isSupplier: true, email: "AAA_1@xx.com", address: "台北市新生南路1號", country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
        }
    }

    def createSupplier = { createLoopData ->

        supplier1 = new Supplier(name: "sup1", title: "肥料供應商", email: "AAA@xx.com", address: "台北市新生南路111號", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        supplier2 = new Supplier(name: "sup2", title: "農藥供應商", email: "BBB@xx.com", address: "台北市忠孝東路222號", country: Country.JAPAN, site: site1).save(failOnError: true, flush: true)
        supplierInspect = new Supplier(name: "supInspect", title: "檢驗商", email: "ABB@xx.com", address: "台北市羅斯福路1號", country: Country.TAIWAN, isManufacturer: true, manufacturer: manufacturerInspect, site: site1).save(failOnError: true, flush: true)
        manufacturerInspect.supplier = supplierInspect

        if (createLoopData) {
            (1..100).each {
                def loopsupplier = new Supplier(name: "loopsupplier_${it}", title: "供應商_${it}", email: "AAA_${it}@xx.com", address: "台北市新生南路${it}號", country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopsupplier1 = loopsupplier)
            }
            loopsupplier_ismanufacturer1 = new Supplier(name: "loopsupplier_ismanufacturer_1", isManufacturer: true, title: "供應製造商_1", email: "AAA_1@xx.com", address: "台北市新生南路1號", country: Country.TAIWAN, manufacturer: loopmanufacturer_issupplier1, site: loopsite1).save(failOnError: true, flush: true)
            loopmanufacturer_issupplier1.supplier = loopsupplier_ismanufacturer1
            loopmanufacturer_issupplier1.save(failOnError: true, flush: true)
        }
    }

    def createWorkstation = { createLoopData ->

        workstation11 = new Workstation(name: "fct1ws1", title: "廠1工作站1", factory: factory1, site: site1).save(failOnError: true, flush: true)
        workstation12 = new Workstation(name: "fct1ws2", title: "廠1工作站2", factory: factory1, site: site1).save(failOnError: true, flush: true)
        workstation21 = new Workstation(name: "fct2ws1", title: "廠2工作站1", factory: factory2, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopworkstation = new Workstation(name: "loopworkstation_${it}", title: "工作站_${it}", factory: loopfactory1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopworkstation1 = loopworkstation)
            }
        }
    }

    def createOperationCategoryLayer1 = { createLoopData ->

        operationCategoryLayer11 = new OperationCategoryLayer1(name: "opca1", title: "種苗與接穗", site: site1).save(failOnError: true, flush: true)
        operationCategoryLayer12 = new OperationCategoryLayer1(name: "opca2", title: "農場準備", site: site1).save(failOnError: true, flush: true)
        operationCategoryLayer13 = new OperationCategoryLayer1(name: "opca3", title: "栽培管理", site: site1).save(failOnError: true, flush: true)
        operationCategoryLayer1Other = new OperationCategoryLayer1(name: "opcaOther", title: "其他作業", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopoperationcategory = new OperationCategoryLayer1(name: "loopopca_${it}", title: "製程類_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopoperationcategory1 = loopoperationcategory)
            }
        }
    }

    def createOperation = { createLoopData ->

        operation1 = new Operation(name: "opt1", title: "施肥", operationCategoryLayer1: operationCategoryLayer13, site: site1).save(failOnError: true, flush: true)
        operation2 = new Operation(name: "opt2", title: "除草", operationCategoryLayer1: operationCategoryLayer13, site: site1).save(failOnError: true, flush: true)
        operation3 = new Operation(name: "opt3", title: "病蟲害管理", operationCategoryLayer1: operationCategoryLayer11, site: site1).save(failOnError: true, flush: true)
        operation4 = new Operation(name: "opt4", title: "土壤改良", operationCategoryLayer1: operationCategoryLayer12, site: site1).save(failOnError: true, flush: true)
        operationInspect = new Operation(name: "optInspect", title: "檢驗", operationCategoryLayer1: operationCategoryLayer1Other, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopoperation = new Operation(name: "loopoperation_${it}", title: "製程_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopoperation1 = loopoperation)
            }
        }
    }

    def createItemStage = { createLoopData ->

        product1ProductBrand1Stage1 = new ItemStage(item: product1ProductBrand1, sequence: 10, title: "播種期", site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Stage2 = new ItemStage(item: product1ProductBrand1, sequence: 20, title: "孕穗期", site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Stage3 = new ItemStage(item: product1ProductBrand1, sequence: 30, title: "開花期", site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Stage4 = new ItemStage(item: product1ProductBrand1, sequence: 40, title: "吐絲期", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                new ItemStage(item: loopitem1, sequence: it*10, title: "期程_${it}", site: loopsite1).save(failOnError: true, flush: true)
            }
        }
    }

    def createItemRegisteredNum = { createLoopData ->

        semiProduct1ProductBrand1RigisteredNumTW = new ItemRegisteredNum(item: semiProduct1ProductBrand1, manufacturer: manufacturerInspect, country: Country.TAIWAN, registeredNum: "SEMI1-13960043", site: site1).save(failOnError: true, flush: true)

        material1MaterialBrand1RigisteredNumTW = new ItemRegisteredNum(item: material1MaterialBrand1, manufacturer: manufacturerTAIWAN, country: Country.TAIWAN, registeredNum: "M1-13960043", site: site1).save(failOnError: true, flush: true)
        material1MaterialBrand1RigisteredNumJP = new ItemRegisteredNum(item: material1MaterialBrand1, manufacturer: manufacturerTAIWAN, country: Country.JAPAN, registeredNum: "M1-5jkw923a", site: site1).save(failOnError: true, flush: true)
        material1MaterialBrand1RigisteredNumHK = new ItemRegisteredNum(item: material1MaterialBrand1, manufacturer: manufacturerTAIWAN, country: Country.HONGKONG, registeredNum: "M1-8dssHK13254", site: site1).save(failOnError: true, flush: true)

        material2MaterialBrand1RigisteredNumTW = new ItemRegisteredNum(item: material2MaterialBrand1, manufacturer: manufacturerHONGKONG, country: Country.TAIWAN, registeredNum: "M2-13960043", site: site1).save(failOnError: true, flush: true)
        material2MaterialBrand2RigisteredNumTW = new ItemRegisteredNum(item: material2MaterialBrand2, manufacturer: manufacturerHONGKONG, country: Country.TAIWAN, registeredNum: "M2-13960043", site: site1).save(failOnError: true, flush: true)
        material3MaterialBrand1RigisteredNumTW = new ItemRegisteredNum(item: material3MaterialBrand1, manufacturer: manufacturerMEXICO, country: Country.TAIWAN, registeredNum: "M3-13960043", site: site1).save(failOnError: true, flush: true)

        material5MaterialBrand2RigisteredNumTW = new ItemRegisteredNum(item: material5MaterialBrand2, manufacturer: manufacturerITALY, country: Country.TAIWAN, registeredNum: "M5-13960043", site: site1).save(failOnError: true, flush: true)
        material5MaterialBrand2RigisteredNumUK = new ItemRegisteredNum(item: material5MaterialBrand2, manufacturer: manufacturerITALY, country: Country.UNITEDKINGDOM, registeredNum: "M5-mdsoiw245456", site: site1).save(failOnError: true, flush: true)
        material5MaterialBrand2RigisteredNumMC = new ItemRegisteredNum(item: material5MaterialBrand2, manufacturer: manufacturerITALY, country: Country.MEXICO, registeredNum: "M5-52401234", site: site1).save(failOnError: true, flush: true)

        material6MaterialBrand2RigisteredNumTW = new ItemRegisteredNum(item: material6MaterialBrand2, manufacturer: manufacturerCHINA, country: Country.TAIWAN, registeredNum: "M6-13960043", site: site1).save(failOnError: true, flush: true)
        material7MaterialBrand2RigisteredNumTW = new ItemRegisteredNum(item: material7MaterialBrand2, manufacturer: manufacturerUNITEDKINGDOM, country: Country.TAIWAN, registeredNum: "M7-13960043", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..29).each {
                new ItemRegisteredNum(item: loopitem1, manufacturer: loopmanufacturer1, country: Country.values()[it], registeredNum: "registeredNum_${it}", site: loopsite1).save(failOnError: true, flush: true)
            }
        }
    }

    def createBillOfMaterial = { createLoopData ->
    }

    def createBillOfMaterialDet = { createLoopData ->
    }

    def createWarehouse = { createLoopData ->

        materialWarehouse11 = new Warehouse(name: "materialWarehouse11", title: "廠1原料倉1", factory: factory1, site: site1).save(failOnError: true, flush: true)
        productWarehouse11 = new Warehouse(name: "productWarehouse11", title: "廠1成品倉1", factory: factory1, site: site1).save(failOnError: true, flush: true)
        materialWarehouse21 = new Warehouse(name: "materialWarehouse21", title: "廠2原料倉1", factory: factory2, site: site1).save(failOnError: true, flush: true)
        productWarehouse21 = new Warehouse(name: "productWarehouse21", title: "廠2成品倉1", factory: factory2, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopwarehouse = new Warehouse(name: "loopwarehouse_${it}", title: "倉庫_${it}", factory: loopfactory1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopwarehouse1 = loopwarehouse)
            }
        }
    }

    def createWarehouseLocation = { createLoopData ->

        materialWarehouseLocation111 = new WarehouseLocation(name: "materialWarehouseLocation111", warehouse: materialWarehouse11, title: "廠1原料儲位11-肥料", site: site1).save(failOnError: true, flush: true)
        materialWarehouseLocation112 = new WarehouseLocation(name: "materialWarehouseLocation112", warehouse: materialWarehouse11, title: "廠1原料儲位12-農藥", site: site1).save(failOnError: true, flush: true)
        productWarehouseLocation111 = new WarehouseLocation(name: "productWarehouseLocation111", warehouse: productWarehouse11, title: "廠1成品儲位11", site: site1).save(failOnError: true, flush: true)
        productWarehouseLocation112 = new WarehouseLocation(name: "productWarehouseLocation112", warehouse: productWarehouse11, title: "廠1成品儲位12", site: site1).save(failOnError: true, flush: true)

        materialWarehouseLocation211 = new WarehouseLocation(name: "materialWarehouseLocation211", warehouse: materialWarehouse21, title: "廠2原料儲位11-肥料", site: site1).save(failOnError: true, flush: true)
        materialWarehouseLocation212 = new WarehouseLocation(name: "materialWarehouseLocation212", warehouse: materialWarehouse21, title: "廠2原料儲位12-農藥", site: site1).save(failOnError: true, flush: true)
        productWarehouseLocation211 = new WarehouseLocation(name: "productWarehouseLocation211", warehouse: productWarehouse21, title: "廠2成品儲位11", site: site1).save(failOnError: true, flush: true)
        productWarehouseLocation212 = new WarehouseLocation(name: "productWarehouseLocation212", warehouse: productWarehouse21, title: "廠2成品儲位12", site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopwarehouselocation = new WarehouseLocation(name: "loopwarehouselocation_${it}", warehouse: loopwarehouse1, title: "儲位_${it}", site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopwarehouselocation1 = loopwarehouselocation)
            }
        }
    }

    def createTypeName = { createLoopData ->

        typeNameTransactionSTORAGE = new TypeName(sheetType: SheetType.TRANSACTION, name: "1111", title: "入庫異動單據", transactionType: TransactionType.STORAGE, site: site1).save(failOnError: true, flush: true)
        typeNameReturnTransactionSTORAGE = new TypeName(sheetType: SheetType.TRANSACTION, name: "1112", title: "出庫異動單據", transactionType: TransactionType.STORAGE, multiplier: -1, site: site1).save(failOnError: true, flush: true)
        typeNameTransactionSALE = new TypeName(sheetType: SheetType.TRANSACTION, name: "112", title: "銷貨異動單據", sheetFormatType: SheetFormatType.MONTH, yearDigit: 3, transactionType: TransactionType.SALE, site: site1).save(failOnError: true, flush: true)
        typeNameTransactionREQUISITION = new TypeName(sheetType: SheetType.TRANSACTION, name: "113", title: "領用異動單據", sheetFormatType: SheetFormatType.RUNNINGNUMBER, runningDigit: 5, transactionType: TransactionType.REQUISITION, site: site1).save(failOnError: true, flush: true)
        typeNameTransactionADJUSTMENT = new TypeName(sheetType: SheetType.TRANSACTION, name: "115", title: "調整異動單據", sheetFormatType: SheetFormatType.MANUAL, transactionType: TransactionType.ADJUSTMENT, site: site1).save(failOnError: true, flush: true)
      
        typeNameCustomerOrder = new TypeName(sheetType: SheetType.CUSTOMERORDER, name: "220", title: "訂單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameSaleSheet = new TypeName(sheetType: SheetType.SALESHEET, name: "230", title: "銷貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameSaleReturnSheet = new TypeName(sheetType: SheetType.SALERETURNSHEET, name: "240", title: "銷退單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        typeNamePurchaseSheet = new TypeName(sheetType: SheetType.PURCHASESHEET, name: "340", title: "進貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNamePurchaseReturnSheet = new TypeName(sheetType: SheetType.PURCHASERETURNSHEET, name: "350", title: "退貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameManufactureOrder = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "511", title: "廠內製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.FACTORY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcManufactureOrder = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "512", title: "託外製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.OUTSRC, site: site1).save(failOnError: true, flush: true)
        // typeNameManufactureOrderRework = new TypeName(sheetType: SheetType.REWORKORDER, name: "230", title: "銷貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameMaterialSheet = new TypeName(sheetType: SheetType.MATERIALSHEET, name: "540", title: "廠內領料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameMaterialReturnSheet = new TypeName(sheetType: SheetType.MATERIALRETURNSHEET, name: "560", title: "廠內退料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcMaterialSheet = new TypeName(sheetType: SheetType.OUTSRCMATERIALSHEET, name: "550", title: "託外領料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcMaterialReturnSheet = new TypeName(sheetType: SheetType.OUTSRCMATERIALRETURNSHEET, name: "570", title: "託外退料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        typeNameStockInSheet = new TypeName(sheetType: SheetType.STOCKINSHEET, name: "580", title: "生產入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcPurchaseSheet = new TypeName(sheetType: SheetType.OUTSRCPURCHASESHEET, name: "590", title: "託外進貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcPurchaseReturnSheet = new TypeName(sheetType: SheetType.OUTSRCPURCHASERETURNSHEET, name: "5A1", title: "託外退貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        
        typeNameTransferOrderRELEASE = new TypeName(sheetType: SheetType.ORDERRELEASE, name: "D11", title: "廠內投料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameTransferOrderTRANSFER = new TypeName(sheetType: SheetType.OPERATIONTRANSFER, name: "D21", title: "廠內移轉單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameTransferOrderSTORAGE = new TypeName(sheetType: SheetType.OPERATIONSTORAGE, name: "D31", title: "廠內入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcTransferOrderRELEASE = new TypeName(sheetType: SheetType.ORDERRELEASE, name: "D12", title: "託外投料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcTransferOrderTRANSFER = new TypeName(sheetType: SheetType.OPERATIONTRANSFER, name: "D22", title: "託外移轉單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        typeNameOutSrcTransferOrderSTORAGE = new TypeName(sheetType: SheetType.OPERATIONSTORAGE, name: "D32", title: "託外入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        // typeNameWorkReport = new TypeName(sheetType: SheetType.WORKREPORT, name: "D4", title: "報工單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def looptypenameTRANSACTION = new TypeName(sheetType: SheetType.TRANSACTION, name: "looptypename_${it}", title: "入庫異動單據", transactionType: TransactionType.STORAGE, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (looptypenameTRANSACTION1 = looptypenameTRANSACTION)
            }

            looptypenameCUSTOMERORDER1 = new TypeName(sheetType: SheetType.CUSTOMERORDER, name: "looptypename_CustomerOrder", title: "訂單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameSALESHEET1 = new TypeName(sheetType: SheetType.SALESHEET, name: "looptypename_SaleSheet", title: "銷貨單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameSALERETURNSHEET1 = new TypeName(sheetType: SheetType.SALERETURNSHEET, name: "looptypename_SaleReturnSheet", title: "銷退單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)

            looptypenamePURCHASESHEET1 = new TypeName(sheetType: SheetType.PURCHASESHEET, name: "looptypename_PurchaseSheet", title: "進貨單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenamePURCHASERETURNSHEET1 = new TypeName(sheetType: SheetType.PURCHASERETURNSHEET, name: "looptypename_PurchaseReturnSheet", title: "退貨單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameMANUFACTUREORDER1 = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "looptypename_ManufactureOrder", title: "廠內製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.FACTORY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameOUTSRCMANUFACTUREORDER1 = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "looptypename_OutSrcManufactureOrder", title: "託外製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.OUTSRC, site: loopsite1).save(failOnError: true, flush: true)

            looptypenameMATERIALSHEET1 = new TypeName(sheetType: SheetType.MATERIALSHEET, name: "looptypename_MaterialSheet", title: "廠內領料單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameMATERIALRETURNSHEET1 = new TypeName(sheetType: SheetType.MATERIALRETURNSHEET, name: "looptypename_MaterialReturnSheet", title: "廠內退料單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameOUTSRCMATERIALSHEET1 = new TypeName(sheetType: SheetType.OUTSRCMATERIALSHEET, name: "looptypename_OutSrcMaterialSheet", title: "託外領料單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameOUTSRCMATERIALRETURNSHEET1 = new TypeName(sheetType: SheetType.OUTSRCMATERIALRETURNSHEET, name: "looptypename_OutSrcMaterialReturnSheet", title: "託外退料單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)

            looptypenameSTOCKINSHEET1 = new TypeName(sheetType: SheetType.STOCKINSHEET, name: "looptypename_StockInSheet", title: "生產入庫單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameOUTSRCPURCHASESHEET1 = new TypeName(sheetType: SheetType.OUTSRCPURCHASESHEET, name: "looptypename_OutSrcPurchaseSheet", title: "託外進貨單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
            looptypenameOUTSRCPURCHASERETURNSHEET1 = new TypeName(sheetType: SheetType.OUTSRCPURCHASERETURNSHEET, name: "looptypename_OutSrcPurchaseReturnSheetDet", title: "託外退貨單", sheetFormatType: SheetFormatType.DAY, site: loopsite1).save(failOnError: true, flush: true)
        }
    }

    def createCustomerOrder = { createLoopData ->
        //訂單
        customerOrder1 = new CustomerOrder(typeName: typeNameCustomerOrder, name: "001", factory: factory1, customer: customer1, site: site1).save(failOnError: true, flush: true)
        customerOrder2 = new CustomerOrder(typeName: typeNameCustomerOrder, name: "002", factory: factory1, customer: customer1, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopcustomerorder = new CustomerOrder(typeName: looptypenameCUSTOMERORDER1, name: "${it}", factory: loopfactory1, customer: loopcustomer1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopcustomerorder1 = loopcustomerorder)
            }
        }

    }

    def createCustomerOrderDet = { createLoopData ->
        //訂單
        customerOrderDet11 = new CustomerOrderDet(header: customerOrder1, typeName: customerOrder1.typeName, name: customerOrder1.name, sequence: 1, item: product1ProductBrand1, unit: product1ProductBrand1.unit, qty: 10000000, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopcustomerorderdet = new CustomerOrderDet(header: loopcustomerorder1, typeName: loopcustomerorder1.typeName, name: loopcustomerorder1.name, sequence: it, item: loopitem1, unit: loopitem1.unit, qty: 10000000, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopcustomerorderdet1 = loopcustomerorderdet)
            }
        }
    }

    def createManufactureOrder = { createLoopData ->

        //半成品製令
        manufactureOrder1 = new ManufactureOrder(typeName: typeNameManufactureOrder, name: "001", manufactureType: typeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.APPROVED, factory: workstation11.factory, workstation: workstation11,
            item: semiProduct1ProductBrand1, batchName: "semiProduct1ProductBrand1Batch1", expectQty: 500000, site: site1).save(failOnError: true, flush: true)
        // def semiProduct1ProductBrand1Batch1 = new Batch(name: "semiProduct1ProductBrand1Batch1", item: semiProduct1ProductBrand1, title: semiProduct1ProductBrand1.title, spec: semiProduct1ProductBrand1.spec, unit: semiProduct1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder1,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 500000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //成品製令
        manufactureOrder2 = new ManufactureOrder(typeName: typeNameManufactureOrder, name: "002", manufactureType: typeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.APPROVED, factory: workstation11.factory, workstation: workstation11,
            item: product1ProductBrand1, batchName: "product1ProductBrand1Batch1", expectQty: 1000000, site: site1).save(failOnError: true, flush: true)
        // def product1ProductBrand1Batch1 = new Batch(name: "product1ProductBrand1Batch1", item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder2,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //半成品託外製令
        manufactureOrder3 = new ManufactureOrder(typeName: typeNameOutSrcManufactureOrder, name: "003", manufactureType: typeNameOutSrcManufactureOrder.manufactureType, status: ManufactureOrderStatus.APPROVED, factory: factory1, supplier: supplierInspect,
            item: semiProduct1ProductBrand1, batchName: "semiProduct1ProductBrand1Batch2", expectQty: 10000, site: site1).save(failOnError: true, flush: true)
        // def semiProduct1ProductBrand1Batch2 = new Batch(name: "semiProduct1ProductBrand1Batch2", item: semiProduct1ProductBrand1, title: semiProduct1ProductBrand1.title, spec: semiProduct1ProductBrand1.spec, unit: semiProduct1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.OUTSRCMANUFACTURE, manufacturer: manufacturerInspect, manufactureOrder: manufactureOrder3,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)


        //待發放製令
        manufactureOrder4 = new ManufactureOrder(typeName: typeNameManufactureOrder, name: "004", manufactureType: typeNameManufactureOrder.manufactureType, factory: workstation11.factory, workstation: workstation11,
            item: product1ProductBrand1, batchName: "product1ProductBrand1Batch2", expectQty: 1000000, site: site1).save(failOnError: true, flush: true)
        // def product1ProductBrand1Batch2 = new Batch(name: "product1ProductBrand1Batch2", item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder4,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //生產中製令
        manufactureOrder5 = new ManufactureOrder(typeName: typeNameManufactureOrder, name: "005", manufactureType: typeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.INPROCESS, factory: workstation11.factory, workstation: workstation11,
            item: product1ProductBrand1, batchName: "product1ProductBrand1Batch3", expectQty: 1000000, site: site1).save(failOnError: true, flush: true)
        // def product1ProductBrand1Batch3 = new Batch(name: "product1ProductBrand1Batch3", item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder5,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //指定結案製令
        manufactureOrder6 = new ManufactureOrder(typeName: typeNameManufactureOrder, name: "006", manufactureType: typeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.ASSIGNEDFINISHED, factory: workstation12.factory, workstation: workstation12,
            item: product1ProductBrand1, batchName: "product1ProductBrand1Batch4", expectQty: 1000000, site: site1).save(failOnError: true, flush: true)
        // def product1ProductBrand1Batch4 = new Batch(name: "product1ProductBrand1Batch4", item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
        //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder6,
        //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        manufactureOrderChangeOrder = new ManufactureOrderChangeOrder(manufactureOrder: manufactureOrder6, typeName: manufactureOrder6.typeName, name :manufactureOrder6.name, sequence: 1,
            originVersion: manufactureOrder6.version, originEditor: manufactureOrder6.editor, originCreator: manufactureOrder6.creator, originDateCreated: manufactureOrder6.dateCreated, originLastUpdated: manufactureOrder6.lastUpdated,
            executionDate: manufactureOrder6.executionDate, originExecutionDate: manufactureOrder6.executionDate,
            status: ManufactureOrderStatus.ASSIGNEDFINISHED, originStatus: ManufactureOrderStatus.PENDING,
            factory: manufactureOrder6.factory, originFactoryId: manufactureOrder6.factory.id,
            workstation: manufactureOrder6.workstation, originWorkstationId: manufactureOrder6.workstation?.id,
            supplier: manufactureOrder6.supplier, originSupplierId: manufactureOrder6.supplier?.id,
            customerOrderDet: manufactureOrder6.customerOrderDet, originCustomerOrderDetId: manufactureOrder6.customerOrderDet?.id,
            batchName: manufactureOrder6.batchName, originBatchName: manufactureOrder6.batchName,
            item: manufactureOrder6.item, originItemId: manufactureOrder6.item.id,
            expectQty: manufactureOrder6.expectQty, originExpectQty: manufactureOrder6.expectQty, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..50).each {
                def loopmanufactureorder = new ManufactureOrder(typeName: looptypenameMANUFACTUREORDER1, name: "${it}", manufactureType: typeNameManufactureOrder.manufactureType, factory: loopfactory1, workstation: loopworkstation1,
                    item: Item.findByNameAndSite("loopitem_${it}", loopsite1), batchName: "loopbatch_${it}", expectQty: 10000000, site: loopsite1).save(failOnError: true, flush: true)
                // new Batch(name: "loopbatch_${it}", item: loopitem1, title: loopitem1.title, spec: loopitem1.spec, unit: loopitem1.unit,
                //         batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: loopmanufactureOrder,
                //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註${it}", expectQty: 1000000, country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopmanufactureorder1 = loopmanufactureorder)
            }
            (51..100).each {
                def loopmanufactureorder = new ManufactureOrder(typeName: looptypenameOUTSRCMANUFACTUREORDER1, name: "${it}", manufactureType: typeNameOutSrcManufactureOrder.manufactureType, factory: loopfactory1, supplier: loopsupplier_ismanufacturer1,
                    item: Item.findByNameAndSite("loopitem_${it}", loopsite1), batchName: "loopbatch_${it}", expectQty: 10000000, site: loopsite1).save(failOnError: true, flush: true)
                // new Batch(name: "loopbatch_${it}", item: loopitem1, title: loopitem1.title, spec: loopitem1.spec, unit: loopitem1.unit,
                //         batchSourceType: BatchSourceType.OUTSRCMANUFACTURE, manufacturer: loopmanufacturer1, manufactureOrder: loopmanufactureOrder, 
                //         dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註${it}", expectQty: 1000000, country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 51 ?: (loopmanufactureorder51 = loopmanufactureorder)

            }
        }
    }

    def createBatch = { createLoopData ->

        //成品製令2批號 product1ProductBrand1Batch1
        product1ProductBrand1Batch1 = new Batch(name: manufactureOrder2.batchName, item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder2,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //待發放製令4批號 product1ProductBrand1Batch2
        product1ProductBrand1Batch2 = new Batch(name: manufactureOrder4.batchName, item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder4,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //生產中製令5批號 product1ProductBrand1Batch3
        product1ProductBrand1Batch3 = new Batch(name: manufactureOrder5.batchName, item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder5,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //指定結案製令6批號 product1ProductBrand1Batch4
        product1ProductBrand1Batch4 = new Batch(name: manufactureOrder6.batchName, item: product1ProductBrand1, title: product1ProductBrand1.title, spec: product1ProductBrand1.spec, unit: product1ProductBrand1.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder6,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //半成品製令1批號 semiProduct1ProductBrand1Batch1
        semiProduct1ProductBrand1Batch1 = new Batch(name: manufactureOrder1.batchName, item: semiProduct1ProductBrand1, title: semiProduct1ProductBrand1.title, spec: semiProduct1ProductBrand1.spec, unit: semiProduct1ProductBrand1.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: manufactureOrder1,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 500000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        //半成品託外製令3批號 semiProduct1ProductBrand1Batch2
        semiProduct1ProductBrand1Batch2 = new Batch(name: manufactureOrder3.batchName, item: semiProduct1ProductBrand1, title: semiProduct1ProductBrand1.title, spec: semiProduct1ProductBrand1.spec, unit: semiProduct1ProductBrand1.unit,
                batchSourceType: BatchSourceType.OUTSRCMANUFACTURE, manufacturer: manufacturerInspect, manufactureOrder: manufactureOrder3,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)


        material1MaterialBrand1Batch1 = new Batch(name: "material1MaterialBrand1Batch1", item: material1MaterialBrand1, title: material1MaterialBrand1.title, spec: material1MaterialBrand1.spec, unit: material1MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerTAIWAN,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        material2MaterialBrand1Batch1 = new Batch(name: "material2MaterialBrand1Batch1", item: material2MaterialBrand1, title: material2MaterialBrand1.title, spec: material2MaterialBrand1.spec, unit: material2MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        material2MaterialBrand2Batch1 = new Batch(name: "material2MaterialBrand2Batch1", item: material2MaterialBrand2, title: material2MaterialBrand1.title, spec: material2MaterialBrand1.spec, unit: material2MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        material3MaterialBrand1Batch1 = new Batch(name: "material3MaterialBrand1Batch1", item: material3MaterialBrand1, title: material3MaterialBrand1.title, spec: material3MaterialBrand1.spec, unit: material3MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerMEXICO,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.MEXICO, site: site1).save(failOnError: true, flush: true)
        material4MaterialBrand1Batch1 = new Batch(name: "material4MaterialBrand1Batch1", item: material4MaterialBrand1, title: material4MaterialBrand1.title, spec: material4MaterialBrand1.spec, unit: material4MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerTAIWAN,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        material5MaterialBrand2Batch1 = new Batch(name: "material5MaterialBrand2Batch1", item: material5MaterialBrand2, title: material5MaterialBrand2.title, spec: material5MaterialBrand2.spec, unit: material5MaterialBrand2.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerITALY,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.ITALY, site: site1).save(failOnError: true, flush: true)
        material6MaterialBrand2Batch1 = new Batch(name: "material6MaterialBrand2Batch1", item: material6MaterialBrand2, title: material6MaterialBrand2.title, spec: material6MaterialBrand2.spec, unit: material6MaterialBrand2.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerCHINA,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.CHINA, site: site1).save(failOnError: true, flush: true)
        material7MaterialBrand2Batch1 = new Batch(name: "material7MaterialBrand2Batch1", item: material7MaterialBrand2, title: material7MaterialBrand2.title, spec: material7MaterialBrand2.spec, unit: material7MaterialBrand2.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerUNITEDKINGDOM,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.UNITEDKINGDOM, site: site1).save(failOnError: true, flush: true)

        transactionBatch11 = new Batch(name: "transactionBatch11", item: material1MaterialBrand1, title: material1MaterialBrand1.title, spec: material1MaterialBrand1.spec, unit: material1MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        transactionBatch21 = new Batch(name: "transactionBatch21", item: material1MaterialBrand1, title: material1MaterialBrand1.title, spec: material1MaterialBrand1.spec, unit: material1MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        transactionBatch31 = new Batch(name: "transactionBatch31", item: material1MaterialBrand1, title: material1MaterialBrand1.title, spec: material1MaterialBrand1.spec, unit: material1MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        transactionBatch41 = new Batch(name: "transactionBatch41", item: material1MaterialBrand1, title: material1MaterialBrand1.title, spec: material1MaterialBrand1.spec, unit: material1MaterialBrand1.unit,
                batchSourceType: BatchSourceType.PURCHASE, manufacturer: manufacturerHONGKONG,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            
            (1..50).each {
                def loopmanufactureOrder = ManufactureOrder.findByTypeNameAndNameAndSite(looptypenameMANUFACTUREORDER1, "${it}", loopsite1)
                // loopbatch_${it}
                def loopbatch = new Batch(name: loopmanufactureOrder.batchName, item: Item.findByNameAndSite("loopitem_${it}", loopsite1), title: loopitem1.title, spec: loopitem1.spec, unit: loopitem1.unit,
                        batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: loopmanufactureOrder,
                        dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註${it}", expectQty: 1000000, country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopbatch1 = loopbatch)
                it != 2 ?: (loopbatch2 = loopbatch)
            }
            (51..100).each {
                def loopmanufactureOrder = ManufactureOrder.findByTypeNameAndNameAndSite(looptypenameOUTSRCMANUFACTUREORDER1, "${it}", loopsite1)
                // loopbatch_${it}
                def loopbatch = new Batch(name: loopmanufactureOrder.batchName, item: Item.findByNameAndSite("loopitem_${it}", loopsite1), title: loopitem1.title, spec: loopitem1.spec, unit: loopitem1.unit,
                        batchSourceType: BatchSourceType.OUTSRCMANUFACTURE, manufacturer: loopmanufacturer1, manufactureOrder: loopmanufactureOrder, 
                        dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註${it}", expectQty: 1000000, country: Country.TAIWAN, site: loopsite1).save(failOnError: true, flush: true)
                it != 51 ?: (loopbatch51 = loopbatch)
                it != 52 ?: (loopbatch52 = loopbatch)
            }
        }
    }

    def createItemRoute = { createLoopData ->

        product1ProductBrand1ItemRoute1 = new ItemRoute(item: product1ProductBrand1, sequence: 1, operation: operation1, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1.addToItemRoutes(product1ProductBrand1ItemRoute1).save(failOnError: true, flush: true)
        product1ProductBrand1ItemRoute2 = new ItemRoute(item: product1ProductBrand1, sequence: 2, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1.addToItemRoutes(product1ProductBrand1ItemRoute2).save(failOnError: true, flush: true)
        product1ProductBrand1ItemRoute3 = new ItemRoute(item: product1ProductBrand1, sequence: 3, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1.addToItemRoutes(product1ProductBrand1ItemRoute3).save(failOnError: true, flush: true)

        semiProduct1ProductBrand1ItemRoute1 = new ItemRoute(item: semiProduct1ProductBrand1, sequence: 1, operation: operation1, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1.addToItemRoutes(semiProduct1ProductBrand1ItemRoute1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1ItemRoute2 = new ItemRoute(item: semiProduct1ProductBrand1, sequence: 2, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1.addToItemRoutes(semiProduct1ProductBrand1ItemRoute2).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..50).each {
                def loopitemroute = new ItemRoute(item: loopitem1, sequence: it, operation: loopoperation1, workstation: loopworkstation1, site: loopsite1).save(failOnError: true, flush: true)
                loopitem1.addToItemRoutes(loopitemroute).save(failOnError: true, flush: true)
            }
            (51..100).each {
                def loopitemroute = new ItemRoute(item: loopitem1, sequence: it, operation: loopoperation1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
                loopitem1.addToItemRoutes(loopitemroute).save(failOnError: true, flush: true)
            }
        }
    }

    def createBatchOperation = { createLoopData ->

        product1ProductBrand1Batch1Operation1 = new BatchOperation(batch: product1ProductBrand1Batch1, sequence: 1,operation: operation1, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1.addToBatchOperations(product1ProductBrand1Batch1Operation1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1Operation2 = new BatchOperation(batch: product1ProductBrand1Batch1, sequence: 2,operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1.addToBatchOperations(product1ProductBrand1Batch1Operation2).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1Operation3 = new BatchOperation(batch: product1ProductBrand1Batch1, sequence: 3,operation: operation3, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1.addToBatchOperations(product1ProductBrand1Batch1Operation3).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1Operation4 = new BatchOperation(batch: product1ProductBrand1Batch1, sequence: 4,operation: operation4, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1.addToBatchOperations(product1ProductBrand1Batch1Operation4).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1Operation5 = new BatchOperation(batch: product1ProductBrand1Batch1, sequence: 5,operation: operationInspect, supplier: supplierInspect, remark: "test remark", site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch1.addToBatchOperations(product1ProductBrand1Batch1Operation5).save(failOnError: true, flush: true)

        semiProduct1ProductBrand1Batch1Operation1 = new BatchOperation(batch: semiProduct1ProductBrand1Batch1, sequence: 1, operation: operation1, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch1.addToBatchOperations(semiProduct1ProductBrand1Batch1Operation1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch1Operation2 = new BatchOperation(batch: semiProduct1ProductBrand1Batch1, sequence: 2, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch1.addToBatchOperations(semiProduct1ProductBrand1Batch1Operation2).save(failOnError: true, flush: true)

        
        product1ProductBrand1Batch3Operation1 = new BatchOperation(batch: product1ProductBrand1Batch3, sequence: 1,operation: operation1, workstation: workstation11, startDate: new Date(), endDate: (new Date()).next(), site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3.addToBatchOperations(product1ProductBrand1Batch3Operation1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3Operation2 = new BatchOperation(batch: product1ProductBrand1Batch3, sequence: 2,operation: operation2, workstation: workstation11, startDate: product1ProductBrand1Batch3Operation1.endDate, endDate: product1ProductBrand1Batch3Operation1.endDate.next(), site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3.addToBatchOperations(product1ProductBrand1Batch3Operation2).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3Operation3 = new BatchOperation(batch: product1ProductBrand1Batch3, sequence: 3,operation: operation3, workstation: workstation11, startDate: product1ProductBrand1Batch3Operation2.endDate, endDate: product1ProductBrand1Batch3Operation2.endDate.next(), site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3.addToBatchOperations(product1ProductBrand1Batch3Operation3).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3Operation4 = new BatchOperation(batch: product1ProductBrand1Batch3, sequence: 4,operation: operation4, workstation: workstation11, startDate: product1ProductBrand1Batch3Operation3.endDate, endDate: product1ProductBrand1Batch3Operation3.endDate.next(), site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3.addToBatchOperations(product1ProductBrand1Batch3Operation4).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3Operation5 = new BatchOperation(batch: product1ProductBrand1Batch3, sequence: 5,operation: operationInspect, supplier: supplierInspect, startDate: product1ProductBrand1Batch3Operation4.endDate, endDate: product1ProductBrand1Batch3Operation4.endDate.next(), site: site1).save(failOnError: true, flush: true)
        product1ProductBrand1Batch3.addToBatchOperations(product1ProductBrand1Batch3Operation5).save(failOnError: true, flush: true)
 
        semiProduct1ProductBrand1Batch2Operation1 = new BatchOperation(batch: semiProduct1ProductBrand1Batch2, sequence: 1, operation: operation1, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch2.addToBatchOperations(semiProduct1ProductBrand1Batch2Operation1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch2Operation2 = new BatchOperation(batch: semiProduct1ProductBrand1Batch2, sequence: 2, operation: operation2, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        semiProduct1ProductBrand1Batch2.addToBatchOperations(semiProduct1ProductBrand1Batch2Operation2).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..50).each {
                def loopbatchoperation = new BatchOperation(batch: loopbatch1, sequence: it, operation: loopoperation1, workstation: loopworkstation1, site: loopsite1).save(failOnError: true, flush: true)
                loopbatch1.addToBatchOperations(loopbatchoperation).save(failOnError: true, flush: true)
                
                it != 1 ?: (loopbatchoperation1 = loopbatchoperation)
            }
            (51..100).each {
                def loopbatchoperation = new BatchOperation(batch: loopbatch1, sequence: it, operation: loopoperation1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
                loopbatch1.addToBatchOperations(loopbatchoperation).save(failOnError: true, flush: true)
            }
        }
    }

    def createInventory = { createLoopData ->
        //須對庫存細項建置
        inventory1 = new Inventory(item: material1MaterialBrand1, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory2 = new Inventory(item: material2MaterialBrand1, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory3 = new Inventory(item: material3MaterialBrand1, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory4 = new Inventory(item: material4MaterialBrand1, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)

        inventory5 = new Inventory(item: material5MaterialBrand2, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory6 = new Inventory(item: material6MaterialBrand2, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory7 = new Inventory(item: material7MaterialBrand2, warehouse: materialWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)

        inventory8 = new Inventory(item: product1ProductBrand1, warehouse: productWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventory9 = new Inventory(item: semiProduct1ProductBrand1, warehouse: productWarehouse11, qty: 0, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopinventory = new Inventory(item: Item.findByNameAndBrandAndSite("loopitem_${it}", loopbrand1, loopsite1), warehouse: loopwarehouse1, qty: 0, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopinventory1 = loopinventory)
            }
        }
    }

    def createInventoryDetail = { createLoopData ->

        // 須對照進貨單、生產入庫單、託外進貨單建置
        inventoryDetail11 = new InventoryDetail(item: material1MaterialBrand1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: material1MaterialBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail21 = new InventoryDetail(item: material2MaterialBrand1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: material2MaterialBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail31 = new InventoryDetail(item: material3MaterialBrand1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: material3MaterialBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail41 = new InventoryDetail(item: material4MaterialBrand1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: material4MaterialBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)

        inventoryDetail51 = new InventoryDetail(item: material5MaterialBrand2, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, batch: material5MaterialBrand2Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail61 = new InventoryDetail(item: material6MaterialBrand2, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, batch: material6MaterialBrand2Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail71 = new InventoryDetail(item: material7MaterialBrand2, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, batch: material7MaterialBrand2Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)

        inventoryDetail81 = new InventoryDetail(item: product1ProductBrand1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, batch: product1ProductBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail91 = new InventoryDetail(item: semiProduct1ProductBrand1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, batch: semiProduct1ProductBrand1Batch1, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail101 = new InventoryDetail(item: semiProduct1ProductBrand1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation112, batch: semiProduct1ProductBrand1Batch2, qty: 0, site: site1).save(failOnError: true, flush: true)

        // for inventoryTransactionSheetDet
        inventoryDetail501 = new InventoryDetail(item: transactionBatch11.item, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: transactionBatch11, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail502 = new InventoryDetail(item: transactionBatch21.item, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: transactionBatch21, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail503 = new InventoryDetail(item: transactionBatch31.item, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: transactionBatch31, qty: 0, site: site1).save(failOnError: true, flush: true)
        inventoryDetail504 = new InventoryDetail(item: transactionBatch41.item, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, batch: transactionBatch41, qty: 0, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopbatch = Batch.findByNameAndSite("loopbatch_${it}", loopsite1)
                def loopinventorydetail = new InventoryDetail(item: loopbatch.item, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, batch: loopbatch, qty: 0, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopinventorydetail1 = loopinventorydetail)
            }
        }
    }

    def createInventoryTransactionSheet = { createLoopData ->

        // 入庫異動單據
        inventoryTransactionSheet1 = new InventoryTransactionSheet(typeName: typeNameTransactionSTORAGE, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"001", factory: factory1, site: site1).save(failOnError: true, flush: true)
        // 出庫異動單據
        inventoryTransactionSheet2 = new InventoryTransactionSheet(typeName: typeNameReturnTransactionSTORAGE, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"001", factory: factory1, site: site1).save(failOnError: true, flush: true)
        // 銷貨異動單據
        inventoryTransactionSheet3 = new InventoryTransactionSheet(typeName: typeNameTransactionSALE, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"001", factory: factory1, site: site1).save(failOnError: true, flush: true)
        // 領用異動單據
        inventoryTransactionSheet4 = new InventoryTransactionSheet(typeName: typeNameTransactionREQUISITION, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"001", factory: factory1, site: site1).save(failOnError: true, flush: true)
        // 調整異動單據
        // inventoryTransactionSheet5 = new InventoryTransactionSheet(typeName: typeNameTransactionADJUSTMENT, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"001", factory: factory1, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopinventorytransactionsheet = new InventoryTransactionSheet(typeName: looptypenameTRANSACTION1, name: dateService.getUserStrDate("yyyyMMdd", "GMT+0800")+"00${it}", factory: loopfactory1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopinventorytransactionsheet1 = loopinventorytransactionsheet)
            }
        }

    }

     def createInventoryTransactionSheetDet = { createLoopData ->
        inventoryTransactionSheetDet11 = new InventoryTransactionSheetDet(typeName: inventoryTransactionSheet1.typeName, name: inventoryTransactionSheet1.name, sequence: 1, header: inventoryTransactionSheet1,
            batch: transactionBatch11, item: transactionBatch11.item, outWarehouse: materialWarehouse11, outWarehouseLocation: materialWarehouseLocation111, qty: 1000, unit: transactionBatch11.item.unit, site: site1).save(failOnError: true, flush: true)
        inventoryTransactionSheetDet21 = new InventoryTransactionSheetDet(typeName: inventoryTransactionSheet2.typeName, name: inventoryTransactionSheet2.name, sequence: 1, header: inventoryTransactionSheet2,
            batch: transactionBatch21, item: transactionBatch21.item, outWarehouse: materialWarehouse11, outWarehouseLocation: materialWarehouseLocation111, qty: 500, unit: transactionBatch21.item.unit, site: site1).save(failOnError: true, flush: true)
        inventoryTransactionSheetDet31 = new InventoryTransactionSheetDet(typeName: inventoryTransactionSheet3.typeName, name: inventoryTransactionSheet3.name, sequence: 1, header: inventoryTransactionSheet3,
            batch: transactionBatch31, item: transactionBatch31.item, outWarehouse: materialWarehouse11, outWarehouseLocation: materialWarehouseLocation111, qty: 1500, unit: transactionBatch31.item.unit, site: site1).save(failOnError: true, flush: true)
        inventoryTransactionSheetDet41 = new InventoryTransactionSheetDet(typeName: inventoryTransactionSheet4.typeName, name: inventoryTransactionSheet4.name, sequence: 1, header: inventoryTransactionSheet4,
            batch: transactionBatch41, item: transactionBatch41.item, outWarehouse: materialWarehouse11, outWarehouseLocation: materialWarehouseLocation111, qty: 600, unit: transactionBatch41.item.unit, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(inventoryTransactionSheetDet11)
        processInventoryQtyAndTransactionRecord(inventoryTransactionSheetDet21)
        processInventoryQtyAndTransactionRecord(inventoryTransactionSheetDet31)
        processInventoryQtyAndTransactionRecord(inventoryTransactionSheetDet41)

         if (createLoopData) {
            (1..100).each {
                def loopinventorytransactionsheetdet = new InventoryTransactionSheetDet(typeName: loopinventorytransactionsheet1.typeName, name: loopinventorytransactionsheet1.name, sequence: it, header: loopinventorytransactionsheet1,
                    batch: loopbatch2, item: loopbatch2.item, outWarehouse: loopwarehouse1, outWarehouseLocation: loopwarehouselocation1, qty: 100, unit: loopbatch2.item.unit, site: loopsite1).save(failOnError: true, flush: true)
                
                processInventoryQtyAndTransactionRecord(loopinventorytransactionsheetdet)

                it != 1 ?: (loopinventorytransactionsheetdet1 = loopinventorytransactionsheetdet)
            }
        }
    }

    def createPurchaseSheet = { createLoopData ->

        purchaseSheet1 = new PurchaseSheet(typeName: typeNamePurchaseSheet, name: "001", factory: factory1, supplier: supplier1, site: site1).save(failOnError: true, flush: true)
        purchaseSheet2 = new PurchaseSheet(typeName: typeNamePurchaseSheet, name: "002", factory: factory1, supplier: supplier2, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            looptypenamePURCHASESHEET1 = TypeName.findByNameAndSite("looptypename_PurchaseSheet", loopsite1)

            (1..100).each {
                def looppurchasesheet = new PurchaseSheet(typeName: looptypenamePURCHASESHEET1, name: "${it}", factory: loopfactory1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (looppurchasesheet1 = looppurchasesheet)
            }
        }
    }
    def createPurchaseSheetDet = { createLoopData ->
        //進貨單
        purchaseSheetDet11 = new PurchaseSheetDet(header: purchaseSheet1, typeName: purchaseSheet1.typeName, name: purchaseSheet1.name, sequence: 1, item: material1MaterialBrand1, unit: material1MaterialBrand1.unit, batch: material1MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 5000, price: 20, site: site1).save(failOnError: true, flush: true)
        purchaseSheetDet12 = new PurchaseSheetDet(header: purchaseSheet1, typeName: purchaseSheet1.typeName, name: purchaseSheet1.name, sequence: 2, item: material2MaterialBrand1, unit: material2MaterialBrand1.unit, batch: material2MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 10000, price: 10, site: site1).save(failOnError: true, flush: true)
        purchaseSheetDet13 = new PurchaseSheetDet(header: purchaseSheet1, typeName: purchaseSheet1.typeName, name: purchaseSheet1.name, sequence: 3, item: material3MaterialBrand1, unit: material3MaterialBrand1.unit, batch: material3MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 15000, price: 2, site: site1).save(failOnError: true, flush: true)
        purchaseSheetDet14 = new PurchaseSheetDet(header: purchaseSheet1, typeName: purchaseSheet1.typeName, name: purchaseSheet1.name, sequence: 4, item: material4MaterialBrand1, unit: material4MaterialBrand1.unit, batch: material4MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 20000, price: 1, site: site1).save(failOnError: true, flush: true)

        purchaseSheetDet21 = new PurchaseSheetDet(header: purchaseSheet2, typeName: purchaseSheet2.typeName, name: purchaseSheet2.name, sequence: 1, item: material5MaterialBrand2, unit: material5MaterialBrand2.unit, batch: material5MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 5000, price: 100, site: site1).save(failOnError: true, flush: true)
        purchaseSheetDet22 = new PurchaseSheetDet(header: purchaseSheet2, typeName: purchaseSheet2.typeName, name: purchaseSheet2.name, sequence: 2, item: material6MaterialBrand2, unit: material6MaterialBrand2.unit, batch: material6MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 10000, price: 10, site: site1).save(failOnError: true, flush: true)
        purchaseSheetDet23 = new PurchaseSheetDet(header: purchaseSheet2, typeName: purchaseSheet2.typeName, name: purchaseSheet2.name, sequence: 3, item: material7MaterialBrand2, unit: material7MaterialBrand2.unit, batch: material7MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 15000, price: 200, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(purchaseSheetDet11)
        processInventoryQtyAndTransactionRecord(purchaseSheetDet12)
        processInventoryQtyAndTransactionRecord(purchaseSheetDet13)
        processInventoryQtyAndTransactionRecord(purchaseSheetDet14)

        processInventoryQtyAndTransactionRecord(purchaseSheetDet21)
        processInventoryQtyAndTransactionRecord(purchaseSheetDet22)
        processInventoryQtyAndTransactionRecord(purchaseSheetDet23)

        processSheetPrice(purchaseSheetDet11)
        processSheetPrice(purchaseSheetDet12)
        processSheetPrice(purchaseSheetDet13)
        processSheetPrice(purchaseSheetDet14)

        processSheetPrice(purchaseSheetDet21)
        processSheetPrice(purchaseSheetDet22)
        processSheetPrice(purchaseSheetDet23)

        if (createLoopData) {
            (1..100).each {
                def looppurchasesheetdet = new PurchaseSheetDet(header: looppurchasesheet1, typeName: looppurchasesheet1.typeName, name: looppurchasesheet1.name, sequence: it, item: loopbatch52.item, unit: loopbatch52.item.unit, batch: loopbatch52, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, qty: 2000, price: 100, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(looppurchasesheetdet)

                it != 1 ?: (looppurchasesheetdet1 = looppurchasesheetdet)
            }
        }
    }

    def createMaterialSheet = { createLoopData ->

        //半成品領料單
        materialSheet1 = new MaterialSheet(typeName: typeNameMaterialSheet, name: "001", factory: workstation11.factory, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        //成品領料單
        materialSheet2 = new MaterialSheet(typeName: typeNameMaterialSheet, name: "002", factory: workstation11.factory, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        //半成品託外領料單
        materialSheet3 = new MaterialSheet(typeName: typeNameOutSrcMaterialSheet, name: "003", factory: factory1, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {

            (1..50).each {
                def loopmaterialsheet = new MaterialSheet(typeName: looptypenameMATERIALSHEET1, name: "${it}", factory: loopfactory1, workstation: loopworkstation1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopmaterialsheet1 = loopmaterialsheet)
            }
            (51..100).each {
                new MaterialSheet(typeName: looptypenameOUTSRCMATERIALSHEET1, name: "${it}", factory: loopfactory1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
            }
        }
    }

    def createMaterialSheetDet = { createLoopData ->
        materialSheetDet11 = new MaterialSheetDet(header: materialSheet1, typeName: materialSheet1.typeName, name: materialSheet1.name, sequence: 1, item: material5MaterialBrand2, unit: material5MaterialBrand2.unit, batch: material5MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 1000, manufactureOrder: manufactureOrder1, batchOperation: Batch.findByNameAndSite(manufactureOrder1.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet12 = new MaterialSheetDet(header: materialSheet1, typeName: materialSheet1.typeName, name: materialSheet1.name, sequence: 2, item: material6MaterialBrand2, unit: material6MaterialBrand2.unit, batch: material6MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 2000, manufactureOrder: manufactureOrder1, batchOperation: Batch.findByNameAndSite(manufactureOrder1.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet13 = new MaterialSheetDet(header: materialSheet1, typeName: materialSheet1.typeName, name: materialSheet1.name, sequence: 3, item: material7MaterialBrand2, unit: material7MaterialBrand2.unit, batch: material7MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 3000, manufactureOrder: manufactureOrder1, batchOperation: Batch.findByNameAndSite(manufactureOrder1.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        manufactureOrder1.status = ManufactureOrderStatus.DISBURSED
        manufactureOrder1.save(failOnError: true, flush: true)

        //成品領料單
        materialSheetDet21 = new MaterialSheetDet(header: materialSheet2, typeName: materialSheet2.typeName, name: materialSheet2.name, sequence: 1, item: material1MaterialBrand1, unit: material1MaterialBrand1.unit, batch: material1MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 1000, manufactureOrder: manufactureOrder2, batchOperation: Batch.findByNameAndSite(manufactureOrder2.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet22 = new MaterialSheetDet(header: materialSheet2, typeName: materialSheet2.typeName, name: materialSheet2.name, sequence: 2, item: material2MaterialBrand1, unit: material2MaterialBrand1.unit, batch: material2MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 2000, manufactureOrder: manufactureOrder2, batchOperation: Batch.findByNameAndSite(manufactureOrder2.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet23 = new MaterialSheetDet(header: materialSheet2, typeName: materialSheet2.typeName, name: materialSheet2.name, sequence: 3, item: material3MaterialBrand1, unit: material3MaterialBrand1.unit, batch: material3MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 3000, manufactureOrder: manufactureOrder2, batchOperation: Batch.findByNameAndSite(manufactureOrder2.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet24 = new MaterialSheetDet(header: materialSheet2, typeName: materialSheet2.typeName, name: materialSheet2.name, sequence: 4, item: material4MaterialBrand1, unit: material4MaterialBrand1.unit, batch: material4MaterialBrand1Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation111, qty: 4000, manufactureOrder: manufactureOrder2, batchOperation: Batch.findByNameAndSite(manufactureOrder2.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet25 = new MaterialSheetDet(header: materialSheet2, typeName: materialSheet2.typeName, name: materialSheet2.name, sequence: 5, item: semiProduct1ProductBrand1, unit: semiProduct1ProductBrand1.unit, batch: semiProduct1ProductBrand1Batch1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, qty: 500, manufactureOrder: manufactureOrder2, batchOperation: Batch.findByNameAndSite(manufactureOrder2.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        manufactureOrder2.status = ManufactureOrderStatus.DISBURSED
        manufactureOrder2.save(failOnError: true, flush: true)

        //半成品託外領料單
        materialSheetDet31 = new MaterialSheetDet(header: materialSheet3, typeName: materialSheet3.typeName, name: materialSheet3.name, sequence: 1, item: material5MaterialBrand2, unit: material5MaterialBrand2.unit, batch: material5MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 500, manufactureOrder: manufactureOrder3, batchOperation: Batch.findByNameAndSite(manufactureOrder3.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet32 = new MaterialSheetDet(header: materialSheet3, typeName: materialSheet3.typeName, name: materialSheet3.name, sequence: 2, item: material6MaterialBrand2, unit: material6MaterialBrand2.unit, batch: material6MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 500, manufactureOrder: manufactureOrder3, batchOperation: Batch.findByNameAndSite(manufactureOrder3.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        materialSheetDet33 = new MaterialSheetDet(header: materialSheet3, typeName: materialSheet3.typeName, name: materialSheet3.name, sequence: 3, item: material7MaterialBrand2, unit: material7MaterialBrand2.unit, batch: material7MaterialBrand2Batch1, warehouse: materialWarehouse11, warehouseLocation: materialWarehouseLocation112, qty: 500, manufactureOrder: manufactureOrder3, batchOperation: Batch.findByNameAndSite(manufactureOrder3.batchName, site1).batchOperations[0], releaseOrder: 1, site: site1).save(failOnError: true, flush: true)
        manufactureOrder1.status = ManufactureOrderStatus.DISBURSED
        manufactureOrder1.save(failOnError: true, flush: true)
        
        processInventoryQtyAndTransactionRecord(materialSheetDet11)
        processInventoryQtyAndTransactionRecord(materialSheetDet12)
        processInventoryQtyAndTransactionRecord(materialSheetDet13)

        processInventoryQtyAndTransactionRecord(materialSheetDet21)
        processInventoryQtyAndTransactionRecord(materialSheetDet22)
        processInventoryQtyAndTransactionRecord(materialSheetDet23)
        processInventoryQtyAndTransactionRecord(materialSheetDet24)
        processInventoryQtyAndTransactionRecord(materialSheetDet25)

        processInventoryQtyAndTransactionRecord(materialSheetDet31)
        processInventoryQtyAndTransactionRecord(materialSheetDet32)
        processInventoryQtyAndTransactionRecord(materialSheetDet33)

        if (createLoopData) {
            (1..100).each {
                def loopmaterialsheetdet = new MaterialSheetDet(header: loopmaterialsheet1, typeName: loopmaterialsheet1.typeName, name: loopmaterialsheet1.name, sequence: it, item: loopbatch52.item, unit: loopbatch52.item.unit, batch: loopbatch52, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, qty: 100, manufactureOrder: loopmanufactureorder1, batchOperation: Batch.findByNameAndSite(loopmanufactureorder1.batchName, loopsite1).batchOperations[0], releaseOrder: 1, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopmaterialsheetdet)

                it != 1 ?: (loopmaterialsheetdet1 = loopmaterialsheetdet)
            }
        }
    }

    def createMaterialSheetDetCustomize = { createLoopData ->

        //所有領料單單身測試
        materialSheetDetCustomize1 = new MaterialSheetDetCustomize(title: "領料單-布林參數", fieldType: FieldType.BOOLEAN, defaultValue: "true", unit: null, site: site1).save(failOnError: true, flush: true)
        //品項測試::原料5品牌2-病蟲草害-無
        material5MaterialSheetDetCustomize1 = new MaterialSheetDetCustomize(item: material5MaterialBrand2, title: "原料5品牌2-病蟲草害-無-數字參數", fieldType: FieldType.DOUBLE, defaultValue: "55.22", unit: null, site: site1).save(failOnError: true, flush: true)
        //品項類別一測試::病蟲草害
        itemCategoryLayer12MaterialSheetDetCustomize1 = new MaterialSheetDetCustomize(itemCategoryLayer1: itemCategoryLayer12, title: "類別一-病蟲草害-字串參數", fieldType: FieldType.STRING, defaultValue: null, unit: null, site: site1).save(failOnError: true, flush: true)
        //品項類別二測試::農藥
        itemCategoryLayer221MaterialSheetDetCustomize1 = new MaterialSheetDetCustomize(itemCategoryLayer1: itemCategoryLayer12, itemCategoryLayer2: itemCategoryLayer221, title: "類別二-農藥-整數參數", fieldType: FieldType.INTEGER, defaultValue: "1000", unit: "倍", site: site1).save(failOnError: true, flush: true)
        //品項+類別一+類別二測試::原料6品牌2-病蟲草害-農藥
        material6ItemCategoryLayer12ItemCategoryLayer221MaterialSheetDetCustomize1 = new MaterialSheetDetCustomize(itemCategoryLayer1: itemCategoryLayer12, itemCategoryLayer2: itemCategoryLayer221, item: material6MaterialBrand2, title: "原料6品牌2-類別一-病蟲草害-類別二-農藥-整數參數", fieldType: FieldType.INTEGER, defaultValue: null, unit: null, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..30).each {
                def loopmaterialsheetdetcustomize = new MaterialSheetDetCustomize(item: loopitem1, title: "loop${it}", fieldType: FieldType.INTEGER, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopmaterialsheetdetcustomize1 = loopmaterialsheetdetcustomize)
            }
            (31..60).each {
                new MaterialSheetDetCustomize(itemCategoryLayer1: loopitemcategorylayer11, title: "loop${it}", fieldType: FieldType.DOUBLE, site: loopsite1).save(failOnError: true, flush: true)
            }
            (61..90).each {
                new MaterialSheetDetCustomize(itemCategoryLayer1: loopitemcategorylayer11, itemCategoryLayer2: loopitemcategorylayer211, title: "loop${it}", fieldType: FieldType.INTEGER, site: loopsite1).save(failOnError: true, flush: true)
            }
            (91..100).each {
                new MaterialSheetDetCustomize(title: "loop${it}", fieldType: FieldType.STRING, site: loopsite1).save(failOnError: true, flush: true)
            }
        }
    }

    def createMaterialSheetDetCustomizeDet = { createLoopData ->


        //所有領料單參數
        materialSheetDet31MaterialSheetDetCustomizeDet1 = new MaterialSheetDetCustomizeDet(materialSheetDet: materialSheetDet31, materialSheetDetCustomize: materialSheetDetCustomize1, value: "false", site: site1).save(failOnError: true, flush: true)

        //品項測試::原料5品牌2-病蟲草害-無
        materialSheetDet31Material5MaterialSheetDetCustomize1 = new MaterialSheetDetCustomizeDet(materialSheetDet: materialSheetDet31, materialSheetDetCustomize: material5MaterialSheetDetCustomize1, value: "20.11", site: site1).save(failOnError: true, flush: true)

        //品項類別一測試::病蟲草害
        materialSheetDet31itemCategoryLayer12MaterialSheetDetCustomize1 = new MaterialSheetDetCustomizeDet(materialSheetDet: materialSheetDet31, materialSheetDetCustomize: itemCategoryLayer12MaterialSheetDetCustomize1, value: "小蚊子", site: site1).save(failOnError: true, flush: true)

        //品項類別二測試::農藥
        materialSheetDet32itemCategoryLayer221MaterialSheetDetCustomize1 = new MaterialSheetDetCustomizeDet(materialSheetDet: materialSheetDet32, materialSheetDetCustomize: itemCategoryLayer221MaterialSheetDetCustomize1, value: "85", site: site1).save(failOnError: true, flush: true)

        //品項+類別一+類別二測試::原料6品牌2-病蟲草害-農藥
        materialSheetDet32material6ItemCategoryLayer12ItemCategoryLayer221MaterialSheetDetCustomize1 = new MaterialSheetDetCustomizeDet(materialSheetDet: materialSheetDet32, materialSheetDetCustomize: material6ItemCategoryLayer12ItemCategoryLayer221MaterialSheetDetCustomize1, value: "31", site: site1).save(failOnError: true, flush: true)

    }

    def createStockInSheet = { createLoopData ->

        stockInSheet1 = new StockInSheet(typeName: typeNameStockInSheet, name: "001", factory: workstation11.factory, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        stockInSheet2 = new StockInSheet(typeName: typeNameStockInSheet, name: "002", factory: workstation11.factory, workstation: workstation11, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopstockinsheet = new StockInSheet(typeName: looptypenameSTOCKINSHEET1, name: "${it}", factory: loopfactory1, workstation: loopworkstation1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopstockinsheet1 = loopstockinsheet)
            }
        }
    }

    def createStockInSheetDet = { createLoopData ->

        //半成品入庫單
        stockInSheetDet11 = new StockInSheetDet(header: stockInSheet1, typeName: stockInSheet1.typeName, name: stockInSheet1.name,sequence: 1, item: manufactureOrder1.item, unit: manufactureOrder1.item.unit, batch: semiProduct1ProductBrand1Batch1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, qty: 500000, manufactureOrder: manufactureOrder1, site: site1).save(failOnError: true, flush: true)

        //成品入庫單
        stockInSheetDet21 = new StockInSheetDet(header: stockInSheet2, typeName: stockInSheet2.typeName, name: stockInSheet2.name,sequence: 1, item: manufactureOrder2.item, unit: manufactureOrder2.item.unit, batch: product1ProductBrand1Batch1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, qty: 1000000,manufactureOrder: manufactureOrder2, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(stockInSheetDet11)
        processInventoryQtyAndTransactionRecord(stockInSheetDet21)

        if (createLoopData) {
            (1..100).each {
                def loopstockinsheetdet = new StockInSheetDet(header: loopstockinsheet1, typeName: loopstockinsheet1.typeName, name: loopstockinsheet1.name, sequence: it, item: loopmanufactureorder1.item, unit: loopmanufactureorder1.item.unit, batch: loopbatch1, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, qty: 100000, manufactureOrder: loopmanufactureorder1, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopstockinsheetdet)

                it != 1 ?: (loopstockinsheetdet1 = loopstockinsheetdet)
            }
        }
    }

    def createOutSrcPurchaseSheet = { createLoopData ->

        outSrcPurchaseSheet1 = new OutSrcPurchaseSheet(typeName: typeNameOutSrcPurchaseSheet, name: "001", factory: factory1, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopoutsrcpurchasesheet = new OutSrcPurchaseSheet(typeName: looptypenameOUTSRCPURCHASESHEET1, name: "${it}", factory: loopfactory1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopoutsrcpurchasesheet1 = loopoutsrcpurchasesheet)
            }
        }
    }

    def createOutSrcPurchaseSheetDet = { createLoopData ->

        //半成品託外進貨單
        outSrcPurchaseSheetDet11 = new OutSrcPurchaseSheetDet(header: outSrcPurchaseSheet1, typeName: outSrcPurchaseSheet1.typeName, name: outSrcPurchaseSheet1.name, sequence: 1, item: semiProduct1ProductBrand1, unit: semiProduct1ProductBrand1.unit, batch: semiProduct1ProductBrand1Batch2, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation112, qty: 10000, manufactureOrder: manufactureOrder3, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(outSrcPurchaseSheetDet11)

        if (createLoopData) {
            (1..100).each {
                def loopoutsrcpurchasesheetdet = new OutSrcPurchaseSheetDet(header: loopoutsrcpurchasesheet1, typeName: loopoutsrcpurchasesheet1.typeName, name: loopoutsrcpurchasesheet1.name, sequence: it, item: loopbatch51.item, unit: loopbatch51.item.unit, batch: loopbatch51, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, qty: 100000, manufactureOrder: loopmanufactureorder51, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopoutsrcpurchasesheetdet)

                it != 1 ?: (loopoutsrcpurchasesheetdet1 = loopoutsrcpurchasesheetdet)
            }
        }
    }

    def createSaleSheet = { createLoopData ->
        //成品銷貨
        saleSheet1 = new SaleSheet(typeName: typeNameSaleSheet, name: "001", factory: factory1, customer: customer1, site: site1).save(failOnError: true, flush: true)
        //託外半成品銷貨
        saleSheet2 = new SaleSheet(typeName: typeNameSaleSheet, name: "002", factory: factory1, customer: customer2, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopsalesheet = new SaleSheet(typeName: looptypenameSALESHEET1, name: "${it}", factory: loopfactory1, customer: loopcustomer1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopsalesheet1 = loopsalesheet)
            }
        }
    }

    def createSaleSheetDet = { createLoopData ->
        
        //成品銷貨
        saleSheetDet11 = new SaleSheetDet(header: saleSheet1, typeName: saleSheet1.typeName, name: saleSheet1.name,sequence: 1, item: product1ProductBrand1, unit: product1ProductBrand1.unit, batch: product1ProductBrand1Batch1, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation111, qty: 5000, customerOrderDet: customerOrderDet11, site: site1).save(failOnError: true, flush: true)

        //託外半成品銷貨
        saleSheetDet21 = new SaleSheetDet(header: saleSheet2, typeName: saleSheet2.typeName, name: saleSheet2.name,sequence: 1, item: semiProduct1ProductBrand1, unit: semiProduct1ProductBrand1.unit, batch: semiProduct1ProductBrand1Batch2, warehouse: productWarehouse11, warehouseLocation: productWarehouseLocation112, qty: 1000, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(saleSheetDet11)
        processInventoryQtyAndTransactionRecord(saleSheetDet21)

        if (createLoopData) {
            (1..100).each {
                def loopsalesheetdet = new SaleSheetDet(header: loopsalesheet1, typeName: loopsalesheet1.typeName, name: loopsalesheet1.name, sequence: it, item: loopbatch1.item, unit: loopbatch1.item.unit, batch: loopbatch1, warehouse: loopwarehouse1, warehouseLocation: loopwarehouselocation1, qty: 1000, customerOrderDet: loopcustomerorderdet1, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopsalesheetdet)

                it != 1 ?: (loopsalesheetdet1 = loopsalesheetdet)
            }
        }
    }

    //************************************退單*****************************************

    def createPurchaseReturnSheet = { createLoopData ->
        purchaseReturnSheet1 = new PurchaseReturnSheet(typeName: typeNamePurchaseReturnSheet, name: "001", factory: purchaseSheet1.factory, supplier: purchaseSheet1.supplier, site: site1).save(failOnError: true, flush: true)
        purchaseReturnSheet2 = new PurchaseReturnSheet(typeName: typeNamePurchaseReturnSheet, name: "002", factory: purchaseSheet2.factory, supplier: purchaseSheet2.supplier, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def looppurchasereturnsheet = new PurchaseReturnSheet(typeName: looptypenamePURCHASERETURNSHEET1, name: "${it}", factory: looppurchasesheet1.factory, supplier: looppurchasesheet1.supplier, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (looppurchasereturnsheet1 = looppurchasereturnsheet)
            }
        }
    }
    def createPurchaseReturnSheetDet = { createLoopData ->
        purchaseReturnSheetDet13 = new PurchaseReturnSheetDet(header: purchaseReturnSheet1, typeName: purchaseReturnSheet1.typeName, name: purchaseReturnSheet1.name, sequence: 3, purchaseSheetDet: purchaseSheetDet13, item: purchaseSheetDet13.item, unit: purchaseSheetDet13.item.unit, batch: purchaseSheetDet13.batch, warehouse: purchaseSheetDet13.warehouse, warehouseLocation: purchaseSheetDet13.warehouseLocation, qty: 1000, site: site1).save(failOnError: true, flush: true)
        purchaseReturnSheetDet14 = new PurchaseReturnSheetDet(header: purchaseReturnSheet1, typeName: purchaseReturnSheet1.typeName, name: purchaseReturnSheet1.name, sequence: 4, purchaseSheetDet: purchaseSheetDet14, item: purchaseSheetDet14.item, unit: purchaseSheetDet14.item.unit, batch: purchaseSheetDet14.batch, warehouse: purchaseSheetDet14.warehouse, warehouseLocation: purchaseSheetDet14.warehouseLocation, qty: 1000, site: site1).save(failOnError: true, flush: true)

        purchaseReturnSheetDet23 = new PurchaseReturnSheetDet(header: purchaseReturnSheet2, typeName: purchaseReturnSheet2.typeName, name: purchaseReturnSheet2.name, sequence: 3, purchaseSheetDet: purchaseSheetDet23, item: purchaseSheetDet23.item, unit: purchaseSheetDet23.item.unit, batch: purchaseSheetDet23.batch, warehouse: purchaseSheetDet23.warehouse, warehouseLocation: purchaseSheetDet23.warehouseLocation, qty: 1000, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(purchaseReturnSheetDet13)
        processInventoryQtyAndTransactionRecord(purchaseReturnSheetDet14)
        processInventoryQtyAndTransactionRecord(purchaseReturnSheetDet23)

        if (createLoopData) {
            (1..100).each {
                def looppurchasereturnsheetdet = new PurchaseReturnSheetDet(header: looppurchasereturnsheet1, typeName: looppurchasereturnsheet1.typeName, name: looppurchasereturnsheet1.name, sequence: it, purchaseSheetDet: looppurchasesheetdet1, item: looppurchasesheetdet1.item, unit: looppurchasesheetdet1.item.unit, batch: looppurchasesheetdet1.batch, warehouse: looppurchasesheetdet1.warehouse, warehouseLocation: looppurchasesheetdet1.warehouseLocation, qty: 1, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(looppurchasereturnsheetdet)

                it != 1 ?: (looppurchasereturnsheetdet1 = looppurchasereturnsheetdet)
            }
        }
    }

    def createMaterialReturnSheet = { createLoopData ->

        materialReturnSheet1 = new MaterialReturnSheet(typeName: typeNameMaterialReturnSheet, name: "001", factory: materialSheet1.factory, supplier: materialSheet1.supplier, workstation: materialSheet1.workstation, site: site1).save(failOnError: true, flush: true)
        materialReturnSheet2 = new MaterialReturnSheet(typeName: typeNameMaterialReturnSheet, name: "002", factory: materialSheet2.factory, supplier: materialSheet2.supplier, workstation: materialSheet2.workstation, site: site1).save(failOnError: true, flush: true)
        materialReturnSheet3 = new MaterialReturnSheet(typeName: typeNameOutSrcMaterialReturnSheet, name: "003", factory: materialSheet2.factory, supplier: materialSheet3.supplier, workstation: materialSheet3.workstation, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopmaterialreturnsheet = new MaterialReturnSheet(typeName: looptypenameMATERIALRETURNSHEET1, name: "${it}", factory: loopmaterialsheet1.factory, supplier: loopmaterialsheet1.supplier, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopmaterialreturnsheet1 = loopmaterialreturnsheet)
            }
        }
    }

    def createMaterialReturnSheetDet = { createLoopData ->

        //成品退料單
        materialReturnSheetDet25 = new MaterialReturnSheetDet(header: materialReturnSheet2, typeName: materialReturnSheet2.typeName, name: materialReturnSheet2.name, sequence: 5, materialSheetDet: materialSheetDet25, item: materialSheetDet25.item, unit: materialSheetDet25.item.unit, batch: materialSheetDet25.batch, warehouse: materialSheetDet25.warehouse, warehouseLocation: materialSheetDet25.warehouseLocation, qty: 100, manufactureOrder: materialSheetDet25.manufactureOrder, site: site1).save(failOnError: true, flush: true)

        //半成品託外退料單
        materialReturnSheetDet31 = new MaterialReturnSheetDet(header: materialReturnSheet3, typeName: materialReturnSheet3.typeName, name: materialReturnSheet3.name, sequence: 1, materialSheetDet: materialSheetDet31, item: materialSheetDet31.item, unit: materialSheetDet31.item.unit, batch: materialSheetDet31.batch, warehouse: materialSheetDet31.warehouse, warehouseLocation: materialSheetDet31.warehouseLocation, qty: 200, manufactureOrder: materialSheetDet31.manufactureOrder, site: site1).save(failOnError: true, flush: true)
        materialReturnSheetDet32 = new MaterialReturnSheetDet(header: materialReturnSheet3, typeName: materialReturnSheet3.typeName, name: materialReturnSheet3.name, sequence: 2, materialSheetDet: materialSheetDet32, item: materialSheetDet32.item, unit: materialSheetDet32.item.unit, batch: materialSheetDet32.batch, warehouse: materialSheetDet32.warehouse, warehouseLocation: materialSheetDet32.warehouseLocation, qty: 200, manufactureOrder: materialSheetDet32.manufactureOrder, site: site1).save(failOnError: true, flush: true)
        materialReturnSheetDet33 = new MaterialReturnSheetDet(header: materialReturnSheet3, typeName: materialReturnSheet3.typeName, name: materialReturnSheet3.name, sequence: 3, materialSheetDet: materialSheetDet33, item: materialSheetDet33.item, unit: materialSheetDet33.item.unit, batch: materialSheetDet33.batch, warehouse: materialSheetDet33.warehouse, warehouseLocation: materialSheetDet33.warehouseLocation, qty: 200, manufactureOrder: materialSheetDet33.manufactureOrder, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(materialReturnSheetDet25)

        processInventoryQtyAndTransactionRecord(materialReturnSheetDet31)
        processInventoryQtyAndTransactionRecord(materialReturnSheetDet32)
        processInventoryQtyAndTransactionRecord(materialReturnSheetDet33)

        if (createLoopData) {
            (1..100).each {
                def loopmaterialreturnsheetdet = new MaterialReturnSheetDet(header: loopmaterialreturnsheet1, typeName: loopmaterialreturnsheet1.typeName, name: loopmaterialreturnsheet1.name, sequence: it, materialSheetDet: loopmaterialsheetdet1, item: loopmaterialsheetdet1.item, unit: loopmaterialsheetdet1.item.unit, batch: loopmaterialsheetdet1.batch, warehouse: loopmaterialsheetdet1.warehouse, warehouseLocation: loopmaterialsheetdet1.warehouseLocation, qty: 1, manufactureOrder: loopmaterialsheetdet1.manufactureOrder, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopmaterialreturnsheetdet)

                it != 1 ?: (loopmaterialreturnsheetdet1 = loopmaterialreturnsheetdet)
            }
        }

    }

    def createOutSrcPurchaseReturnSheet = { createLoopData ->
        outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(typeName: typeNameOutSrcPurchaseReturnSheet, name: "001", factory: outSrcPurchaseSheet1.factory, supplier: outSrcPurchaseSheet1.supplier, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopoutsrcpurchasereturnsheet = new OutSrcPurchaseReturnSheet(typeName: looptypenameOUTSRCPURCHASERETURNSHEET1, name: "${it}", factory: loopoutsrcpurchasesheet1.factory, supplier: loopoutsrcpurchasesheet1.supplier, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopoutsrcpurchasereturnsheet1 = loopoutsrcpurchasereturnsheet)
            }
        }
    }

    def createOutSrcPurchaseReturnSheetDet = { createLoopData ->

        //半成品託外退貨單
        outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(header: outSrcPurchaseReturnSheet1, typeName: outSrcPurchaseReturnSheet1.typeName, name: outSrcPurchaseReturnSheet1.name, sequence: 1, outSrcPurchaseSheetDet: outSrcPurchaseSheetDet11, item: outSrcPurchaseSheetDet11.item, unit: outSrcPurchaseSheetDet11.item.unit, batch: outSrcPurchaseSheetDet11.batch, warehouse: outSrcPurchaseSheetDet11.warehouse, warehouseLocation: outSrcPurchaseSheetDet11.warehouseLocation, qty: 1000, manufactureOrder: outSrcPurchaseSheetDet11.manufactureOrder, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(outSrcPurchaseReturnSheetDet11)

        if (createLoopData) {
            (1..100).each {
                def loopoutsrcpurchasereturnsheetdet = new OutSrcPurchaseReturnSheetDet(header: loopoutsrcpurchasereturnsheet1, typeName: loopoutsrcpurchasereturnsheet1.typeName, name: loopoutsrcpurchasereturnsheet1.name, sequence: it, outSrcPurchaseSheetDet: loopoutsrcpurchasesheetdet1, item: loopoutsrcpurchasesheetdet1.item, unit: loopoutsrcpurchasesheetdet1.item.unit, batch: loopoutsrcpurchasesheetdet1.batch, warehouse: loopoutsrcpurchasesheetdet1.warehouse, warehouseLocation: loopoutsrcpurchasesheetdet1.warehouseLocation, qty: 1, manufactureOrder: loopoutsrcpurchasesheetdet1.manufactureOrder, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopoutsrcpurchasereturnsheetdet)

                it != 1 ?: (loopoutsrcpurchasereturnsheetdet1 = loopoutsrcpurchasereturnsheetdet)
            }
        }

    }

    def createSaleReturnSheet = { createLoopData ->

        //成品銷退
        saleReturnSheet1 = new SaleReturnSheet(typeName: typeNameSaleReturnSheet, name: "001", factory: saleSheet1.factory, customer: saleSheet1.customer, site: site1).save(failOnError: true, flush: true)
        //託外半成品銷退
        saleReturnSheet2 = new SaleReturnSheet(typeName: typeNameSaleReturnSheet, name: "002", factory: saleSheet2.factory, customer: saleSheet2.customer, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopsalereturnsheet = new SaleReturnSheet(typeName: looptypenameSALERETURNSHEET1, name: "${it}", factory: loopsalesheet1.factory, customer: loopsalesheet1.customer, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopsalereturnsheet1 = loopsalereturnsheet)
            }
        }
    }

    def createSaleReturnSheetDet = { createLoopData ->
        //成品銷退
        saleReturnSheetDet11 = new SaleReturnSheetDet(header: saleReturnSheet1, typeName: saleReturnSheet1.typeName, name: saleReturnSheet1.name, sequence: 1, saleSheetDet: saleSheetDet11, item: saleSheetDet11.item, unit: saleSheetDet11.item.unit, batch: saleSheetDet11.batch, warehouse: saleSheetDet11.warehouse, warehouseLocation: saleSheetDet11.warehouseLocation, qty: 1000, customerOrderDet: saleSheetDet11.customerOrderDet, site: site1).save(failOnError: true, flush: true)

        processInventoryQtyAndTransactionRecord(saleReturnSheetDet11)

        if (createLoopData) {
            (1..100).each {
                def loopsalereturnsheetdet = new SaleReturnSheetDet(header: loopsalereturnsheet1, typeName: loopsalereturnsheet1.typeName, name: loopsalereturnsheet1.name, sequence: it, saleSheetDet: loopsalesheetdet1, item: loopsalesheetdet1.item, unit: loopsalesheetdet1.item.unit, batch: loopsalesheetdet1.batch, warehouse: loopsalesheetdet1.warehouse, warehouseLocation: loopsalesheetdet1.warehouseLocation, qty: 10, customerOrderDet: loopsalesheetdet1.customerOrderDet, site: loopsite1).save(failOnError: true, flush: true)
                processInventoryQtyAndTransactionRecord(loopsalereturnsheetdet)

                it != 1 ?: (loopsalereturnsheetdet1 = loopsalereturnsheetdet)
            }
        }

    }

    def createParam = { createLoopData ->

        paramInspect1 = new Param(name: "paramInspect1", title: "益多松", unit: "ppm", defaultValue: "0", paramType: ParamType.INTEGER, site: site1).save(failOnError: true, flush: true)
        paramInspect2 = new Param(name: "paramInspect2", title: "芬殺蟎", unit: "ppm", defaultValue: false, paramType: ParamType.BOOLEAN, site: site1).save(failOnError: true, flush: true)
        paramInspect3 = new Param(name: "paramInspect3", title: "芬普蟎", unit: "ppm", defaultValue: "a little", paramType: ParamType.STRING, site: site1).save(failOnError: true, flush: true)
        paramInspect4 = new Param(name: "paramInspect4", title: "Image", paramType: ParamType.IMAGE, site: site1).save(failOnError: true, flush: true)
        paramInspect5 = new Param(name: "paramInspect5", title: "File", paramType: ParamType.FILE, site: site1).save(failOnError: true, flush: true)
        paramOther1 = new Param(name: "paramOther1", title: "AirLevel", defaultValue: "100", paramType: ParamType.INTEGER, site: site1).save(failOnError: true, flush: true)
        paramNutrition1 = new Param(name: "paramNutrition1", title: "Pack", unit: "毫升/g", paramType: ParamType.INTEGER, site: site1).save(failOnError: true, flush: true)
        paramNutrition2 = new Param(name: "paramNutrition2", title: "Image", unit: "", paramType: ParamType.IMAGE, site: site1).save(failOnError: true, flush: true)
        paramNutrition3 = new Param(name: "paramNutrition3", title: "File", unit: "", paramType: ParamType.FILE, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopparam = new Param(name: "loopparam_${it}", title: "參數_${it}", unit: "單位_${it}", paramType: ParamType.INTEGER, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopparam1 = loopparam)
            }
        }
    }

    def createReport = { createLoopData ->
        reportInspect1 = new Report(name: "reportInspect1", title: "成品檢驗報告集", reportType: ReportType.INSPECT, item: product1ProductBrand1, site: site1).save(failOnError: true, flush: true)
        reportOther1 = new Report(name: "reportOther1", title: "其他收集資料", item: product1ProductBrand1, site: site1).save(failOnError: true, flush: true)
        reportNutrition1 = new Report(name: "reportNutrition1", title: "營養標示履歷", reportType: ReportType.NUTRITION, item: product1ProductBrand1, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..100).each {
                def loopreport = new Report(name: "loopreport_${it}", title: "履歷_${it}", reportType: ReportType.INSPECT, item: loopitem1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopreport1 = loopreport)
            }
        }
    }

    def createReportParam = { createLoopData ->
        reportParamInspect1 = new ReportParam(report: reportInspect1, param: paramInspect1, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        reportParamInspect2 = new ReportParam(report: reportInspect1, param: paramInspect2, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        reportParamInspect3 = new ReportParam(report: reportInspect1, param: paramInspect3, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        reportParamInspect4 = new ReportParam(report: reportInspect1, param: paramInspect4, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)
        reportParamInspect5 = new ReportParam(report: reportInspect1, param: paramInspect5, operation: operationInspect, supplier: supplierInspect, site: site1).save(failOnError: true, flush: true)

        reportParamOther1 = new ReportParam(report: reportOther1, param: paramOther1, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        reportParamNutrition1 = new ReportParam(report: reportNutrition1, param: paramNutrition1, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        reportParamNutrition2 = new ReportParam(report: reportNutrition1, param: paramNutrition2, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)
        reportParamNutrition3 = new ReportParam(report: reportNutrition1, param: paramNutrition3, operation: operation2, workstation: workstation11, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..50).each {
                def loopreportparam = new ReportParam(report: loopreport1, param: Param.findByNameAndSite("loopparam_${it}", loopsite1), operation: loopoperation1, workstation: loopworkstation1, site: loopsite1).save(failOnError: true, flush: true)
                it != 1 ?: (loopreportparam1 = loopreportparam)
            }
            (51..100).each {
                new ReportParam(report: loopreport1, param: Param.findByNameAndSite("loopparam_${it}", loopsite1), operation: loopoperation1, supplier: loopsupplier1, site: loopsite1).save(failOnError: true, flush: true)
            }
        }
    }

    def createBatchReportDet = { createLoopData ->

        brdInspect1 = new BatchReportDet(batch: product1ProductBrand1Batch1, reportParam: reportParamInspect1, value: 10, batchOperation: product1ProductBrand1Batch1Operation5, site: site1).save(failOnError: true, flush: true)
        brdInspect2 = new BatchReportDet(batch: product1ProductBrand1Batch1, reportParam: reportParamInspect2, value: "true", batchOperation: product1ProductBrand1Batch1Operation5, site: site1).save(failOnError: true, flush: true)

        brdOther1 = new BatchReportDet(batch: product1ProductBrand1Batch1, reportParam: reportParamOther1, batchOperation: product1ProductBrand1Batch1Operation2, site: site1).save(failOnError: true, flush: true)
        brdNutrition1 = new BatchReportDet(batch: product1ProductBrand1Batch1, reportParam: reportParamNutrition1, batchOperation: product1ProductBrand1Batch1Operation2, site: site1).save(failOnError: true, flush: true)

        if (createLoopData) {
            (1..50).each {
                def loopparam = ReportParam.findByReportAndParamAndOperationAndWorkstationAndSupplierAndSite(loopreport1, Param.findByNameAndSite("loopparam_${it}", loopsite1), loopoperation1, loopworkstation1, null, loopsite1)
                new BatchReportDet(batch: loopbatch1, reportParam: loopparam, value: it, batchOperation: loopbatchoperation1, site: loopsite1).save(failOnError: true, flush: true)
            }
            (51..100).each {
                def loopparam = ReportParam.findByReportAndParamAndOperationAndWorkstationAndSupplierAndSite(loopreport1, Param.findByNameAndSite("loopparam_${it}", loopsite1), loopoperation1, null, loopsupplier1, loopsite1)
                new BatchReportDet(batch: loopbatch1, reportParam: loopparam, value: it, batchOperation: loopbatchoperation1, site: loopsite1).save(failOnError: true, flush: true)
            }
        }

    }

    def createBatchBox = { createLoopData ->
    }

    def createBatchBoxDet = { createLoopData ->
    }

    def createDeliveryKanban = { createLoopData ->
    }

    def createTransferOrder = { createLoopData ->
    }
    
    def createTransferOrderDet = { createLoopData ->
    }

    def createManufactureOrderDet = { createLoopData ->
        def site1 = Site.findByName("site1")
    }

    def processInventoryQtyAndTransactionRecord = { sheet ->
        def warehouse = sheet instanceof InventoryTransactionSheetDet ? sheet.outWarehouse : sheet.warehouse
        def warehouseLocation = sheet instanceof InventoryTransactionSheetDet ? sheet.outWarehouseLocation : sheet.warehouseLocation
        
        def record = new InventoryTransactionRecord(typeName: sheet.typeName, name: sheet.name, sequence: sheet.sequence,
            executionDate: sheet.header.executionDate, transactionType: sheet.typeName.transactionType, multiplier: sheet.typeName.multiplier,
            batch: sheet.batch, item: sheet.item, qty: sheet.qty, unit: sheet.unit,
            warehouse: warehouse, warehouseLocation: warehouseLocation,
            site: sheet.site).save(failOnError: true, flush: true)

        def inventory = Inventory.findByItemAndWarehouseAndSite(sheet.item, warehouse, sheet.site)
        def inventoryDetail = InventoryDetail.findByItemAndWarehouseAndWarehouseLocationAndBatchAndSite(sheet.item, warehouse, warehouseLocation, sheet.batch, sheet.site)

        inventory.qty += sheet.qty*sheet.typeName.multiplier
        inventoryDetail.qty += sheet.qty*sheet.typeName.multiplier

        inventory.save(failOnError: true, flush: true)
        inventoryDetail.save(failOnError: true, flush: true)
    }

    def processSheetPrice = { sheet ->

        sheet.totalPrice = sheet.price*sheet.qty
        switch(sheet) {
            case PurchaseSheetDet :
                sheet.header.totalPrice+=sheet.totalPrice
                break
            case StockInSheetDet :
            case OutSrcPurchaseSheetDet :
            case MaterialReturnSheetDet :
            case SaleReturnSheetDet :
            case MaterialSheetDet :
            case SaleSheetDet :
            case PurchaseReturnSheetDet :
            case OutSrcPurchaseReturnSheetDet :
                break
            default:
                log.debug "TestService---does not know sheet domain class"
                break
        }

    }

    def createTestMessage = {

        grailsApplication.config.grails.i18nType = "mfg"

        messageSource.addMessage("mfg.default.message.save.success", Locale.getDefault(), "儲存成功")
        messageSource.addMessage("mfg.default.message.save.failed", Locale.getDefault(), "儲存失敗")
        messageSource.addMessage("mfg.default.message.delete.success", Locale.getDefault(), "刪除成功")
        messageSource.addMessage("mfg.default.message.update.failed", Locale.getDefault(), "更新失敗")
        messageSource.addMessage("mfg.default.message.not.found", Locale.getDefault(), "查無資料")

        messageSource.addMessage("mfg.user.label", Locale.getDefault(), "使用者")
        messageSource.addMessage("mfg.site.label", Locale.getDefault(), "公司")
        messageSource.addMessage("mfg.brand.label", Locale.getDefault(), "品牌")
        messageSource.addMessage("mfg.item.label", Locale.getDefault(), "品項")
        messageSource.addMessage("mfg.customer.label", Locale.getDefault(), "客戶")
        messageSource.addMessage("mfg.employee.label", Locale.getDefault(), "職員")
        messageSource.addMessage("mfg.workstation.label", Locale.getDefault(), "工作站")
        messageSource.addMessage("mfg.supplier.label", Locale.getDefault(), "供應商")
        messageSource.addMessage("mfg.operation.label", Locale.getDefault(), "製程")
        messageSource.addMessage("mfg.itemRoute.label", Locale.getDefault(), "品項途程")
        messageSource.addMessage("mfg.batchOperation.label", Locale.getDefault(), "批號製程")
        messageSource.addMessage("mfg.reportParam.label", Locale.getDefault(), "履歷參數")
        messageSource.addMessage("mfg.batchReportDet.label", Locale.getDefault(), "批號履歷參數")

        messageSource.addMessage("mfg.country.TAIWAN.label", Locale.getDefault(), "台灣")
        messageSource.addMessage("mfg.country.AUSTRALIA.label", Locale.getDefault(), "澳洲")
        messageSource.addMessage("mfg.country.BRASIL.label", Locale.getDefault(), "巴西")
        messageSource.addMessage("mfg.country.CANADA.label", Locale.getDefault(), "加拿大")
        messageSource.addMessage("mfg.country.CHINA.label", Locale.getDefault(), "中國")
        messageSource.addMessage("mfg.country.COLOMBIA.label", Locale.getDefault(), "哥倫比亞")
        messageSource.addMessage("mfg.country.DANMARK.label", Locale.getDefault(), "丹麥")
        messageSource.addMessage("mfg.country.FINLAND.label", Locale.getDefault(), "芬蘭")
        messageSource.addMessage("mfg.country.FRANCE.label", Locale.getDefault(), "法國")
        messageSource.addMessage("mfg.country.GERMANY.label", Locale.getDefault(), "德國")
        messageSource.addMessage("mfg.country.HONGKONG.label", Locale.getDefault(), "香港")
        messageSource.addMessage("mfg.country.INDIA.label", Locale.getDefault(), "印度")
        messageSource.addMessage("mfg.country.INDONESIA.label", Locale.getDefault(), "印尼")
        messageSource.addMessage("mfg.country.ISRAEL.label", Locale.getDefault(), "以色列")
        messageSource.addMessage("mfg.country.ITALY.label", Locale.getDefault(), "義大利")
        messageSource.addMessage("mfg.country.JAPAN.label", Locale.getDefault(), "日本")
        messageSource.addMessage("mfg.country.KOREA.label", Locale.getDefault(), "韓國")
        messageSource.addMessage("mfg.country.MACAO.label", Locale.getDefault(), "澳門")
        messageSource.addMessage("mfg.country.MEXICO.label", Locale.getDefault(), "墨西哥")
        messageSource.addMessage("mfg.country.NETHERLANDS.label", Locale.getDefault(), "荷蘭")
        messageSource.addMessage("mfg.country.NEWZEALAND.label", Locale.getDefault(), "紐西蘭")
        messageSource.addMessage("mfg.country.SINGAPORT.label", Locale.getDefault(), "新加坡")
        messageSource.addMessage("mfg.country.SPAIN.label", Locale.getDefault(), "西班牙")
        messageSource.addMessage("mfg.country.SWEDEN.label", Locale.getDefault(), "瑞典")
        messageSource.addMessage("mfg.country.SWITZERLAND.label", Locale.getDefault(), "瑞士")
        messageSource.addMessage("mfg.country.THAILAND.label", Locale.getDefault(), "泰國")
        messageSource.addMessage("mfg.country.UNITEDKINGDOM.label", Locale.getDefault(), "英國")
        messageSource.addMessage("mfg.country.UNITEDSTATES.label", Locale.getDefault(), "美國")
        messageSource.addMessage("mfg.country.VIETNAM.label", Locale.getDefault(), "越南")
        messageSource.addMessage("mfg.country.PHILIPPINES.label", Locale.getDefault(), "菲律賓")

        messageSource.addMessage("mfg.paramType.INTEGER.label", Locale.getDefault(), "整數")
        messageSource.addMessage("mfg.paramType.DOUBLE.label", Locale.getDefault(), "實數")
        messageSource.addMessage("mfg.paramType.BOOLEAN.label", Locale.getDefault(), "布林值")
    }
}
