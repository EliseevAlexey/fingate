package co.eliseev.fingate.security.service.exception

class EmailDuplicateException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
