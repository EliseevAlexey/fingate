package co.eliseev.fingate.service.exception

class PaymentCategoryNotFoundException(message: String) : RuntimeException(message)

class PaymentCategoryAlreadyExists(message: String) : RuntimeException(message)
