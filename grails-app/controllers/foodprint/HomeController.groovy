package foodprint

class HomeController {

    def index = {
    	def path = grailsApplication.config.grails.indexPath
    	path == '/' ? render(view:"/index") :redirect(uri: path)
    }
}
