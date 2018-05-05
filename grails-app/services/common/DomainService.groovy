package common
import org.springframework.transaction.annotation.Transactional
import org.springframework.dao.DataIntegrityViolationException
import foodprint.common.DomainDataException

class DomainService {

    def grailsApplication
    def messageSource

    def i18nType

    @Transactional
    def save(domainObject) {

        def msg
        Object[] args = [domainObject]

        if (!domainObject) {
            msg = messageSource.getMessage("${i18nType}.default.message.not.found", args, Locale.getDefault())

            throw new DomainDataException(msg)

        }
        else if (domainObject.validate() && domainObject.save()) {
            msg = messageSource.getMessage("${i18nType}.default.message.save.success", args, Locale.getDefault())

            return [success: true, data: domainObject, message: msg]

        }
        else {
            def errors = [:]
            //batchOperation update operator時，雖然validate false仍會儲存，加入discard()指令避免儲存更新。
            domainObject.discard()

            domainObject.errors.allErrors.each {

                it.codes.eachWithIndex { code, i ->
                    println code
                    it.codes[i] = i18nType+"."+code
                }

                if (it.field == 'operation' || it.field == 'workstation' || it.field == 'item')
                    errors[it.field+".name"] = messageSource.getMessage(it, Locale.getDefault())
                else errors[it.field] = messageSource.getMessage(it, Locale.getDefault())
                log.error messageSource.getMessage(it, Locale.getDefault())
            }

            msg = messageSource.getMessage("${i18nType}.default.message.save.failed", args, Locale.getDefault())

            throw new DomainDataException(msg, errors)
        }
    }

    @Transactional
    def delete(domainObject) {

        Object[] args = [domainObject, null]
        def msg

        if (!domainObject) {
            msg = messageSource.getMessage("${i18nType}.default.message.not.found", args, Locale.getDefault())
            throw new DomainDataException(msg)
        }

        try {
            domainObject.delete(flush: true)
            msg = messageSource.getMessage("${i18nType}.default.message.delete.success", args, Locale.getDefault())
            return [success: true, message: msg]
        }
        catch (DataIntegrityViolationException e) {
            log.debug "catch DataIntegrityViolationException"
            log.error e
            args = [domainObject, e.getMostSpecificCause()]
            msg = messageSource.getMessage("${i18nType}.default.message.delete.failed", args, Locale.getDefault())
            throw new DomainDataException(msg)
        }
        catch (e) {
            log.error e
            args = [domainObject, e.getMessage()]
            msg = messageSource.getMessage("${i18nType}.default.message.delete.failed", args, Locale.getDefault())
            throw new DomainDataException(msg)
        }

    }

}
