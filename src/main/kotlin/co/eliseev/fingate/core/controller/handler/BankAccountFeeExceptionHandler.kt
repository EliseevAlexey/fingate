package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.core.service.exception.BankAccountFeeNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class BankAccountFeeExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(BankAccountFeeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBankAccountFeeNotFoundException(
        ex: BankAccountFeeNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto = exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
