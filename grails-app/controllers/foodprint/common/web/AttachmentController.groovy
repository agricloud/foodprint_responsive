package foodprint.common.web

import foodprint.common.DomainDataException

import grails.converters.JSON
import org.apache.catalina.connector.ClientAbortException

class AttachmentController {

    def attachmentService
    def grailsApplication
    def assetResourceLocator
    def notFoundImg = "upload.gif"

    def i18nType

    def save = {

        def result = [:]
        try {
            
            params.file = request.getFile('file')
            if (params.file.empty) {
                flash.message = 'file cannot be empty'
                throw new DomainDataException(message(code: "${i18nType}.attachment.file.nullable.error"))
            }
            else
                result = attachmentService.save()
        }
        catch (DomainDataException e) {
            result = e.getResult()
        }

        if (params.senchaEnv == "touch") {
            def text = "{'success':true}"
            render(text:text, contentType: "text/html", encoding:"UTF-8")
        }
        else {
            render (contentType:'application/json') {
                result
            }
        }

    }

    def list = {
        render (contentType:'application/json') {
            attachmentService.list(params)
        }
    }

    def show = { Long id ->

        try {
            def result

            if (params.path) {
                result = attachmentService.showByPath(params.path)
            }
            else {
                result = attachmentService.show(params)
            }

            if (params.fileFormat == "img")
                response.contentType = "image/*"

            if (result.success) {
                response.outputStream << result.object
            }
            else if (params.notFoundRes && params.notFoundRes.toBoolean()) {
                render (contentType:'application/json') {
                    result
                }
            }
            else {
                if (params.useBlank?.toBoolean() == true)
                    notFoundImg = "blank.gif"
                response.outputStream << assetResourceLocator.findAssetForURI(notFoundImg).getInputStream()
            }
        }
        catch (ClientAbortException e) {
            log.error "ClientAbortException error cause :: ${e.getCause()}"
            log.error "ClientAbortException error message :: ${e.getMessage()}" 
        }
        catch (IOException e) {
            log.error "IOExeption error message:: ", e
        }

    }

    def showPdf = { Long id ->

        def result = attachmentService.showPdf(params)

        if (result.success)
            response.outputStream << result.object
        else if (params.notFoundRes && params.notFoundRes.toBoolean())
            response.sendError(404, 'Not Found File')
        else {
            if (params.useBlank?.toBoolean() == true)
                notFoundImg = "blank.gif"
            response.outputStream << assetResourceLocator.findAssetForURI(notFoundImg).getInputStream()
        }
    }

    def delete = { Long id ->
        params.id = id
        def result        
        try {
            result = attachmentService.delete(params)
        }
        catch (DomainDataException e) {
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    def deleteAll = { Long id ->
        params.id = id
        def result        
        try {
            result = attachmentService.deleteAll(params)
        }
        catch (DomainDataException e) {
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

}
