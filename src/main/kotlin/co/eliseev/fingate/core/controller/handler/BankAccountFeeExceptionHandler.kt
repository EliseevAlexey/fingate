package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.BankAccountFeeNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BankAccountFeeExceptionHandler  {

    @ExceptionHandler(BankAccountFeeNotFoundException::class)
    fun handleBankAccountFeeNotFoundException(ex: BankAccountFeeNotFoundException): String? = ex.message

}
