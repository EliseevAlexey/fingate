package co.eliseev.fingate.core.service.exception

class PaymentCategoryNotFoundException(message: String) : RuntimeException(message)

class PaymentCategoryAlreadyExists(message: String) : RuntimeException(message)
