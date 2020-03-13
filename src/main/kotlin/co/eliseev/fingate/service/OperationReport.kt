package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.repository.OperationReportRepository
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Year

interface OperationReport {
    fun getAllYTD(): List<Operation>
}

@Service
class OperationReportImpl(
    private val operationReportRepository: OperationReportRepository,
    private val clock: Clock
) : OperationReport {

    override fun getAllYTD(): List<Operation> =
        getFirstDayOfCurrentYear().let {
            operationReportRepository.findAllYTD(it)
        }

    private fun getFirstDayOfCurrentYear() = Year.now(clock).atDay(1)

}
