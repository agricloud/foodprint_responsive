package foodprint.sft.api

import foodprint.erp.*
import foodprint.sft.*

import foodprint.common.Country
import foodprint.common.DomainDataException

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import grails.converters.JSON
import grails.transaction.Transactional
import grails.util.Environment

class BatchExecutionController {

    static namespace = 'api'

    def grailsApplication
    def domainService
    def batchExecutionService

    @Transactional
    def checkIn() {
        def result
        def resultCode
        def updateResult
        def updateFailData
        def i18nType = grailsApplication.config.grails.i18nType
        try {
            def site = Site.get(params.site.id)
            def workstation = Workstation.get(params.workstation.id)

            def data = JSON.parse(params.data)
            if (data){
                data.each{ orgdata ->
                    updateFailData = orgdata
                    def batch = Batch.findByNameAndSite(orgdata["batch.name"], site)
                    def batchOperation = BatchOperation.findByBatchAndSequenceAndSite(batch, orgdata["batchOperation.sequence"], site)
                    def batchRealTimeRecord = BatchRealtimeRecord.findByBatchAndBatchOperationAndSite(batch, batchOperation, site)
                    def manufactureOrder = batch.manufactureOrder

                    if (!batchRealTimeRecord || batchRealTimeRecord.status != BatchStatus.CHECKIN || !batchOperation || !batch || !manufactureOrder || manufactureOrder.workstation != workstation) {
                        if(!batch){
                            resultCode = "F510"
                        }else if(!batchOperation){
                            resultCode = "F513"
                        }else if(!manufactureOrder){
                            resultCode = "F520"
                        }else if(!batchRealTimeRecord){
                            resultCode = "F521"
                        }else if(manufactureOrder.workstation != workstation){
                            resultCode = "F525"
                        }else {
                            resultCode = "F511"
                        }
                        throw new DomainDataException(message(code:"${i18nType}.batch.checkin.failed"))
                    } else {
                        updateResult = batchExecutionService.batchCheckIn(batch, batchOperation, batchRealTimeRecord, manufactureOrder, workstation, site)
                    }
                }
                result = [success: true, message: message(code: "${i18nType}.batch.checkin.success"), code:"S000", data:data]
            }else{
                result = [success: false, message: message(code: "${i18nType}.data.error"), code:"F0001"]
            }
        }catch(ConverterException e){
            transactionStatus.setRollbackOnly()
            result = [success: false, message: message(code: "${i18nType}.data.error"), code:"F001"]
        }catch(DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
            if (resultCode){
                result << [code: resultCode, data:updateFailData]
            }else{
                result << [code: "F000", data:updateFailData]
            }
        }

        result 
    }
    
    @Transactional
    def checkOut() {
        def result
        def resultCode
        def updateResult
        def updateFailData
        def i18nType = grailsApplication.config.grails.i18nType
        try {
            def site = Site.get(params.site.id)
            def workstation = Workstation.get(params.workstation.id)
            def data = JSON.parse(params.data)
            if (data){
                data.each{ orgdata ->
                    updateFailData = orgdata
                    def batch = Batch.findByNameAndSite(orgdata["batch.name"], site)
                    def batchOperation = BatchOperation.findByBatchAndSequenceAndSite(batch, orgdata["batchOperation.sequence"], site)
                    def batchRealTimeRecord = BatchRealtimeRecord.findByBatchAndBatchOperationAndSite(batch, batchOperation, site)
                    def manufactureOrder = batch.manufactureOrder

                    def batchOperationRealtimeRecord = BatchOperationRealtimeRecord.findByBatchAndBatchOperationAndSite(batch,batchOperation,site)
                    
                    if (!batchRealTimeRecord || batchRealTimeRecord.status != BatchStatus.CHECKOUT || !batchOperation || 
                        !batch || !manufactureOrder || manufactureOrder.workstation != workstation|| !batchOperationRealtimeRecord){
                        
                        if(!batch){
                            resultCode = "F510"
                        }else if(!batchOperation){
                            resultCode = "F513"
                        }else if(!manufactureOrder){
                            resultCode = "F520"
                        }else if(!batchRealTimeRecord){
                            resultCode = "F521"
                        }else if(manufactureOrder.workstation != workstation){
                            resultCode = "F525"
                        }else {
                            resultCode = "F512"
                        }
                        throw new DomainDataException(message(code:"${i18nType}.batch.checkin.failed"))
                    }else {
                        def batchNextOperationSequence = BatchOperation.createCriteria().list({
                            eq('batch', batch)
                            eq('site',site)
                            isNull('startDate')
                        }).sequence.min()
                    
                        def batchNextOperation 

                        if(batchNextOperationSequence){
                            batchNextOperation = BatchOperation.where {
                                batch==batch && startDate == null && site==site && sequence == batchNextOperationSequence
                            }.find()
                        }

                        def inWarehouse = Warehouse.get(orgdata["warehouse.id"])
                        def warehouseLocation = WarehouseLocation.get(orgdata["warehouseLocation.id"])
                        if (!batchNextOperation){
                            if(!inWarehouse || !warehouseLocation){
                                if(!inWarehouse){
                                    resultCode = "F590"
                                }else{
                                    resultCode = "F595"
                                }
                                throw new DomainDataException(message(code:"${i18nType}.stock.not.found"))
                            }
                        }
                        updateResult = batchExecutionService.batchCheckOut(batch, batchOperation, batchRealTimeRecord, manufactureOrder, workstation, orgdata.outputQty, inWarehouse, warehouseLocation, batchOperationRealtimeRecord, site)
                    }
                }
                result = [success: true, message: message(code: "${i18nType}.batch.checkout.success"), code:"S000", data:data]
            } else {
                result = [success: false, message: message(code: "${i18nType}.data.error"), code:"F001"]
            }
        }catch (ConverterException e) {
            transactionStatus.setRollbackOnly()
            result = [success: false, message: message(code: "${i18nType}.data.error"), code:"F001"]
        }catch (DomainDataException e) {
            transactionStatus.setRollbackOnly()
            result = e.getResult()
            if (resultCode){
                result << [code: resultCode, data:updateFailData]
            }else{
                result << [code: "F000", data:updateFailData]
            }
        }

        result
    }
}