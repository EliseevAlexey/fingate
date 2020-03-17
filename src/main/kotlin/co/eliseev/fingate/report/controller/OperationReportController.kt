package co.eliseev.fingate.report.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.report.dto.AccountReportDto
import co.eliseev.fingate.core.model.dto.OperationDto
import co.eliseev.fingate.report.service.OperationReport
import co.eliseev.fingate.security.util.HasAdminOrUserRights
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@HasAdminOrUserRights
@RequestMapping("/reports")
class OperationReportController(private val operationReport: OperationReport) {

    @GetMapping("/ytd")
    fun getAllYtd(): List<AccountReportDto> = operationReport.getAllYtd()

    @GetMapping("/rejected")
    fun getRejectedOperations(): List<OperationDto> = operationReport.getRejectedOperations().toDto()

}
