package co.eliseev.fingate.core.model.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "bank_account_fees")
class BankAccountFee(

    @Column(name = "card_system")
    @Enumerated(EnumType.STRING)
    val cardSystem: CardSystem,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency,

    @Column(name = "value")
    val value: BigDecimal

) : BaseEntity()
