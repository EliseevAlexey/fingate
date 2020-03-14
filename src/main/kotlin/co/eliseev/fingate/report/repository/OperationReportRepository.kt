package co.eliseev.fingate.report.repository

import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface OperationReportRepository : JpaRepository<Operation, Long> {
    fun getAllByPaymentDateTimeGreaterThanEqualAndUser(paymentDateTime: LocalDateTime, user: User): List<Operation>

    @Query(
        """
        SELECT *
        FROM operations o1
        WHERE o1.operation_status = 'REJECTED'
          AND 'PROCESSED' =
              (SELECT o2.operation_status
               FROM operations o2
               WHERE 
                 o2.payment_date_time < o1.payment_date_time
                 AND o2.bank_account_id != o1.bank_account_id
               ORDER BY o2.payment_date_time DESC
               LIMIT 1)
        """,
        nativeQuery = true
    )
    fun getRejectedOperations(): List<Operation>
}
