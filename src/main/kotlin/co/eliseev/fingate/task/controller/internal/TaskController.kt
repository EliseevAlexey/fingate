package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.task.service.FeeService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks/fees")
class TaskController(private val feeService: FeeService) {

    @PutMapping("/check-all-and-withdraw-fee")
    fun allAccountFeeWithdraws() = feeService.checkAllAccountsAndWithdrawFee()

}
