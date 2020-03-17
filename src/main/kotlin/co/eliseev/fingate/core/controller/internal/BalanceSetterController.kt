package co.eliseev.fingate.core.controller.internal

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.dto.BankAccountDto
import co.eliseev.fingate.core.service.BankAccountService
import co.eliseev.fingate.security.util.HasAdminRights
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@HasAdminRights
@RequestMapping("/bank-accounts")
class BalanceSetterController(private val bankAccountService: BankAccountService) {

    @PutMapping("/{bankAccountId}")
    fun setBalanceToAccount(
        @RequestParam("balance") balance: BigDecimal,
        @PathVariable bankAccountId: Long
    ): BankAccountDto = bankAccountService.setBalance(balance, bankAccountId).toDto()

}
