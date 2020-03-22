package co.eliseev.fingate.security.model.validator.exception

class EmailValidationException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
