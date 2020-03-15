package co.eliseev.fingate.report.service

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.report.dto.AccountReportDto
import co.eliseev.fingate.core.model.dto.OperationDto
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.report.repository.OperationReportRepository
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.core.service.SecurityService
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime
import java.time.Year

interface OperationReport {
    fun getAllYtd(): List<AccountReportDto> // TODO model then in controller Dto
    fun getRejectedOperations(): List<Operation>
}

@Component
class OperationReportImpl(
    private val operationReportRepository: OperationReportRepository,
    private val securityService: SecurityService,
    private val bankAccountService: BankAccountService,
    private val clock: Clock
) : OperationReport {

    override fun getAllYtd(): List<AccountReportDto> { // TODO add specified period
        val allOperations = getBankIdByOperations()
        return bankAccountService.getAll().map { bankAccount ->
            createReportData(allOperations, bankAccount)
        }
    }

    private fun getBankIdByOperations(): Map<Long, List<Operation>> {
        val firstDayOfCurrentYear = getFirstDayOfCurrentYear()
        val user = securityService.getCurrentUser()
        return operationReportRepository.getAllByPaymentDateTimeGreaterThanEqualAndUser(firstDayOfCurrentYear, user)
            .groupBy { it.bankAccount.id!! }
    }

    private fun createReportData(
        allOperations: Map<Long, List<Operation>>,
        bankAccount: BankAccount
    ): AccountReportDto {
        val bankAccountId = bankAccount.id!!
        val accountOperations = allOperations[bankAccountId] ?: emptyList()
        return createAccountReport(accountOperations, bankAccountId)
    }

    private fun createAccountReport(
        accountOperations: List<Operation>,
        bankAccountId: Long
    ): AccountReportDto {
        var totalAmountProcessed = 0.toBigDecimal()
        var totalAmountSpent = 0.toBigDecimal()
        val operationsDto = mutableListOf<OperationDto>()
        accountOperations.forEach {
            val payment = it.paymentAmount
            if (it.operationType == OperationType.WITHDRAW) totalAmountSpent += payment
            if (it.operationStatus == OperationStatus.PROCESSED) totalAmountProcessed += payment
            operationsDto.add(it.toDto())
        }
        return AccountReportDto(
            totalAmountProcessed = totalAmountProcessed,
            totalAmountSpent = totalAmountSpent,
            bankAccountId = bankAccountId,
            operations = operationsDto
        )
    }

    private fun getFirstDayOfCurrentYear(): LocalDateTime = Year.now(clock).atDay(1).atStartOfDay()

    override fun getRejectedOperations(): List<Operation> = operationReportRepository.getRejectedOperations()

}
