package co.eliseev.fingate.task.controller.internal.handler

import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.task.service.exception.UnsupportedFeeFrequencyException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class FeeFrequencyExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(UnsupportedFeeFrequencyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUnsupportedFeeFrequencyException(
        ex: UnsupportedFeeFrequencyException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
