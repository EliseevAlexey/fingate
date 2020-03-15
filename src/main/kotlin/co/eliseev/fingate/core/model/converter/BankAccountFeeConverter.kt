package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.dto.BankAccountFeeDto
import co.eliseev.fingate.core.model.entity.BankAccountFee

fun BankAccountFee.toDto() =
    BankAccountFeeDto(
        id = this.id!!,
        value = this.value
    )
