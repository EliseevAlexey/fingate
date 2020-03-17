package co.eliseev.fingate.notification.model

data class UserNotificationModel(
    val userId: Long?,
    val notificationIds: List<Long>
)
