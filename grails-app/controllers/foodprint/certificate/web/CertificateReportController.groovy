package foodprint.certificate.web

import foodprint.common.Country
import foodprint.erp.Site
import foodprint.erp.Brand
import foodprint.erp.Item
import foodprint.erp.Factory
import foodprint.erp.Supplier
import foodprint.erp.Employee
import foodprint.erp.Batch
import foodprint.erp.PurchaseSheetDet
import foodprint.erp.PurchaseReturnSheetDet
import foodprint.erp.ManufactureOrder
import foodprint.erp.MaterialSheetDet
import foodprint.erp.MaterialReturnSheetDet
import foodprint.sft.ItemCategoryLayer1
import foodprint.sft.ItemCategoryLayer2
import foodprint.sft.ItemRegisteredNum
import foodprint.sft.MaterialSheetDetCustomizeDet

import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class CertificateReportController {

    def grailsApplication
    def sheetInquiryService
    def dateService
    def enumService
    def jasperReportService

    def i18nType

    def generateMaterialApplyReport = {

        def site = Site.get(params.site.id)

        def masterBatch = Batch.get(params.batch.id)

        def itemCategoryLayer1 = ItemCategoryLayer1.get(params.itemCategoryLayer1.id)

        def manufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(masterBatch.name, site)

        def reportData = []

        def manufactureOrdersApplyRatio = [:]
        // 不能使用each 因為陣列大小不固定可能會增加
        // manufactureOrders.each{ manufactureOrder ->
        int index = 0
        while(index < manufactureOrders.size()) {
            def manufactureOrder = manufactureOrders[index]

            def materialSheetDets = MaterialSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, site)

            materialSheetDets.each { materialSheetDet ->

                //確認此領料單是否為半製品
                def semiManufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(materialSheetDet.batch.name, site)
                //確認此領料單是否存在領退單
                def materialReturnSheetDets = MaterialReturnSheetDet.findAllByMaterialSheetDetAndSite(materialSheetDet, site)
                //計算此領料單實際領用量 = 領料量-領退量
                def applyQty = materialSheetDet.qty
                materialReturnSheetDets.each { materialReturnSheetDet ->
                    applyQty -= materialReturnSheetDet.qty
                }

                if (applyQty!=0) {
                    if (semiManufactureOrders == []) {//非半製品

                        def brand = Brand.findByNameAndSite(materialSheetDet.item.brand.name, site)
                        def item = Item.findByNameAndBrandAndSite(materialSheetDet.item.name, brand, site)

                        if (item.itemCategoryLayer1 == itemCategoryLayer1) {

                            def data = [:]

                            data.dateCreated = materialSheetDet.dateCreated
                            data.executionDate = materialSheetDet.header.executionDate

                            data.masterBatch = [:]
                            data.masterBatch.name = masterBatch.name
                            data.masterBatch.item = masterBatch.item
                            if (masterBatch.manufactureDate)
                                data.masterBatch.manufactureDate = masterBatch.manufactureDate
                            else
                                data.masterBatch.manufactureDate = null
                            def country = enumService.name(masterBatch.country)
                            data.masterBatch.country = country.name
                            data.masterBatch.countryTitle = country.title

                            def batch = Batch.findByNameAndSite(materialSheetDet.batch.name, site)
                            data.batch = [:]
                            data.batch.name = batch.name
                            //製造商批號 暫時以編碼處理
                            if (batch.name.size() > 14)
                                data.batch.name = batch.name[14..-1]

                            if (batch.manufactureDate)
                                data.batch.manufactureDate = batch.manufactureDate
                            else
                                data.batch.manufactureDate = null
                            country = enumService.name(batch.country)
                            data.batch.country = country.name
                            data.batch.countryTitle = country.title

                            data.itemRegisteredNum = ItemRegisteredNum.findByItemAndManufacturerAndCountryAndSite(item, batch.manufacturer, Country.TAIWAN, site)

                            data.item = item
                            data.qty = applyQty * (manufactureOrdersApplyRatio[manufactureOrder.id] ?: 1) //半製品若有領退，須使用領用比率計算
                            if (materialSheetDet.remark)
                                data.remark = materialSheetDet.remark
                            else
                                data.remark = null

                            if (itemCategoryLayer1.title == "病蟲草害防治") {
                                def materialSheetDetCustomizeDets = MaterialSheetDetCustomizeDet.find {
                                    materialSheetDet==materialSheetDet && materialSheetDetCustomize.title=="防治對象" && site==site
                                }
                                if (materialSheetDetCustomizeDets != null)
                                    data.preventTarget = materialSheetDetCustomizeDets?.value

                                materialSheetDetCustomizeDets = MaterialSheetDetCustomizeDet.find {
                                    materialSheetDet==materialSheetDet && materialSheetDetCustomize.title=="稀釋倍數" && site==site
                                }
                                if (materialSheetDetCustomizeDets != null)
                                    data.dilutionFactor = materialSheetDetCustomizeDets?.value

                                materialSheetDetCustomizeDets = MaterialSheetDetCustomizeDet.find {
                                    materialSheetDet==materialSheetDet && materialSheetDetCustomize.title=="防治方式" && site==site
                                }
                                if (materialSheetDetCustomizeDets != null)
                                    data.controlMethod = materialSheetDetCustomizeDets?.value
                            }

                            reportData << data
                        }
                    }
                    else {
                        semiManufactureOrders.each { mo ->
                            //計算半製品的領退使用比率
                            manufactureOrdersApplyRatio[mo.id] = applyQty / materialSheetDet.qty
                            manufactureOrders << mo

                        }
                    }
                } //end if (applyQty!=0)
            }
            index++
        }


        def operator = Employee.get(params.operator.id)

        def reportTitle = message(code: "${i18nType}.certificateReport.materialApplyReport.title.label")

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('executionDate');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        JRDesignSortField sortField2 = new JRDesignSortField();
        sortField2.setName('item.itemCategoryLayer2.title');
        sortField2.setOrder(SortOrderEnum.ASCENDING);
        sortField2.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField2);
        //設定額外傳入參數
        def parameters = [:]
        parameters["SORT_FIELDS"] = sortList
        parameters["operator.title"] = operator.title
        parameters["operator.correspondenceAddress"] = operator.correspondenceAddress

        def jasperFileName

        if (itemCategoryLayer1.title == "病蟲草害防治")
            jasperFileName = 'DiseasePreventionMaterialApplyReport.jasper'
        else
            jasperFileName = 'MaterialApplyReport.jasper'

        def reportFile = jasperReportService.printPdf(params, jasperFileName, reportTitle, parameters, reportData, params.timeZoneId)

        log.debug "Generate MaterialApplyReport Finished:: ${reportTitle}.pdf"
        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')

    }

    def generateMaterialPurchaseReport = {

        def site = Site.get(params.site.id)

        def masterBatch = Batch.get(params.batch.id)

        def itemCategoryLayer1 = ItemCategoryLayer1.get(params.itemCategoryLayer1.id)

        def factory

        if (params.factory?.id && params.factory.id!="null")
            factory = Factory.get(params.factory.id)

        def manufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(masterBatch.name, site)

        def reportData = []

        def purchaseSheetDetResult = [:]

        // 不能使用each 因為陣列大小不固定可能會增加
        // manufactureOrders.each { manufactureOrder ->
        int index = 0

        while(index < manufactureOrders.size()) {
            def manufactureOrder = manufactureOrders[index]

            def materialSheetDets = MaterialSheetDet.findAllByManufactureOrderAndSite(manufactureOrder, site)
            
            materialSheetDets.each { materialSheetDet ->

                def semiManufactureOrders = ManufactureOrder.findAllByBatchNameAndSite(materialSheetDet.name, site)
                
                if (semiManufactureOrders == []) {//非半製品

                    def purchaseSheetDets = PurchaseSheetDet.findAllByBatchAndSite(materialSheetDet.batch, site)
                    //每次query可能會出現重複的採購單，用map的id作為識別，避免重複儲存。
                    purchaseSheetDets.each { purchaseSheetDet ->
                        purchaseSheetDetResult[purchaseSheetDet.id] = purchaseSheetDet
                    }

                }
                else {
                    semiManufactureOrders.each { mo ->
                        manufactureOrders << mo
                    }
                }
            }
            index++
        }

        purchaseSheetDetResult.each { purchaseSheetDetId, purchaseSheetDet ->

            def brand = Brand.findByNameAndSite(purchaseSheetDet.item.brand.name, site)
            def item = Item.findByNameAndBrandAndSite(purchaseSheetDet.item.name, brand, site)
            def sheetFactory = Factory.findByNameAndSite(purchaseSheetDet.header.factory.name, site)

            if (item.itemCategoryLayer1 == itemCategoryLayer1) {
                if (!factory || sheetFactory==factory) {
                    def data = [:]

                    data.dateCreated = dateService.parseUserISO8601ToUTC(purchaseSheetDet.dateCreated, params.timeZoneId)
                    data.executionDate = dateService.parseUserISO8601ToUTC(purchaseSheetDet.header.executionDate, params.timeZoneId)

                    data.masterBatch = [:]
                    data.masterBatch.name = masterBatch.name
                    data.masterBatch.item = masterBatch.item
                    if (masterBatch.manufactureDate)
                        data.masterBatch.manufactureDate = masterBatch.manufactureDate
                    else
                        data.masterBatch.manufactureDate = null
                    def country = enumService.name(masterBatch.country)
                    data.masterBatch.country = country.name
                    data.masterBatch.countryTitle = country.title

                    def batch = Batch.findByNameAndSite(purchaseSheetDet.batch.name, site)
                    data.batch = [:]
                    data.batch.name = batch.name
                    //製造商批號 暫時以編碼處理
                    if (batch.name.size() > 14)
                        data.batch.name = batch.name[14..-1]

                    if (batch.manufactureDate)
                        data.batch.manufactureDate = batch.manufactureDate
                    else
                        data.batch.manufactureDate = null

                    if (batch.manufacturer)
                        data.batch.manufacturer = batch.manufacturer
                    else
                        data.batch.manufacturer = null

                    country = enumService.name(batch.country)
                    data.batch.country = country.name
                    data.batch.countryTitle = country.title

                    data.supplier = Supplier.findByNameAndSite(purchaseSheetDet.header.supplier.name, site)
                    data.itemRegisteredNum = ItemRegisteredNum.findByItemAndManufacturerAndCountryAndSite(item, batch.manufacturer, Country.TAIWAN, site)
                    data.item = item
                    data.qty = purchaseSheetDet.qty
                    data.price = purchaseSheetDet.price
                    data.subTotalPrice = purchaseSheetDet.totalPrice
                    if (purchaseSheetDet.remark)
                        data.remark = purchaseSheetDet.remark
                    else
                        data.remark = null

                    reportData << data

                    //確認是否有退貨單
                    def purchaseReturnSheetDets = PurchaseReturnSheetDet.findAllByPurchaseSheetDetAndSite(purchaseSheetDet, site)

                    if (purchaseReturnSheetDets != []) {
                        // println "有退貨單${purchaseSheetDet.typeName}-${purchaseSheetDet.name}-${purchaseSheetDet.sequence}"
                        purchaseReturnSheetDets.each { purchaseReturnSheetDet ->
                            def retrunData = [:]

                            retrunData.dateCreated = purchaseReturnSheetDet.dateCreated
                            retrunData.executionDate = purchaseReturnSheetDet.header.executionDate

                            retrunData.masterBatch = [:]
                            retrunData.masterBatch.name = masterBatch.name
                            retrunData.masterBatch.item = masterBatch.item
                            if (masterBatch.manufactureDate)
                                retrunData.masterBatch.manufactureDate = masterBatch.manufactureDate
                            else
                                retrunData.masterBatch.manufactureDate = null

                            retrunData.masterBatch.country = country.name
                            retrunData.masterBatch.countryTitle = country.title

                            def returnBatch = Batch.findByNameAndSite(purchaseReturnSheetDet.batch.name, site)
                            retrunData.batch = [:]
                            retrunData.batch.name = returnBatch.name
                            //製造商批號 暫時以編碼處理
                            if (batch.name.size() > 14)
                                retrunData.batch.name = returnBatch.name[14..-1]

                            if (batch.manufactureDate)
                                retrunData.batch.manufactureDate = returnBatch.manufactureDate
                            else
                                retrunData.batch.manufactureDate = null

                            if (batch.manufacturer)
                                retrunData.batch.manufacturer = returnBatch.manufacturer
                            else
                                retrunData.batch.manufacturer = null

                            country = enumService.name(returnBatch.country)
                            retrunData.batch.country = country.name
                            retrunData.batch.countryTitle = country.title

                             retrunData.supplier = Supplier.findByNameAndSite(purchaseReturnSheetDet.header.supplier.name, site)
                            retrunData.itemRegisteredNum = ItemRegisteredNum.findByItemAndManufacturerAndCountryAndSite(batch.item, returnBatch.manufacturer, Country.TAIWAN, site)
                            retrunData.item = batch.item
                            //目前退貨單無價格資訊 暫時先使用進貨單計算
                            retrunData.qty = -(purchaseReturnSheetDet.qty)
                            retrunData.price = -(purchaseSheetDet.price)
                            retrunData.subTotalPrice = -(purchaseReturnSheetDet.qty*purchaseSheetDet.price)
                            if (purchaseReturnSheetDet.remark)
                                retrunData.remark = purchaseReturnSheetDet.remark
                            else
                                retrunData.remark = null

                            reportData << retrunData
                        }
                    }
                    // else println "沒退貨單${purchaseSheetDet.typeName}-${purchaseSheetDet.name}-${purchaseSheetDet.sequence}"

                }//end if (factory && factory==sheetFactory)
            }//end if (item.itemCategoryLayer1 == itemCategoryLayer1)
        }//end purchaseSheetDetResult.each { purchaseSheetDetId, purchaseSheetDet ->


        def operator = Employee.get(params.operator.id)

        def reportTitle = message(code: "${i18nType}.certificateReport.materialPurchaseReport.title.label")

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>()
        JRDesignSortField sortField = new JRDesignSortField()
        sortField.setName('executionDate');
        sortField.setOrder(SortOrderEnum.ASCENDING)
        sortField.setType(SortFieldTypeEnum.FIELD)
        sortList.add(sortField)
        // JRDesignSortField sortField2 = new JRDesignSortField()
        // sortField2.setName('item.itemCategoryLayer2.title')
        // sortField2.setOrder(SortOrderEnum.ASCENDING)
        // sortField2.setType(SortFieldTypeEnum.FIELD)
        // sortList.add(sortField2)
        //設定額外傳入參數
        def parameters = [:]
        parameters["SORT_FIELDS"] = sortList
        parameters["operator.title"] = operator.title
        parameters["operator.correspondenceAddress"] = operator.correspondenceAddress

        def jasperFileName = 'MaterialPurchaseReport.jasper'

        def reportFile = jasperReportService.printPdf(params, jasperFileName, reportTitle, parameters, reportData, params.timeZoneId)
        log.debug "Generate MaterialPurchaseReport Finished:: ${reportTitle}.pdf"
        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')

    }

    def generateBatchRouteReport = {

        def site = Site.get(params.site.id)

        def batch = Batch.get(params.batch.id)

        def reportData = []

        batch.batchOperations.each{ batchOperation ->
            def data = [:]

            data.dateCreated = batchOperation.dateCreated

            data.batch = [:]
            data.batch.name = batchOperation.batch.name
            data.batch.item = batchOperation.batch.item

            if (batchOperation.batch.manufactureDate)
                data.batch.manufactureDate = batchOperation.batch.manufactureDate
            else
                data.batch.manufactureDate = null

            def country = enumService.name(batch.country)
            data.batch.country = country.name
            data.batch.countryTitle = country.title

            if (batchOperation.startDate)
                data.startDate = batchOperation.startDate
            else
                data.startDate = null

            if (batchOperation.endDate)
                data.endDate = batchOperation.endDate
            else
                data.endDate = null

            data.operation = batchOperation.operation

            if (batchOperation.remark)
                data.remark = batchOperation.remark
            else
                data.remark = null

            reportData << data

        }

        def operator = Employee.get(params.operator.id)

        def reportTitle = message(code: "${i18nType}.certificateReport.batchRouteReport.title.label")

        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('operation.operationCategoryLayer1.title');
        sortField.setOrder(SortOrderEnum.DESCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        JRDesignSortField sortField2 = new JRDesignSortField();
        sortField2.setName('startDate');
        sortField2.setOrder(SortOrderEnum.ASCENDING);
        sortField2.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField2);
        //設定額外傳入參數
        def parameters = [:]
        parameters["SORT_FIELDS"] = sortList
        parameters["operator.title"] = operator.title
        parameters["operator.correspondenceAddress"] = operator.correspondenceAddress

        def jasperFileName = 'BatchRouteReport.jasper'

        def reportFile = jasperReportService.printPdf(params, jasperFileName, reportTitle, parameters, reportData, params.timeZoneId)

        log.debug "Generate BatchRouteReport Finished:: ${reportTitle}.pdf"
        render (file:reportFile, fileNmae:'${reportTitle}.pdf', contentType:'application/pdf')

    }
}
