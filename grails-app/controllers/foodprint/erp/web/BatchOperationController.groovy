package foodprint.erp.web

import foodprint.erp.Batch
import foodprint.erp.BatchOperation
import foodprint.sft.BatchReportDet

import foodprint.common.DomainDataException

import grails.transaction.Transactional

class BatchOperationController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]

    def grailsApplication
    def domainService

    def i18nType

    def index = {

        def batch = Batch.get(params.batch.id)

        if (batch) {

            params.criteria = { eq('batch', batch) } >> params.criteria

            def list = BatchOperation.createCriteria().list(params, params.criteria)

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

        def batchOperation = BatchOperation.get(params.id)
        if (batchOperation) {
            render (contentType: 'application/json') {
                [success: true, data: batchOperation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.default.message.show.failed")]
            }
        }
    }



    def create = {

        if (params.batch.id) {

            def batchOperation = new BatchOperation(params)

            if (batchOperation.batch.batchOperations)
                batchOperation.sequence = ((int)(batchOperation.batch.batchOperations*.sequence.max()/10))*10+10
            else batchOperation.sequence = 10

            render (contentType: 'application/json') {
                [success: true, data: batchOperation]
            }
        }
        else {
            render (contentType: 'application/json') {
                [success: false, message: message(code: "${i18nType}.batchOperation.create.failed")]
            }
        }
    }

    @Transactional
    def save() {
        def result
        try {
            def batchOperation = new BatchOperation(params)
            result = domainService.save(batchOperation)
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
            def newBatchOperation = new BatchOperation(params)
            def oldBatchOperation = BatchOperation.get(params.id)

            // version1:
            // 已存在關聯批號履歷參數(BatchReportDet)不允許變更工作站、供應商、製程
            if (BatchReportDet.findAllByBatchOperationAndSite(oldBatchOperation, oldBatchOperation.site)) {
                if (oldBatchOperation.operation!=newBatchOperation.operation ||
                   oldBatchOperation.workstation!=newBatchOperation.workstation ||
                   oldBatchOperation.supplier!=newBatchOperation.supplier) {
                    throw new DomainDataException(message(code: "${i18nType}.batchOperation.batchReportDet.exists.operation.workstation.supplier.cannot.change"))
                }
            }

            // version2:
            // // 已存在關聯批號履歷參數(BatchReportDet)，且變更工作站、供應商、製程，須將關聯批號履歷參數全部刪除
            // if (BatchReportDet.findAllByBatchOperationAndSite(oldBatchOperation, oldBatchOperation.site)) {
            //     if (oldBatchOperation.operation!=newBatchOperation.operation ||
            //        oldBatchOperation.workstation!=newBatchOperation.workstation ||
            //        oldBatchOperation.supplier!=newBatchOperation.supplier) {
            //        BatchReportDet.findAllByBatchOperationAndSite(oldBatchOperation, oldBatchOperation.site).each{
            //             domainService.delete(it)
            //         }
            //     }
            // }

            oldBatchOperation.properties = params
            result = domainService.save(oldBatchOperation)

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
            // 會將關聯的批號履歷參數(BatchReportDet)一併刪除
            def batchOperation = BatchOperation.get(params.id)
            result = domainService.delete(batchOperation)
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
