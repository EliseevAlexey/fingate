package co.eliseev.fingate.model.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = PaymentCategory.TABLE_NAME)
data class PaymentCategory(
    @Column(name = "name", unique = true)
    val name: String
) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "payment_categories"
    }
}
