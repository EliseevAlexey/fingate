package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.service.exception.GiftNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GiftExceptionHandler {

    @ExceptionHandler(GiftNotFoundException::class)
    fun handleGiftNotFoundException(ex: GiftNotFoundException) = ex.message

}
