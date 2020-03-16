package co.eliseev.fingate.core.service.exception

class BankAccountNotFoundException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
