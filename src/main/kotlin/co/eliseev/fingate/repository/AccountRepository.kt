package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<BankAccount, Long>{
    fun getAllByIssuer(issuer: User): List<BankAccount>
    fun countAllByIssuer(issuer: User): Long
}
