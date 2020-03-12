package co.eliseev.fingate.controller

import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.model.converter.toModel
import co.eliseev.fingate.model.dto.AccountDto
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.model.entity.User
import co.eliseev.fingate.service.AccountService
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

@WebMvcTest(controllers = [AccountController::class])
internal class AccountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var accountService: AccountService


    @Test
    fun testCreateAccount() {
        val accountDto = AccountDto(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDate = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY
        )
        val accountModel = accountDto.toModel()
        val user = User("someEmail", "somePassword").apply { id = 1L }
        val account = accountModel.toEntity(user, LocalDate.now(clock))
        whenever(accountService.create(accountModel)).thenReturn(account)

        mockMvc.post(ACCOUNTS_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(accountDto)
        }.andExpect { status { isOk } }
        verify(accountService, times(1)).create(accountModel)
    }

    @Test
    fun testDelete() {
        val accountId = 1L

        mockMvc.delete("$ACCOUNTS_PATH/${accountId}")
            .andExpect { status { isOk } }
        verify(accountService, times(1)).delete(accountId)
    }

    @Test
    fun testGetAll() {
        mockMvc.get(ACCOUNTS_PATH)
            .andExpect { status { isOk } }
        verify(accountService, times(1)).getAll()
    }

    companion object {
        private const val ACCOUNTS_PATH = "/accounts"
        private val clock = Clock.systemDefaultZone()
    }

}
