package foodprint.common

import foodprint.authority.*
import foodprint.erp.*
import foodprint.erp.ChangeOrder.*
import foodprint.erp.ChangeRecord.*
import foodprint.sft.*
import foodprint.sft.pull.*

class TestMFGService {

    def grailsApplication
    def messageSource
    def dateService

    def siteGroup1, site1, user1

    def leanBrand, leanMaterialBrand

    def leanManufacturerTAIWAN, leanManufacturerJAPAN, leanManufacturerHONGKONG, leanManufacturerMEXICO, leanManufacturerCHINA,
        leanManufacturerITALY, leanManufacturerUNITEDKINGDOM, leanManufacturerInspect

    def leanProduct, leanMaterial, leanMaterial1, leanMaterial2
    
    def leanFactory
    def leanCustomer
    def leanworkstation11
    def leanOperation1, leanOperation2, leanOperation3, leanOperation4,
        leanOperation5, leanOperation6, leanOperation7

    def leanMaterialWarehouse, leanProductWarehouse

    def leanProductWarehouseLocation, leanMaterialWarehouseLocation1, leanMaterialWarehouseLocation2, leanMaterialWarehouseLocation3

    def leanTypeNameTransactionSTORAGE, leanTypeNameReturnTransactionSTORAGE,
        leanTypeNameTransactionSALE, leanTypeNameTransactionREQUISITION, leanTypeNameTransactionADJUSTMENT,
        leanTypeNameCustomerOrder, leanTypeNameSaleSheet, leanTypeNameSaleReturnSheet,
        leanTypeNamePurchaseSheet, leanTypeNamePurchaseReturnSheet,
        leanTypeNameManufactureOrder, leanTypeNameOutSrcManufactureOrder,
        leanTypeNameMaterialSheet, leanTypeNameMaterialReturnSheet,
        leanTypeNameOutSrcMaterialSheet, leanTypeNameOutSrcMaterialReturnSheet,
        leanTypeNameStockInSheet, leanTypeNameOutSrcPurchaseSheet, leanTypeNameOutSrcPurchaseReturnSheet,
        leanTypeNameTransferOrderRELEASE, leanTypeNameTransferOrderTRANSFER, leanTypeNameTransferOrderSTORAGE,
        leanTypeNameOutSrcTransferOrderRELEASE, leanTypeNameOutSrcTransferOrderTRANSFER, leanTypeNameOutSrcTransferOrderSTORAGE,
        leanTypeNameDeliveryKanban

    def leanCustomerOrder
    def leanCustomerOrderDet1, leanCustomerOrderDet2
    def leanManufactureOrder1, leanManufactureOrder2, leanManufactureOrder3
    
    def leanProductBatch, leanProductBatch2, leanProductBatch3

    def leanProductBatchOperation1, leanProductBatchOperation2, leanProductBatchOperation3,
        leanProductBatchOperation4, leanProductBatchOperation5, leanProductBatchOperation6,
        leanProductBatchOperation7, leanProductBatchOperation8, leanProductBatchOperation9,
        leanProductBatchOperation10,leanProductBatchOperation11, leanProductBatchOperation12,
        leanProductBatchOperation13, leanProductBatchOperation14, leanProductBatchOperation15,
        leanProductBatchOperation16, leanProductBatchOperation17, leanProductBatchOperation18,
        leanProductBatchOperation19, leanProductBatchOperation20, leanProductBatchOperation21

    def leanBillOfMaterial
    def leanBillOfMaterialDet1, leanBillOfMaterialDet2, leanBillOfMaterialDet3

    def leanProductBatchBox, leanMaterialBatchBox1, leanMaterialBatchBox2, leanMaterialBatchBox3
    def leanProductBatchBoxDet, leanMaterialBatchBoxDet1, leanMaterialBatchBoxDet2, leanMaterialBatchBoxDet3

