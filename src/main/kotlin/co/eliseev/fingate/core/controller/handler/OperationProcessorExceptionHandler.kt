package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.IllegalOperationStatusException
import co.eliseev.fingate.core.service.exception.OperationNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OperationProcessorExceptionHandler {

    @ExceptionHandler(IllegalOperationStatusException::class)
    fun handleIllegalOperationStatusException(ex: IllegalOperationStatusException): String? = ex.message

    @ExceptionHandler(OperationNotFoundException::class)
    fun handleOperationNotFoundException(ex: OperationNotFoundException): String? = ex.message

}
