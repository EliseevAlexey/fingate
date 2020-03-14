package co.eliseev.fingate.core.controller.internal

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.dto.AccountDto
import co.eliseev.fingate.core.service.AccountService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/accounts")
class BalanceSetterController(private val accountService: AccountService) {

    @PutMapping("/{accountId}")
    fun setBalanceToAccount(
        @RequestParam("balance") balance: BigDecimal,
        @PathVariable accountId: Long
    ): AccountDto = accountService.setBalance(balance, accountId).toDto()

}
