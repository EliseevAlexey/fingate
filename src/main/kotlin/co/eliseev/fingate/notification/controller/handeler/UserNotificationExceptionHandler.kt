package co.eliseev.fingate.notification.controller.handeler

import co.eliseev.fingate.notification.service.exception.UserNotificationNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserNotificationExceptionHandler {

    @ExceptionHandler(UserNotificationNotFoundException::class)
    fun handleUserNotificationNotFoundException(ex: UserNotificationNotFoundException) = ex.message

}
