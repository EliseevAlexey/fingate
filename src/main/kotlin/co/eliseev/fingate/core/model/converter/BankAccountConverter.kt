package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.BankAccountModel
import co.eliseev.fingate.core.model.dto.BankAccountDto
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import java.time.LocalDate

fun BankAccountModel.toEntity(
    cardIssuer: User, // FIXME another entity
    user: User,
    registrationDate: LocalDate
) = BankAccount(
        name = this.name,
        cardIssuer = cardIssuer,
        cardNumber = this.cardNumber,
        cardExpirationDateTime = this.cardExpirationDateTime,
        cardCvvNumber = this.cardCvvNumber,
        cardType = this.cardType,
        currency = this.currency,
        cardSystem = this.cardSystem,
        feeFrequency = this.feeFrequency,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate,
        registrationDate = registrationDate,
        user = user
    )

fun BankAccountDto.toModel() =
    BankAccountModel(
        name = this.name,
        cardNumber = this.cardNumber,
        cardExpirationDateTime = this.expirationDateTime,
        cardCvvNumber = this.cardCvvNumber,
        cardType = this.cardType,
        currency = this.currency,
        cardSystem = this.cardSystem,
        feeFrequency = this.feeFrequency,
        registrationDate = this.registrationDate,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate
    )

fun BankAccount.toDto() =
    BankAccountDto(
        id = this.id,
        name = this.name,
        cardNumber = this.cardNumber,
        expirationDateTime = this.cardExpirationDateTime,
        cardCvvNumber = this.cardCvvNumber,
        cardType = this.cardType,
        currency = this.currency,
        cardSystem = this.cardSystem,
        balance = this.balance,
        feeFrequency = this.feeFrequency,
        bankAccountFeeDto = this.bankAccountFee?.toDto(),
        registrationDate = this.registrationDate,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate
    )

fun List<BankAccount>.toDto() = this.map { it.toDto() }
