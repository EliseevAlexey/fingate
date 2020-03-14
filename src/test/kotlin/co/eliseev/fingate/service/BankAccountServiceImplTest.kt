package co.eliseev.fingate.service

import co.eliseev.fingate.core.model.AccountModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.repository.AccountRepository
import co.eliseev.fingate.core.service.AccountFeeService
import co.eliseev.fingate.core.service.AccountService
import co.eliseev.fingate.core.service.AccountServiceImpl
import co.eliseev.fingate.core.service.SecurityService
import co.eliseev.fingate.core.service.exception.AccountNotFoundException
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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional

internal class BankAccountServiceImplTest {

    private val accountRepository = mock<AccountRepository>()
    private val securityService = mock<SecurityService>()
    private val accountFeeChecker = mock<AccountFeeService>()
    private val clock = mock<Clock>()
    private lateinit var accountService: AccountService

    @BeforeEach
    fun reset() {
        reset(accountRepository, securityService, accountFeeChecker, clock)
        accountService = AccountServiceImpl(
            accountRepository,
            securityService,
            accountFeeChecker,
            clock
        )
    }

    @Test
    @Disabled("Comparison Failure: NEW LINE, NPE mock clock")
    fun testCreate() {
        val accountModel = AccountModel(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDate = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.YEARLY
        )
        val expected = BankAccount(
            number = accountModel.number,
            currency = accountModel.currency,
            cvv = accountModel.cvv,
            expirationDateTime = accountModel.expirationDate,
            system = accountModel.system,
            type = accountModel.type,
            feeFrequency = FeeFrequency.YEARLY,
            registrationDate = LocalDate.now()
        )
        whenever(accountRepository.save(any<BankAccount>())).thenReturn(expected)
        val testDate = LocalDate.of(2020, 3, 12)
        whenever(clock.instant()).thenReturn(Instant.ofEpochMilli(testDate.toEpochDay()))
        mockUser()

        val actual = accountService.create(accountModel)
        verify(accountFeeChecker, times(1)).applyFee(expected)
        assertEquals(expected, actual)
    }

    @Test
    fun testDelete() {
        val accountId = 1L
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount))

        val result = accountService.delete(accountId)
        assertTrue(result)
        verify(accountRepository, times(1)).delete(testAccount)
    }

    @Test
    fun `when delete not existing account then throw`() {
        val accountId = 1L
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        assertThrows(AccountNotFoundException::class.java) { accountService.delete(accountId) }
    }

    @Test
    fun testGetAll() {
        mockUser()
        val accounts = listOf(testAccount)
        whenever(accountRepository.getAllByIssuer(testUser)).thenReturn(accounts)

        val actual = accountService.getAll()
        assertEquals(accounts, actual)
    }

    @Test
    fun testSetBalance() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount))

        accountService.setBalance(balance, accountId)
    }

    @Test
    fun `test set balance when account not found`() {
        val accountId = 1L
        val balance = 100.toBigDecimal()
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        assertThrows(AccountNotFoundException::class.java) { accountService.setBalance(balance, accountId) }
    }

    private fun mockUser() {
        whenever(securityService.getCurrentUser()).thenReturn(testUser)
    }

    companion object {
        private val testUser = User("testEmail", "testPassword").apply { id = 1 }
        private val testAccount = BankAccount(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDateTime = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.YEARLY,
            registrationDate = LocalDate.now()
        ).apply { id = 1L }
    }

}
