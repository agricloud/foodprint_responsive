package common

import foodprint.erp.Site
import foodprint.erp.Workstation

class ParamsFilters {

    def springSecurityService
    def dateService

    def filters = {
        create(controller:'*', action:'save') {
            before = {
                def user = springSecurityService.currentUser
                if (user) {
                    params["creator"] = user.username
                }
            }
        }
        all(controller:'*', action:'*') {
            before = {
                params.each {
                    key, value ->

                    //原則：如果是其他 application call foodprint 則預設該 application 已轉換為utc time 不須處理時區
                    //     若是foodprint call 其他 application 則須取得該 application 的 timezone 處理時區
                    // Transform value from UTC date to Grails date style
                    // 時區 (\+|\-)\d\d:\d\d
                    if (value ==~ /^\d\d\d\d\-\d\d\-\d\dT\d\d:\d\d:\d\d.\d\d\dZ$/ || value ==~ /^\d\d\d\d\-\d\d\-\d\dT\d\d:\d\d:\d\dZ$/) {
						params[key] = dateService.parseUTCISO8601ToUTC(value)
						log.info "Found ${value} is a UTC date format, transform into Grails style"
					}

                    // 處理科學記號數字
                    if (value ==~ /^[-]?(\d.)?[\d]+[e][+-][\d]+$/) {
                        params[key] = Double.parseDouble(value)
                    }

                    // 參考連結 http://grails.org/doc/latest/guide/single.html#dataBinding
                    // 其中：An association property can be set to null by passing the literal String "null".
                    // 可能風險，null 值是真的要作為 null 值，而不是文字的 'null' 值
                    if (!value && key.endsWith(".id")) {
                        value = "null"
                    }
                    else {//for sencha touch
                        if (params.senchaEnv=="touch" && value=="null") {
                            params[key] = null
                        }
                    }

                    //touch版須重改
                    //for sencha touch date format: Sat Jul 25 2015 00:00:00 GMT+0800 (CST)
                    if (params.senchaEnv=="touch" && value==~/^\w\w\w \w\w\w \d\d \d\d\d\d \d\d:\d\d:\d\d GMT[+-]\d\d\d\d \(CST\)$/) {
                        params[key] = dateService.parseCSTToUTC(value)
                    }

                }

                // 如果從前端 extjs 傳進來須要進行分頁處理，轉換為 grails 處理分頁之 params
                if (params.start && params.limit) {
                    params.offset = params.int('start')?:0
                    params.max = params.int('limit')?:50
                }

                def user = springSecurityService.currentUser

                if (user) {
                    if (user.lastLoginSite && user.siteGroup!=user.lastLoginSite?.siteGroup) {
                        user.lastLoginSite = null
                        render (contentType: 'application/json') {
                            [success: false, message: "錯誤訊息：群組異常<br>系統已自動重置帳號，請重新登入。", error: "錯誤訊息：群組異常<br>系統已自動重置帳號，請重新登入。"]
                        }
                        return
                    }

                    if (controllerName != 'attachment' || !params["site.id"])
                        params["site.id"] = user.lastLoginSite?.id

                    // SFT tracking
                    if (controllerName in ['trackingOrSomething','batchExecution','manufactureOrderDet']) {
                        if (!user.lastLoginWorkstation) {
                            render (contentType: 'application/json') {
                                [success: false, message: "錯誤訊息：尚未選擇工作站。"]
                            }
                            return
                        }
                        if (user.lastLoginWorkstation.site != user.lastLoginSite) {
                            render (contentType: 'application/json') {
                                [success: false, message: "錯誤訊息：工作站不屬於目前登入之公司，請選擇其他工作站。", error: "錯誤訊息：工作站不屬於目前登入之公司，請選擇其他工作站。"]
                            }
                            return
                        }
                        params["workstation.id"] = user.lastLoginWorkstation.id
                    }

                    params["siteGroup.id"] = user.siteGroup.id
                    params["editor"] = user.username
                }


                //criteria裡的邏輯，不會被foodpaintController執行，因為foodpaintController並沒有使用 params.criteria
                params.criteria = {

                    if (params.containsKey("site.id") && controllerName != 'userSite') {

                        if (controllerName == 'siteGroup') {
                            eq('id', user?.siteGroup.id)

                        }
                        else if (controllerName in ['site', 'user', 'roleGroup']) {
                            eq('siteGroup.id', user?.siteGroup.id)

                            if (controllerName == 'user' && actionName == 'index')
                                eq('id', user?.id)
                        }
                        else {
                            if (!(controllerName in ['role', 'roleGroupRole', 'userRoleGroup'])) {
                                eq('site', Site.get(params["site.id"]))
                            }
                        }
                    }

                    // SFT tracking
                    if (params.containsKey("workstation.id")) {

                        if (controllerName in ['trackingOrSomething']) {
                            eq('workstation', Workstation.get(params["workstation.id"]))
                        }
                    }

                    if (params.filter) {
                        def filterJson = grails.converters.JSON.parse(params.filter)
                        filterJson.each {

                            if (it.type == 'string') {
                                it.value = it.value+"%"
                            }else if (it.type == 'numeric') {
                                it.value = it.value.toDouble()
                            }else if (it.type == 'date') {
                                log.debug "filter date is ${it.value}"
								it.value = it.value.toDate()
                            }

                            if (it.comparison) {

                                if (it.comparison == 'lt') lt(it.field, it.value)
                                else if (it.comparison == 'gt')gt(it.field, it.value)
                                else eq(it.field, it.value)

                            } else like(it.field, it.value)

                        }
                    }
                    if (params.nameLike) {
                        like('name', params.nameLike+"%")
                    }

                    if (params.sort) {
                        params.sortJson = grails.converters.JSON.parse(params.sort)
                        params.sortJson.each {
                            order(it.property, it.direction.toLowerCase())
                        }
                        params.remove('sort')
                    }

                }//end params.criteria
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
