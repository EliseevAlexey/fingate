package co.eliseev.fingate.model.converter

import co.eliseev.fingate.model.AccountModel
import co.eliseev.fingate.model.dto.AccountDto
import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.User
import java.time.LocalDate

fun AccountModel.toEntity(issuer: User, registrationDate: LocalDate) =
    Account(
        issuer = issuer,
        number = this.number,
        expirationDateTime = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        feeFrequency = this.feeFrequency,
        registrationDate = this.registrationDate ?: registrationDate
    )

fun AccountDto.toModel() =
    AccountModel(
        number = this.number,
        expirationDate = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        feeFrequency = this.feeFrequency,
        registrationDate = this.registrationDate
    )

fun Account.toDto() =
    AccountDto(
        id = this.id,
        number = this.number,
        expirationDate = this.expirationDateTime,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        balance = this.balance,
        feeFrequency = this.feeFrequency,
        registrationDate = this.registrationDate,
        accountFeeDto = this.accountFee?.toDto()
    )

fun List<Account>.toDto() = this.map { it.toDto() }
