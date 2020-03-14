package co.eliseev.fingate.core.model

import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import java.math.BigDecimal
import java.time.LocalDateTime

data class OperationModel(
    val id: Long? = null,
    val withdrawServiceName: String,
    val paymentCategoryId: Long,
    val paymentAmount: BigDecimal,
    val paymentDateTime: LocalDateTime? = null,
    val accountId: Long,
    val operationType: OperationType,
    val operationStatus: OperationStatus? = null
)
