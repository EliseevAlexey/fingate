package co.eliseev.fingate.security.controller.handler

import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.security.service.exception.RoleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class RoleExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(RoleNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRoleNotFoundException(
        ex: RoleNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto = exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
