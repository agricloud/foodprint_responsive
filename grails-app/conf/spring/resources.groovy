// Place your Spring DSL code here
import common.*
import foodprint.*
import foodprint.common.*

import grails.util.Environment

import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy
import org.springframework.security.web.session.ConcurrentSessionFilter
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

beans = {


    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("zh","TW")
        java.util.Locale.setDefault(defaultLocale)
    }

    sessionRegistry(SessionRegistryImpl)

    sessionAuthenticationStrategy(ConcurrentSessionControlStrategy, sessionRegistry) { 
            maximumSessions = 1 
    } 
    
    concurrentSessionFilter(ConcurrentSessionFilter) { 
            sessionRegistry = sessionRegistry 
            logoutHandlers = [ref("rememberMeServices"), ref("securityContextLogoutHandler")]
            expiredUrl = '/login/concurrentSession' 
    }

    if (application.config.grails.upload.location.s3) {
        attachmentService(S3AttachmentService) {
            grailsApplication = ref('grailsApplication')
            messageSource = ref('messageSource')
            s3Service = ref("s3Service")
            imageModiService = ref("imageModiService")
            springSecurityService = ref("springSecurityService")
            fileLocation = application.config.grails.aws.root
        }
    }
    else {
        attachmentService(LocalAttachmentService) {
            grailsApplication = ref('grailsApplication')
            messageSource = ref('messageSource')
            fileLocation = application.config.grails.upload.location.local.path
        }
    }
}
