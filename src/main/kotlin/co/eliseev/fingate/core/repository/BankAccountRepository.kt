package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository : JpaRepository<BankAccount, Long>{
    fun getAllByUser(issuer: User): List<BankAccount>
    fun countAllByUser(user: User): Long
    fun getByIsDefaultAccountTrue(): BankAccount // TODO ?
}
