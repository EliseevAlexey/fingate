package co.eliseev.fingate.report.controller

import co.eliseev.fingate.core.model.converter.toDto
import co.eliseev.fingate.core.model.dto.OperationDto
import co.eliseev.fingate.report.service.AdminOperationReport
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports/admin/")
class AdminOperationReportController(private val adminOperationReport: AdminOperationReport) {

    @GetMapping("/ytd")
    fun getAllYtd(): List<OperationDto> = adminOperationReport.getAllYTD().toDto()

}
