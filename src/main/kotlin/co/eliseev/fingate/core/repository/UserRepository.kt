package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface UserRepository : JpaRepository<User, Long>{
    fun getAllByBirthday(date: LocalDate): List<User>
}
