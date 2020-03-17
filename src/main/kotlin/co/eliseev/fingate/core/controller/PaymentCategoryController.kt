package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.PaymentCategoryDto
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.security.util.HasAdminOrUserRights
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@HasAdminOrUserRights
@RequestMapping("/payment-categories")
class PaymentCategoryController(private val paymentCategoryService: PaymentCategoryService) {

    @GetMapping
    fun getAll(): List<PaymentCategoryDto> = paymentCategoryService.getAll().toDto()

    @PostMapping
    fun createPaymentCategory(@Valid @RequestBody paymentCategoryDto: PaymentCategoryDto): PaymentCategoryDto =
        paymentCategoryDto.toModel()
            .let { paymentCategoryService.create(it) }
            .toDto()

    @DeleteMapping("/{paymentCategoryId}")
    fun deletePaymentCategory(@PathVariable paymentCategoryId: Long): Boolean =
        paymentCategoryService.delete(paymentCategoryId)

}
