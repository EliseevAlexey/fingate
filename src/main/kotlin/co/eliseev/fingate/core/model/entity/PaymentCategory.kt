package co.eliseev.fingate.core.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = PaymentCategory.TABLE_NAME)
class PaymentCategory(

    @Column(name = "name", unique = true)
    val name: String

) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "payment_categories"
    }
}
