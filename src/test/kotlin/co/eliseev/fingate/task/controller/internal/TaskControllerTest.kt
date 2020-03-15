package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.task.service.FeeService
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

@WebMvcTest(controllers = [TaskController::class])
internal class TaskControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var feeService: FeeService

    @Test
    fun allAccountFeeWithdraws() {
        mockMvc.put("/tasks/fees/check-all-and-withdraw-fee")
            .andExpect { status { isOk } }
        verify(feeService, times(1)).checkAllAccountsAndWithdrawFee()
    }
    
}
