package co.eliseev.fingate.core.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "payment_categories")
class PaymentCategory(

    @Column(name = "name", unique = true)
    val name: String

) : BaseEntity()
