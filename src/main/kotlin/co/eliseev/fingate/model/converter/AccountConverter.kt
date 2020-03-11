package co.eliseev.fingate.model.converter

import co.eliseev.fingate.model.AccountModel
import co.eliseev.fingate.model.dto.AccountDto
import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.User

fun AccountModel.toEntity(issuer: User) =
    Account(
        issuer = issuer,
        number = this.number,
        expirationDate = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system
    )

fun AccountDto.toModel() =
    AccountModel(
        number = this.number,
        expirationDate = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system
    )

fun Account.toDto() =
    AccountDto(
        id = this.id,
        number = this.number,
        expirationDate = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        balance = this.balance
    )

fun List<Account>.toDto() = this.map { it.toDto() }
