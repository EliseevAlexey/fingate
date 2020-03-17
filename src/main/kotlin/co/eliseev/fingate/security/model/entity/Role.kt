package co.eliseev.fingate.security.model.entity

import co.eliseev.fingate.core.model.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = Role.TABLE_NAME)
class Role(

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    var name: UserRole? = null

) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "roles"
    }
}
