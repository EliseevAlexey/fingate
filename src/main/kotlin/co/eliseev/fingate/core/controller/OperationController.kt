package co.eliseev.fingate.core.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.converter.toModel
import co.eliseev.fingate.core.model.dto.OperationDto
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.service.OperationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/operations")
class OperationController(private val operationService: OperationService) {

    @PostMapping
    fun create(@Valid @RequestBody operationDto: OperationDto): OperationDto =
        operationDto.toModel().let {
            operationService.create(it)
        }.toDto()

    @GetMapping
    fun getAllByOperationType(@RequestParam("operationType") operationType: OperationType): List<OperationDto> =
        operationService.getAllByOperationType(operationType).toDto()

    @GetMapping("/history")
    fun getHistoryData(): List<OperationDto> = operationService.getHistoryData().toDto()

    @PutMapping("/{operationId}/reject")
    fun reject(@PathVariable operationId: Long): OperationDto = operationService.reject(operationId).toDto()

}