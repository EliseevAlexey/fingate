package co.eliseev.fingate.core.service.exception

import java.lang.RuntimeException

class IllegalOperationStatusException(message: String) : RuntimeException(message)

class OperationNotFoundException(message: String) : RuntimeException(message)
