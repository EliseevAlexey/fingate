package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.exception.IllegalOperationStatusException
import co.eliseev.fingate.notification.model.event.FundEvent
import co.eliseev.fingate.notification.model.event.RejectEvent
import co.eliseev.fingate.notification.model.event.WithdrawEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

interface OperationProcessor {
    fun processAndChangeStatus(operation: Operation, force: Boolean = false)
    fun reject(operation: Operation)
}

@Component
class OperationProcessorImpl(
    private val eventPublisher: ApplicationEventPublisher,
    private val securityService: SecurityService
) : OperationProcessor {

    override fun processAndChangeStatus(operation: Operation, force: Boolean) {
        validateStatus(operation)
        when (operation.operationType) {
            OperationType.WITHDRAW -> tryToWithdrawAndChangeStatus(operation, force)
            OperationType.FUNDING -> fundAndSetProcessedStatus(operation)
            else -> error("Unsupported operation ${operation.operationType}")
        }
    }

    private fun validateStatus(operation: Operation) {
        val operationStatus = operation.operationStatus
        if (operationStatus != OperationStatus.NEW) {
            throw IllegalOperationStatusException(
                "Operation status must be ${OperationStatus.NEW} status while processing, but was $operationStatus"
            )
        }
    }

    private fun tryToWithdrawAndChangeStatus(operation: Operation, force: Boolean) {
        val payment = operation.paymentAmount
        val balance = operation.bankAccount.balance
        when {
            force || balance >= payment -> withdrawAndSetProcessedStatus(operation)
            else -> setRejectStatus(operation)
        }
    }

    private fun withdrawAndSetProcessedStatus(operation: Operation) {
        withdraw(operation)
        setProcessedStatus(operation)
    }

    private fun fundAndSetProcessedStatus(operation: Operation) {
        fund(operation)
        setProcessedStatus(operation)
    }

    private fun fund(operation: Operation) {
        operation.bankAccount.balance += operation.paymentAmount
        eventPublisher.publishEvent(FundEvent(securityService.getCurrentUser()))
    }

    private fun withdraw(operation: Operation) {
        operation.bankAccount.balance -= operation.paymentAmount
        eventPublisher.publishEvent(WithdrawEvent(securityService.getCurrentUser()))
    }

    private fun setProcessedStatus(operation: Operation) {
        operation.operationStatus = OperationStatus.PROCESSED
    }

    private fun setRejectStatus(operation: Operation) {
        operation.operationStatus = OperationStatus.REJECTED
    }

    override fun reject(operation: Operation) {
        validateRollback(operation)
        when (operation.operationType) {
            OperationType.WITHDRAW -> rollbackWithdrawAndSetRejectStatus(operation)
            OperationType.FUNDING -> rollbackFundAndSetRejectedStatus(operation)
            else -> error("Unsupported operation type ${operation.operationType}")
        }
        eventPublisher.publishEvent(RejectEvent(securityService.getCurrentUser()))
    }

    private fun validateRollback(operation: Operation) {
        val operationStatus = operation.operationStatus
        if (operationStatus != OperationStatus.PROCESSED) {
            throw IllegalOperationStatusException(
                "Operation status must be ${OperationStatus.PROCESSED} status while rollback, but was $operationStatus"
            )
        }
    }

    private fun rollbackWithdrawAndSetRejectStatus(operation: Operation) {
        fund(operation)
        setRejectStatus(operation)
    }

    private fun rollbackFundAndSetRejectedStatus(operation: Operation) {
        withdraw(operation)
        setRejectStatus(operation)
    }

}
