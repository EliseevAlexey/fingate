package co.eliseev.fingate.model.converter

import co.eliseev.fingate.model.OperationModel
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.PaymentCategory
import java.time.LocalDateTime

fun OperationModel.toEntity(
    paymentDateTime: LocalDateTime,
    bankAccount: BankAccount,
    operationStatus: OperationStatus,
    paymentCategory: PaymentCategory
) = Operation(
    withdrawServiceName = this.withdrawServiceName,
    paymentCategory = paymentCategory,
    paymentAmount = this.paymentAmount,
    paymentDateTime = paymentDateTime,
    bankAccount = bankAccount,
    operationType = this.operationType,
    operationStatus = operationStatus
)

fun OperationDto.toModel() =
    OperationModel(
        withdrawServiceName = this.withdrawServiceName,
        paymentCategoryId = this.paymentCategoryId,
        paymentAmount = this.paymentAmount,
        accountId = this.accountId,
        operationType = this.operationType
    )

fun Operation.toDto() =
    OperationDto(
        id = this.id,
        withdrawServiceName = this.withdrawServiceName,
        paymentCategoryId = this.paymentCategory.id!!,
        paymentAmount = this.paymentAmount,
        paymentDateTime = this.paymentDateTime,
        accountId = this.bankAccount.id!!,
        operationType = this.operationType,
        operationStatus = this.operationStatus
    )

fun List<Operation>.toDto() = this.map { it.toDto() }
