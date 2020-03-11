package co.eliseev.fingate.service

import co.eliseev.fingate.model.AccountModel
import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.model.entity.User
import co.eliseev.fingate.repository.AccountRepository
import co.eliseev.fingate.service.exception.AccountNotFoundException
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.Optional

internal class AccountServiceImplTest {

    private val accountRepository = mock<AccountRepository>()
    private val securityService = mock<SecurityService>()
    private lateinit var accountService: AccountService

    @BeforeEach
    fun reset1() {
        reset(accountRepository, securityService)
        accountService = AccountServiceImpl(accountRepository, securityService)
    }

    @Test
    fun testCreate() {
        val accountModel = AccountModel(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDate = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT
        )
        val expected = accountModel.toEntity(testUser)
        whenever(accountRepository.save(any<Account>())).thenReturn(expected)
        mockUser()

        val actual = accountService.create(accountModel)
        assertEquals(expected, actual)
    }

    @Test
    fun testDelete() {
        val accountId = 1L
        whenever(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount))

        val result = accountService.delete(accountId)
        assertTrue(result)
        verify(accountRepository, atLeastOnce()).delete(testAccount)
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
        private val testAccount = Account(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDate = LocalDateTime.now(),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT
        ).apply { id = 1L }
    }
}
