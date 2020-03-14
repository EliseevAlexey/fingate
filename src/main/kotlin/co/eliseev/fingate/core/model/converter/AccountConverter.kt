package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.AccountModel
import co.eliseev.fingate.core.model.dto.AccountDto
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import java.time.LocalDate

fun AccountModel.toEntity(issuer: User, registrationDate: LocalDate) =
    BankAccount(
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

fun BankAccount.toDto() =
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

fun List<BankAccount>.toDto() = this.map { it.toDto() }
