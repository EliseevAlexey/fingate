package co.eliseev.fingate.core.model.dto

import java.math.BigDecimal

data class BankAccountFeeDto(
    val id: Long,
    val value: BigDecimal
)
