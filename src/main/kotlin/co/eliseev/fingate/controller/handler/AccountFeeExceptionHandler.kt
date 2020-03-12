package co.eliseev.fingate.controller.handler

import co.eliseev.fingate.service.exception.AccountFeeNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AccountFeeExceptionHandler  {

    @ExceptionHandler(AccountFeeNotFoundException::class)
    fun handleAccountFeeNotFoundException(ex: AccountFeeNotFoundException): String? = ex.message

}
