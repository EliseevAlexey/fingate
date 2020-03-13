package co.eliseev.fingate.controller.handler

import co.eliseev.fingate.service.exception.IllegalOperationStatusException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OperationProcessorExceptionHandler {

    @ExceptionHandler(IllegalOperationStatusException::class)
    fun handleIllegalOperationStatusException(ex: IllegalOperationStatusException): String? = ex.message

}
