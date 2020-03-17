package co.eliseev.fingate.core.model.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = BankAccountFee.TABLE_NAME)
class BankAccountFee(

    @Column(name = "card_system")
    @Enumerated(EnumType.STRING)
    val cardSystem: CardSystem,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency,

    @Column(name = "value")
    val value: BigDecimal

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "bank_account_fees"
    }

}
