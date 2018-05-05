package foodprint.common

import foodprint.authority.User

import grails.converters.JSON

class FoodauthService {

    def grailsApplication
    def httpService

    static def serviceName = "foodauth"

    /**
     * Ping to /api/ping to check service available
     */
    def ping() {

        return httpService.ping(serviceName)
    }

    def validateUser = { def activationCode, userType ->
        def result

        try {

            def userCount = User.findAllByActivationCode(activationCode).size()

            def queryParams = [activationCode: activationCode, userType: userType, userCount: userCount, timeZone: TimeZone.getDefault() as JSON]

            result = doCallService("api", "validateUser", queryParams)
            
            result.success ?: log.info("{${activationCode}}-${result.message}")
        }
        catch (e) {
            log.error e
            result = [success: false, message: 'Could not validate your License, please contact foodprint support.']
        }

        return result
    }

    def validateLogin = { def activationCode, userType ->
        def result

        try {

            def userCount = User.findAllByActivationCode(activationCode).size()

            def queryParams = [activationCode: activationCode, userType: userType, userCount: userCount, timeZone: TimeZone.getDefault() as JSON]

            result = doCallService("api", "validateLogin", queryParams)
            
            result.success ?: log.info("{${activationCode}}-${result.message}")
        }
        catch (e) {
            log.error e
            result = [success: false, message: 'Could not validate your License, please contact foodprint support.']
        }

        return result
    }

    def validateSiteLicense = { def activationCode, deviceCode ->
        def result

        try {
            def queryParams = [activationCode: activationCode, deviceCode: deviceCode, timeZone: TimeZone.getDefault() as JSON]

            result = doCallService("api", "validateSiteLicense", queryParams)
            
            result.success ?: log.info("{${activationCode}}-${result.message}")

        }
        catch (e) {
            log.error e
            result = [success: false, message: 'Could not validate your License, please contact foodprint support.']
        }

        return result
    }

    def validateBatch = { def activationCode, batchCount ->
        def result

        try {
            def queryParams = [activationCode: activationCode, batchCount: batchCount, timeZone: TimeZone.getDefault() as JSON]

            result = doCallService("api", "validateBatch", queryParams)
            
            result.success ?: log.info("{${activationCode}}-${result.message}")

        }
        catch (e) {
            log.error e
            result = [success: false, message: 'Could not validate your License, please contact foodprint support.']
        }

        return result
    }

    def doCallService = { def controller, action, queryParams ->
        def result = httpService.doCallService(serviceName, controller, action, queryParams)
    }
}
