package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.BankAccountDto
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.service.BankAccountService
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
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [BankAccountController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class BankBankAccountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var bankAccountService: BankAccountService

    @MockBean
    lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testCreateAccount() {
        val accountDto = BankAccountDto(
            cardNumber = 9990,
            currency = "USD",
            cardCvvNumber = 999,
            expirationDateTime = LocalDateTime.now(),
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY
        )
        val accountModel = accountDto.toModel()
        val user = User("someEmail", "somePassword").apply { id = 1L }
        val account = accountModel.toEntity(user, user, LocalDate.now(clock))
        whenever(bankAccountService.create(accountModel)).thenReturn(account)

        mockMvc.post(ACCOUNTS_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(accountDto)
        }.andExpect { status { isOk } }
        verify(bankAccountService, times(1)).create(accountModel)
    }

    @Test
    fun testDelete() {
        val accountId = 1L
        mockMvc.delete("$ACCOUNTS_PATH/${accountId}")
            .andExpect { status { isOk } }
        verify(bankAccountService, times(1)).delete(accountId)
    }

    @Test
    fun testGetAll() {
        mockMvc.get(ACCOUNTS_PATH)
            .andExpect { status { isOk } }
        verify(bankAccountService, times(1)).getAll()
    }

    companion object {
        private const val ACCOUNTS_PATH = "/bank-accounts"
        private val clock = Clock.systemDefaultZone()
    }

}
