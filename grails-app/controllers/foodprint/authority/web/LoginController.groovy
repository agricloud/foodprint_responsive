package foodprint.authority.web

import foodprint.authority.*

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.Environment

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes

class LoginController {

    def grailsApplication
    def foodauthService

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    def i18nType

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {
        log.debug "execute LoginController--auth"
        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                                   rememberMeParameter: config.rememberMe.parameter]
    }

    /**
     * The redirect action for Ajax requests.
     */
    def authAjax = {
        log.debug "execute LoginController--authAjax"
        
        response.setHeader 'Location', grailsApplication.config.grails.indexPath

        // redirect uri: grailsApplication.config.grails.indexPath

        // response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        // response.sendError HttpServletResponse.SC_UNAUTHORIZED
        
        render (contentType:'application/json', status: HttpServletResponse.SC_UNAUTHORIZED) {
            [success: false, message: message(code: "${i18nType}.default.message.login")]
        }
    }

    def concurrentSession = {
        
        def msg = message(code: "${i18nType}.springSecurity.errors.concurrent.session")
        log.debug "execute LoginController--concurrentSession"
        log.debug "isAjax? "+springSecurityService.isAjax(request)

        if (springSecurityService.isAjax(request)) {
            render (contentType:'application/json', status: HttpServletResponse.SC_UNAUTHORIZED) {
                [success: false, cause:'concurrent session', message: msg]
            }
            // render([success: false, error: msg] as JSON)
        }
        else {
            flash.message = msg
            // redirect action: 'auth', params: params
            redirect uri:"/"
        }

    }

    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
            model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                    postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {
        log.debug "execute LoginController--authfail"
        
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        /*
        AccountExpiredException
        AuthenticationFailureExpiredEvent
        AuthenticationServiceException
        AuthenticationFailureServiceExceptionEvent
        LockedException
        AuthenticationFailureLockedEvent
        CredentialsExpiredException
        AuthenticationFailureCredentialsExpiredEvent
        DisabledException
        AuthenticationFailureDisabledEvent
        BadCredentialsException
        AuthenticationFailureBadCredentialsEvent
        UsernameNotFoundException
        AuthenticationFailureBadCredentialsEvent
        ProviderNotFoundException
        */
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "${i18nType}.springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "${i18nType}.springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "${i18nType}.springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "${i18nType}.springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "${i18nType}.springSecurity.errors.login.fail")
            }
        }
        else {
            msg = g.message(code: "${i18nType}.springSecurity.errors.login.fail")
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        log.debug "execute LoginController--ajaxSuccess"

        def user = springSecurityService.currentUser
        def result

        try {
            
            if (!foodauthService.ping().success) {
                if (Environment.current == Environment.DEVELOPMENT) {
                    log.warn "Development Environment::: foodauthService dosen't startup..."
                    result = [success: true, userType: user.userType,  username: user.username, fullName: user.fullName]
                }
                else {
                    result = [success: false, error: "We apologize for any inconvenience caused, please contact foodprint support."]
                }
            }
            else {


                def validateResult = foodauthService.validateLogin(user.activationCode, user.userType)

                if (validateResult.success) {
                    result = [success: true, userType: user.userType, username: user.username, fullName: user.fullName]
                }
                else {
                    result = [success: false, error: validateResult.message]
                }
            }

            result.success ?: session.invalidate()

            render(result as JSON)
        }
        catch (e) {
            log.error e.message
            session.invalidate()
            redirect uri:"/"
        }
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        
        log.debug "execute LoginController--authDenied"
        // render([error: 'access denied'] as JSON)
        render([error: message(code: "${i18nType}.springSecurity.errors.access.denied")] as JSON)
    }
    /**
    * The ExtJS Authentication success handler
    */

    def authSuccessExtJs = {
        log.debug "execute LoginController--authSuccessExtJs"
        render([success: true, username: springSecurityService.authentication.name, fullName: springSecurityService.currentUser.fullName] as JSON)
    }

    /**
      * The ExtJS Authentication failure handler
      */
    def authFailExtJs = {
        log.debug "execute LoginController--authFailExtJs"
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.expired
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.passwordExpired
            }
            else if (exception instanceof DisabledException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.disabled
            }
            else if (exception instanceof LockedException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.locked
            }
            else {
                msg = SpringSecurityUtils.securityConfig.errors.login.fail
            }
        }

        render([success: false, error: msg] as JSON)
    }


}
