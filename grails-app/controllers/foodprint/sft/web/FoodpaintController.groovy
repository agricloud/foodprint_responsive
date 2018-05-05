package foodprint.sft.web

import grails.converters.JSON

class FoodpaintController {

    def grailsApplication
    def springSecurityService
    def foodpaintService
    def dateService

    def importData = {
        render (contentType: 'application/json') {
            foodpaintService.doDataImport()
        }
    }

    def index = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render(status: 403, text: [success: false, error: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "index", processParams()
        }
    }

    def show = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "show", processParams()
        }
    }


    def create = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "create", processParams()
        }
    }

    def save = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "save", processParams()
        }
    }

    def update = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "update", processParams()
        }
    }

    def delete = {
        def i18nType = grailsApplication.config.grails.i18nType

        if (!checkAuthority()) {
            render([success: false, message: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
            return
        }

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "delete", processParams()
        }
    }

    def print() {

        def result = foodpaintService.doCallService "${params.foodpaintController}", "print", processParams()
        response.outputStream << result
    }

    def action = {

        render (contentType: 'application/json') {
            foodpaintService.doCallService "${params.foodpaintController}", "${params.foodpaintAction}", processParams()
        }
    }

    def private processParams() {

        def queryParams = [:]
        params.each {
            key, value ->

            if (key!='action' && key!='controller'&& key!='criteria' && key!='order') {

                if (value instanceof Date)
                    queryParams.put(key, dateService.formatWithISO8601(value))

                if (value instanceof String)
                    queryParams.put(key,value)

                if (value instanceof Double)
                    queryParams.put(key,value)

                if (key.endsWith('.id')) {
                    if (value instanceof Long || value.isLong()) {

                        if (key == 'site.id') {
                            queryParams.put('site.name', Site.get(value)?.name)
                        }
                        // && value!="null" for sencha touch
                        if (key == 'brand.id') {
                            queryParams.put('brand.name', Brand.get(value)?.name)
                        }
                        if (key == 'item.id') {
                            queryParams.put('item.name', Item.get(value)?.name)
                            queryParams.put('item.brand.name', Item.get(value)?.brand?.name)
                        }
                        if (key == 'batch.id') {
                            queryParams.put('batch.name', Batch.get(value)?.name)
                        }
                        if (key == 'factory.id') {
                            queryParams.put('factory.name', Factory.get(value)?.name)
                        }
                        if (key == 'operation.id') {
                            queryParams.put('operation.name', Operation.get(value)?.name)
                        }
                        if (key == 'workstation.id') {
                            queryParams.put('workstation.name', Workstation.get(value)?.name)
                        }
                        if (key == 'supplier.id') {
                            queryParams.put('supplier.name', Supplier.get(value)?.name)
                        }
                        if (key == 'customer.id') {
                            queryParams.put('customer.name', Customer.get(value)?.name)
                        }
                    }
                    else {
                        queryParams[key] = null
                    }
                }//end with id
            }
        }
        return queryParams
    }

    // 檢查權限
    def private checkAuthority = {
        //!!!尚未完成 ex: 有銷退權限 即使沒有銷貨權限 也要可以查銷貨單

        def user = springSecurityService.currentUser
        // 取得目前登入之使用者所擁有之權限    array 內的 object Class SimpleGrantedAuthority
        def roles = springSecurityService.getPrincipal().getAuthorities()
        
        def foodpaintController = params.foodpaintController.toUpperCase()
        // 不能直接刪DET 否則 materialSheetDetCustomizeDet 會變成 materialSheetCustomizeDet
        // 因此改用 foodpaintController[0..-4]
        foodpaintController = foodpaintController[-3..-1] == "DET" ? foodpaintController[0..-4] : foodpaintController
        def role = "ROLE_${foodpaintController}_MAINTAIN"

        def pass = false

        def inventoryAuth = [
            "ROLE_WAREHOUSELOCATION_MAINTAIN",
            "ROLE_INVENTORY_MAINTAIN",
            "ROLE_INVENTORYDETAIL_MAINTAIN",
            "ROLE_PURCHASESHEET_MAINTAIN",
            "ROLE_PURCHASERETURNSHEET_MAINTAIN",
            "ROLE_MATERIALSHEET_MAINTAIN",
            "ROLE_MATERIALRETURNSHEET_MAINTAIN",
            "ROLE_STOCKINSHEET_MAINTAIN",
            "ROLE_OUTSRCPURCHASESHEET_MAINTAIN",
            "ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN",
            "ROLE_SALESHEET_MAINTAIN",
            "ROLE_SALERETURNSHEET_MAINTAIN",
        ]

        roles.find {
            if (it.getAuthority()==role || (actionName=="index" &&
                ((it.getAuthority() in inventoryAuth && (foodpaintController=="WAREHOUSE" || foodpaintController=="WAREHOUSELOCATION" || foodpaintController=="INVENTORYDETAIL"))) ||
                 (it.getAuthority()=="ROLE_PURCHASERETURNSHEET_MAINTAIN" && foodpaintController=="PURCHASESHEET") ||
                 (it.getAuthority()=="ROLE_MATERIALSHEET_MAINTAIN" && (foodpaintController=="MANUFACTUREORDER" || foodpaintController=="MATERIALSHEETDETCUSTOMIZE")) ||
                 (it.getAuthority()=="ROLE_MATERIALRETURNSHEET_MAINTAIN" && (foodpaintController=="MANUFACTUREORDER" || foodpaintController=="MATERIALSHEET")) ||
                 (it.getAuthority()=="ROLE_STOCKINSHEET_MAINTAIN" && foodpaintController=="MANUFACTUREORDER") ||
                 (it.getAuthority()=="ROLE_OUTSRCPURCHASESHEET_MAINTAIN" && foodpaintController=="MANUFACTUREORDER") ||
                 (it.getAuthority()=="ROLE_OUTSRCPURCHASERETURNSHEET_MAINTAIN" && (foodpaintController=="MANUFACTUREORDER" || foodpaintController=="OUTSRCPURCHASESHEET")) ||
                 (it.getAuthority()=="ROLE_SALESHEET_MAINTAIN" && foodpaintController=="CUSTOMERORDER") ||
                 (it.getAuthority()=="ROLE_SALERETURNSHEET_MAINTAIN" && (foodpaintController=="CUSTOMERORDER" || foodpaintController=="SALESHEET")))
            ) {
                pass = true
                return true // break
            }
            return false // keep looping
        }

        return pass
    }

}
