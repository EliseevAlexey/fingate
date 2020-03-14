package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.AccountFee
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.FeeFrequency
import org.springframework.data.jpa.repository.JpaRepository

interface AccountFeeRepository: JpaRepository<AccountFee, Long> {
    fun findBySystemAndFeeFrequency(system: CardSystem, feeFrequency: FeeFrequency): AccountFee?
}
