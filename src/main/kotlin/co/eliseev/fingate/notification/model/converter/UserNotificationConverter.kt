package co.eliseev.fingate.notification.model.converter

import co.eliseev.fingate.notification.model.UserNotificationModel
import co.eliseev.fingate.notification.model.dto.UserNotificationDto
import co.eliseev.fingate.notification.model.entity.UserNotification

fun UserNotificationDto.toModel() = UserNotificationModel(
    userId = this.userId,
    notificationIds = this.notificationIds
)

fun UserNotification.toDto() = UserNotificationDto(
    userId = this.user.id!!,
    notificationIds = this.enabledNotifications.map { it.id!! }
)
