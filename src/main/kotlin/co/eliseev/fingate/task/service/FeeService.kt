package co.eliseev.fingate.task.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.repository.BankAccountFeeRepository
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.OperationService
import co.eliseev.fingate.core.service.PaymentCategoryService
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
    private val accountService: BankAccountService,
    private val bankAccountFeeRepository: BankAccountFeeRepository,
    private val paymentCategoryService: PaymentCategoryService,
    private val clock: Clock
) : FeeService {

    @Transactional
    override fun checkAllAccountsAndWithdrawFee() {
        val today = LocalDate.now(clock)
        bankAccountService.getAll()
            .forEach { tryToWithdrawFee(it, today) }
    }

    private fun tryToWithdrawFee(bankAccount: BankAccount, today: LocalDate) {
        when (bankAccount.feeFrequency) {
            FeeFrequency.MONTHLY -> tryToWithDrawMonthlyFee(bankAccount, today)
            FeeFrequency.YEARLY -> tryToWithDrawYearlyFee(bankAccount, today)
            else -> error("Unsupported Fee frequency ${bankAccount.feeFrequency}")
        }
    }

    private fun tryToWithDrawMonthlyFee(bankAccount: BankAccount, today: LocalDate) {
        if (isPeriodMoreThanMonth(bankAccount, today)) {
            createOperationModel(bankAccount).also { operationModel ->
                save(operationModel)
                updateLastFeeWithdrawDate(bankAccount, today)
            }
        }
    }

    private fun updateLastFeeWithdrawDate(bankAccount: BankAccount, today: LocalDate) {
        bankAccount.lastFeeWithdrawDate = today
    }

    private fun createOperationModel(bankAccount: BankAccount): OperationModel {
        val bankAccountFee = bankAccountFeeRepository.findBySystemAndFeeFrequency(
            system = bankAccount.system,
            feeFrequency = bankAccount.feeFrequency
        )
        return OperationModel(
            accountId = bankAccount.id!!,
            operationStatus = OperationStatus.NEW,
            operationType = OperationType.WITHDRAW,
            paymentAmount = bankAccountFee?.value ?: defaultPayment,
            paymentCategoryId = paymentCategoryService.getFeePaymentCategory().id!!,
            withdrawServiceName = accountService.getDefaultAccount().name!!
        )
    }

    private fun save(operationModel: OperationModel): Operation =
        operationService.create(operationModel = operationModel, force = true)

    private fun isPeriodMoreThanMonth(bankAccount: BankAccount, today: LocalDate): Boolean {
        val from = bankAccount.lastFeeWithdrawDate ?: bankAccount.registrationDate
        val period = Period.between(from, today)
        return period.months > 0
    }

    private fun tryToWithDrawYearlyFee(bankAccount: BankAccount, today: LocalDate) {
        if (isPeriodMoreThanYear(bankAccount, today)) {
            createOperationModel(bankAccount).also { operationModel ->
                save(operationModel)
                updateLastFeeWithdrawDate(bankAccount, today)
            }
        }
    }

    private fun isPeriodMoreThanYear(bankAccount: BankAccount, today: LocalDate): Boolean {
        val from = bankAccount.lastFeeWithdrawDate ?: bankAccount.registrationDate
        val period = Period.between(from, today)
        return period.years > 0
    }

    companion object {
        private val defaultPayment = 0.toBigDecimal()
        private const val WITHDRAW_SERVICE_NAME = "default_service_name"
    }

}
