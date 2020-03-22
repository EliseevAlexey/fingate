package co.eliseev.fingate.core.model.dto

import co.eliseev.fingate.core.configuration.CURRENCY_LENGTH
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import com.fasterxml.jackson.annotation.JsonFormat
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
    @field:Size(min = 16, max = 16) val cardNumber: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") val expirationDateTime: LocalDateTime? = null,
    @field:Positive val cardCvvNumber: Int? = null,
    val cardType: CardType? = null,
    @field:Size(min = CURRENCY_LENGTH, max = CURRENCY_LENGTH) val currency: String? = null,
    val cardSystem: CardSystem? = null,
    val balance: BigDecimal? = null,
    val feeFrequency: FeeFrequency? = null,
    @JsonFormat(pattern = "yyyy-MM-dd") val registrationDate: LocalDate? = null,
    val bankAccountFeeDto: BankAccountFeeDto? = null,
    val lastFeeWithdrawDate: LocalDate? = null
)
