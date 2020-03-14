package co.eliseev.fingate.report.dto

import co.eliseev.fingate.core.model.dto.OperationDto
import java.math.BigDecimal

data class AccountReportDto(
    val totalAmountProcessed: BigDecimal,
    val totalAmountSpent: BigDecimal,
    val bankAccountId: Long,
    val operations: List<OperationDto>
)
