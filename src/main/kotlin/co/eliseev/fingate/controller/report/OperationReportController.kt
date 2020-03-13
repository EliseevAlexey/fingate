package co.eliseev.fingate.controller.report

import co.eliseev.fingate.model.converter.toDto
import co.eliseev.fingate.model.dto.AccountReportDto
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.service.report.OperationReport
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class OperationReportController(private val operationReport: OperationReport) {

    @GetMapping("/ytd")
    fun getAllYtd(): List<AccountReportDto> = operationReport.getAllYtd() // TODO toDto()

    @GetMapping("/rejected")
    fun getRejectedOperations(): List<OperationDto> = operationReport.getRejectedOperations().toDto()

}
