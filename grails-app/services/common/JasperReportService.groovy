package common

import foodprint.erp.Site
import grails.converters.JSON

//generate jasperreport
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils

import org.codehaus.groovy.grails.web.context.ServletContextHolder

class JasperReportService{

    def grailsApplication
    def dateService
    def jasperService
    def assetResourceLocator

    def printPdf = { def params, jasperFile, reportTitle, parameters, reportData, timeZoneId ->

        def site
        if (params.site.id && params.site.id != "null")
            site = Site.get(params.site.id)

        parameters["site.title"] = site?.title
        parameters["report.title"] = reportTitle
        parameters["REPORT_TIME_ZONE"] = dateService.getUserTimeZone(timeZoneId)
        parameters["SUBREPORT_DIR"] = ServletContextHolder.servletContext.getResource("/reports/")
        parameters["LOGO"] = assetResourceLocator.findAssetForURI("logo-foodprint.png").getInputStream()

        def reportDef = new JasperReportDef(name:jasperFile, parameters:parameters, reportData:reportData, fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName = dateService.getUserStrDate('yyyy-MM-dd HHmmss', timeZoneId)+" "+reportTitle+".pdf"

        def file = new File(ServletContextHolder.servletContext.getRealPath("/reportFiles/")+"${site?'/'+site.name:''}/${fileName}")

        FileUtils.writeByteArrayToFile(file, jasperService.generateReport(reportDef).toByteArray())

        return file
    }

}
