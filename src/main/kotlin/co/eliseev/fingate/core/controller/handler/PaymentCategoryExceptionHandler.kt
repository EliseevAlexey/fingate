package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.core.service.exception.PaymentCategoryAlreadyExists
import co.eliseev.fingate.core.service.exception.PaymentCategoryNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class PaymentCategoryExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(PaymentCategoryNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePaymentCategoryNotFoundException(
        ex: PaymentCategoryNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

    @ExceptionHandler(PaymentCategoryAlreadyExists::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePaymentCategoryAlreadyExists(
        ex: PaymentCategoryAlreadyExists,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
