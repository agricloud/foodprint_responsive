package foodprint.common

import foodprint.common.DomainDataException

import grails.converters.JSON
import grails.transaction.Transactional
import org.jets3t.service.S3ServiceException

class S3AttachmentService {

    def grailsApplication
    def messageSource
    def s3Service
    def fileLocation
    def imageModiService
    def springSecurityService

    def i18nType

    def save = { def params ->

        Object[] args = []

        def s3Location
        def result = [:]
        try {
            if (params.file.empty) {
                throw new DomainDataException(messageSource.getMessage("${i18nType}.attachment.file.nullable.error", args, Locale.getDefault()))
            }
            else {
                if (params.fileFormat.toLowerCase() in ['jpg', 'jpeg', 'gif', 'png', 'bmp', 'ico']) {
                    s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.domainId}"
                    if (params.domainName == "batchReportDet") {
                        s3Location += "/"
                        def list = s3Service.getObjectList s3Location
                        s3Location += "${list ? (list*.name.max{ it as Integer }.toInteger()+1) : 1}"
                    }
                }
                else
                    s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.domainId}.${params.fileFormat}"

                def inputStream = (InputStream)params.file.inputStream

                if (params.fileFormat.toLowerCase() in ['jpg', 'jpeg', 'gif', 'png']) {
                    def byteArrayOutputStream = imageModiService.sizeNormal(inputStream, params.fileFormat)
                    ByteArrayInputStream inputStreamScaled = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())

                    s3Service.saveObject s3Location, inputStreamScaled
                }
                else {

                    def byteArrayOutputStream = new ByteArrayOutputStream()
                    int reads = inputStream.read()

                    while(reads != -1) {
                        byteArrayOutputStream.write(reads)
                        reads = inputStream.read()
                    }

                    ByteArrayInputStream inputStreamScaled = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())

                    s3Service.saveObject s3Location, inputStreamScaled
                }

                result.success = true

            }
        }catch (e) {
            log.error("Failed to upload file.", e)
            throw e
        }

        return result


    }

    def list = { def params ->

        def s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}/"
        def objects = s3Service.getObjectList(s3Location)

        def list = []
        objects.each { object ->
            if (object.size > 0)
                list << object
        }

        list.sort{it.name as Integer}

        return [success: true, data: list]
    }

    def showByPath = { def path ->
        def object = s3Service.getObject(path)
        return [success: true, object: object.dataInputStream]
    }

    def show = { def params ->

        Object[] args = []

        def s3Location

        try {

            if (params.fileFormat == 'img') {
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}"
            }
            else
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.${params.fileFormat}"

            def object = s3Service.getObject(s3Location)

            if (object) {
                return [success: true, object: object.dataInputStream]
            }
            else return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
        }
        catch (e) {
            log.error "Could not read ${s3Location}"
            //如錯誤訊息為 The specified key does not exist 可能是檔案不存在
            // e.printStackTrace()
            // throw new RuntimeException(e)
            return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
        }

    }

    def showPdf = { def params ->
        Object[] args = []

        def s3Location

        try {
            s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.pdf"
            def object = s3Service.getObject(s3Location)

            if (object)
                return [success: true, object: object.dataInputStream]
            else return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
        }
        catch (e) {
            log.error "Could not read ${s3Location}"
            return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
        }
    }

    def delete = { def params ->
        def s3Location
        def result = [:]

        try {
            if (params.fileFormat == 'img')
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}"
            else
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.${params.fileFormat}"

            println "${s3Location}"
            s3Service.deleteObject s3Location
            result.success = true
        }
        catch (e) {
            log.error "Could not delete ${s3Location}"
            e.printStackTrace()
            throw new DomainDataException(null)
        }

        return result
    }

    def deleteAll = { def params ->
        def s3Location
        def result = [:]

        try {
            s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}"

            def list = s3Service.getObjectList s3Location
            list.each { object ->
                println "${object.path}"
                s3Location = object.path
                s3Service.deleteObject s3Location
            }
            
            result.success = true
        }
        catch (e) {
            log.error "Could not delete ${s3Location}"
            e.printStackTrace()
            throw new DomainDataException(null)
        }
        return result
    }


}
