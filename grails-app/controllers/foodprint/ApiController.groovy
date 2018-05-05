package foodprint

import grails.util.Environment
import grails.converters.JSON

class ApiController {
    def grailsApplication
    def springSecurityService

    def i18nType

    def readSysConfig = {
        def config = [:]

        config.version = grailsApplication.metadata['app.version']
        config.build = grailsApplication.metadata['app.build']
        config.environment = Environment.current.name
        config.i18nType = i18nType
        config.foodpaintUrl = grailsApplication.config.grails.foodpaint.service.server.url
        config.userType = springSecurityService?.currentUser?.userType
        config.id = springSecurityService?.currentUser?.id
        config.username = springSecurityService?.currentUser?.username
        config.fullName = springSecurityService?.currentUser?.fullName
        config.lastLoginSite = springSecurityService?.currentUser?.lastLoginSite
        config.lastLoginWorkstation = springSecurityService?.currentUser?.lastLoginWorkstation
        config.lastLoginPanel = springSecurityService?.currentUser?.lastLoginPanel
        config.timeZone = TimeZone.getDefault()
        config.useAuthority = grailsApplication.config.grails.useAuthority

        //HACK: 重新思考處理方式
        if (params.senchaEnv=="extjs")
            grailsApplication.config.grails.indexPath = grailsApplication.config.grails.extjsIndexPath
        if (params.senchaEnv=="touch")
            grailsApplication.config.grails.indexPath = grailsApplication.config.grails.touchIndexPath

        log.debug "\n extjs config: \n"+ config
        render (contentType: "application/json") {
            config
        }

    }

    def ping = {
        render (contentType: "application/json") {
            success  =  true
            service =  grailsApplication.metadata['app.name']
            version =  grailsApplication.metadata['app.version']
            build   =  grailsApplication.metadata['app.build']
        }
    }

    def readAuthorities = {
        render (contentType: "application/json") {
            springSecurityService.getPrincipal().getAuthorities()
        }
    }

    def exportData = { def params ->
        def data = [:]

        def exportDomainList = params.domainList

		exportDomainList.each{ domainName->
			def artefactClass = grailsApplication.getDomainClass("foodprint." + domainName)

            if (artefactClass) {
                Class domainClass = artefactClass.clazz
                def domains = domainClass.list()
                data.put(domainName, domains)
            }
            else {
                data["errors"] = data["errors"] ? data["errors"] + "," + domainName : domainName
                log.warn "export data: foodprint no such class: " + domainName
            }
		}

        data.put("timeZone", TimeZone.getDefault())

        log.info "foodprint export data finished!!"

        def converter = data as JSON
        converter.render(response)

	}

    def itemCategoryLayer1 = {
		//使用 http://localhost:8280/foodprint/itemCategoryLayer1/show/1
		//會自動呼叫 itemCategoryLayer1Controller show
		//若須要使用到 controller 以外的查詢，須寫在下方switch中
		log.debug "api params ==== ${params}"

		def result = fowardQuery(ItemCategoryLayer1Controller)
		if (!result.success) {
			switch (params.actionName) {
                case "indexAll": //自己額外設計查詢方法 http://localhost:8280/foodprint/itemCategoryLayer1/indexAll
					def list = ItemCategoryLayer1.createCriteria().list(params, params.criteria)
					render (contentType: 'application/json') {
						[data: list, totalCount: list.totalCount]
					}
					break;
				case "findByTitle": //自己額外設計查詢方法 http://localhost:8280/foodprint/itemCategoryLayer1/findByTitle?title=肥料
					def instance = ItemCategoryLayer1.findByTitle(params.title)
					render (contentType: 'application/json') {
						[data: instance]
					}
					break;
				default:
					render (contentType: 'application/json') {
                        [success: false, msg: "${params.controllerName}/${params.actionName} not exist"]
                    }
					break;
			}
		}
	}

    def workstation = {

		log.debug "api params ==== ${params}"

		def result = fowardQuery(WorkstationController)
        if (!result.success) {
	    	render (contentType: 'application/json') {
	        	[success: false, msg: "${params.controllerName}/${params.actionName} not exist"]
		    }
		}
	}

    private fowardQuery = { controller ->
        params.controllerName = params.action
		def controllerName = params.controllerName
		def actionName = params.actionName ? params.actionName : "index"
		// def controller = grailsApplication.getControllerClass("foodprint."+controllerName[0].toUpperCase() + controllerName[1..-1] + "Controller")
		if (!actionName.startsWith("save") && !actionName.startsWith("update") && !actionName.startsWith("delete") && !actionName.startsWith("create")) {
            if (controller && controller.metaClass.pickMethod(actionName)) {
                processParams()
				forward controller: controllerName, action: actionName, params: params
				return [success: true]
			}
			else {
				return [success: false]
			}
		}
		else {
            render (contentType: 'application/json') {
                [success: false, msg: "permission denied"]
            }
			return [success: true]
		}
	}

    private processParams = {
        // 前端傳入的參數id為foodparkDB的id，在此以name查詢出foodprintDB的id
        def site
        if (params["site.name"]) {
            site = Site.findByName(params["site.name"])
            params.site.id = site?.id
            params["site.id"] = site?.id
        }

        if (params["brand.name"]) {
            def brand = Brand.findByNameAndSite(params["brand.name"], site)
            params.brand.id = brand?.id
            params["brand.id"] = brand?.id
        }

        if (params["item.name"]) {
            def brand = Brand.findByNameAndSite(params["item.brand.name"], site)
            def item = Item.findByNameAndBrandAndSite(params["item.name"], brand, site)
            params.item.id = item?.id
            params["item.id"] = item?.id
        }

        //只傳入batch.name表示有batchService處理 因此不須另外設定id
        if (params["batch.id"] && params["batch.name"]) {
            def batch = Batch.findByNameAndSite(params["batch.name"], site)
            params.batch.id = batch?.id
            params["batch.id"] = batch?.id
        }

        if (params["factory.name"]) {
            def factory = Factory.findByNameAndSite(params["factory.name"], site)
            params.factory.id = factory?.id
            params["factory.id"] = factory?.id
        }

        if (params["workstation.name"]) {
            def workstation = Workstation.findByNameAndSite(params["workstation.name"], site)
            params.workstation.id = workstation?.id
            params["workstation.id"] = workstation?.id
        }

        if (params["employee.name"]) {
            def employee = Employee.findByNameAndSite(params["employee.name"], site)
            params.employee.id = employee?.id
            params["employee.id"] = employee?.id
        }

        if (params.controllerName) {
            def className = params.controllerName[0].toUpperCase() + params.controllerName[1..-1]
            if (grailsApplication.getDomainClass("foodprint."+className) && params["name"]) {
                def domainClass = grailsApplication.getDomainClass("foodprint."+className).clazz
                def domain = domainClass.findByName(params["name"])
                params["id"] = domain.id
            }
        }

        log.debug "after process params ==== ${params}"
    }
}
