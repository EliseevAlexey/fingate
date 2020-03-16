package co.eliseev.fingate.security.repository

import co.eliseev.fingate.security.model.entity.Role
import co.eliseev.fingate.security.model.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>{
    fun findByName(name: UserRole): Role?
}
