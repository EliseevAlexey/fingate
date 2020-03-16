package co.eliseev.fingate.notification.model.event

import co.eliseev.fingate.core.model.entity.User
import org.springframework.context.ApplicationEvent

data class WithdrawEvent(
    val user: User
) : ApplicationEvent(user)

data class FundEvent(
    val user: User
): ApplicationEvent(user)

data class RejectEvent(
    val user: User
): ApplicationEvent(user)
