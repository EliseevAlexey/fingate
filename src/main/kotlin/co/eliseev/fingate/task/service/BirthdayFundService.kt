package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.GiftType
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.GiftService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.core.service.UserService
import co.eliseev.fingate.notification.model.event.FundEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.math.BigDecimal

interface BirthdayFundService {
    fun checkBirthdayAndFund()
}

@Service
class BirthdayFundServiceImpl(
    private val userService: UserService,
    private val operationService: OperationService,
    private val giftService: GiftService,
    private val paymentCategoryService: PaymentCategoryService,
    private val bankAccountService: BankAccountService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BirthdayFundService {

    override fun checkBirthdayAndFund() {
        val fundData = prepareFundData()
        userService.getAllWithTodayBirthday()
            .forEach { user ->
                user.defaultAccount?.let { defaultAccount ->
                    createOperation(fundData, defaultAccount)
                    notifyAboutFund(user)
                }
            }
    }

    inner class FunData(
        val giftAmount: BigDecimal,
        val paymentCategoryId: Long,
        val withdrawServiceName: String
    )

    fun prepareFundData(): FunData {
        val giftAmount = giftService.getByType(GiftType.BIRTHDAY).value
        val giftFundPaymentCategoryId = paymentCategoryService.getGiftFundPaymentCategory().id!!
        val withdrawServiceName = bankAccountService.getDefaultAccount().name!!

        return FunData(
            giftAmount = giftAmount,
            paymentCategoryId = giftFundPaymentCategoryId,
            withdrawServiceName = withdrawServiceName
        )
    }

    private fun createOperation(
        fundData: FunData,
        defaultAccount: BankAccount
    ) {
        val operationModel = OperationModel(
            accountId = defaultAccount.id!!,
            operationType = OperationType.FUNDING,
            paymentAmount = fundData.giftAmount,
            paymentCategoryId = fundData.paymentCategoryId,
            withdrawServiceName = fundData.withdrawServiceName
        )
        operationService.create(operationModel)
    }

    private fun notifyAboutFund(user: User) =
        applicationEventPublisher.publishEvent(FundEvent(user))

}
