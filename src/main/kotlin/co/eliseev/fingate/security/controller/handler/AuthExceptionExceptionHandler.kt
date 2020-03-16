package co.eliseev.fingate.security.controller.handler

import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.dto.RestMessagesDto
import co.eliseev.fingate.security.service.exception.EmailDuplicateException
import co.eliseev.fingate.security.service.exception.RoleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class AuthExceptionExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(EmailDuplicateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmailDuplicateException(
        ex: EmailDuplicateException,
        locale: Locale
    ): RestMessagesDto = exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
