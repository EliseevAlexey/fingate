package co.eliseev.fingate.audit.entity

import co.eliseev.fingate.core.model.entity.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "audit_user_actions")
class AuditUserAction(

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "class_name")
    val className: String,

    @Column(name = "function_name")
    val functionName: String,

    @Column(name = "args", columnDefinition = "nvarchar(max)")
    val args: String,

    @Column(name = "saved_at")
    val savedAt: LocalDateTime

) : BaseEntity()
