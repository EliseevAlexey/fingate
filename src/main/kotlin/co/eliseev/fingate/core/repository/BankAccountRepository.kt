package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository : JpaRepository<BankAccount, Long>{
    fun getAllByIssuer(issuer: User): List<BankAccount>
    fun countAllByIssuer(issuer: User): Long
    fun getByDefaultTrue(): BankAccount
}
