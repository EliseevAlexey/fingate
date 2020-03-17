package co.eliseev.fingate.core.model.entity

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
@Table(name = BankAccount.TABLE_NAME)
class BankAccount(

    @ManyToOne
    val issuer: User? = null, // FIXME Bank
    // TODO add holder

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "number")
    val number: Long? = null,

    @Column(name = "expiration_date_time")
    val expirationDateTime: LocalDateTime? = null,

    @Column(name = "cvv")
    val cvv: Int? = null,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: CardType? = null,

    @Column(name = "currency")
    val currency: String? = null,

    @Column(name = "balance")
    var balance: BigDecimal = 0.toBigDecimal(),

    @Column(name = "system")
    @Enumerated(EnumType.STRING)
    val system: CardSystem? = null,

    @Column(name = "default", nullable = false)
    val default: Boolean = false,

    @Column(name = "registration_date")
    val registrationDate: LocalDate? = null,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency? = null,

    @ManyToOne
    var bankAccountFee: BankAccountFee? = null,

    @ManyToOne
    var user: User? = null,

    @Column(name = "last_fee_withdraw")
    var lastFeeWithdrawDate: LocalDate? = null

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "bank_accounts"
    }

}
