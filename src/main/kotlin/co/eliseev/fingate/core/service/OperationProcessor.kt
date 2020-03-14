package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.exception.IllegalOperationStatusException
import org.springframework.stereotype.Component

interface OperationProcessor {
    fun processAndChangeStatus(operation: Operation)
    fun reject(operation: Operation)
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
        val operationStatus = operation.operationStatus
        if (operationStatus != OperationStatus.NEW) {
            throw IllegalOperationStatusException(
                "Operation status must be ${OperationStatus.NEW} status while processing, but was $operationStatus"
            )
        }
    }

    private fun tryToWithdrawAndChangeStatus(operation: Operation) {
        val payment = operation.paymentAmount
        val balance = operation.bankAccount.balance
        when {
            balance >= payment -> withdrawAndSetProcessedStatus(operation)
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
    }

    private fun withdraw(operation: Operation) {
        operation.bankAccount.balance -= operation.paymentAmount
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
            else -> rollbackFundAndSetRejectedStatus(operation)
        }
        //TODO notify client
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
