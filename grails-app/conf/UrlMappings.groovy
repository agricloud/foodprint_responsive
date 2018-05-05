class UrlMappings {

    static mappings = {

        //api fowardQuery for foodpark
        "/api/$action/$actionName?/$id?(.$format)?"(controller: "api")

        "/api/$controller/$action?" {
            namespace = 'api' 
        }
        "/api/pull/$controller/$action?" {
            namespace = 'apipull' 
        }
        "/api/push/$controller/$action?" {
            namespace = 'apipush' 
        }

        "/$namespace/$controller/$action?"()

        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/reports/query" (controller: "reportViewer", action: "query")
        "/reports/questionnaire" (controller: "reportViewer", action: "questionnaire")
        "/reports/$name/$action" (controller: "reportViewer")
        "/reports/$name" (controller: "reportViewer", action: "index")
        "/reports" (controller: "reportViewer", action: "search")




        "/"(controller: "home", action: "index")
        "500"(view:'/error')



    }
}
