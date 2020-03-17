package co.eliseev.fingate.notification.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.security.service.SecurityService
import co.eliseev.fingate.notification.model.UserNotificationModel
import co.eliseev.fingate.notification.model.entity.UserNotification
import co.eliseev.fingate.notification.repository.UserNotificationRepository
import co.eliseev.fingate.notification.service.exception.UserNotificationNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserNotificationService {
    fun create(userNotificationModel: UserNotificationModel): UserNotification
    fun getBy(user: User): UserNotification
    fun getCurrentUserNotification(): UserNotification
    fun addNotificationToCurrentUser(notificationId: Long): UserNotification
    fun deleteNotificationToCurrentUser(notificationId: Long): UserNotification
}

@Service
class UserNotificationServiceImpl(
    private val userNotificationRepository: UserNotificationRepository,
    private val notificationService: NotificationService,
    private val securityService: SecurityService
) : UserNotificationService {

    override fun create(userNotificationModel: UserNotificationModel): UserNotification {
        val userNotification = UserNotification(
            user = getCurrentUser(),
            enabledNotifications = notificationService.getByIds(userNotificationModel.notificationIds).toMutableSet()
        )
        return userNotificationRepository.save(userNotification)
    }

    private fun getCurrentUser() = securityService.getCurrentUser()

    override fun getCurrentUserNotification(): UserNotification = getByUser(getCurrentUser())

    override fun getBy(user: User): UserNotification = getByUser(user)

    @Transactional
    override fun addNotificationToCurrentUser(notificationId: Long): UserNotification {
        val userNotification = getByUser(getCurrentUser())
        userNotification.enabledNotifications.add(notificationService.getById(notificationId))
        return userNotification
    }

    override fun deleteNotificationToCurrentUser(notificationId: Long): UserNotification {
        val userNotification = getByUser(getCurrentUser())
        userNotification.enabledNotifications.removeIf { it.id == notificationId } // FIXME concurrency
        return userNotification
    }

    private fun getByUser(user: User): UserNotification {
        return userNotificationRepository.getByUser(user)
            ?: throw UserNotificationNotFoundException("User notification by user ${user.id} user not found")
    }

}
