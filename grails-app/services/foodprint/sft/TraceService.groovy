package foodprint.sft

import foodprint.erp.Site
import foodprint.erp.Batch
import foodprint.erp.TypeName
import foodprint.erp.PurchaseSheetDet
import foodprint.erp.PurchaseReturnSheetDet
import foodprint.erp.OutSrcPurchaseSheetDet
import foodprint.erp.OutSrcPurchaseReturnSheetDet
import foodprint.erp.ManufactureOrder
import foodprint.erp.MaterialSheetDet
import foodprint.erp.MaterialReturnSheetDet
import foodprint.erp.StockInSheetDet
import foodprint.erp.SaleSheetDet
import foodprint.erp.SaleReturnSheetDet

class TraceService {

    def grailsApplication
    def messageSource
    def inventoryDetailService

    def i18nType

    def backwardTraceRoot(batchId, siteId) {

        def site = Site.get(siteId)
        def batch = Batch.findByIdAndSite(batchId, site)

        def rootJson = [:]
        def sourceSheetType, sourceSheet, returnSheetType, returnSheet

        //批號
        rootJson.type = messageSource.getMessage("${i18nType}.batch.label", null, Locale.getDefault())
        rootJson.className = "Batch"
        rootJson.name = batch.name
        rootJson.item = batch.item
        rootJson.qty = 0

        sourceSheet = SaleSheetDet.findAllByBatchAndSite(batch, site)
        if (sourceSheet) {
            //銷貨
            rootJson.note = messageSource.getMessage("${i18nType}.trace.note.sale.label", null, Locale.getDefault())//實際上該批號可能還有部分存於庫存
            sourceSheetType = messageSource.getMessage("${i18nType}.saleSheet.label", null, Locale.getDefault())+": "//銷貨單:
            returnSheetType = messageSource.getMessage("${i18nType}.saleReturnSheet.label", null, Locale.getDefault())+": "//銷退單:
            returnSheet = SaleReturnSheetDet.findAllByBatchAndSite(batch, site)
        }
        else {
            sourceSheet = StockInSheetDet.findAllByBatchAndSite(batch, site)
            if (sourceSheet) {
                //自製
                rootJson.note = messageSource.getMessage("${i18nType}.trace.note.produce.label", null, Locale.getDefault())
                sourceSheetType = messageSource.getMessage("${i18nType}.stockInSheet.label", null, Locale.getDefault())+": "//入庫單:
            }
            else {
                sourceSheet = OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch, site)
                if (sourceSheet) {
                    //託外
                    rootJson.note = messageSource.getMessage("${i18nType}.trace.note.outSrc.label", null, Locale.getDefault())
                    sourceSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseSheet.label", null, Locale.getDefault())+": "//託外進貨單:
                    returnSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", null, Locale.getDefault())+": "//託外退貨單:
                    returnSheet = OutSrcPurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
                }
                else {
                    sourceSheet = PurchaseSheetDet.findAllByBatchAndSite(batch, site)
                    if (sourceSheet) {
                        //採購
                        rootJson.note = messageSource.getMessage("${i18nType}.trace.note.purchase.label", null, Locale.getDefault())
                        sourceSheetType = messageSource.getMessage("${i18nType}.purchaseSheet.label", null, Locale.getDefault())+": "//進貨單:
                        returnSheetType = messageSource.getMessage("${i18nType}.purchaseReturnSheet.label", null, Locale.getDefault())+": "//退貨單:
                        returnSheet = PurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
                    }
                    else {
                        //子節點
                       sourceSheet = ManufactureOrder.findAllByBatchNameAndSite(batch.name, site)
                        if (sourceSheet) {
                            //在製
                            rootJson.note = messageSource.getMessage("${i18nType}.trace.note.wip.label", null, Locale.getDefault())
                            sourceSheetType = messageSource.getMessage("${i18nType}.manufactureOrder.label", null, Locale.getDefault())+": "//製令:
                            rootJson.children = []
                            rootJson.leaf = true
                        }
                        else {
                            //初始庫存批號
                            rootJson.note = "初始庫存"
                            def inventoryDetails = inventoryDetailService.indexByBatchAndGroupByWarehouse(batch.id, site.id)

                            if (inventoryDetails) {
                                inventoryDetails.each() { inventoryDetail ->
                                    //0:warehouse.id,1:warehouse.name,2:warehouse.title,3:item.id,4:item.name,5:item.title,6:batch.id,7:batch.name,8:sum(qty)
                                    rootJson.qty += inventoryDetail[8]
                                }
                            }
                            rootJson.children = []
                            rootJson.leaf = true
                        }
                    }
                }
            }
        }
        rootJson = processNodeSheet(rootJson, sourceSheetType, sourceSheet, returnSheetType, returnSheet)

