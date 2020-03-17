package co.eliseev.fingate.audit.repository

import co.eliseev.fingate.audit.entity.AuditUserAction
import org.springframework.data.jpa.repository.JpaRepository

interface AuditUserActionRepository : JpaRepository<AuditUserAction, Long>
