package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.BankAccountDto
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.security.util.HasAdminOrUserRights
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@HasAdminOrUserRights
@RequestMapping("/bank-accounts")
class BankAccountController(private val bankAccountService: BankAccountService) {

    @PostMapping
    fun createAccount(@RequestBody @Valid bankAccountDto: BankAccountDto): BankAccountDto =
        bankAccountDto.toModel()
            .let { bankAccountService.create(it) }
            .toDto()

    @DeleteMapping("/{bankAccountId}")
    fun delete(@PathVariable bankAccountId: Long): Boolean = bankAccountService.delete(bankAccountId)

    @GetMapping
    fun getAll(): List<BankAccountDto> = bankAccountService.getAll().toDto()

}
