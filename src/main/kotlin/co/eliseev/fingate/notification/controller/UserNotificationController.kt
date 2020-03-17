package co.eliseev.fingate.notification.controller

import co.eliseev.fingate.notification.model.converter.toDto
import co.eliseev.fingate.notification.model.converter.toModel
import co.eliseev.fingate.notification.model.dto.UserNotificationDto
import co.eliseev.fingate.notification.service.UserNotificationService
import co.eliseev.fingate.security.util.HasAdminOrUserRights
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@HasAdminOrUserRights
@RequestMapping("/notifications/users/current-user")
class UserNotificationController(private val userNotificationService: UserNotificationService) {

    @PostMapping
    fun create(@RequestBody userNotificationDto: UserNotificationDto): UserNotificationDto =
        userNotificationDto.toModel()
            .let { userNotificationService.create(it) }
            .toDto()

    @GetMapping
    fun getCurrentUserNotification(): UserNotificationDto = 
        userNotificationService.getCurrentUserNotification().toDto()

    @PutMapping("/add-notification")
    fun addNotificationToCurrentUser(@RequestParam(name = "notificationId") notificationId: Long): UserNotificationDto =
        userNotificationService.addNotificationToCurrentUser(notificationId)
            .toDto()

}
