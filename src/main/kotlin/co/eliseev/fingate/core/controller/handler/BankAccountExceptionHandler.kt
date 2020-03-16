package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.RestMessagesDto
import co.eliseev.fingate.core.service.exception.BankAccountNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class BankAccountExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(BankAccountNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBankAccountNotFoundException(
        ex: BankAccountNotFoundException,
        locale: Locale
    ): RestMessagesDto = exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
