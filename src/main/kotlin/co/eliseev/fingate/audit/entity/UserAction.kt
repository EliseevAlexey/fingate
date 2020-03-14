package co.eliseev.fingate.audit.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = UserAction.TABLE_NAME)
data class UserAction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "class_name")
    val className: String,
    @Column(name = "function_name")
    val functionName: String,

    @Column(name = "args")
    val args: String,

    @Column(name = "saved_at")
    val savedAt: LocalDateTime

) {
    companion object {
        const val TABLE_NAME = "audit_user_actions"
    }
}
