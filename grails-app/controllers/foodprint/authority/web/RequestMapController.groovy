package foodprint.authority.web

import foodprint.authority.*

class RequestMapController {
    def springSecurityService

    //重新載入權限規則
    def clearCached = {
        springSecurityService.clearCachedRequestmaps()
    }
}
