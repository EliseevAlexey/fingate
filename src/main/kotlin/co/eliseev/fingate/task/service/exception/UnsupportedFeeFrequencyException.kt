package co.eliseev.fingate.task.service.exception

class UnsupportedFeeFrequencyException(
    val messageCode: String,
    val param: Any? = null,
    val params: Array<Any>? = null
) : RuntimeException()
