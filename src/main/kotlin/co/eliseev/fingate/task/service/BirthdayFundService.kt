package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.GiftType
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.GiftService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.core.service.UserService
import org.springframework.stereotype.Service

interface BirthdayFundService {
    fun checkBirthdayAndFund()
}

@Service
class BirthdayFundServiceImpl(
    private val userService: UserService,
    private val operationService: OperationService,
    private val giftService: GiftService,
    private val paymentCategoryService: PaymentCategoryService,
    private val bankAccountService: BankAccountService
) : BirthdayFundService {

    override fun checkBirthdayAndFund() {
        val users = userService.getAllWithTodayBirthday()
        val giftAmount = giftService.getByType(GiftType.BIRTHDAY).value
        val giftFundPaymentCategoryId = paymentCategoryService.getGiftFundPaymentCategory().id!!
        val withdrawServiceName = bankAccountService.getDefaultAccount().name!!
        users.forEach { user ->
            user.defaultAccount?.let { defaultAccount ->
                val operationModel = OperationModel(
                    accountId = defaultAccount.id!!,
                    operationType = OperationType.FUNDING,
                    paymentAmount = giftAmount,
                    paymentCategoryId = giftFundPaymentCategoryId,
                    withdrawServiceName = withdrawServiceName
                )
                operationService.create(operationModel)
                // TODO notification
            }
        }
    }

}
