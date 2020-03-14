package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.dto.AccountFeeDto
import co.eliseev.fingate.core.model.entity.AccountFee

fun AccountFee.toDto() =
    AccountFeeDto(
        id = this.id!!,
        value = this.value
    )
