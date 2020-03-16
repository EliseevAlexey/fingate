package co.eliseev.fingate.core.service.exception

import java.lang.RuntimeException

class GiftNotFoundException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
