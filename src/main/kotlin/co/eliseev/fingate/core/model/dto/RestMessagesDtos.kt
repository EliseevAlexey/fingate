package co.eliseev.fingate.core.model.dto

data class RestMessagesDto(
    val error: MessageDto
)

data class MessageDto(
    val message: String
)
