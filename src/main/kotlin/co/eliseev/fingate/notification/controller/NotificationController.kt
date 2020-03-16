package co.eliseev.fingate.notification.controller

import co.eliseev.fingate.notification.model.converter.toDto
import co.eliseev.fingate.notification.model.converter.toEntity
import co.eliseev.fingate.notification.model.dto.NotificationDto
import co.eliseev.fingate.notification.service.NotificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @PostMapping
    fun create(@RequestBody notificationDto: NotificationDto) =
        notificationService.create(notificationDto.toEntity())

    @GetMapping("/all")
    fun getAll(): List<NotificationDto> = notificationService.getAll().toDto()

}
