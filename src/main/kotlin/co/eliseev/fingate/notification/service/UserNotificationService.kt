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
    fun deleteCurrentUserNotification(notificationId: Long): UserNotification
}

@Service
class UserNotificationServiceImpl(
    private val userNotificationRepository: UserNotificationRepository,
    private val notificationService: NotificationService,
    private val securityService: SecurityService
) : UserNotificationService {

    override fun create(userNotificationModel: UserNotificationModel): UserNotification =
        createCurrentUserNotifications(userNotificationModel)
            .let { userNotificationRepository.save(it) }

    private fun createCurrentUserNotifications(userNotificationModel: UserNotificationModel): UserNotification {
        return UserNotification(
            user = getCurrentUser(),
            enabledNotifications = notificationService.getByIds(userNotificationModel.notificationIds).toMutableSet()
        )
    }

    private fun getCurrentUser(): User = securityService.getCurrentUser()

    override fun getCurrentUserNotification(): UserNotification = getByUser(getCurrentUser())

    override fun getBy(user: User): UserNotification = getByUser(user)

    @Transactional
    override fun addNotificationToCurrentUser(notificationId: Long): UserNotification {
        val notification = notificationService.getById(notificationId)
        return getCurrentUserNotification().also {
            it.enabledNotifications.add(notification)
        }
    }

    @Transactional
    override fun deleteCurrentUserNotification(notificationId: Long): UserNotification =
        getCurrentUserNotification().also { userNotification ->
            userNotification.enabledNotifications.removeIf { it.id == notificationId }
        }

    private fun getByUser(user: User): UserNotification =
        userNotificationRepository.getByUser(user)
            ?: throw UserNotificationNotFoundException("user_notifications.not_found", user.id)

}
