package foodprint.erp.web

import foodprint.erp.*

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class EmployeeController {

    def grailsApplication
    def domainService

    def i18nType

    def index = {
        def list = Employee.createCriteria().list(params, params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByEmployeeType = {

        def employeeType = params.employeeType as EmployeeType

        params.criteria = { eq('employeeType', employeeType) } >> params.criteria

        def list = Employee.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }
    def show = {

        def employee = Employee.get(params.id)

        if (employee) {
            render (contentType: 'application/json') {
                [success: true, data: employee]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def employee = new Employee()
        render (contentType: 'application/json') {
            [success: true, data: employee]
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def employee = new Employee(params)
            result = domainService.save(employee)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def update() {
        def result
        try {

            def employee = Employee.get(params.id)
            employee.properties = params
            result = domainService.save(employee)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def delete() {

        def result        
        try {
            def employee = Employee.get(params.id)
            result = domainService.delete(employee)
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }

        render (contentType: 'application/json') {
            result
        }
    }
}
