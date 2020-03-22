package co.eliseev.fingate.security.model.entity

import co.eliseev.fingate.core.model.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role(

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    var name: UserRole? = null

) : BaseEntity()

