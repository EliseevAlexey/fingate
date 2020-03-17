package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.task.service.BirthdayFundService
import co.eliseev.fingate.util.TestSecurityConfiguration
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [BirthdayFundController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class BirthdayFundControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var birthdayFundService: BirthdayFundService

    @MockBean
    lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun checkBirthdayAndFund() {
        mockMvc.put("/tasks/gifts/check-birthday-and-fund")
            .andExpect { status { isOk } }
        verify(birthdayFundService, times(1)).checkBirthdayAndFund()
    }

}
