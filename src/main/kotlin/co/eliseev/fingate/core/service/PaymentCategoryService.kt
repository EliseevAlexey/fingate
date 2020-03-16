package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.PaymentCategoryModel
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.core.repository.PaymentCategoryRepository
import co.eliseev.fingate.core.service.exception.PaymentCategoryAlreadyExists
import co.eliseev.fingate.core.service.exception.PaymentCategoryNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PaymentCategoryService {
    fun create(paymentCategoryModel: PaymentCategoryModel): PaymentCategory
    fun get(paymentCategoryId: Long): PaymentCategory
    fun getFeePaymentCategory(): PaymentCategory
    fun getGiftFundPaymentCategory(): PaymentCategory
    fun delete(paymentCategoryId: Long): Boolean
    fun getAll(): List<PaymentCategory>
}

@Service
class PaymentCategoryServiceImpl(
    private val paymentCategoryRepository: PaymentCategoryRepository
) : PaymentCategoryService {

    override fun create(paymentCategoryModel: PaymentCategoryModel): PaymentCategory =
        paymentCategoryModel.toEntity().also {
            save(it)
        }

    private fun save(paymentCategory: PaymentCategory): PaymentCategory =
        try {
            paymentCategoryRepository.save(paymentCategory)
        } catch (e: DataIntegrityViolationException) {
            throw PaymentCategoryAlreadyExists("payments.create.duplicate", paymentCategory)
        }

    override fun get(paymentCategoryId: Long): PaymentCategory = getOne(paymentCategoryId)

    override fun getFeePaymentCategory(): PaymentCategory =
        paymentCategoryRepository.getByName(FEE_PAYMENT_CATEGORY)

    override fun getGiftFundPaymentCategory(): PaymentCategory =
        paymentCategoryRepository.getByName(GIFT_FUND_PAYMENT_CATEGORY)

    @Transactional
    override fun delete(paymentCategoryId: Long): Boolean =
        getOne(paymentCategoryId).let {
            paymentCategoryRepository.delete(it)
            true
        }

    override fun getAll(): List<PaymentCategory> = paymentCategoryRepository.findAll()

    private fun getOne(paymentCategoryId: Long): PaymentCategory = paymentCategoryRepository.findById(paymentCategoryId)
        .orElseThrow { PaymentCategoryNotFoundException("payments.not_found", paymentCategoryId) }

    companion object {
        private const val FEE_PAYMENT_CATEGORY = "FeeWithdraw"
        private const val GIFT_FUND_PAYMENT_CATEGORY = "GiftFund"
    }

}
