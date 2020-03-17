package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.core.service.exception.IllegalOperationStatusException
import co.eliseev.fingate.core.service.exception.OperationNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class OperationProcessorExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(IllegalOperationStatusException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalOperationStatusException(
        ex: IllegalOperationStatusException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

    @ExceptionHandler(OperationNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleOperationNotFoundException(
        ex: OperationNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
