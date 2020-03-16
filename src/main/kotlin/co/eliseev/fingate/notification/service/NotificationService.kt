package co.eliseev.fingate.notification.service

import co.eliseev.fingate.notification.model.entity.Notification
import co.eliseev.fingate.notification.repository.NotificationRepository
import co.eliseev.fingate.notification.service.exception.NotificationNotFoundException
import org.springframework.stereotype.Service

interface NotificationService {
    fun create(notification: Notification): Notification
    fun getAll(): List<Notification>
    fun getById(id: Long): Notification
    fun getByIds(ids: List<Long>): List<Notification>
}

@Service
class NotificationServiceImpl(private val notificationRepository: NotificationRepository) : NotificationService {

    override fun create(notification: Notification): Notification = notificationRepository.save(notification)

    override fun getAll(): List<Notification> = notificationRepository.findAll()

    override fun getByIds(ids: List<Long>): List<Notification> = notificationRepository.findAllById(ids)

    override fun getById(id: Long): Notification =
        notificationRepository.findById(id)
            .orElseThrow { NotificationNotFoundException("Notification with id $id not found") }

}
