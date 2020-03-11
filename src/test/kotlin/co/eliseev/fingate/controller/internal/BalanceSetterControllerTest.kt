package co.eliseev.fingate.controller.internal

import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.service.AccountService
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import java.time.LocalDateTime

@WebMvcTest(controllers = [BalanceSetterController::class])
internal class BalanceSetterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var accountService: AccountService

    @Test
    fun testSetBalanceToAccount() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        val account = Account(
            currency = "USD",
            number = 9990,
            cvv = 999,
            expirationDate = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT
        )
        whenever(accountService.setBalance(balance, accountId)).thenReturn(account)

        mockMvc.put("/accounts/$accountId?balance=$balance")
            .andExpect { status { isOk } }
        verify(accountService, atLeastOnce()).setBalance(balance, accountId)
    }

}
