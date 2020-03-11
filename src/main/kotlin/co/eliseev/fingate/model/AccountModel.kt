package co.eliseev.fingate.model

import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountModel(
    val number: Int,
    val expirationDate: LocalDateTime,
    val cvv: Int,
    val type: CardType,
    val currency: String,
    val system: CardSystem,
    val balance: BigDecimal? = null
)
