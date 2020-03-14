package co.eliseev.fingate.core.model

import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class AccountModel(
    val number: Long,
    val expirationDate: LocalDateTime,
    val cvv: Int,
    val type: CardType,
    val currency: String,
    val system: CardSystem,
    val balance: BigDecimal? = null,
    val feeFrequency: FeeFrequency,
    val registrationDate: LocalDate? = null
)
