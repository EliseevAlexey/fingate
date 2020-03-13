package co.eliseev.fingate.service

import co.eliseev.fingate.model.OperationModel
import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.repository.OperationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDateTime

interface OperationService {
    fun create(operationModel: OperationModel): Operation
    fun getAllByOperationType(operationType: OperationType): List<Operation>
    fun getHistoryData(): List<Operation>
    fun reject(operationId: Long): Operation
}

@Service
class OperationServiceImpl(
    private val operationRepository: OperationRepository,
    private val accountService: AccountService,
    private val operationProcessor: OperationProcessor,
    private val paymentCategoryService: PaymentCategoryService,
    private val clock: Clock
) : OperationService {

    @Transactional
    override fun create(operationModel: OperationModel): Operation {
        val operation = toEntity(operationModel)
        operationProcessor.processAndChangeStatus(operation)
        return operationRepository.save(operation)
    }

    private fun toEntity(operationModel: OperationModel): Operation =
        operationModel.toEntity(
            paymentDateTime = LocalDateTime.now(clock),
            bankAccount = getAccount(operationModel),
            operationStatus = OperationStatus.NEW,
            paymentCategory = paymentCategoryService.get(operationModel.paymentCategoryId)
        )

    private fun getAccount(operationModel: OperationModel) = accountService.get(operationModel.accountId)

    override fun getAllByOperationType(operationType: OperationType): List<Operation> =
        operationRepository.findAllByOperationType(operationType)

    override fun getHistoryData(): List<Operation> = operationRepository.findHistoryData()

    override fun reject(operationId: Long): Operation {
        TODO("Not yet implemented")
    }

}
