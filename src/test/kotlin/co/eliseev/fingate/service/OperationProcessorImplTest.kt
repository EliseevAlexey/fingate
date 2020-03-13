package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.model.entity.PaymentCategory
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.LocalDateTime

internal class OperationProcessorImplTest {

    @Test
    fun processAndChangeStatus() {
        val operationStatus = OperationStatus.NEW
        val operationProcessor = OperationProcessorImpl()

        val account = BankAccount(
            currency = "RUB",
            cvv = 100,
            expirationDateTime = LocalDateTime.now(),
            feeFrequency = FeeFrequency.MONTHLY,
            number = 1111222233334444,
            registrationDate = LocalDate.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT
        ).apply { id = 1L }
        val paymentCategory = PaymentCategory(
            name = "testName"
        ).apply { id = 1L }
        val operation = Operation(
            bankAccount = account,
            operationStatus = operationStatus,
            operationType = OperationType.WITHDRAW,
            paymentAmount = 100.toBigDecimal(),
            paymentCategory = paymentCategory,
            paymentDateTime = LocalDateTime.now(),
            withdrawServiceName = "testWithdrawServiceName"
        )
        operationProcessor.processAndChangeStatus(operation)
    }

}
