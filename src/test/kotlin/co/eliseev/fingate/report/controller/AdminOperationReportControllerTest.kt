package co.eliseev.fingate.report.controller

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.report.service.AdminOperationReport
import co.eliseev.fingate.util.TestSecurityConfiguration
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.time.LocalDateTime

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [AdminOperationReportController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class AdminOperationReportControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var adminOperationReport: AdminOperationReport

    @MockBean
    private lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testGetAllYTD() {
        val operation = Operation(
            bankAccount = testBankAccount,
            operationType = OperationType.WITHDRAW,
            operationStatus = OperationStatus.REJECTED,
            paymentAmount = 100.toBigDecimal(),
            paymentDateTime = LocalDateTime.now(),
            paymentCategory = testPaymentCategory,
            withdrawServiceName = "testWithdrawServiceName"
        )
        whenever(adminOperationReport.getAllYTD()).thenReturn(listOf(operation))
        mockMvc.get("$OPERATION_REPORTS_PATH/admin/ytd")
            .andExpect { status { isOk } }
        verify(adminOperationReport, times(1)).getAllYTD()
    }

    companion object {
        private const val OPERATION_REPORTS_PATH = "/reports"
        private val testBankAccount = BankAccount(
            cardNumber = "0000111122223333",
            currency = "USD",
            cardCvvNumber = 999,
            cardExpirationDateTime = LocalDateTime.now(),
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = LocalDate.now()
        ).apply { id = 1L }
        private val testPaymentCategory = PaymentCategory(name = "testName").apply { id = 1L }
    }

}
