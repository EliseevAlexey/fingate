package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.BankAccountFee
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.springframework.context.ApplicationEventPublisher
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

internal class FeeServiceImplTest {

    private val bankAccountService = mock<BankAccountService>()
    private val operationService = mock<OperationService>()
    private val paymentCategoryService = mock<PaymentCategoryService>()
    private val clock = mock<Clock>()
    private val applicationEventPublisher = mock<ApplicationEventPublisher>()
    private lateinit var feeService: FeeService

    @BeforeEach
    fun restMocks() {
        reset(
            bankAccountService,
            operationService,
            paymentCategoryService,
            clock,
            applicationEventPublisher
        )
        prepareClock()
        feeService = FeeServiceImpl(
            bankAccountService,
            operationService,
            paymentCategoryService,
            clock,
            applicationEventPublisher
        )
    }

    @Test
    fun `when withdraw monthly fee and last was yesterday then skip`() {

        val lastFeeWithdrawDate = testDate.minusDays(1)
        val bankAccounts = listOf(
            createBankAccountWithParameters(FeeFrequency.MONTHLY, lastFeeWithdrawDate, masterCardMonthlyBankAccountFee)
        )
        whenever(bankAccountService.getAll()).thenReturn(bankAccounts)
        whenever(bankAccountService.getDefaultAccount()).thenReturn(defaultAccount)
        whenever(paymentCategoryService.getFeePaymentCategory()).thenReturn(feePaymentCategory)

        feeService.checkAllAccountsAndWithdrawFee()
        verify(operationService, never()).create(operationModel = any(), force = any())
        verify(applicationEventPublisher, never()).publishEvent(any())
    }

    @Test
    fun `when withdraw monthly fee and last was greater than month then withdraw`() {

        val lastFeeWithdrawDate = testDate.minusMonths(1).minusDays(1)
        val bankAccounts = listOf(
            createBankAccountWithParameters(FeeFrequency.MONTHLY, lastFeeWithdrawDate, masterCardMonthlyBankAccountFee)
        )
        whenever(bankAccountService.getAll()).thenReturn(bankAccounts)
        whenever(bankAccountService.getDefaultAccount()).thenReturn(defaultAccount)
        whenever(paymentCategoryService.getFeePaymentCategory()).thenReturn(feePaymentCategory)
        val operationModel = OperationModel(
            accountId = defaultAccount.id!!,
            operationType = OperationType.WITHDRAW,
            paymentAmount = masterCardMonthlyBankAccountFee.value,
            paymentCategoryId = feePaymentCategory.id!!,
            withdrawServiceName = defaultAccount.name!!,
            operationStatus = OperationStatus.NEW
        )

        feeService.checkAllAccountsAndWithdrawFee()
        verify(operationService, times(1)).create(operationModel = operationModel, force = true)
        verify(applicationEventPublisher, times(1)).publishEvent(any())
    }

    @Test
    fun `when withdraw yearly fee and last was yesterday then skip`() {

        val lastFeeWithdrawDate = testDate.minusDays(1)
        val bankAccounts = listOf(
            createBankAccountWithParameters(FeeFrequency.YEARLY, lastFeeWithdrawDate, masterCardYearlyBankAccountFee)
        )
        whenever(bankAccountService.getAll()).thenReturn(bankAccounts)
        whenever(bankAccountService.getDefaultAccount()).thenReturn(defaultAccount)
        whenever(paymentCategoryService.getFeePaymentCategory()).thenReturn(feePaymentCategory)

        feeService.checkAllAccountsAndWithdrawFee()
        verify(operationService, never()).create(operationModel = any(), force = any())
        verify(applicationEventPublisher, never()).publishEvent(any())

    }


    @Test
    fun `when withdraw yearly fee and last was greater than year then withdraw`() {
        val lastFeeWithdrawDate = testDate.minusYears(1).minusDays(1)
        val bankAccounts = listOf(
            createBankAccountWithParameters(FeeFrequency.YEARLY, lastFeeWithdrawDate, masterCardYearlyBankAccountFee)
        )
        whenever(bankAccountService.getAll()).thenReturn(bankAccounts)
        whenever(bankAccountService.getDefaultAccount()).thenReturn(defaultAccount)
        whenever(paymentCategoryService.getFeePaymentCategory()).thenReturn(feePaymentCategory)
        val operationModel = OperationModel(
            accountId = defaultAccount.id!!,
            operationType = OperationType.WITHDRAW,
            paymentAmount = masterCardYearlyBankAccountFee.value,
            paymentCategoryId = feePaymentCategory.id!!,
            withdrawServiceName = defaultAccount.name!!,
            operationStatus = OperationStatus.NEW
        )

        feeService.checkAllAccountsAndWithdrawFee()
        verify(operationService, times(1)).create(operationModel = operationModel, force = true)
        verify(applicationEventPublisher, times(1)).publishEvent(any())
    }

    private fun createBankAccountWithParameters(
        feeFrequency: FeeFrequency,
        lastFeeWithdrawDate: LocalDate,
        bankAccountFee: BankAccountFee
    ) = BankAccount(
        cardNumber = 9990,
        currency = "USD",
        cardCvvNumber = 999,
        cardSystem = CardSystem.MASTER_CARD,
        cardType = CardType.CREDIT,
        feeFrequency = feeFrequency,
        cardExpirationDateTime = LocalDateTime.now(),
        lastFeeWithdrawDate = lastFeeWithdrawDate,
        registrationDate = testDate,
        bankAccountFee = bankAccountFee,
        user = testUser
    ).apply { id = 1L }

    private fun prepareClock() {
        whenever(clock.instant()).thenReturn(fixedClock.instant())
        whenever(clock.zone).thenReturn(fixedClock.zone)
    }

    companion object {

        private val testDateTime = LocalDateTime.of(2020, 3, 11, 22, 10, 0)
        private val testDate = testDateTime.toLocalDate()
        private val fixedClock =
            Clock.fixed(testDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())

        private val testUser = User(
            email = "testEmail",
            password = "testPassword"
        ).apply { id = 1L }

        private val defaultAccount = BankAccount(
            currency = "RUB",
            cardCvvNumber = 0,
            cardExpirationDateTime = LocalDateTime.now().plusYears(1),
            feeFrequency = FeeFrequency.MONTHLY,
            cardNumber = 0,
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            lastFeeWithdrawDate = testDate,
            registrationDate = testDate,
            name = "testName",
            user = testUser
        ).apply { id = 1L }

        private val masterCardMonthlyBankAccountFee = BankAccountFee(
            cardSystem = CardSystem.MASTER_CARD,
            feeFrequency = FeeFrequency.MONTHLY,
            value = 100.toBigDecimal()
        ).apply { id = 1L }

        private val masterCardYearlyBankAccountFee = BankAccountFee(
            cardSystem = CardSystem.MASTER_CARD,
            feeFrequency = FeeFrequency.YEARLY,
            value = 1000.toBigDecimal()
        ).apply { id = 1L }

        private val feePaymentCategory = PaymentCategory(
            name = "feePaymentCategory"
        ).apply { id = 1L }

    }

}
