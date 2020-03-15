package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.BankAccountNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BankAccountExceptionHandler  {

    @ExceptionHandler(BankAccountNotFoundException::class)
    fun handleBankAccountNotFoundException(ex: BankAccountNotFoundException): String? = ex.message

}
