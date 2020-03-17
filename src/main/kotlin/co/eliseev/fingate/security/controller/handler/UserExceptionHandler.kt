package co.eliseev.fingate.security.controller.handler

import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.security.service.exception.UserByEmailNotFoundException
import co.eliseev.fingate.security.service.exception.UserByIdNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class UserExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(UserByIdNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserByIdNotFoundException(
        ex: UserByIdNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto = exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

    @ExceptionHandler(UserByEmailNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserByEmailNotFoundException(
        ex: UserByEmailNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
