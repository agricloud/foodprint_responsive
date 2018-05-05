package foodprint.common

import foodprint.common.DomainDataException


class LocalAttachmentService {

    def grailsApplication
    def messageSource
    def fileLocation
    def i18nType

    def save = { def params ->

        Object[] args = []

        def location
        def result = [:]

        try{
            def f = params.file
            if (f.empty) {
                throw new DomainDataException(messageSource.getMessage("${i18nType}.attachment.file.nullable.error", args, Locale.getDefault()))
            }
            else {
                if (params.fileFormat.toLowerCase() in ['jpg', 'jpeg', 'gif', 'png', 'bmp', 'ico'])
                    location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.domainId}"
                else
                    location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.domainId}.${params.fileFormat}"

                checkAndCreate(new File("${fileLocation}/${params.site.id}/${params.domainName}"));
                File uploadedFile = new File(location)

                f.transferTo(uploadedFile)
                result.success = true
            }
        }catch (e) {
            log.error("Failed to upload file.", e)
            throw e
        }

        return result
    }



    def show = { def params ->
        Object[] args = []

        def location

        try {

            if (params.fileFormat == 'img')
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}"
            else
                s3Location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.${params.fileFormat}"


            println "read file: ${location}"

            File object = new File(location)

            if (object.exists())
                return [success: true, object: new FileInputStream(object)]
            else {
                return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
            }
        }
        catch (e) {

            e.printStackTrace()
            log.error "Could not read ${location}"
            return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]

        }
    }

    def showPdf = { def params ->
        Object[] args = []

        def location

        try {
            location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.pdf"
            println "read file: ${location}"

            File object = new File(location)

            if (object.exists())
                return [success: true, object: new FileInputStream(object)]
            else {
                return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]
            }
        }
        catch (e) {

            e.printStackTrace()
            log.error "Could not read ${location}"
            return [success: false, message: messageSource.getMessage("${i18nType}.attachment.file.not.found.error", args, Locale.getDefault())]

        }
    }
    
    def delete = { def params ->
        def location
        def result = [:]

        try {
            if (params.fileFormat == 'img')
                location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}"
            else
                location = "${fileLocation}/${params.site.id}/${params.domainName}/${params.id}.${params.fileFormat}"

            def file = new File(location);
            file.delete();
            result.success=true
        }
        catch (e) {
            log.error "Could not read ${location}"
            e.printStackTrace()
            throw new DomainDataException(null)
        }
        return result

    }

    private def checkAndCreate(File storagePathDirectory) {
        if (!storagePathDirectory.exists()) {
            if (storagePathDirectory.mkdirs()) {
                log.info " folder create SUCCESS"
            } else {
                log.info " folder create FAILED"
            }
        }
    }
}
