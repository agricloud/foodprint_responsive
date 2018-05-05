package foodprint.common

import foodprint.erp.SheetFormatType
import foodprint.erp.ChangeRecord.InventoryTransactionRecord

import foodprint.common.DomainDataException

class SheetService {

    def grailsApplication
    def messageSource
    def dateService
    def domainService

    def i18nType

    def generateSheetName = { sheet, siteId, timeZoneId ->

        def date = dateService.getUserStrDate(grailsApplication.config.grails.sheetDateFormat, timeZoneId)
        def numericalOrder = String.format("%0${grailsApplication.config.grails.sheetNoDigits}d", 1)
        def name
        def expression = /^${date}\d{${grailsApplication.config.grails.sheetNoDigits}}$/

        def domainName = sheet.class.getSimpleName()

        def sql = "SELECT id, typeName, MAX(CAST(name as int)) from " + domainName +
                    " WHERE site.id = ${siteId} AND name like '"+date+"___'"

        def lastSheet = sheet.class.executeQuery(sql)

        if (lastSheet && lastSheet[0][2] != null) {
            name = (lastSheet[0][2] + 1).toString()
        }

        if (!name)
            name = date + numericalOrder

        return name
    }

    def generateSheetNameByTypeName = { sheet, siteId, timeZoneId ->

        def expression = getTypeNameExpression(sheet.typeName, sheet.executionDate, timeZoneId).expression

        if (expression) {

            def domainName = sheet.class.getSimpleName(), name

            def sql = "SELECT id, typeName.id, MAX(CAST(name as long)) from " + domainName +
                        " WHERE site.id = ${siteId} AND name like '"+expression+"'"

            def lastSheet = sheet.class.executeQuery(sql)

            if (lastSheet && lastSheet[0][2] != null) {
                name = (lastSheet[0][2] + 1).toString()
                name = (sheet.typeName.sheetFormatType != SheetFormatType.RUNNINGNUMBER) ? name : name.padLeft(sheet.typeName.runningDigit,'0')
            }
            else {
                name = expression.replace('_', '0')
                name = name[0..-2] + "1"
            }

            return name
        }
        else return null
        
    }

    def validateSheetNameByTypeName = {/* site,*/ sheet, timeZoneId ->

        def typeName = sheet.typeName

        def result = getTypeNameExpression(sheet.typeName, sheet.executionDate, timeZoneId)

        if (result.expression) {
            def regExp = /^${result.expression.replace('_', '\\d')}$/
            if (!(sheet.name ==~ regExp)) {
                throw new DomainDataException(messageSource.getMessage("${i18nType}.sheet.name.invalid", [result.sheetNameFormat] as Object[], Locale.getDefault()))
            }
        }
        
        return [success: true]
    }

    private getTypeNameExpression = { typeName, executionDate, timeZoneId ->
        def dateFormat, date, expression, sheetNameFormat

        if (typeName.sheetFormatType == SheetFormatType.DAY) {
            dateFormat = 'y'*typeName.yearDigit+'MM'+'dd'
            // date = dateService.getUserStrDate(dateFormat, timeZoneId)
            date = dateService.formatWithUserFormat(executionDate, dateFormat, timeZoneId)
            expression = date+'_'*typeName.runningDigit
            sheetNameFormat = dateFormat.toUpperCase()+'9'*typeName.runningDigit
        }
        else if (typeName.sheetFormatType == SheetFormatType.MONTH) {
            dateFormat = 'y'*typeName.yearDigit+'MM'
            date = dateService.formatWithUserFormat(executionDate, dateFormat, timeZoneId)
            expression = date+'_'*typeName.runningDigit
            sheetNameFormat = dateFormat.toUpperCase()+'9'*typeName.runningDigit
        }
        else if (typeName.sheetFormatType == SheetFormatType.RUNNINGNUMBER) {
            expression = '_'*typeName.runningDigit
            sheetNameFormat = '9'*typeName.runningDigit
        }
        else if (typeName.sheetFormatType == SheetFormatType.MANUAL) {
            expression = null
        }

        return [expression: expression, sheetNameFormat: sheetNameFormat]
    }

    def updateInventoryTransactionRecordExecutionDate = { sheet, details ->
        //若變更單據日期，須連同變更庫存異動紀錄日期。
        if (sheet.isDirty('executionDate')) {
            details.each { detail ->
                def records = InventoryTransactionRecord.findAllByTypeNameAndNameAndSequenceAndSite(detail.typeName, detail.name, detail.sequence, detail.site)
                records.each { record ->
                    record.executionDate = sheet.executionDate
                    domainService.save(record)
                }
            }
        }
    }
}
