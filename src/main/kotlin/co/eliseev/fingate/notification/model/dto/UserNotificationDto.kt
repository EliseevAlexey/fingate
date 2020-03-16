package co.eliseev.fingate.notification.model.dto

data class UserNotificationDto (
    val userId: Long? = null,
    val notificationIds: List<Long>
)
