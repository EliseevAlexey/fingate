package co.eliseev.fingate.model.converter

import co.eliseev.fingate.model.dto.AccountFeeDto
import co.eliseev.fingate.model.entity.AccountFee

fun AccountFee.toDto() =
    AccountFeeDto(
        id = this.id!!,
        value = this.value
    )
