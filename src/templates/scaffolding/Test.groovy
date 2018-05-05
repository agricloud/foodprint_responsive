package foodprint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(${className}Controller)
@Mock([${className}, 
    DomainService, TestService])
class ${className}ControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)
    }

    def populateValidParams(params) {
        assert params != null
        params["name"] = '${propertyName}NewName'
    }

    void testIndex() {
        new ${className}(name: '${propertyName}').save(failOnError: true)
        controller.index()

        assert response.json.${propertyName}InstanceList.size() == 1   
        assert response.json.${propertyName}InstanceTotal == 1   
        assert response.json.${propertyName}InstanceList[0].name == "${propertyName}"
    }

    void testShow(){
        def ${propertyName} = new ${className}(name: '${propertyName}').save(failOnError: true)

        params.id = ${propertyName}.id
        controller.show()

        assert response.json.success
        assert response.json.data.class == "foodprint.${className}"

    }

    void testCreate() {
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodprint.${className}"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert ${className}.list().size() == 1
        assert ${className}.get(1).name == '${propertyName}NewName'   
    }

    def testUpdate(){
        def ${propertyName} = new ${className}(name: '${propertyName}').save(failOnError: true)

        populateValidParams(params)
        params.id = ${propertyName}.id

        controller.update()
        
        assert response.json.success
        assert ${className}.list().size() == 1
        assert ${className}.get(1).name == '${propertyName}NewName'
    }

    def testDelete(){
        def ${propertyName} = new ${className}(name: '${propertyName}').save(failOnError: true)
        params.id = ${propertyName}.id

        controller.delete()
        assert response.json.success
        assert ${className}.list().size() == 0
    }

}
