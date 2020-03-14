package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.AccountDto
import co.eliseev.fingate.core.service.AccountService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody @Valid accountDto: AccountDto): AccountDto =
        accountDto.toModel()
            .let { accountService.create(it) }
            .toDto()

    @DeleteMapping("/{accountId}")
    fun delete(@PathVariable accountId: Long): Boolean = accountService.delete(accountId)

    @GetMapping
    fun getAll(): List<AccountDto> = accountService.getAll().toDto()

}
