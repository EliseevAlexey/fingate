package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.AccountNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AccountExceptionHandler  {

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(ex: AccountNotFoundException): String? = ex.message

}
