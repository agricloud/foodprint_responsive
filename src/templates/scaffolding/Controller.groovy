<%=packageName ? "package ${packageName}\n\n" : ''%>
import org.springframework.dao.DataIntegrityViolationException

class ${className}Controller {

    def domainService

    def index = {
        def list = ${className}.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [${propertyName}List: list, ${propertyName}Total: list.totalCount]
        }
    }

    def show = {
        def ${propertyName}=${className}.get(params.id);  
        if(${propertyName}){   
            render (contentType: 'application/json') {
                [success: true,data:${propertyName}]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }
    
    def create = {
        def ${propertyName}=new ${className}()        
        render (contentType: 'application/json') {
            [success: true,data:${propertyName}]
        }
    }

    def save = {
        def ${propertyName}=new ${className}(params)
        render (contentType: 'application/json') {
            domainService.save(${propertyName})
        }
    }

    def update = {
        def  ${propertyName} = ${className}.get(params.id)
        ${propertyName}.properties = params
        render (contentType: 'application/json') {
            domainService.save(${propertyName})
        }         
    }

    def delete = {
        
        def ${propertyName} = ${className}.get(params.id)
        def result
        try {
            
            result = domainService.delete(${propertyName})
        
        }catch(DataIntegrityViolationException e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [itemInstance, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
