package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
