package co.eliseev.fingate.repository.report

import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface OperationReportRepository : JpaRepository<Operation, Long> {
    fun getAllByPaymentDateTimeGreaterThanEqualAndUser(paymentDateTime: LocalDateTime, user: User): List<Operation>
    fun getAllByPaymentDateTimeGreaterThanEqual(paymentDateTime: LocalDateTime): List<Operation>
}
