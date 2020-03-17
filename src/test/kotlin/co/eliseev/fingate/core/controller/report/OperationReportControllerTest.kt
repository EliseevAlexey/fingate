package co.eliseev.fingate.core.controller.report

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.report.controller.OperationReportController
import co.eliseev.fingate.report.service.OperationReport
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
import org.springframework.test.web.servlet.get

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [OperationReportController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class OperationReportControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var operationReport: OperationReport

    @MockBean
    private lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testGetAllYtd() {
        mockMvc.get("/reports/ytd")
            .andExpect { status { isOk } }
        verify(operationReport, times(1)).getAllYtd()
    }


    @Test
    fun testGetRejectedOperations() {
        mockMvc.get("/reports/rejected")
            .andExpect { status { isOk } }
        verify(operationReport, times(1)).getRejectedOperations()
    }

}
