package co.eliseev.fingate.controller

import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.model.converter.toModel
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.model.entity.PaymentCategory
import co.eliseev.fingate.service.OperationService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.time.LocalDateTime

@WebMvcTest(controllers = [OperationController::class])
internal class OperationControllerTest {

    @Autowired
    private lateinit var mockMvn: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var operationService: OperationService

    @Test
    fun create() {
        val operationDto = OperationDto(
            withdrawServiceName = "SomeService",
            paymentCategoryId = 1L,
            paymentAmount = 100.toBigDecimal(),
            accountId = 1L,
            operationType = OperationType.WITHDRAW
        )
        val account = BankAccount(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDateTime = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = LocalDate.now()
        ).apply { id = 1L }
        val operationModel = operationDto.toModel()
        val operation = operationModel.toEntity(
            bankAccount = account,
            operationStatus = OperationStatus.PROCESSED,
            paymentCategory = PaymentCategory(name = "testName").apply { id = 1L },
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
    fun getAllByOperationType() {
        val operationType = OperationType.WITHDRAW
        mockMvn.get("$OPERATIONS_PATH?operationType=$operationType")
            .andExpect { status { isOk } }
        verify(operationService, times(1)).getAllByOperationType(operationType)
    }

    companion object {
        private const val OPERATIONS_PATH = "/operations"
    }
}
