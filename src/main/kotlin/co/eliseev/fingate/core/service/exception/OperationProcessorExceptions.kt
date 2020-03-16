package co.eliseev.fingate.core.service.exception

import java.lang.RuntimeException

class IllegalOperationStatusException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()

class OperationNotFoundException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
