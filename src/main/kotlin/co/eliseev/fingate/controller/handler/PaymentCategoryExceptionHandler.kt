package co.eliseev.fingate.controller.handler

import co.eliseev.fingate.service.exception.PaymentCategoryAlreadyExists
import co.eliseev.fingate.service.exception.PaymentCategoryNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PaymentCategoryExceptionHandler {

    @ExceptionHandler(PaymentCategoryNotFoundException::class)
    fun handlePaymentCategoryNotFoundException(ex: PaymentCategoryNotFoundException): String? = ex.message

    @ExceptionHandler(PaymentCategoryAlreadyExists::class)
    fun handlePaymentCategoryAlreadyExists(ex: PaymentCategoryAlreadyExists): String? = ex.message

}
