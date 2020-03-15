package co.eliseev.fingate.core.model

import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class BankAccountModel(
    val name: String? = null,
    val number: Long? = null,
    val expirationDate: LocalDateTime? = null,
    val cvv: Int? = null,
    val type: CardType? = null,
    val currency: String? = null,
    val system: CardSystem? = null,
    val balance: BigDecimal? = null,
    val feeFrequency: FeeFrequency? = null,
    val registrationDate: LocalDate? = null,
    val lastFeeWithdrawDate: LocalDate? = null
)