        return rootJson
    }

    //逆溯批號來源，可能是由製令製造或由供應商進貨。
    //由於foodprint製令有記錄批號，因此可能會有在製狀態未入庫的製令，尚未處理。
    def backwardTraceByBatch(batchName, siteId) {

        def site = Site.get(siteId)
        def batch = Batch.findByName(batchName, site)

        def childJson = []

        def manufactureOrders = StockInSheetDet.findAllByBatchAndSite(batch, site).manufactureOrder.unique()
        if (manufactureOrders) {
            manufactureOrders.each() { manufactureOrder ->
                def node = [:]
                def sourceSheetType, returnSheetType

                //自製
                node.note = messageSource.getMessage("${i18nType}.trace.note.produce.label", null, Locale.getDefault())
                //製令
                node.type = messageSource.getMessage("${i18nType}.manufactureOrder.label", null, Locale.getDefault())
                node.className = "ManufactureOrder"
                node.name = manufactureOrder.typeName.name+"-"+manufactureOrder.name
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.qty = 0
                sourceSheetType = messageSource.getMessage("${i18nType}.stockInSheet.label", null, Locale.getDefault())+": "//入庫單:

                def stockInSheetDets = StockInSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

                node = processNodeSheet(node, sourceSheetType, stockInSheetDets, null, null)

                childJson << node
            }
        }

        manufactureOrders = OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch, site).manufactureOrder.unique()
        if (manufactureOrders) {
            manufactureOrders.each() { manufactureOrder ->
                def node = [:]
                def sourceSheetType, returnSheetType

                //託外
                node.note = messageSource.getMessage("${i18nType}.trace.note.outSrc.label", null, Locale.getDefault())
                //製令
                node.type = messageSource.getMessage("${i18nType}.manufactureOrder.label", null, Locale.getDefault())
                node.className = "ManufactureOrder"
                node.name = manufactureOrder.typeName.name+"-"+manufactureOrder.name
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.qty = 0
                sourceSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseSheet.label", null, Locale.getDefault())+": "//託外進貨單:
                returnSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", null, Locale.getDefault())+": "//託外退貨單:


                def outSrcPurchaseSheetDets = OutSrcPurchaseSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)
                def outSrcPurchaseReturnSheetDets = OutSrcPurchaseReturnSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

                node = processNodeSheet(node, sourceSheetType, outSrcPurchaseSheetDets, returnSheetType, outSrcPurchaseReturnSheetDets)

                childJson << node
            }
        }

        //葉節點：供應商
        def suppliers = PurchaseSheetDet.findAllByBatchAndSite(batch, site).header*.supplier.unique()
        if (suppliers) {
            suppliers.each() { supplier->
                def node = [:]
                def sourceSheetType, returnSheetType
                node.leaf = true
                //廠商
                node.note = messageSource.getMessage("${i18nType}.trace.note.supplier.label", null, Locale.getDefault())
                //供應商
                node.type = messageSource.getMessage("${i18nType}.supplier.label", null, Locale.getDefault())
                node.className = "Supplier"
                node.name = supplier.name+"/"+supplier.title
                node.tel = supplier.tel
                node.fax = supplier.fax
                node.contact = supplier.contact
                node.address = supplier.address
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.qty = 0
                sourceSheetType = messageSource.getMessage("${i18nType}.purchaseSheet.label", null, Locale.getDefault())+": "//進貨單:
                returnSheetType = messageSource.getMessage("${i18nType}.purchaseReturnSheet.label", null, Locale.getDefault())+": "//退貨單:

                def purchaseSheetDets = PurchaseSheetDet.where {
                    header.supplier==supplier && batch==batch && site==site
                }.list()

                def purchaseReturnSheetDets = PurchaseReturnSheetDet.where {
                    header.supplier==supplier && batch==batch && site==site
                }.list()
                node = processNodeSheet(node, sourceSheetType, purchaseSheetDets, returnSheetType, purchaseReturnSheetDets)

                childJson << node
            }
        }

        return childJson

    }

    //逆溯製令領用了哪些批號
    def backwardTraceByManufactureOrder(TypeName typeName, name, siteId) {

        def site = Site.get(siteId)
        def manufactureOrder = ManufactureOrder.findByTypeNameAndNameAndSite(typeName, name, site)
        def batches = MaterialSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, site)*.batch.unique()

        def childJson = []

        batches.each() { batch ->
            def node = [:]
            def sourceSheetType, sourceSheet, returnSheetType, returnSheet

            //領用
            node.note = messageSource.getMessage("${i18nType}.trace.note.requisition.label", null, Locale.getDefault())
            //批號
            node.type = messageSource.getMessage("${i18nType}.batch.label", null, Locale.getDefault())
            node.className = "Batch"
            node.name = batch.name
            node.item = batch.item
            //** for extjs5 tree//
            node["item.id"] = batch.item.id
            node["item.name"] = batch.item.name
            node["item.title"] = batch.item.title
            node["item.brand.name"] = batch.item.brand.name
            node["item.brand.title"] = batch.item.brand.title
            node["item.unit"] = batch.item.unit
            node["item.spec"] = batch.item.spec
            //** end for extjs5 tree//
            node.qty = 0

            sourceSheet = StockInSheetDet.findAllByBatchAndSite(batch, site)
            if (sourceSheet) {
                sourceSheetType = messageSource.getMessage("${i18nType}.stockInSheet.label", null, Locale.getDefault())+": "//入庫單:
            }
            else {
                sourceSheet = OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch, site)
                if (sourceSheet) {
                    sourceSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseSheet.label", null, Locale.getDefault())+": "//託外進貨單:
                    returnSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", null, Locale.getDefault())+": "//託外退貨單:
                    returnSheet = OutSrcPurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
                }
                else {
                    sourceSheet = PurchaseSheetDet.findAllByBatchAndSite(batch, site)
                    if (sourceSheet) {
                        sourceSheetType = messageSource.getMessage("${i18nType}.purchaseSheet.label", null, Locale.getDefault())+": "//進貨單:
                        returnSheetType = messageSource.getMessage("${i18nType}.purchaseReturnSheet.label", null, Locale.getDefault())+": "//退貨單:
                        returnSheet = PurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
                    }
                    else {
                        node.leaf = true
                        sourceSheet = null
                        log.info "逆溯查無此批號${batch.name}進貨單！"
                    }
                }
            }

            node = processNodeSheet(node, sourceSheetType, sourceSheet, returnSheetType, returnSheet)
            childJson << node
        }

        return childJson

    }

    def forwardTraceRoot(batchId, siteId) {

        def site = Site.get(siteId)
        def batch = Batch.findByIdAndSite(batchId, site)

        def rootJson = [:]
        def sourceSheetType, sourceSheet, returnSheetType, returnSheet

        //批號
        rootJson.type = messageSource.getMessage("${i18nType}.batch.label", null, Locale.getDefault())
        rootJson.className = "Batch"
        rootJson.name = batch.name
        rootJson.item = batch.item
        rootJson.qty = 0

        sourceSheet = PurchaseSheetDet.findAllByBatchAndSite(batch, site)
        if (sourceSheet) {
            //採購
            rootJson.note = messageSource.getMessage("${i18nType}.trace.note.purchase.label", null, Locale.getDefault())
            sourceSheetType = messageSource.getMessage("${i18nType}.purchaseSheet.label", null, Locale.getDefault())+": "//進貨單:
            returnSheetType = messageSource.getMessage("${i18nType}.purchaseReturnSheet.label", null, Locale.getDefault())+": "//退貨單:
            returnSheet = PurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
        }
        else {
            sourceSheet = StockInSheetDet.findAllByBatchAndSite(batch, site)
            if (sourceSheet) {
                //自製
                rootJson.note = messageSource.getMessage("${i18nType}.trace.note.produce.label", null, Locale.getDefault())
                sourceSheetType = messageSource.getMessage("${i18nType}.stockInSheet.label", null, Locale.getDefault())+": "//入庫單:
            }
            else {
                sourceSheet = OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch, site)
                if (sourceSheet) {
                    //託外
                    rootJson.note = messageSource.getMessage("${i18nType}.trace.note.outSrc.label", null, Locale.getDefault())
                    sourceSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseSheet.label", null, Locale.getDefault())+": "//託外進貨單:
                    returnSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", null, Locale.getDefault())+": "//託外退貨單:
                    returnSheet = OutSrcPurchaseReturnSheetDet.findAllByBatchAndSite(batch, site)
                }
                else {
                    //子節點
                   sourceSheet = ManufactureOrder.findAllByBatchNameAndSite(batch.name, site)
                    if (sourceSheet) {
                        //在製
                        rootJson.note = messageSource.getMessage("${i18nType}.trace.note.wip.label", null, Locale.getDefault())
                        sourceSheetType = messageSource.getMessage("${i18nType}.manufactureOrder.label", null, Locale.getDefault())+": "//製令:
                        rootJson.children = []
                        rootJson.leaf = true
                    }
                    else {
                        //初始庫存批號
                        rootJson.note = "初始庫存"
                        def inventoryDetails = inventoryDetailService.indexByBatchAndGroupByWarehouse(batch.id, site.id)

                        if (inventoryDetails) {
                            inventoryDetails.each() { inventoryDetail ->
                                //0:warehouse.id,1:warehouse.name,2:warehouse.title,3:item.id,4:item.name,5:item.title,6:batch.id,7:batch.name,8:sum(qty)
                                rootJson.qty += inventoryDetail[8]
                            }
                        }
                    }
                }
            }
        }
        rootJson = processNodeSheet(rootJson, sourceSheetType, sourceSheet, returnSheetType, returnSheet)
        return rootJson
    }

    //順溯批號流向，可能是被製令領用、銷貨給客戶或存於庫存中。
    def forwardTraceByBatch(batchName, siteId) {

        def site = Site.get(siteId)
        def batch = Batch.findByNameAndSite(batchName,site)

        def childJson = []

        def manufactureOrders = MaterialSheetDet.findAllByBatchAndSite(batch, site).manufactureOrder.unique()

        if (manufactureOrders) {

            manufactureOrders.each { manufactureOrder->
                def node = [:]
                def sourceSheetType, returnSheetType

                if (manufactureOrder.workstation) {
                    //自製領用
                    node.note = messageSource.getMessage("${i18nType}.trace.note.produceRequisition.label", null, Locale.getDefault())
                }
                if (manufactureOrder.supplier) {
                    //託外領用
                    node.note = messageSource.getMessage("${i18nType}.trace.note.outSrcRequisition.label", null, Locale.getDefault())
                }
                //製令
                node.type = messageSource.getMessage("${i18nType}.manufactureOrder.label", null, Locale.getDefault())
                node.className = "ManufactureOrder"
                node.name = manufactureOrder.typeName.name+"-"+manufactureOrder.name
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.qty = 0
                sourceSheetType = messageSource.getMessage("${i18nType}.materialSheet.label", null, Locale.getDefault())+": "//領料單:
                returnSheetType = messageSource.getMessage("${i18nType}.materialReturnSheet.label", null, Locale.getDefault())+": "//退料單:

                def materialSheetDets = MaterialSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

                def materialReturnSheetDets = MaterialReturnSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

                node = processNodeSheet(node, sourceSheetType, materialSheetDets, returnSheetType, materialReturnSheetDets)

                childJson << node
            }
        }
        //葉節點：客戶
        def customers = SaleSheetDet.findAllByBatchAndSite(batch, site).header*.customer.unique()
        if (customers) {
            customers.each() { customer->
                def node = [:]
                def sourceSheetType, returnSheetType

                node.leaf = true
                //客戶
                node.note = messageSource.getMessage("${i18nType}.trace.note.customer.label", null, Locale.getDefault())
                //客戶
                node.type = messageSource.getMessage("${i18nType}.customer.label", null, Locale.getDefault())
                node.sheet = messageSource.getMessage("${i18nType}.saleSheet.label", null, Locale.getDefault())+": "//銷貨單:
                node.className = "Customer"
                node.name = customer.name+"/"+customer.title
                node.tel = customer.tel
                node.fax = customer.fax
                node.contact = customer.contact
                node.address = customer.address
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.qty = 0
                sourceSheetType = messageSource.getMessage("${i18nType}.saleSheet.label", null, Locale.getDefault())+": "//銷貨單:
                returnSheetType = messageSource.getMessage("${i18nType}.saleReturnSheet.label", null, Locale.getDefault())+": "//銷退單:

                def saleSheetDets = SaleSheetDet.where {
                    header.customer == customer && batch==batch && site==site
                }.list()
                def saleReturnSheetDets = SaleReturnSheetDet.where {
                    header.customer == customer && batch==batch && site==site
                }.list()
                node = processNodeSheet(node, sourceSheetType, saleSheetDets, returnSheetType, saleReturnSheetDets)

                childJson << node
            }
        }
        //葉節點：庫存
        def inventoryDetails = inventoryDetailService.indexByBatchAndGroupByWarehouse(batch.id, site.id)
        if (inventoryDetails) {
            inventoryDetails.each() { inventoryDetail ->
                //0:warehouse.id,1:warehouse.name,2:warehouse.title,3:item.id,4:item.name,5:item.title,6:batch.id,7:batch.name,8:sum(qty)
                def node = [:]
                node.leaf = true
                //庫存
                node.note = messageSource.getMessage("${i18nType}.trace.note.inventory.label", null, Locale.getDefault())
                //倉庫
                node.type = messageSource.getMessage("${i18nType}.warehouse.label", null, Locale.getDefault())
                node.sheet = null
                node.className = "Warehouse"
                // node.name = inventoryDetail.warehouse.name+"/"+inventoryDetail.warehouse.title
                node.name = inventoryDetail[1]+"/"+inventoryDetail[2]
                node.item = batch.item
                //** for extjs5 tree//
                node["item.id"] = batch.item.id
                node["item.name"] = batch.item.name
                node["item.title"] = batch.item.title
                node["item.brand.name"] = batch.item.brand.name
                node["item.brand.title"] = batch.item.brand.title
                node["item.unit"] = batch.item.unit
                node["item.spec"] = batch.item.spec
                //** end for extjs5 tree//
                node.batch = batch
                node["warehouse.name"] = inventoryDetail[1]
                node["warehouse.title"] = inventoryDetail[2]
                // node.qty = inventoryDetail.qty
                node.qty = inventoryDetail[8]
                childJson << node
            }
        }

        return childJson

    }
    //順溯製令流向，可能入庫至批號或仍在製造中。
    def forwardTraceByManufactureOrder(TypeName typeName, name, siteId) {

        def childJson = []

        def site = Site.get(siteId)
        def manufactureOrder = ManufactureOrder.findByTypeNameAndNameAndSite(typeName, name, site)
        def batches = StockInSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, site)*.batch.unique()

        batches.each() { batch ->
            def node = [:]
            def sourceSheetType, sourceSheet, returnSheetType, returnSheet

            //入庫
            node.note = messageSource.getMessage("${i18nType}.trace.note.stockIn.label", null, Locale.getDefault())
            //批號
            node.type = messageSource.getMessage("${i18nType}.batch.label", null, Locale.getDefault())
            node.className = "Batch"
            node.name = batch.name
            node.item = batch.item
            //** for extjs5 tree//
            node["item.id"] = batch.item.id
            node["item.name"] = batch.item.name
            node["item.title"] = batch.item.title
            node["item.brand.name"] = batch.item.brand.name
            node["item.brand.title"] = batch.item.brand.title
            node["item.unit"] = batch.item.unit
            node["item.spec"] = batch.item.spec
            //** end for extjs5 tree//
            node.qty = 0
            sourceSheetType = messageSource.getMessage("${i18nType}.stockInSheet.label", null, Locale.getDefault())+": "//入庫單:

            sourceSheet = StockInSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

            node = processNodeSheet(node, sourceSheetType, sourceSheet, null, null)

            childJson << node
        }

        batches = OutSrcPurchaseSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, site)*.batch.unique()

        batches.each() { batch ->
            def node = [:]
            def sourceSheetType, sourceSheet, returnSheetType, returnSheet

            //託外
            node.note = messageSource.getMessage("${i18nType}.trace.note.outSrc.label", null, Locale.getDefault())
            //批號
            node.type = messageSource.getMessage("${i18nType}.batch.label", null, Locale.getDefault())
            node.className = "Batch"
            node.name = batch.name
            node.item = batch.item
            //** for extjs5 tree//
            node["item.id"] = batch.item.id
            node["item.name"] = batch.item.name
            node["item.title"] = batch.item.title
            node["item.brand.name"] = batch.item.brand.name
            node["item.brand.title"] = batch.item.brand.title
            node["item.unit"] = batch.item.unit
            node["item.spec"] = batch.item.spec
            //** end for extjs5 tree//
            node.qty = 0
            sourceSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseSheet.label", null, Locale.getDefault())+": "//託外進貨單:
            returnSheetType = messageSource.getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", null, Locale.getDefault())+": "//託外退貨單:

            sourceSheet = OutSrcPurchaseSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)
            returnSheet = OutSrcPurchaseReturnSheetDet.findAllByBatchAndManufactureOrderAndSite(batch, manufactureOrder, site)

            node = processNodeSheet(node, sourceSheetType, sourceSheet, returnSheetType, returnSheet)

            childJson << node
        }
        //葉節點：查詢該製令是否仍有在製品

        return childJson

    }

    def processNodeSheet(node, String sourceSheetType, sourceSheet, String returnSheetType, returnSheet) {

        if (sourceSheet) {
            node.sheet = sourceSheetType
            sourceSheet.eachWithIndex{ sheet , i->
                if (node.note == messageSource.getMessage("${i18nType}.trace.note.wip.label", null, Locale.getDefault()))
                    node.sheet += sheet.typeName.name+"-"+sheet.name
                else
                    node.sheet += sheet.typeName.name+"-"+sheet.name+"-"+sheet.sequence

                if (i != sourceSheet.size()-1)
                    node.sheet += ","

                node.qty = sheet.instanceOf(ManufactureOrder) ? sheet.expectQty.toDouble() : node.qty.toDouble()+sheet.qty.toDouble()
            }

            node.sheetDetail = sourceSheet
        }

        if (returnSheet) {
            node.sheet += " "+returnSheetType
            returnSheet.eachWithIndex{ sheet , i->
                node.sheet += sheet.typeName.name+"-"+sheet.name+"-"+sheet.sequence

                if (i != returnSheet.size()-1)
                    node.sheet += ","

                node.qty = node.qty.toDouble()-sheet.qty.toDouble()

            }

            node.sheetDetail+=returnSheet
        }
        
        node
    }
}
