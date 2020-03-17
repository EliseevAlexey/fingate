package co.eliseev.fingate.notification.repository

import co.eliseev.fingate.notification.model.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long>
