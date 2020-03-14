package co.eliseev.fingate.report.repository

import co.eliseev.fingate.core.model.entity.Operation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface AdminOperationReportRepository : JpaRepository<Operation, Long> {
    @Query(
        """
            SELECT o
            FROM co.eliseev.fingate.core.model.entity.Operation o
            WHERE o.paymentDateTime >= :date
        """
    )
    fun findAllYTD(date: LocalDateTime): List<Operation>
}
