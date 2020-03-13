package co.eliseev.fingate.model.dto

import java.math.BigDecimal

data class AccountReportDto(
    val totalAmountProcessed: BigDecimal,
    val totalAmountSpent: BigDecimal,
    val bankAccountId: Long,
    val operations: List<OperationDto>
)
