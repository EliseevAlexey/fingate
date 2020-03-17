package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.PaymentCategoryDto
import co.eliseev.fingate.core.service.PaymentCategoryService
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [PaymentCategoryController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class PaymentCategoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var paymentCategoryService: PaymentCategoryService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testGetAll() {
        mockMvc.get(PAYMENT_CATEGORIES_PATH)
            .andExpect { status { isOk } }
        verify(paymentCategoryService, times(1)).getAll()
    }

    @Test
    fun testCreatePaymentCategory() {
        val paymentCategoryDto = PaymentCategoryDto(name = "testName")
        val paymentCategoryModel = paymentCategoryDto.toModel()
        val paymentCategory = paymentCategoryModel.toEntity()
        whenever(paymentCategoryService.create(paymentCategoryModel)).thenReturn(paymentCategory)

        mockMvc.post(PAYMENT_CATEGORIES_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(paymentCategoryDto)
        }.andExpect { status { isOk } }
        verify(paymentCategoryService, times(1)).create(paymentCategoryModel)
    }

    @Test
    fun testDeletePaymentCategory() {
        val paymentCategoryId = 1L
        mockMvc.delete("$PAYMENT_CATEGORIES_PATH/${paymentCategoryId}")
            .andExpect { status { isOk } }
        verify(paymentCategoryService, times(1)).delete(paymentCategoryId)
    }

    companion object {
        private const val PAYMENT_CATEGORIES_PATH = "/payment-categories"
    }

}
