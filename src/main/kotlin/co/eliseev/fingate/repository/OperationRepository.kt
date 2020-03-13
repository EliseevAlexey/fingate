package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OperationRepository : JpaRepository<Operation, Long> {
    fun findAllByOperationType(operationType: OperationType): List<Operation>

    /* TODO
        SELECT
            a.type
        FROM operations o
            LEFT JOIN bank_accounts a ON a.id = o.bank_account_id
        WHERE
            o.payment_date_time BETWEEN '2010-01-01' AND '2020-05-01'
            AND a.type IN ('DEBIT', 'CREDIT')
            AND a.issuer_id = 1
            AND o.payment_category_id = 1
            AND o.payment_amount BETWEEN 1 AND 150
        GROUP BY a.type
     */
    @Query(
        """
        SELECT o
        FROM co.eliseev.fingate.model.entity.Operation o
        """
    )
    fun findHistoryData(): List<Operation>
}
