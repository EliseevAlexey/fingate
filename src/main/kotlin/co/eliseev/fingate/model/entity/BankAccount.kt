package co.eliseev.fingate.model.entity

import co.eliseev.fingate.model.entity.BankAccount.Companion.TABLE_NAME
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = TABLE_NAME)
data class BankAccount(

    @ManyToOne
    val issuer: User? = null, // FIXME Bank
    // TODO add holder

    @Column(name = "number")
    val number: Long,

    @Column(name = "expiration_date_time")
    val expirationDateTime: LocalDateTime,

    @Column(name = "cvv")
    val cvv: Int,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: CardType,

    @Column(name = "currency")
    val currency: String,

    @Column(name = "balance")
    var balance: BigDecimal = 0.toBigDecimal(),

    @Column(name = "system")
    @Enumerated(EnumType.STRING)
    val system: CardSystem,

    @Column(name = "default")
    val default: Boolean = false,

    @Column(name = "registration_date")
    val registrationDate: LocalDate,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency,

    @ManyToOne
    var accountFee: AccountFee? = null,

    @ManyToOne
    var user: User? = null

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "bank_accounts"
    }

}
