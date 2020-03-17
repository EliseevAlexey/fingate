package co.eliseev.fingate.core.controller.internal

import co.eliseev.fingate.core.model.entity.Gift
import co.eliseev.fingate.core.model.entity.GiftType
import co.eliseev.fingate.core.service.GiftService
import co.eliseev.fingate.security.util.HasAdminRights
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@HasAdminRights
@RequestMapping("/gifts")
class GiftController(private val giftService: GiftService) {

    @GetMapping
    fun getByType(@RequestParam("giftType") giftType: GiftType): Gift = giftService.getByType(giftType)

}
