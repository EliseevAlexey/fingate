package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.PaymentCategory
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentCategoryRepository : JpaRepository<PaymentCategory, Long>
