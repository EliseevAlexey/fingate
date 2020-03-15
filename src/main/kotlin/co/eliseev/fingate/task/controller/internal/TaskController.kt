package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.task.service.FeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks/fees")
class TaskController(private val feeService: FeeService) {

    @GetMapping("/check-all-and-withdraw-fee")
    fun allAccountFeeWithdraws() = feeService.checkAllAccountsAndWithdrawFee()

}
