package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
