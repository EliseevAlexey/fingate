package co.eliseev.fingate.entity

import co.eliseev.fingate.entity.Account.Companion.TABLE_NAME
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = TABLE_NAME)
class Account(

    @ManyToOne
    private val issuer: User? = null,

    @Column(name = "number")
    private val number: String,

    @Column(name = "expiration_date")
    private val expirationDate: LocalDateTime,

    @Column(name = "cvv")
    private val cvv: Int,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private val type: CardType,

    @Column(name = "currency")
    private val currency: String,

    @Column(name = "balance")
    private val balance: BigDecimal,

    @Column(name = "system")
    @Enumerated(EnumType.STRING)
    private val system: CardSystem,

    @Column(name = "default")
    private val default: Boolean = false

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "accounts"
    }

}
