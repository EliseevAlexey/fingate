package co.eliseev.fingate.notification.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.notification.model.EventType
import org.springframework.stereotype.Component

interface NotificationProcessor {
    fun process(user: User, eventType: EventType)
}

@Component
class NotificationProcessorImpl(
    private val userNotificationService: UserNotificationService
) : NotificationProcessor {

    override fun process(user: User, eventType: EventType) {
        val userNotification = userNotificationService.getBy(user)
        userNotification.enabledNotifications.forEach {
            // TODO send to different ways
        }
    }

}
