package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.BankAccountFee
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.FeeFrequency
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountFeeRepository: JpaRepository<BankAccountFee, Long> {
    fun findByCardSystemAndFeeFrequency(system: CardSystem?, feeFrequency: FeeFrequency?): BankAccountFee?
}
