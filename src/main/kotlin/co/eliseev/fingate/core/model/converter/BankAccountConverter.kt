package co.eliseev.fingate.core.model.converter

import co.eliseev.fingate.core.model.BankAccountModel
import co.eliseev.fingate.core.model.dto.BankAccountDto
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import java.time.LocalDate

fun BankAccountModel.toEntity(issuer: User, registrationDate: LocalDate) =
    BankAccount(
        name = this.name,
        issuer = issuer,
        number = this.number,
        expirationDateTime = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        feeFrequency = this.feeFrequency,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate,
        registrationDate = registrationDate
    )

fun BankAccountDto.toModel() =
    BankAccountModel(
        name = this.name,
        number = this.number,
        expirationDate = this.expirationDate,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        feeFrequency = this.feeFrequency,
        registrationDate = this.registrationDate,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate
    )

fun BankAccount.toDto() =
    BankAccountDto(
        id = this.id,
        name = this.name,
        number = this.number,
        expirationDate = this.expirationDateTime,
        cvv = this.cvv,
        type = this.type,
        currency = this.currency,
        system = this.system,
        balance = this.balance,
        feeFrequency = this.feeFrequency,
        bankAccountFeeDto = this.bankAccountFee?.toDto(),
        registrationDate = this.registrationDate,
        lastFeeWithdrawDate = this.lastFeeWithdrawDate
    )

fun List<BankAccount>.toDto() = this.map { it.toDto() }
