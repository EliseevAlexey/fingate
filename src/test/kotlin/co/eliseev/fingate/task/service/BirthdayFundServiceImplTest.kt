package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.Gift
import co.eliseev.fingate.core.model.entity.GiftType
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.GiftService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.core.service.UserService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

internal class BirthdayFundServiceImplTest {

    private val userService = mock<UserService>()
    private val operationService = mock<OperationService>()
    private val giftService = mock<GiftService>()
    private val paymentCategoryService = mock<PaymentCategoryService>()
    private val bankAccountService = mock<BankAccountService>()
    private lateinit var birthdayFundService: BirthdayFundService

    @BeforeEach
    fun resetMocks() {
        reset(userService, operationService, giftService, paymentCategoryService, bankAccountService)
        birthdayFundService = BirthdayFundServiceImpl(
            userService,
            operationService,
            giftService,
            paymentCategoryService,
            bankAccountService
        )
    }

    @Test
    fun testCheckBirthdayAndFund() {
        val userDefaultBankAccount = BankAccount(name = "testName").apply { id = 1L }
        val users = listOf(
            User(
                email = "testEmail",
                password = "testPassword",
                defaultAccount = userDefaultBankAccount
            )
        )
        whenever(userService.getAllWithTodayBirthday()).thenReturn(users)
        val gift = Gift(giftType = GiftType.BIRTHDAY, value = 100.toBigDecimal())
        whenever(giftService.getByType(GiftType.BIRTHDAY)).thenReturn(gift)
        val giftFundPaymentCategory = PaymentCategory(name = "test").apply { id = 1L }
        whenever(paymentCategoryService.getGiftFundPaymentCategory()).thenReturn(giftFundPaymentCategory)
        val defaultBankAccount = BankAccount(name = "testName").apply { id = 1L }
        whenever(bankAccountService.getDefaultAccount()).thenReturn(defaultBankAccount)
        val operationModel = OperationModel(
            accountId = defaultBankAccount.id!!,
            operationType = OperationType.FUNDING,
            paymentAmount = gift.value,
            paymentCategoryId = giftFundPaymentCategory.id!!,
            withdrawServiceName = defaultBankAccount.name!!
        )

        birthdayFundService.checkBirthdayAndFund()
        verify(operationService, times(1)).create(operationModel)
    }


}
