package co.eliseev.fingate.audit.configuration.aop

import co.eliseev.fingate.audit.entity.AuditUserAction
import co.eliseev.fingate.audit.service.AuditUserActionService
import co.eliseev.fingate.security.service.SecurityService
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Aspect
@Component
class AuditAspect(
    private val auditUserActionService: AuditUserActionService,
    private val securityService: SecurityService,
    private val clock: Clock,
    private val objectMapper: ObjectMapper
) {

    @Before(value = "within(co.eliseev.fingate.*.controller..*)")
    fun audit(joinPoint: JoinPoint) {
        AuditUserAction(
            userId = securityService.getCurrentUser().id!!,
            className = joinPoint.target.javaClass.canonicalName,
            functionName = joinPoint.signature.name,
            args = objectMapper.writeValueAsString(joinPoint.args),
            savedAt = LocalDateTime.now(clock)
        ).also { auditUserActionService.save(it) }
    }

}
