package co.eliseev.fingate.core.service.exception

class BankAccountFeeNotFoundException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
