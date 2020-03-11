package co.eliseev.fingate.model.dto

import co.eliseev.fingate.configuration.CURRENCY_LENGTH
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Validated
data class AccountDto(
    val id: Long? = null,
    val number: Int,
    val expirationDate: LocalDateTime,
    @field:Positive val cvv: Int,
    val type: CardType,
    @field:Size(min = CURRENCY_LENGTH, max = CURRENCY_LENGTH) val currency: String,
    val system: CardSystem,
    val balance: BigDecimal? = null
)
