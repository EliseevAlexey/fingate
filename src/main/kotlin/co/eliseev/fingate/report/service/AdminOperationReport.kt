package co.eliseev.fingate.report.service

import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.report.repository.AdminOperationReportRepository
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime
import java.time.Year

interface AdminOperationReport {
    fun getAllYTD(): List<Operation>
}

@Service
class AdminOperationReportImpl(
    private val adminOperationReportRepository: AdminOperationReportRepository,
    private val clock: Clock
) : AdminOperationReport {

    override fun getAllYTD(): List<Operation> =
        getFirstDayOfCurrentYear().let {
            adminOperationReportRepository.findAllYTD(it)
        }

    private fun getFirstDayOfCurrentYear(): LocalDateTime = Year.now(clock).atDay(1).atStartOfDay()

}
