package co.eliseev.fingate.core.model

import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class BankAccountModel(
    val name: String? = null,
    val cardNumber: Long? = null,
    val cardIssuer: String? = null,
    val cardExpirationDateTime: LocalDateTime? = null,
    val cardCvvNumber: Int? = null,
    val cardType: CardType? = null,
    val currency: String? = null,
    val cardSystem: CardSystem? = null,
    val balance: BigDecimal? = null,
    val feeFrequency: FeeFrequency? = null,
    val registrationDate: LocalDate? = null,
    val lastFeeWithdrawDate: LocalDate? = null
)
