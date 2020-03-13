package co.eliseev.fingate.controller

import co.eliseev.fingate.model.converter.toDto
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.service.OperationReport
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class OperationReportController(private val operationReport: OperationReport) {

    @GetMapping("/ytd")
    fun getAllYtd(): List<OperationDto> = operationReport.getAllYTD().toDto()

}
