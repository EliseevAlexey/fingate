package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.OperationModel
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.repository.OperationRepository
import co.eliseev.fingate.core.service.exception.OperationNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDateTime

interface OperationService {
    fun create(operationModel: OperationModel, force: Boolean = false): Operation // TODO create by entity
    fun getAllByOperationType(operationType: OperationType): List<Operation>
    fun getHistoryData(): List<Operation>
    fun reject(operationId: Long): Operation
}

@Service
class OperationServiceImpl(
    private val operationRepository: OperationRepository,
    private val bankAccountService: BankAccountService,
    private val operationProcessor: OperationProcessor,
    private val paymentCategoryService: PaymentCategoryService,
    private val clock: Clock
) : OperationService {

    @Transactional
    override fun create(operationModel: OperationModel, force: Boolean): Operation {
        val operation = toEntity(operationModel)
        operationProcessor.processAndChangeStatus(operation, force)
        return operationRepository.save(operation)
    }

    private fun toEntity(operationModel: OperationModel): Operation =
        operationModel.toEntity(
            paymentDateTime = LocalDateTime.now(clock),
            bankAccount = getAccount(operationModel),
            operationStatus = OperationStatus.NEW,
            paymentCategory = paymentCategoryService.get(operationModel.paymentCategoryId)
        )

    private fun getAccount(operationModel: OperationModel) = bankAccountService.get(operationModel.accountId)

    override fun getAllByOperationType(operationType: OperationType): List<Operation> =
        operationRepository.findAllByOperationType(operationType)

    override fun getHistoryData(): List<Operation> = operationRepository.findHistoryData()

    @Transactional
    override fun reject(operationId: Long): Operation {
        getOne(operationId).let { operation ->
            operationProcessor.reject(operation)
            return operation
        }
    }

    private fun getOne(operationId: Long): Operation {
        return operationRepository.findById(operationId)
            .orElseThrow { OperationNotFoundException("operations.not_found", operationId) }
    }

}
