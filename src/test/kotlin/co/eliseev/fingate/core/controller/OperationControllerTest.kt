package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.OperationDto
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.util.TestSecurityConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDate
import java.time.LocalDateTime

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [OperationController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class OperationControllerTest {

    @Autowired
    private lateinit var mockMvn: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var operationService: OperationService

    @MockBean
    lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testCreateOperation() {
        val operationDto = OperationDto(
            withdrawServiceName = "SomeService",
            paymentCategoryId = 1L,
            paymentAmount = 100.toBigDecimal(),
            accountId = 1L,
            operationType = OperationType.WITHDRAW
        )
        val operationModel = operationDto.toModel()
        val operation = operationModel.toEntity(
            bankAccount = testBankAccount,
            operationStatus = OperationStatus.PROCESSED,
            paymentCategory = testPaymentCategory,
            paymentDateTime = LocalDateTime.now()
        )
        whenever(operationService.create(operationModel)).thenReturn(operation)

        mockMvn.post(OPERATIONS_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(operationDto)
        }.andExpect { status { isOk } }
        verify(operationService, times(1)).create(operationModel)
    }

    @Test
    fun testGetAllByOperationType() {
        val operationType = OperationType.WITHDRAW
        mockMvn.get("$OPERATIONS_PATH?operationType=$operationType")
            .andExpect { status { isOk } }
        verify(operationService, times(1)).getAllByOperationType(operationType)
    }

    @Test
    fun testReject() {
        val operationId = 1L

        val operation = Operation(
            bankAccount = testBankAccount,
            operationStatus = OperationStatus.REJECTED,
            operationType = OperationType.WITHDRAW,
            paymentAmount = 100.toBigDecimal(),
            paymentCategory = testPaymentCategory,
            paymentDateTime = LocalDateTime.now(),
            withdrawServiceName = "testWithdrawServiceName"
        )
        whenever(operationService.reject(operationId)).thenReturn(operation)
        mockMvn.put("$OPERATIONS_PATH/$operationId/reject")
            .andExpect { status { isOk } }
        verify(operationService, times(1)).reject(operationId)
    }

    companion object {
        private const val OPERATIONS_PATH = "/operations"
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
