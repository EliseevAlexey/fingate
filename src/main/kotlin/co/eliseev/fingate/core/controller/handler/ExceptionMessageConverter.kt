package co.eliseev.fingate.core.controller.handler

import co.eliseev.fingate.core.model.dto.MessageDto
import co.eliseev.fingate.core.model.dto.RestResponseMessagesDto
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

interface ExceptionMessageConverter {
    fun createErrorMessage(
        messageCode: String,
        locale: Locale = Locale.ROOT,
        param: Any?,
        params: Array<Any>?
    ): RestResponseMessagesDto
}

@Component
class ExceptionMessageConverterImpl(
    private val messageSource: MessageSource
) : ExceptionMessageConverter {

    override fun createErrorMessage(
        messageCode: String,
        locale: Locale,
        param: Any?,
        params: Array<Any>?
    ): RestResponseMessagesDto {
        val allParams = param?.let { arrayOf(param) } ?: params
        val message = messageSource.getMessage(messageCode, allParams, locale)
        return RestResponseMessagesDto(error = MessageDto(message))
    }

}
