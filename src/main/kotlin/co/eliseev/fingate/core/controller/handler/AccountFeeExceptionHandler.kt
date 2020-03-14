package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.AccountFeeNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AccountFeeExceptionHandler  {

    @ExceptionHandler(AccountFeeNotFoundException::class)
    fun handleAccountFeeNotFoundException(ex: AccountFeeNotFoundException): String? = ex.message

}
