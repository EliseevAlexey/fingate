package co.eliseev.fingate.core.model.dto

import co.eliseev.fingate.core.configuration.CURRENCY_LENGTH
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Validated
data class BankAccountDto(
    val id: Long? = null,
    val name: String? = null,
    val number: Long? = null,
    val expirationDate: LocalDateTime? = null,
    @field:Positive val cvv: Int? = null,
    val type: CardType? = null,
    @field:Size(min = CURRENCY_LENGTH, max = CURRENCY_LENGTH) val currency: String? = null,
    val system: CardSystem? = null,
    val balance: BigDecimal? = null,
    val feeFrequency: FeeFrequency? = null,
    val registrationDate: LocalDate? = null,
    val bankAccountFeeDto: BankAccountFeeDto? = null,
    val lastFeeWithdrawDate: LocalDate? = null
)