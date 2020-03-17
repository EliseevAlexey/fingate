package co.eliseev.fingate.audit.service

import co.eliseev.fingate.audit.entity.AuditUserAction
import co.eliseev.fingate.audit.repository.AuditUserActionRepository
import org.springframework.stereotype.Service

interface AuditUserActionService {
    fun save(auditUserAction: AuditUserAction): AuditUserAction
}

@Service
class AuditUserActionServiceImpl(
    private val auditUserActionRepository: AuditUserActionRepository
) : AuditUserActionService {

    override fun save(auditUserAction: AuditUserAction): AuditUserAction = auditUserActionRepository.save(auditUserAction)

}
