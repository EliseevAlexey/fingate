package co.eliseev.fingate.audit.configuration.aop

import co.eliseev.fingate.audit.entity.UserAction
import co.eliseev.fingate.audit.service.UserActionService
import co.eliseev.fingate.core.service.SecurityService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Aspect
@Component
class LoggingAspect(
    private val userActionService: UserActionService,
    private val securityService: SecurityService,
    private val clock: Clock
) {

    @Before(value = "within(co.eliseev.fingate.*.controller..*)")
    fun audit(joinPoint: JoinPoint) {
        UserAction(
            userId = securityService.getCurrentUser().id!!,
            className = joinPoint.target.javaClass.canonicalName,
            functionName = joinPoint.signature.name,
            args = joinPoint.args.toList().toString(),
            savedAt = LocalDateTime.now(clock)
        ).also { userActionService.save(it) }
    }
}
