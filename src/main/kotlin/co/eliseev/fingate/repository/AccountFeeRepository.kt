package co.eliseev.fingate.repository

import co.eliseev.fingate.model.entity.AccountFee
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.FeeFrequency
import org.springframework.data.jpa.repository.JpaRepository

interface AccountFeeRepository: JpaRepository<AccountFee, Long> {
    fun findBySystemAndFeeFrequency(system: CardSystem, feeFrequency: FeeFrequency): AccountFee?
}
