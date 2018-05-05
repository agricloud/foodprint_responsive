package common

import grails.plugins.rest.client.RestBuilder
import java.net.ConnectException
import java.net.SocketTimeoutException
import org.springframework.web.client.ResourceAccessException
import groovyx.net.http.HttpResponseException

class HttpService {

    def grailsApplication

    def ping = { String serviceName ->

        def url = grailsApplication.config.grails."${serviceName}".service.api.url
        def result = [success: false]

        int TENSECONDS  = 10*1000;
        int TWENTYSECONDS = 20*1000;

        try {

            //查無 withHttp 設定 timeout方法，因此改用 restBuilder，
            //原先以 httpBuilder處理，結果無法讀取 response 內容。
            def rest = new RestBuilder(connectTimeout: TENSECONDS, readTimeout: TWENTYSECONDS)
            def response = rest.get("${url}/ping")

            result = response.json

            log.info "PING: ${serviceName} service ${url} successful"
        }
        catch (ResourceAccessException e) {

            def exception = e.getMostSpecificCause()
            
            switch (exception) {
                case ConnectException :
                    log.info "PING: ${serviceName} service connection refused"
                    break
                case SocketTimeoutException :
                    log.info "PING: ${serviceName} service timed out"
                    break
                default :
                    log.info "PING: ${serviceName} service unknown error"
            }
            log.error e.message
            
        }
        catch (e) {
            log.info "PING: ${serviceName} service error"
            log.error e.message
        }
        finally {
            return result
        }
    }

    def doCallService = { String serviceName, controller, action, Map queryParams ->

        def url = grailsApplication.config.grails."${serviceName}".service.server.url

        log.debug "call service ===== ${url}/${controller}/${action}"
        try {
            withHttp(uri: "${url}/${controller}/${action}") {
                // def html = get(path : '/search', query : [q:'Groovy'])
                def response = get( query: queryParams)
            }
        }
        catch (e) {
            log.error e.message
            def msg
            if (e instanceof HttpResponseException && e.getMessage()=="Not Found") {
                msg = "${serviceName} Service Response Not Found"
            }
            else {
                msg = "call sevice error"
            }
            return [success: false, message: msg]
        }
    }
}
