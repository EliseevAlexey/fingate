package co.eliseev.fingate.model.converter

import co.eliseev.fingate.model.PaymentCategoryModel
import co.eliseev.fingate.model.dto.PaymentCategoryDto
import co.eliseev.fingate.model.entity.PaymentCategory

fun PaymentCategoryModel.toEntity() = PaymentCategory(this.name)

fun PaymentCategory.toDto() =
    PaymentCategoryDto(
        id = this.id,
        name = this.name
    )

fun List<PaymentCategory>.toDto() = this.map { it.toDto() }

fun PaymentCategoryDto.toModel() = PaymentCategoryModel(name = this.name)
