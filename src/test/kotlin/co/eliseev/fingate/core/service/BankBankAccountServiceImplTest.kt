package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.BankAccountModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.repository.BankAccountRepository
import co.eliseev.fingate.core.service.exception.BankAccountNotFoundException
import co.eliseev.fingate.security.service.SecurityService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Optional

internal class BankBankAccountServiceImplTest {

    private val accountRepository = mock<BankAccountRepository>()
    private val securityService = mock<SecurityService>()
    private val accountFeeChecker = mock<BankAccountFeeService>()
    private val clock = mock<Clock>()
    private lateinit var bankAccountService: BankAccountService

    @BeforeEach
    fun reset() {
        reset(accountRepository, securityService, accountFeeChecker, clock)
        bankAccountService = BankAccountServiceImpl(
            accountRepository,
            securityService,
            accountFeeChecker,
            clock
        )
    }

    @Disabled("verify has new line")
    @Test
    fun testCreate() {
        val accountModel = BankAccountModel(
            cardNumber = "0000111122223333",
            currency = "USD",
            cardCvvNumber = 999,
            cardExpirationDateTime = LocalDateTime.now(),
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.YEARLY
        )
        val expected = BankAccount(
            cardIssuer = testUser,
            cardNumber = accountModel.cardNumber,
            currency = accountModel.currency,
            cardCvvNumber = accountModel.cardCvvNumber,
            cardExpirationDateTime = accountModel.cardExpirationDateTime,
            cardSystem = accountModel.cardSystem,
            cardType = accountModel.cardType,
            feeFrequency = FeeFrequency.YEARLY,
            registrationDate = testDate
        )
        whenever(accountRepository.save(any<BankAccount>())).thenReturn(expected)
        prepareClock()
        mockUser()

        val actual = bankAccountService.create(accountModel)
        verify(accountFeeChecker, times(1)).applyFee(expected)
        assertEquals(expected, actual)
    }

    @Test
    fun testDelete() {
        val accountId = 1L
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount))

        val result = bankAccountService.delete(accountId)
        assertTrue(result)
        verify(accountRepository, times(1)).delete(testAccount)
    }

    @Test
    fun `when delete not existing account then throw`() {
        val accountId = 1L
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        assertThrows(BankAccountNotFoundException::class.java) { bankAccountService.delete(accountId) }
    }

    @Test
    fun testGetAll() {
        mockUser()
        val accounts = listOf(testAccount)
        whenever(accountRepository.getAllByUser(testUser)).thenReturn(accounts)

        val actual = bankAccountService.getAll()
        assertEquals(accounts, actual)
    }

    @Test
    fun testSetBalance() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount))

        bankAccountService.setBalance(balance, accountId)
    }

    @Test
    fun `test set balance when account not found`() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        assertThrows(BankAccountNotFoundException::class.java) { bankAccountService.setBalance(balance, accountId) }
    }

    private fun mockUser() {
        whenever(securityService.getCurrentUser()).thenReturn(testUser)
    }

    private fun prepareClock() {
        whenever(clock.instant()).thenReturn(fixedClock.instant())
        whenever(clock.zone).thenReturn(fixedClock.zone)
    }

    companion object {
        private val testDateTime = LocalDateTime.of(2020, 3, 11, 22, 10, 0)
        private val testDate = testDateTime.toLocalDate()
        private val fixedClock =
            Clock.fixed(testDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
        private val testUser = User("testEmail", "testPassword").apply { id = 1 }
        private val testAccount = BankAccount(
            cardNumber = "0000111122223333",
            currency = "USD",
            cardCvvNumber = 999,
            cardExpirationDateTime = testDateTime,
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.YEARLY,
            registrationDate = testDate
        ).apply { id = 1L }
    }

}
