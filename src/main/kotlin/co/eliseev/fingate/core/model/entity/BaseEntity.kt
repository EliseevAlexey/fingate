package co.eliseev.fingate.core.model.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    override fun hashCode(): Int = 31

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is BaseEntity -> false
        else -> this.id == other.id
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

}
