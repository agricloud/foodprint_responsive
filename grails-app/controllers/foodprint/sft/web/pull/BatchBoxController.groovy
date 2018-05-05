package foodprint.sft.web.pull

import foodprint.common.Country
import foodprint.sft.pull.*
import foodprint.erp.*

import grails.converters.JSON
import grails.transaction.Transactional

import foodprint.common.DomainDataException

class BatchBoxController {

    def grailsApplication
    def domainService
    def dateService
    def batchBoxDetService
    
    def index = {

        def list = BatchBox.createCriteria().list(params, params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def showByItem = {
        def site = Site.get(params.site.id)
        def item = Item.get(params.item.id)
        def batchBox = BatchBox.findByItemAndSite(item, site)

        if (batchBox) {

            render (contentType: 'application/json') {
                [success: true, data: batchBox]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }

    }

    def show = {

        log.info "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType

        def batchBox = BatchBox.get(params.id);

        if (batchBox) {

            render (contentType: 'application/json') {
                [success: true, data: batchBox]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }

    def create = {
        def batchBox = new BatchBox()
        render (contentType: 'application/json') {
            [success: true, data: batchBox]
        }
    }

    @Transactional
    def save() {
        def i18nType = grailsApplication.config.grails.i18nType
        def result
        try {
            if (!params.splitQty){
                params.splitQty = "0"
            }

            if (Double.parseDouble(params.kanbanQty) > Double.parseDouble(params.formLevel) ){
                throw new DomainDataException(message(code:"${i18nType}.kanbanQty.max.error"))
            }else if( Double.parseDouble(params.kanbanQty) == 0 || Double.parseDouble(params.formLevel) == 0){
                throw new DomainDataException(message(code:"${i18nType}.kanbanQty.error"))
            }else if( Boolean.parseBoolean(params.autoSplit) && Double.parseDouble(params.splitQty) > Double.parseDouble(params.kanbanQty)){
                throw new DomainDataException(message(code:"${i18nType}.splitQty.max.error")) 
            }else if( Boolean.parseBoolean(params.autoSplit) &&  Double.parseDouble(params.splitQty) == 0){
                throw new DomainDataException(message(code:"${i18nType}.splitQty.error")) 
            }else{
                if (Double.parseDouble(params.splitQty) == Double.parseDouble(params.kanbanQty)){
                    params.autoSplit = false
                    params.splitQty = "0"
                }

                def batchBox = new BatchBox(params)
                result = domainService.save(batchBox)
            }
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
        def i18nType = grailsApplication.config.grails.i18nType
        def result
        try {
            if (!params.splitQty){
                params.splitQty = "0"
            }

            if (Double.parseDouble(params.kanbanQty) > Double.parseDouble(params.formLevel) ){
                throw new DomainDataException(message(code:"${i18nType}.kanbanQty.max.error"))
            }else if( Double.parseDouble(params.kanbanQty) == 0 || Double.parseDouble(params.formLevel) == 0){
                throw new DomainDataException(message(code:"${i18nType}.kanbanQty.error"))    
            }else if( Boolean.parseBoolean(params.autoSplit) && Double.parseDouble(params.splitQty) > Double.parseDouble(params.kanbanQty)){
                throw new DomainDataException(message(code:"${i18nType}.splitQty.max.error")) 
            }else if( Boolean.parseBoolean(params.autoSplit) &&  Double.parseDouble(params.splitQty) == 0){
                throw new DomainDataException(message(code:"${i18nType}.splitQty.error")) 
            }else{
                def site = Site.get(params.site.id)
                def batchBox = BatchBox.get(params.id)
                def batchBoxDet = BatchBoxDet.findAllByBatchBoxAndStatusAndsite(batchBox, BatchFormStatus.PENDING, site)
                if ((batchBox.kanbanQty != params.kanbanQty || batchBox.formLevel != params.formLevel) && 
                    (batchBox.qty > 0 || batchBox.accumulationQty > 0 || batchBoxDet)){
                    throw new DomainDataException(message(code:"${i18nType}.batchBoxDet.update.error")) 
                }else{
                    if (Double.parseDouble(params.splitQty) == Double.parseDouble(params.kanbanQty)){
                        params.autoSplit = false
                        params.splitQty = "0"
                    }
                    batchBox.properties = params
                    result = domainService.save(batchBox)
                }
            }
        }
        catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
        }
        catch (NumberFormatException e) {
            transactionStatus.setRollbackOnly()
            result = [success: false, message: message(code: "${i18nType}.data.error")]
        }

        render (contentType: 'application/json') {
            result
        }
    }

    @Transactional
    def delete() {
        def result
        try {

            def batchBox = BatchBox.get(params.id)
            result = domainService.delete(batchBox)

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
