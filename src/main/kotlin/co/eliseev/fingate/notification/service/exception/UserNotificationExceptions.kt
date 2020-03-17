package co.eliseev.fingate.notification.service.exception

import java.lang.RuntimeException

class UserNotificationNotFoundException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
