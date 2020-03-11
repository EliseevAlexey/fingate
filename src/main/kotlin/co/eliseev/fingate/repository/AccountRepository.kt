package co.eliseev.fingate.repository

import co.eliseev.fingate.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>
