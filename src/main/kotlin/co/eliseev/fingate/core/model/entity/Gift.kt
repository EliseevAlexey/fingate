package co.eliseev.fingate.core.model.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = Gift.TABLE_NAME)
data class Gift(

    @Column(name = "gift_type", unique = true)
    @Enumerated(EnumType.STRING)
    val giftType: GiftType,

    @Column(name = "value")
    val value: BigDecimal

): BaseEntity() {
    companion object {
        const val TABLE_NAME = "gifts"
    }
}