    def createSite = {
        siteGroup1 = SiteGroup.findByName("default") ?: new SiteGroup(name: "default", title: "default").save(failOnError: true, flush: true)

        site1 = Site.findByName("site1") ?: new Site(name: "site1", title: "測試公司1", siteGroup: siteGroup1,
            activationCode: grailsApplication.config.grails.dev.site.activationCode).save(failOnError: true, flush: true)
        user1 = User.findByUsername("admin") ?: new User(userType: UserType.ADMIN, username: "admin",
            fullName: "admin", password: "admin", lastLoginSite: site1, enabled: true, siteGroup: siteGroup1,
            activationCode: grailsApplication.config.grails.dev.admin.activationCode).save(failOnError: true, flush: true)
        UserSite.create(user1, site1)
    }

    def createItemCategoryLayer1 = {}
    def createItemCategoryLayer2 = {}

    def createBrand = {
        leanBrand = new Brand(name: "leanBrand", title: "精密科技", site: site1).save(failOnError: true, flush: true)
        leanMaterialBrand = new Brand(name: "leanMaterialBrand", title: "惠國工業", site: site1).save(failOnError: true, flush: true)
    }

    def createItem = {
        leanProduct = new Item(name: "leanProduct", brand: leanBrand, title: "成品-引擎蓋", itemType: ItemType.MANUFACTURE, workFlowType: WorkFlowType.COMBINE, spec: "長70cm 寬30cm 高10cm", unit: "個", defaultWorkstation:leanworkstation11, site: site1).save(failOnError: true, flush: true)
        
        leanMaterial = new Item(name: "leanMaterial", brand: leanMaterialBrand, title: "原料-引擎蓋", itemType: ItemType.PURCHASE, workFlowType: WorkFlowType.COMBINE, spec: "長70cm 寬30cm 高10cm", unit: "個", defaultWorkstation:leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanMaterial1 = new Item(name: "leanMaterial1", brand: leanMaterialBrand, title: "原料-彎管", itemType: ItemType.PURCHASE, workFlowType: WorkFlowType.COMBINE, spec: "長5cm 半徑2cm", unit: "個", defaultWorkstation:leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanMaterial2 = new Item(name: "leanMaterial2", brand: leanMaterialBrand, title: "原料-隔板", itemType: ItemType.PURCHASE, workFlowType: WorkFlowType.COMBINE, spec: "長65cm 寬25cm", unit: "個", defaultWorkstation:leanworkstation11, site: site1).save(failOnError: true, flush: true)
    }

    def createFactory = {
        leanFactory = new Factory(name: "leanfct", title: "精實工廠", tel: "02-333-111-3", address: "雲林縣斗六市大學路三段123號", site: site1).save(failOnError: true, flush: true)
    }

    def createCustomer = {
        leanCustomer = new Customer(name: "leanCustomer", title: "惠國工業", site: site1).save(failOnError: true, flush: true)
    }

    def createEmployee = {}

    def createManufacturer = {
        leanManufacturerTAIWAN = new Manufacturer(name:"leanManufacturerTAIWAN", title: "lean製造商TAIWAN", email: "A@xx.com", address: "高雄市鹽埕區中華路99號", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        leanManufacturerJAPAN = new Manufacturer(name: "leanManufacturerJAPAN", title: "lean製造商JAPAN", email: "B@xx.com", address: "彰化縣員林鎮三民路7號", country: Country.JAPAN, site: site1).save(failOnError: true, flush: true)
        leanManufacturerHONGKONG = new Manufacturer(name: "leanManufacturerHONGKONG", title: "lean製造商HONGKONG", email: "HONGKONG@xx.com", address: "彰化縣員林鎮三民路7號", country: Country.HONGKONG, site: site1).save(failOnError: true, flush: true)
        leanManufacturerMEXICO = new Manufacturer(name: "leanManufacturerMEXICO", title: "lean製造商MEXICO", email: "MEXICO@xx.com", address: "MEXICO", country: Country.MEXICO, site: site1).save(failOnError: true, flush: true)
        leanManufacturerCHINA = new Manufacturer(name: "leanManufacturerCHINA", title: "lean製造商CHINA", email: "CHINA@xx.com", address: "CHINA", country: Country.CHINA, site: site1).save(failOnError: true, flush: true)
        leanManufacturerITALY = new Manufacturer(name: "leanManufacturerITALY", title: "lean製造商ITALY", email: "ITALY@xx.com", address: "ITALY", country: Country.ITALY, site: site1).save(failOnError: true, flush: true)
        leanManufacturerUNITEDKINGDOM = new Manufacturer(name: "leanManufacturerUNITEDKINGDOM", title: "lean製造商UNITEDKINGDOM", email: "UNITEDKINGDOM@xx.com", address: "UNITEDKINGDOM", country: Country.UNITEDKINGDOM, site: site1).save(failOnError: true, flush: true)
        leanManufacturerInspect = new Manufacturer(name: "leanManufacturerInspectCreatedBySupplier", title: "lean檢驗商", email: "ABB@xx.com", address: "台北市羅斯福路1號", country: Country.TAIWAN, isSupplier: true, site: site1).save(failOnError: true, flush: true)
    }
    def createSupplier = {}

    def createWorkstation = { createLoopData ->
        leanworkstation11 = new Workstation(name: "leanfctws1", title: "精實工廠-工作站", factory: leanFactory, site: site1).save(failOnError: true, flush: true)
    }

    def createOperationCategoryLayer1 = {}

    def createOperation = {
        leanOperation1 = new Operation(name: "leanOp1", title: "打刻", site: site1).save(failOnError: true, flush: true)
        leanOperation2 = new Operation(name: "leanOp2", title: "彎管孔加工", site: site1).save(failOnError: true, flush: true)
        leanOperation3 = new Operation(name: "leanOp3", title: "彎管壓入", site: site1).save(failOnError: true, flush: true)
        leanOperation4 = new Operation(name: "leanOp4", title: "塗膠", site: site1).save(failOnError: true, flush: true)
        leanOperation5 = new Operation(name: "leanOp5", title: "鉚壓", site: site1).save(failOnError: true, flush: true)
        leanOperation6 = new Operation(name: "leanOp6", title: "鉚壓檢測", site: site1).save(failOnError: true, flush: true)
        leanOperation7 = new Operation(name: "leanOp7", title: "洩漏測試", site: site1).save(failOnError: true, flush: true)
    }

    def createItemStage = {}

    def createItemRegisteredNum = {}

    def createBillOfMaterial = { createLoopData ->
        leanBillOfMaterial = new BillOfMaterial(item: leanProduct, site: site1).save(failOnError: true, flush: true)
    }

    def createBillOfMaterialDet = { createLoopData ->
        leanBillOfMaterialDet1 = new BillOfMaterialDet(header: leanBillOfMaterial, sequence: 1, item: leanMaterial, operation: leanOperation1, site: site1).save(failOnError: true, flush: true)
        leanBillOfMaterialDet2 = new BillOfMaterialDet(header: leanBillOfMaterial, sequence: 2, item: leanMaterial1, operation: leanOperation3, site: site1).save(failOnError: true, flush: true)
        leanBillOfMaterialDet3 = new BillOfMaterialDet(header: leanBillOfMaterial, sequence: 3, item: leanMaterial2, operation: leanOperation5, site: site1).save(failOnError: true, flush: true)
    }

    def createWarehouse = {
        leanMaterialWarehouse = new Warehouse(name: "leanMaterialWarehouse", title: "精實工廠原料倉", factory: leanFactory, site: site1).save(failOnErroe: true, flush: true)
        leanProductWarehouse = new Warehouse(name: "leanProductWarehouse", title: "精實工廠成品倉", factory: leanFactory, site: site1).save(failOnErroe: true, flush: true)
    }

    def createWarehouseLocation = {

        leanProductWarehouseLocation = new WarehouseLocation(name: "leanProductWarehouseLocation", warehouse: leanProductWarehouse, title: "精實工廠成品儲位-引擎護蓋", site: site1).save(failOnError: true, flush: true)
        leanMaterialWarehouseLocation1 = new WarehouseLocation(name: "leanMaterialWarehouseLocation1", warehouse: leanMaterialWarehouse, title: "精實工廠半成品儲位-引擎護蓋", site: site1).save(failOnError: true, flush: true)
        leanMaterialWarehouseLocation2 = new WarehouseLocation(name: "leanMaterialWarehouseLocation2", warehouse: leanMaterialWarehouse, title: "精實工廠半成品儲位-彎管", site: site1).save(failOnError: true, flush: true)
        leanMaterialWarehouseLocation3 = new WarehouseLocation(name: "leanMaterialWarehouseLocation3", warehouse: leanMaterialWarehouse, title: "精實工廠半成品儲位-隔板", site: site1).save(failOnError: true, flush: true)
    }

    def createTypeName = { createLoopData ->
        leanTypeNameTransactionSTORAGE = new TypeName(sheetType: SheetType.TRANSACTION, name: "lean1111", title: "入庫異動單據", transactionType: TransactionType.STORAGE, site: site1).save(failOnError: true, flush: true)
        leanTypeNameReturnTransactionSTORAGE = new TypeName(sheetType: SheetType.TRANSACTION, name: "lean1112", title: "出庫異動單據", transactionType: TransactionType.STORAGE, multiplier: -1, site: site1).save(failOnError: true, flush: true)
        leanTypeNameTransactionSALE = new TypeName(sheetType: SheetType.TRANSACTION, name: "lean112", title: "銷貨異動單據", sheetFormatType: SheetFormatType.MONTH, yearDigit: 3, transactionType: TransactionType.SALE, site: site1).save(failOnError: true, flush: true)
        leanTypeNameTransactionREQUISITION = new TypeName(sheetType: SheetType.TRANSACTION, name: "lean113", title: "領用異動單據", sheetFormatType: SheetFormatType.RUNNINGNUMBER, runningDigit: 5, transactionType: TransactionType.REQUISITION, site: site1).save(failOnError: true, flush: true)
        leanTypeNameTransactionADJUSTMENT = new TypeName(sheetType: SheetType.TRANSACTION, name: "lean115", title: "調整異動單據", sheetFormatType: SheetFormatType.MANUAL, transactionType: TransactionType.ADJUSTMENT, site: site1).save(failOnError: true, flush: true)

        leanTypeNameCustomerOrder = new TypeName(sheetType: SheetType.CUSTOMERORDER, name: "lean220", title: "訂單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameSaleSheet = new TypeName(sheetType: SheetType.SALESHEET, name: "lean230", title: "銷貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameSaleReturnSheet = new TypeName(sheetType: SheetType.SALERETURNSHEET, name: "lean240", title: "銷退單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        leanTypeNamePurchaseSheet = new TypeName(sheetType: SheetType.PURCHASESHEET, name: "lean340", title: "進貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNamePurchaseReturnSheet = new TypeName(sheetType: SheetType.PURCHASERETURNSHEET, name: "lean350", title: "退貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameManufactureOrder = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "lean511", title: "廠內製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.FACTORY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcManufactureOrder = new TypeName(sheetType: SheetType.MANUFACTUREORDER, name: "lean512", title: "託外製令", sheetFormatType: SheetFormatType.DAY, manufactureType: ManufactureType.OUTSRC, site: site1).save(failOnError: true, flush: true)
        leanTypeNameMaterialSheet = new TypeName(sheetType: SheetType.MATERIALSHEET, name: "lean540", title: "廠內領料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameMaterialReturnSheet = new TypeName(sheetType: SheetType.MATERIALRETURNSHEET, name: "lean560", title: "廠內退料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcMaterialSheet = new TypeName(sheetType: SheetType.OUTSRCMATERIALSHEET, name: "lean550", title: "託外領料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcMaterialReturnSheet = new TypeName(sheetType: SheetType.OUTSRCMATERIALRETURNSHEET, name: "lean570", title: "託外退料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        leanTypeNameStockInSheet = new TypeName(sheetType: SheetType.STOCKINSHEET, name: "lean580", title: "生產入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcPurchaseSheet = new TypeName(sheetType: SheetType.OUTSRCPURCHASESHEET, name: "lean590", title: "託外進貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcPurchaseReturnSheet = new TypeName(sheetType: SheetType.OUTSRCPURCHASERETURNSHEET, name: "lean5A1", title: "託外退貨單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

        leanTypeNameDeliveryKanban = new TypeName(sheetType: SheetType.DELIVERYKANBAN, name: "lean250", title: "顧客交貨看板", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        
        leanTypeNameTransferOrderRELEASE = new TypeName(sheetType: SheetType.ORDERRELEASE, name: "leanD11", title: "廠內投料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameTransferOrderTRANSFER = new TypeName(sheetType: SheetType.OPERATIONTRANSFER, name: "leanD21", title: "廠內移轉單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameTransferOrderSTORAGE = new TypeName(sheetType: SheetType.OPERATIONSTORAGE, name: "leanD31", title: "廠內入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcTransferOrderRELEASE = new TypeName(sheetType: SheetType.ORDERRELEASE, name: "leanD12", title: "託外投料單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcTransferOrderTRANSFER = new TypeName(sheetType: SheetType.OPERATIONTRANSFER, name: "leanD22", title: "託外移轉單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)
        leanTypeNameOutSrcTransferOrderSTORAGE = new TypeName(sheetType: SheetType.OPERATIONSTORAGE, name: "leanD32", title: "託外入庫單", sheetFormatType: SheetFormatType.DAY, site: site1).save(failOnError: true, flush: true)

    }

    def createCustomerOrder = {
        leanCustomerOrder = new CustomerOrder(typeName: leanTypeNameCustomerOrder, name: "20171115001", factory: leanFactory, customer: leanCustomer, site: site1).save(failOnError: true, flush: true)
    }

    def createCustomerOrderDet = {
        leanCustomerOrderDet1 = new CustomerOrderDet(header: leanCustomerOrder, typeName: leanCustomerOrder.typeName, name: leanCustomerOrder.name, sequence: 1, item: leanProduct, unit: leanProduct.unit, qty: 100, existsCustomerKanban: true, site: site1).save(failOnError: true, flush: true)
        leanCustomerOrderDet2 = new CustomerOrderDet(header: leanCustomerOrder, typeName: leanCustomerOrder.typeName, name: leanCustomerOrder.name, sequence: 2, item: leanProduct, unit: leanProduct.unit, qty: 100, existsCustomerKanban: true, site: site1).save(failOnError: true, flush: true)
    }

    def createManufactureOrder = {

        leanManufactureOrder1 = new ManufactureOrder(typeName: leanTypeNameManufactureOrder, name: "lean20171128001", manufactureType: leanTypeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.PENDING, factory: leanworkstation11.factory, workstation: leanworkstation11,
            item: leanProduct, batchName: "20171128001" ,expectQty: 1000, site: site1).save(failOnError: true, flush: true)

        leanManufactureOrder2 = new ManufactureOrder(typeName: leanTypeNameManufactureOrder, name: "lean20171128002", manufactureType: leanTypeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.PENDING, factory: leanworkstation11.factory, workstation: leanworkstation11,
            item: leanProduct, batchName: "20171128002" ,expectQty: 1000, site: site1).save(failOnError: true, flush: true)

        leanManufactureOrder3 = new ManufactureOrder(typeName: leanTypeNameManufactureOrder, name: "lean20171128003", manufactureType: leanTypeNameManufactureOrder.manufactureType, status: ManufactureOrderStatus.PENDING, factory: leanworkstation11.factory, workstation: leanworkstation11,
            item: leanProduct, batchName: "20171128003" ,expectQty: 1000, site: site1).save(failOnError: true, flush: true)
    }

    def createManufactureOrderDet = {}

    def createBatch = {

        leanProductBatch = new Batch(name: "20171128001", item: leanProduct, title: leanProduct.title, spec: leanProduct.spec, unit: leanProduct.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: leanManufactureOrder1,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        leanProductBatch2 = new Batch(name: "20171128002", item: leanProduct, title: leanProduct.title, spec: leanProduct.spec, unit: leanProduct.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: leanManufactureOrder2,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        leanProductBatch3 = new Batch(name: "20171128003", item: leanProduct, title: leanProduct.title, spec: leanProduct.spec, unit: leanProduct.unit,
                batchSourceType: BatchSourceType.MANUFACTURE, manufactureOrder: leanManufactureOrder3,
                dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", expectQty: 1000, country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)

        (1..5).each {
            new Batch(name: "leanProductInvBatch${it}", item: leanProduct, title: leanProduct.title, spec: leanProduct.spec, unit: leanProduct.unit,
            batchSourceType: BatchSourceType.PURCHASE, manufacturer: leanManufacturerTAIWAN,
            dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        }
        (6..10).each {
            new Batch(name: "leanMaterialnvBatch${it}", item: leanMaterial, title: leanMaterial.title, spec: leanMaterial.spec, unit: leanMaterial.unit,
            batchSourceType: BatchSourceType.PURCHASE, manufacturer: leanManufacturerTAIWAN,
            dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        }
        (11..15).each {
            new Batch(name: "leanMaterialnvBatch${it}", item: leanMaterial1, title: leanMaterial1.title, spec: leanMaterial1.spec, unit: leanMaterial1.unit,
            batchSourceType: BatchSourceType.PURCHASE, manufacturer: leanManufacturerTAIWAN,
            dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        }
        (16..20).each {
            new Batch(name: "leanMaterialnvBatch${it}", item: leanMaterial2, title: leanMaterial2.title, spec: leanMaterial2.spec, unit: leanMaterial2.unit,
            batchSourceType: BatchSourceType.PURCHASE, manufacturer: leanManufacturerTAIWAN,
            dueDate: new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: "備註", country: Country.TAIWAN, site: site1).save(failOnError: true, flush: true)
        }
    }

    def createItemRoute = {
        (1..7).each { 
            def leanop1 = Operation.findByNameAndSite("leanOp${it}", site1)
            def leanProductRoute = new ItemRoute(item: leanProduct, sequence: "${it}", operation: leanop1, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
            leanProduct.addToItemRoutes(leanProductRoute).save(failOnError: true, flush: true)
        }
    }

    def createBatchOperation = {

        leanProductBatchOperation1 = new BatchOperation(batch: leanProductBatch, sequence: 1,operation: leanOperation1, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation2 = new BatchOperation(batch: leanProductBatch, sequence: 2,operation: leanOperation2, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation3 = new BatchOperation(batch: leanProductBatch, sequence: 3,operation: leanOperation3, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation4 = new BatchOperation(batch: leanProductBatch, sequence: 4,operation: leanOperation4, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation5 = new BatchOperation(batch: leanProductBatch, sequence: 5,operation: leanOperation5, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation6 = new BatchOperation(batch: leanProductBatch, sequence: 6,operation: leanOperation6, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation7 = new BatchOperation(batch: leanProductBatch, sequence: 7,operation: leanOperation7, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)

        leanProductBatch.addToBatchOperations(leanProductBatchOperation1).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation2).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation3).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation4).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation5).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation6).save(failOnError: true, flush: true)
        leanProductBatch.addToBatchOperations(leanProductBatchOperation7).save(failOnError: true, flush: true)

        leanProductBatchOperation8 = new BatchOperation(batch: leanProductBatch2, sequence: 1,operation: leanOperation1, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation9 = new BatchOperation(batch: leanProductBatch2, sequence: 2,operation: leanOperation2, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation10 = new BatchOperation(batch: leanProductBatch2, sequence: 3,operation: leanOperation3, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation11 = new BatchOperation(batch: leanProductBatch2, sequence: 4,operation: leanOperation4, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation12 = new BatchOperation(batch: leanProductBatch2, sequence: 5,operation: leanOperation5, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation13 = new BatchOperation(batch: leanProductBatch2, sequence: 6,operation: leanOperation6, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation14 = new BatchOperation(batch: leanProductBatch2, sequence: 7,operation: leanOperation7, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)

        leanProductBatch2.addToBatchOperations(leanProductBatchOperation8).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation9).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation10).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation11).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation12).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation13).save(failOnError: true, flush: true)
        leanProductBatch2.addToBatchOperations(leanProductBatchOperation14).save(failOnError: true, flush: true)

        leanProductBatchOperation15 = new BatchOperation(batch: leanProductBatch3, sequence: 1,operation: leanOperation1, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation16 = new BatchOperation(batch: leanProductBatch3, sequence: 2,operation: leanOperation2, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation17 = new BatchOperation(batch: leanProductBatch3, sequence: 3,operation: leanOperation3, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation18 = new BatchOperation(batch: leanProductBatch3, sequence: 4,operation: leanOperation4, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation19 = new BatchOperation(batch: leanProductBatch3, sequence: 5,operation: leanOperation5, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation20 = new BatchOperation(batch: leanProductBatch3, sequence: 6,operation: leanOperation6, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)
        leanProductBatchOperation21 = new BatchOperation(batch: leanProductBatch3, sequence: 7,operation: leanOperation7, workstation: leanworkstation11, site: site1).save(failOnError: true, flush: true)

        leanProductBatch3.addToBatchOperations(leanProductBatchOperation15).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation16).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation17).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation18).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation19).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation20).save(failOnError: true, flush: true)
        leanProductBatch3.addToBatchOperations(leanProductBatchOperation21).save(failOnError: true, flush: true)
    }

    def createInventory = {
        def leanProductInventory1 = new Inventory(item: leanProduct, warehouse: leanProductWarehouse, qty: 50, site: site1).save(failOnError: true, flush: true)
        def leanProductInventory2 = new Inventory(item: leanMaterial, warehouse: leanMaterialWarehouse, qty: 50, site: site1).save(failOnError: true, flush: true)
        def leanProductInventory3 = new Inventory(item: leanMaterial1, warehouse: leanMaterialWarehouse, qty: 50, site: site1).save(failOnError: true, flush: true)
        def leanProductInventory4 = new Inventory(item: leanMaterial2, warehouse: leanMaterialWarehouse, qty: 50, site: site1).save(failOnError: true, flush: true)
    }

    def createInventoryDetail = {

        (1..5).each {
            def leanBatch = Batch.findByNameAndSite("leanProductInvBatch${it}", site1)
            new InventoryDetail(item: leanProduct, warehouse: leanProductWarehouse, warehouseLocation: leanProductWarehouseLocation, batch: leanBatch, qty: 10, site: site1).save(failOnError: true, flush: true)
        }
        (6..10).each {
            def leanBatch = Batch.findByNameAndSite("leanMaterialnvBatch${it}", site1)
            new InventoryDetail(item: leanMaterial, warehouse: leanMaterialWarehouse, warehouseLocation: leanMaterialWarehouseLocation1, batch: leanBatch, qty: 10, site: site1).save(failOnError: true, flush: true)
        }
        (11..15).each {
            def leanBatch = Batch.findByNameAndSite("leanMaterialnvBatch${it}", site1)
            new InventoryDetail(item: leanMaterial1, warehouse: leanMaterialWarehouse, warehouseLocation: leanMaterialWarehouseLocation2, batch: leanBatch, qty: 10, site: site1).save(failOnError: true, flush: true)
        }
        (16..20).each {
            def leanBatch = Batch.findByNameAndSite("leanMaterialnvBatch${it}", site1)
            new InventoryDetail(item: leanMaterial2, warehouse: leanMaterialWarehouse, warehouseLocation: leanMaterialWarehouseLocation3, batch: leanBatch, qty: 10, site: site1).save(failOnError: true, flush: true)
        }
    }

    def createInventoryTransactionSheet = {}

     def createInventoryTransactionSheetDet = {}

    def createPurchaseSheet = {}
    def createPurchaseSheetDet = {}

    def createMaterialSheet = {}

    def createMaterialSheetDet = {}

    def createMaterialSheetDetCustomize = {}

    def createMaterialSheetDetCustomizeDet = {}

    def createStockInSheet = {}

    def createStockInSheetDet = {}

    def createOutSrcPurchaseSheet = {}

    def createOutSrcPurchaseSheetDet = {}

    def createSaleSheet = {}

    def createSaleSheetDet = {}

    //************************************退單*****************************************

    def createPurchaseReturnSheet = {}
    def createPurchaseReturnSheetDet = {}

    def createMaterialReturnSheet = {}

    def createMaterialReturnSheetDet = {}

    def createOutSrcPurchaseReturnSheet = {}

    def createOutSrcPurchaseReturnSheetDet = {}

    def createSaleReturnSheet = {}

    def createSaleReturnSheetDet = {}

    def createParam = {}

    def createReport = {}

    def createReportParam = {}

    def createBatchReportDet = {}

    def createBatchBox = {

        leanProductBatchBox = new BatchBox(item: leanProduct, factory: leanFactory, warehouse:leanProductWarehouse, formLevel: 50, kanbanQty:10, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBox1 = new BatchBox(item: leanMaterial, factory: leanFactory, warehouse:leanMaterialWarehouse, formLevel: 50, kanbanQty:10, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBox2 = new BatchBox(item: leanMaterial1, factory: leanFactory, warehouse:leanMaterialWarehouse, formLevel: 50, kanbanQty:10, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBox3 = new BatchBox(item: leanMaterial2, factory: leanFactory, warehouse:leanMaterialWarehouse, formLevel: 50, kanbanQty:10, site: site1).save(failOnError: true, flush: true)
    }

    def createBatchBoxDet = {

        leanProductBatchBoxDet = new BatchBoxDet(batchBox: leanProductBatchBox, sequence: 1, warehouse: leanProductWarehouseLocation.warehouse, qty: 10, warehouseLocation: leanProductWarehouseLocation, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBoxDet1 = new BatchBoxDet(batchBox: leanMaterialBatchBox1, sequence: 1, warehouse: leanMaterialWarehouseLocation1.warehouse, qty: 10, warehouseLocation: leanMaterialWarehouseLocation1, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBoxDet2 = new BatchBoxDet(batchBox: leanMaterialBatchBox2, sequence: 1, warehouse: leanMaterialWarehouseLocation2.warehouse, qty: 10, warehouseLocation: leanMaterialWarehouseLocation2, site: site1).save(failOnError: true, flush: true)
        leanMaterialBatchBoxDet3 = new BatchBoxDet(batchBox: leanMaterialBatchBox3, sequence: 1, warehouse: leanMaterialWarehouseLocation3.warehouse, qty: 10, warehouseLocation: leanMaterialWarehouseLocation3, site: site1).save(failOnError: true, flush: true)
    }

    def createDeliveryKanban = {

        (1..10).each{
            new DeliveryKanban(typeName: leanTypeNameDeliveryKanban, name: leanCustomerOrder.name+String.format("%03d", leanCustomerOrderDet1.sequence), sequence: it, item: leanProduct, qty: leanCustomerOrderDet1.qty/10, customerOrderDet: leanCustomerOrderDet1,site: site1).save(failOnError: true, flush: true)
        }
    }

    def createTransferOrder = {}
    
    def createTransferOrderDet = {}

    

    def createTestMessage = {

        grailsApplication.config.grails.i18nType = "mfg"

        // messageSource.addMessage("mfg.default.message.save.success", Locale.getDefault(), "儲存成功")
        // messageSource.addMessage("mfg.default.message.save.failed", Locale.getDefault(), "儲存失敗")
        // messageSource.addMessage("mfg.default.message.delete.success", Locale.getDefault(), "刪除成功")
        // messageSource.addMessage("mfg.default.message.update.failed", Locale.getDefault(), "更新失敗")
        // messageSource.addMessage("mfg.default.message.not.found", Locale.getDefault(), "查無資料")
    }
}
