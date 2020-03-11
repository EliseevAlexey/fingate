package co.eliseev.fingate.model.entity

import co.eliseev.fingate.model.entity.Account.Companion.TABLE_NAME
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
    val issuer: User? = null,

    @Column(name = "number")
    val number: Int,

    @Column(name = "expiration_date")
    val expirationDate: LocalDateTime,

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
    val default: Boolean = false

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "accounts"
    }

}
