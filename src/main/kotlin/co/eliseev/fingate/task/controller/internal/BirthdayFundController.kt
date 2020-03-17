package co.eliseev.fingate.task.controller.internal

import co.eliseev.fingate.security.util.HasAdminRights
import co.eliseev.fingate.task.service.BirthdayFundService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@HasAdminRights
@RequestMapping("/tasks/gifts")
class BirthdayFundController(private val birthdayFundService: BirthdayFundService) {

    @PutMapping("/check-birthday-and-fund")
    fun checkBirthdayAndFund() = birthdayFundService.checkBirthdayAndFund()

}
