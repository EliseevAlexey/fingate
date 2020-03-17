package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.exception.IllegalOperationStatusException
import co.eliseev.fingate.notification.model.event.FundEvent
import co.eliseev.fingate.notification.model.event.RejectEvent
import co.eliseev.fingate.notification.model.event.WithdrawEvent
import co.eliseev.fingate.security.service.SecurityService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

interface OperationProcessor {
    fun process(operation: Operation, force: Boolean = false)
    fun reject(operation: Operation)
}

@Component
class OperationProcessorImpl(
    private val eventPublisher: ApplicationEventPublisher,
    private val securityService: SecurityService
) : OperationProcessor {

    override fun process(operation: Operation, force: Boolean) {
        validateStatus(operation)
        when (operation.operationType) {
            OperationType.WITHDRAW -> tryToWithdrawAndChangeStatus(operation, force)
            OperationType.FUNDING -> fundAndSetProcessedStatus(operation)
            else -> throw IllegalOperationStatusException("operations.type.not_supported", operation.operationType)
        }
    }

    private fun validateStatus(operation: Operation) {
        val operationStatus = operation.operationStatus
        if (operationStatus != OperationStatus.NEW) {
            throw IllegalOperationStatusException(
                "operations.processing.status.illegal_state", arrayOf(OperationStatus.NEW, operationStatus)
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
        withdrawAndNotify(operation)
        setProcessedStatus(operation)
    }

    private fun fundAndSetProcessedStatus(operation: Operation) {
        fundAndNotify(operation)
        setProcessedStatus(operation)
    }

    private fun fundAndNotify(operation: Operation) {
        operation.bankAccount.balance += operation.paymentAmount
        eventPublisher.publishEvent(FundEvent(securityService.getCurrentUser()))
    }

    private fun withdrawAndNotify(operation: Operation) {
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
            else -> throw IllegalOperationStatusException("operations.type.not_supported", operation.operationType)
        }
        eventPublisher.publishEvent(RejectEvent(securityService.getCurrentUser()))
    }

    private fun validateRollback(operation: Operation) {
        val operationStatus = operation.operationStatus
        if (operationStatus != OperationStatus.PROCESSED) {
            throw IllegalOperationStatusException(
                "operations.rejecting.status.illegal_state", arrayOf(OperationStatus.PROCESSED, operationStatus)
            )
        }
    }

    private fun rollbackWithdrawAndSetRejectStatus(operation: Operation) {
        fundAndNotify(operation)
        setRejectStatus(operation)
    }

    private fun rollbackFundAndSetRejectedStatus(operation: Operation) {
        withdrawAndNotify(operation)
        setRejectStatus(operation)
    }

}
