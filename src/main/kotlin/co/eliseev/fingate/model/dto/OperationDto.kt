package co.eliseev.fingate.model.dto

import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import java.time.LocalDateTime

@Validated
data class OperationDto(
    val id: Long? = null,
    val withdrawServiceName: String,
    val paymentCategoryId: Long,
    val paymentAmount: BigDecimal,
    val paymentDateTime: LocalDateTime? = null,
    val accountId: Long,
    val operationType: OperationType,
    val operationStatus: OperationStatus? = null
)
