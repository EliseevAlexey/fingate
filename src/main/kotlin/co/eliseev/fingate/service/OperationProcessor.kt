package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.service.exception.IllegalOperationStatusException
import org.springframework.stereotype.Component

interface OperationProcessor {
    fun processAndChangeStatus(operation: Operation)
}

@Component
class OperationProcessorImpl : OperationProcessor {

    override fun processAndChangeStatus(operation: Operation) {
        validateStatus(operation)
        when (operation.operationType) {
            OperationType.WITHDRAW -> tryToWithdrawAndChangeStatus(operation)
            else -> fundAndSetProcessedStatus(operation)
        }
    }

    private fun validateStatus(operation: Operation) {
        if (operation.operationStatus != OperationStatus.NEW) {
            throw IllegalOperationStatusException(
                "Operation status must be ${OperationStatus.NEW} while processing account, " +
                    "but was ${operation.operationStatus}"
            )
        }
    }

    private fun tryToWithdrawAndChangeStatus(operation: Operation) {
        val payment = operation.paymentAmount
        val balance = operation.account.balance
        when {
            balance >= payment -> withdrawAndSetProcessedStatus(operation)
            else -> setRejectStatus(operation)
        }
    }

    private fun withdrawAndSetProcessedStatus(operation: Operation) {
        operation.account.balance -= operation.paymentAmount
        operation.operationStatus = OperationStatus.PROCESSED
    }

    private fun setRejectStatus(operation: Operation) {
        operation.operationStatus = OperationStatus.REJECTED
    }

    private fun fundAndSetProcessedStatus(operation: Operation) {
        operation.account.balance += operation.paymentAmount
        operation.operationStatus = OperationStatus.PROCESSED
    }

}
