package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.notification.model.event.WithdrawEvent
import co.eliseev.fingate.security.service.SecurityService
import co.eliseev.fingate.task.service.exception.UnsupportedFeeFrequencyException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDate
import java.time.Period

interface FeeService {
    fun checkAllAccountsAndWithdrawFee()
}

@Service
class FeeServiceImpl(
    private val bankAccountService: BankAccountService,
    private val operationService: OperationService,
    private val paymentCategoryService: PaymentCategoryService,
    private val clock: Clock,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val securityService: SecurityService
) : FeeService {

    @Transactional
    override fun checkAllAccountsAndWithdrawFee() {
        val operationData = prepareOperationData()
        bankAccountService.getAll()
            .forEach { tryToWithdrawFee(it, operationData) }
    }

    inner class OperationData(
        val today: LocalDate,
        val paymentCategoryId: Long,
        val defaultAccountName: String
    )

    fun prepareOperationData(): OperationData {
        val today = LocalDate.now(clock)
        val paymentCategoryId = paymentCategoryService.getFeePaymentCategory().id!!
        val defaultAccountName = bankAccountService.getDefaultAccount().name!!
        return OperationData(
            today = today,
            paymentCategoryId = paymentCategoryId,
            defaultAccountName = defaultAccountName
        )
    }

    private fun tryToWithdrawFee(bankAccount: BankAccount, operationData: OperationData) {
        when (bankAccount.feeFrequency) {
            FeeFrequency.MONTHLY -> tryToWithDrawMonthlyFee(bankAccount, operationData)
            FeeFrequency.YEARLY -> tryToWithDrawYearlyFee(bankAccount, operationData)
            else -> throw UnsupportedFeeFrequencyException("fees.frequency.not_supported", bankAccount.feeFrequency)
        }
    }

    private fun tryToWithDrawMonthlyFee(bankAccount: BankAccount, operationData: OperationData) {
        val today = operationData.today
        if (isPeriodMoreThanMonth(bankAccount, today)) {
            createOperationModel(bankAccount, operationData).also { operationModel ->
                save(operationModel)
                updateLastFeeWithdrawDate(bankAccount, today)
                notifyAboutWithdraw()
            }
        }
    }

    private fun isPeriodMoreThanMonth(bankAccount: BankAccount, today: LocalDate): Boolean =
        getPeriod(bankAccount, today).months > 0

    private fun getPeriod(bankAccount: BankAccount, today: LocalDate): Period {
        val from = bankAccount.lastFeeWithdrawDate ?: bankAccount.registrationDate
        return Period.between(from, today)
    }

    private fun createOperationModel(
        bankAccount: BankAccount,
        operationData: OperationData
    ) =
        OperationModel(
            accountId = bankAccount.id!!,
            operationStatus = OperationStatus.NEW,
            operationType = OperationType.WITHDRAW,
            paymentAmount = bankAccount.bankAccountFee?.value ?: defaultPayment,
            paymentCategoryId = operationData.paymentCategoryId,
            withdrawServiceName = operationData.defaultAccountName
        )
    private fun save(operationModel: OperationModel): Operation =
        operationService.create(operationModel = operationModel, force = true)

    private fun updateLastFeeWithdrawDate(bankAccount: BankAccount, today: LocalDate) {
        bankAccount.lastFeeWithdrawDate = today
    }

    private fun notifyAboutWithdraw() =
        applicationEventPublisher.publishEvent(WithdrawEvent(securityService.getCurrentUser()))

    private fun tryToWithDrawYearlyFee(bankAccount: BankAccount, operationData: OperationData) {
        val today = operationData.today
        if (isPeriodMoreThanYear(bankAccount, today)) {
            createOperationModel(bankAccount, operationData).also { operationModel ->
                save(operationModel)
                updateLastFeeWithdrawDate(bankAccount, today)
                notifyAboutWithdraw()
            }
        }
    }

    private fun isPeriodMoreThanYear(bankAccount: BankAccount, today: LocalDate): Boolean =
        getPeriod(bankAccount, today).years > 0

    companion object {
        private val defaultPayment = 0.toBigDecimal()
    }

}
