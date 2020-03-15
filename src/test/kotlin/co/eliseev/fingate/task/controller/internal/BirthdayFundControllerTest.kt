package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.task.service.BirthdayFundService
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

@WebMvcTest(controllers = [BirthdayFundController::class])
internal class BirthdayFundControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var birthdayFundService: BirthdayFundService

    @Test
    fun checkBirthdayAndFund() {
        mockMvc.put("/tasks/gifts/check-birthday-and-fund")
            .andExpect { status { isOk } }
        verify(birthdayFundService, times(1)).checkBirthdayAndFund()
    }

}
