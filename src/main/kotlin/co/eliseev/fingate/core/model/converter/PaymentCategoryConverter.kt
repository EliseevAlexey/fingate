package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.PaymentCategoryModel
import co.eliseev.fingate.core.model.dto.PaymentCategoryDto
import co.eliseev.fingate.core.model.entity.PaymentCategory

fun PaymentCategoryModel.toEntity() = PaymentCategory(name = this.name)

fun PaymentCategory.toDto() =
    PaymentCategoryDto(
        id = this.id,
        name = this.name
    )

fun List<PaymentCategory>.toDto() = this.map { it.toDto() }

fun PaymentCategoryDto.toModel() = PaymentCategoryModel(name = this.name)
