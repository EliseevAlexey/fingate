package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.PaymentCategory
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentCategoryRepository : JpaRepository<PaymentCategory, Long> {
    fun getByName(name: String): PaymentCategory
}
