package co.eliseev.fingate.controller

import co.eliseev.fingate.model.converter.toDto
import co.eliseev.fingate.model.converter.toModel
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.service.OperationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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

}
