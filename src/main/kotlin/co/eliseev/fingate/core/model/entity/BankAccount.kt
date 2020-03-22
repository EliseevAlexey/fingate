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
@Table(name = "bank_accounts")
class BankAccount(

    @ManyToOne
    val cardIssuer: User? = null, // FIXME Bank
    // TODO add holder

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "card_number")
    val cardNumber: Long? = null,

    @Column(name = "card_expiration_date_time")
    val cardExpirationDateTime: LocalDateTime? = null,

    @Column(name = "card_cvv_number")
    val cardCvvNumber: Int? = null,

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    val cardType: CardType? = null,

    @Column(name = "currency")
    val currency: String? = null,

    @Column(name = "balance")
    var balance: BigDecimal = 0.toBigDecimal(),

    @Column(name = "card_system")
    @Enumerated(EnumType.STRING)
    val cardSystem: CardSystem? = null,

    @Column(name = "is_default_account", nullable = false)
    val isDefaultAccount: Boolean = false,

    @Column(name = "registration_date")
    val registrationDate: LocalDate? = null,

    @Column(name = "fee_frequency")
    @Enumerated(EnumType.STRING)
    val feeFrequency: FeeFrequency? = null,

    @ManyToOne
    var bankAccountFee: BankAccountFee? = null,

    @ManyToOne
    var user: User? = null,

    @Column(name = "last_fee_withdraw_date")
    var lastFeeWithdrawDate: LocalDate? = null

) : BaseEntity()
