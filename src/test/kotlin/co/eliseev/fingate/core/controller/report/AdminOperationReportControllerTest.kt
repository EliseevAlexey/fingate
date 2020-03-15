package co.eliseev.fingate.core.controller.report

import co.eliseev.fingate.report.controller.AdminOperationReportController
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.report.service.AdminOperationReport
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.apache.tomcat.jni.Local
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.time.LocalDateTime

@WebMvcTest(controllers = [AdminOperationReportController::class])
internal class AdminOperationReportControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var adminOperationReport: AdminOperationReport

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
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDateTime = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = LocalDate.now()
        ).apply { id = 1L }
        private val testPaymentCategory = PaymentCategory(name = "testName").apply { id = 1L }
    }

}
