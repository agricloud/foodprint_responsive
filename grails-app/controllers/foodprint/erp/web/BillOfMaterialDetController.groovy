package foodprint.erp.web

import foodprint.erp.BillOfMaterial
import foodprint.erp.BillOfMaterialDet

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class BillOfMaterialDetController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def sheet = BillOfMaterial.get(params.header.id)

        if (sheet) {

            params.criteria = { eq('header', sheet) } >> params.criteria

            def list = BillOfMaterialDet.createCriteria().list(params, params.criteria)

            render (contentType: 'application/json') {
                [data: list, total: list.totalCount]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }


    }

    def show = {

        def billOfMaterialDet = BillOfMaterialDet.get(params.id)
        if (billOfMaterialDet) {
            render (contentType: 'application/json') {
                [success: true, data: billOfMaterialDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }



    def create = {

        if (params.header.id) {

            def billOfMaterialDet = new BillOfMaterialDet(params)

            if (billOfMaterialDet.header.details)
                billOfMaterialDet.sequence = billOfMaterialDet.header.details*.sequence.max()+1
            else billOfMaterialDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true, data: billOfMaterialDet]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.billOfMaterialDet.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def billOfMaterialDet = new BillOfMaterialDet(params)
            billOfMaterialDet.header.updatedDate = new Date()
            result = domainService.save(billOfMaterialDet)
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
            def newBillOfMaterialDet = new BillOfMaterialDet(params)
            def oldBillOfMaterialDet = BillOfMaterialDet.get(params.id)


            oldBillOfMaterialDet.properties = params
            oldBillOfMaterialDet.header.updatedDate = new Date()
            result = domainService.save(oldBillOfMaterialDet)

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
            def billOfMaterialDet = BillOfMaterialDet.get(params.id)
            result = domainService.delete(billOfMaterialDet)
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
