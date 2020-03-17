package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.core.service.exception.GiftNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class GiftExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(GiftNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleGiftNotFoundException(
        ex: GiftNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
