package co.eliseev.fingate.service.exception

import java.lang.RuntimeException

class IllegalOperationStatusException(message: String) : RuntimeException(message)

class OperationNotFoundException(message: String) : RuntimeException(message)
