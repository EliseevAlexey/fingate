package co.eliseev.fingate.audit.repository

import co.eliseev.fingate.audit.entity.UserAction
import org.springframework.data.jpa.repository.JpaRepository

interface UserActionRepository : JpaRepository<UserAction, Long>
