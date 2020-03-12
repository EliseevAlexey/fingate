package co.eliseev.fingate.service

import co.eliseev.fingate.model.PaymentCategoryModel
import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.model.entity.PaymentCategory
import co.eliseev.fingate.repository.PaymentCategoryRepository
import co.eliseev.fingate.service.exception.PaymentCategoryAlreadyExists
import co.eliseev.fingate.service.exception.PaymentCategoryNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PaymentCategoryService {
    fun getAll(): List<PaymentCategory>
    fun create(paymentCategoryModel: PaymentCategoryModel): PaymentCategory
    fun delete(paymentCategoryId: Long): Boolean
}

@Service
class PaymentCategoryServiceImpl(
    private val paymentCategoryRepository: PaymentCategoryRepository
) : PaymentCategoryService {

    override fun getAll(): List<PaymentCategory> = paymentCategoryRepository.findAll()

    override fun create(paymentCategoryModel: PaymentCategoryModel): PaymentCategory =
        paymentCategoryModel.toEntity().also {
            save(it)
        }

    private fun save(paymentCategory: PaymentCategory): PaymentCategory {
        try {
            return paymentCategoryRepository.save(paymentCategory)
        } catch (e: DataIntegrityViolationException) {
            throw PaymentCategoryAlreadyExists("Payment category $paymentCategory already exists")
        }
    }

    @Transactional
    override fun delete(paymentCategoryId: Long): Boolean =
        getOne(paymentCategoryId).let {
            paymentCategoryRepository.delete(it)
            true
        }

    private fun getOne(paymentCategoryId: Long): PaymentCategory = paymentCategoryRepository.findById(paymentCategoryId)
        .orElseThrow { PaymentCategoryNotFoundException("Payment category by id '$paymentCategoryId' not found") }

}
