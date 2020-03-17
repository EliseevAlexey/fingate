package co.eliseev.fingate.notification.model.entity

import co.eliseev.fingate.core.model.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = Notification.TABLE_NAME)
class Notification(

    @Column(name = "name", unique = true)
    val name: String

) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "notifications"
    }
}
