package co.eliseev.fingate.controller.report

import co.eliseev.fingate.model.converter.toDto
import co.eliseev.fingate.model.dto.OperationDto
import co.eliseev.fingate.service.report.AdminOperationReport
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports/admin/")
class AdminOperationReportController(private val adminOperationReport: AdminOperationReport) {

    @GetMapping("/ytd")
    fun getAllYtd(): List<OperationDto> = adminOperationReport.getAllYTD().toDto()

}
