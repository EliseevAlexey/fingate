package co.eliseev.fingate.notification.model.entity

import co.eliseev.fingate.core.model.entity.BaseEntity
import co.eliseev.fingate.core.model.entity.User
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "user_notifications")
class UserNotification(

    @OneToOne
    val user: User,

    @ManyToMany
    val enabledNotifications: MutableSet<Notification> = mutableSetOf()

) : BaseEntity()
