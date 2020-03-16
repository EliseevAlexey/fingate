package co.eliseev.fingate.notification.model.converter

import co.eliseev.fingate.notification.model.dto.NotificationDto
import co.eliseev.fingate.notification.model.entity.Notification

fun NotificationDto.toEntity() = Notification(name = this.name)

fun Notification.toDto() = NotificationDto(
    id = this.id,
    name = this.name
)

fun List<Notification>.toDto() = this.map { it.toDto() }
