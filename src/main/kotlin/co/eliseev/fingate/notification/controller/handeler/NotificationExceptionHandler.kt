package co.eliseev.fingate.notification.controller.handeler

import co.eliseev.fingate.notification.service.exception.NotificationNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NotificationExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException::class)
    fun handleNotificationNotFoundException(ex: NotificationNotFoundException) = ex.message

}
