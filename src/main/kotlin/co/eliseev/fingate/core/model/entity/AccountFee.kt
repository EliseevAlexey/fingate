package co.eliseev.fingate.core.model.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = AccountFee.TABLE_NAME)
data class AccountFee(

    @Column(name = "system")
    @Enumerated(EnumType.STRING)
    val system: CardSystem,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency,

    @Column(name = "value")
    val value: BigDecimal

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "account_fees"
    }

}
