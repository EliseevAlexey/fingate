package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>{
    fun getAllByIssuer(issuer: User): List<Account>
}
