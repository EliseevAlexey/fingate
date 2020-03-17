package co.eliseev.fingate.core.model.dto

import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class OperationDto(
    val id: Long? = null,
    val withdrawServiceName: String,
    val paymentCategoryId: Long,
    val paymentAmount: BigDecimal,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") val paymentDateTime: LocalDateTime? = null,
    val accountId: Long,
    val operationType: OperationType,
    val operationStatus: OperationStatus? = null
)
