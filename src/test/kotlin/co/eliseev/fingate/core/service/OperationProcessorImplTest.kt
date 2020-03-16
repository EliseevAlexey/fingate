package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

import java.time.LocalDate
import java.time.LocalDateTime

internal class OperationProcessorImplTest {

    private val applicationEventPublisher = mock<ApplicationEventPublisher>()
    private val securityService = mock<SecurityService>()
    private lateinit var operationProcessor: OperationProcessor

    @BeforeEach
    fun resetMocks() {
        reset(applicationEventPublisher, securityService)
        operationProcessor = OperationProcessorImpl(applicationEventPublisher, securityService)
    }

    @Test
    fun processAndChangeStatus() {
        val operationStatus = OperationStatus.NEW
        val account = BankAccount(
            currency = "RUB",
            cvv = 100,
            expirationDateTime = LocalDateTime.now(),
            feeFrequency = FeeFrequency.MONTHLY,
            number = 1111222233334444,
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            registrationDate = LocalDate.now()
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

    @Test
    fun `test reject withdraw operation`() {
        // TODO
    }

    @Test
    fun `test reject fund operation`() {
        // TODO
    }

}
