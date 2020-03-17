package co.eliseev.fingate.security.repository

import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface SecurityUserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}
