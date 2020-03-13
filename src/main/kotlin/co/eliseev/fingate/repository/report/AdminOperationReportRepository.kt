package co.eliseev.fingate.repository.report

import co.eliseev.fingate.model.entity.Operation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface AdminOperationReportRepository : JpaRepository<Operation, Long> {
    @Query(
        """
            SELECT o
            FROM co.eliseev.fingate.model.entity.Operation o
            WHERE o.paymentDateTime >= :date
        """
    )
    fun findAllYTD(date: LocalDateTime): List<Operation>
}
