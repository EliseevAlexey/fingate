package co.eliseev.fingate.notification.repository

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.notification.model.entity.UserNotification
import org.springframework.data.jpa.repository.JpaRepository

interface UserNotificationRepository: JpaRepository<UserNotification, Long>{
    fun getByUser(user: User): UserNotification?
}
