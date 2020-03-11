package co.eliseev.fingate.controller.handler

import co.eliseev.fingate.service.exception.AccountNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AccountExceptionHandler  {

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(ex: AccountNotFoundException): String? = ex.message

}
