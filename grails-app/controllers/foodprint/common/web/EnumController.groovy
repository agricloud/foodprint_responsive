package foodprint.common.web

class EnumController {

    def enumService

    def indexCountry = {

        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.common.Country)]
        }
    }

    def indexUserType = {

        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.authority.UserType)]
        }
    }

    def indexItemType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.ItemType)]
        }
    }

    def indexWorkFlowType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.sft.WorkFlowType)]
        }
    }

    def indexSheetType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.SheetType)]
        }
    }

    def indexSheetTypeBySheetTypeGroup = {
        //強制SheetType初始化，否則Group.sheetTypes為null
        foodprint.erp.SheetType.values()
        def sheetTypeGroup = params.sheetTypeGroup as foodprint.erp.SheetType.Group

        render (contentType: 'application/json') {
            [data: enumService.values(sheetTypeGroup.sheetTypes)]
        }
    }

    def indexSheetTypeByNonInventorySheetTypeGroup = {
        //強制SheetType初始化，否則Group.sheetTypes為null
        def sheetTypes = []
        foodprint.erp.SheetType.values().each {
            if (it.group != foodprint.erp.SheetType.Group.INVENTORY)
                sheetTypes << it
        }

        render (contentType: 'application/json') {
            [data: enumService.values(sheetTypes)]
        }
    }

    def indexSheetFormatType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.SheetFormatType)]
        }
    }

    def indexManufactureType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.ManufactureType)]
        }
    }
    
    def indexTransactionType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.TransactionType)]
        }
    }

    def indexEmployeeType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.EmployeeType)]
        }
    }

    def indexBatchSourceType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.BatchSourceType)]
        }
    }

    def indexManufactureOrderStatus = {

        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.erp.ManufactureOrderStatus)]
        }
    }

    def indexParamType = {

        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.sft.ParamType)]
        }
    }

    def indexReportType = {
        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.sft.ReportType)]
        }
    }

    def indexFieldType = {

        render (contentType: 'application/json') {
            [data: enumService.values(foodprint.common.FieldType)]
        }
    }

}
