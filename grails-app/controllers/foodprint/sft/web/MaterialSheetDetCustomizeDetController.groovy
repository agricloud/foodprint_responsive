package foodprint.sft.web

import foodprint.sft.MaterialSheetDetCustomizeDet

class MaterialSheetDetCustomizeDetController {

    def grailsApplication
    def domainService
    def enumService
    def sheetCustomizeService

    def index = {

        render (contentType: 'application/json') {
            sheetCustomizeService.index(params.id, params.item.id, MaterialSheetDetCustomizeDet, params.site.id)
        }

    }

}
