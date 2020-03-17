package co.eliseev.fingate.notification.controller

import co.eliseev.fingate.notification.model.converter.toDto
import co.eliseev.fingate.notification.model.converter.toModel
import co.eliseev.fingate.notification.model.dto.NotificationDto
import co.eliseev.fingate.notification.service.NotificationService
import co.eliseev.fingate.security.util.HasAdminOrUserRights
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@HasAdminOrUserRights
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @PostMapping
    fun create(@RequestBody notificationDto: NotificationDto) =
        notificationDto.toModel()
            .let { notificationService.create(it) }
            .toDto()

    @GetMapping
    fun getAll(): List<NotificationDto> = notificationService.getAll().toDto()

}
