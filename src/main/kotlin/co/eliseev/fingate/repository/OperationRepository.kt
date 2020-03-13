package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationType
import org.springframework.data.jpa.repository.JpaRepository

interface OperationRepository : JpaRepository<Operation, Long> {
    fun findAllByOperationType(operationType: OperationType): List<Operation>
}
