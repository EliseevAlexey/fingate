package co.eliseev.fingate.core.controller.internal

import co.eliseev.fingate.core.controller.handler.BankAccountExceptionHandler
import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.util.TestSecurityConfiguration
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import java.time.LocalDate
import java.time.LocalDateTime

@Disabled("HttpStatus 404")
@WebMvcTest(controllers = [BalanceSetterController::class])
@ContextConfiguration(
    classes = [
        TestSecurityConfiguration::class,
        BankAccountExceptionHandler::class
    ]
)
internal class BalanceSetterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bankAccountService: BankAccountService

    @MockBean
    private lateinit var exceptionMessageConverter: ExceptionMessageConverter

    @Test
    fun testSetBalanceToAccount() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        val account = BankAccount(
            currency = "USD",
            cardNumber = 9990,
            cardCvvNumber = 999,
            cardExpirationDateTime = LocalDateTime.now().plusYears(1),
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = LocalDate.now()
        )
        whenever(bankAccountService.setBalance(balance, accountId)).thenReturn(account)

        mockMvc.put("/accounts/$accountId?balance=$balance")
            .andExpect { status { isOk } }
        verify(bankAccountService, times(1)).setBalance(balance, accountId)
    }

}
