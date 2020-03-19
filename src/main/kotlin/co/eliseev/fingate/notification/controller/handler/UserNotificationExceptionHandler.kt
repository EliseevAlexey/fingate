package co.eliseev.fingate.notification.controller.handler

import co.eliseev.fingate.core.controller.handler.ExceptionMessageConverter
import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import co.eliseev.fingate.notification.service.exception.UserNotificationNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class UserNotificationExceptionHandler(private val exceptionMessageConverter: ExceptionMessageConverter) {

    @ExceptionHandler(UserNotificationNotFoundException::class)
    fun handleUserNotificationNotFoundException(
        ex: UserNotificationNotFoundException,
        locale: Locale
    ): RestResponseMessagesDto =
        exceptionMessageConverter.createErrorMessage(ex.messageCode, locale, ex.param, ex.params)

}
