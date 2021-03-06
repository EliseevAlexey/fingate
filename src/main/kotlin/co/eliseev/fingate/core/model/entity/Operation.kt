package co.eliseev.fingate.core.model.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "operations")
class Operation(

    @Column(name = "withdraw_service_name")
    val withdrawServiceName: String,

    @ManyToOne
    val paymentCategory: PaymentCategory,

    @Column(name = "payment_amount")
    val paymentAmount: BigDecimal,

    @Column(name = "payment_date_time")
    val paymentDateTime: LocalDateTime,

    @ManyToOne
    val bankAccount: BankAccount,

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    val operationType: OperationType,

    @Column(name = "operation_status")
    @Enumerated(EnumType.STRING)
    var operationStatus: OperationStatus,

    @ManyToOne
    var user: User? = null

) : BaseEntity()
