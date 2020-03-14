package co.eliseev.fingate.service

import co.eliseev.fingate.core.model.PaymentCategoryModel
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.core.repository.PaymentCategoryRepository
import co.eliseev.fingate.core.service.PaymentCategoryService
import co.eliseev.fingate.core.service.PaymentCategoryServiceImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import java.util.Optional

internal class PaymentCategoryServiceImplTest {

    private val paymentCategoryRepository = mock<PaymentCategoryRepository>()
    private lateinit var paymentCategoryService: PaymentCategoryService

    @BeforeEach
    fun resetMock() {
        reset(paymentCategoryRepository)
        paymentCategoryService =
            PaymentCategoryServiceImpl(paymentCategoryRepository)
    }

    @Test
    fun getAll() {
        val expected = listOf(
            PaymentCategory(name = "testName").apply { id = 1L }
        )
        whenever(paymentCategoryRepository.findAll()).thenReturn(expected)

        val actual = paymentCategoryService.getAll()
        assertEquals(expected, actual)
    }

    @Test
    fun create() {
        val paymentCategoryModel = PaymentCategoryModel(name = "testName")
        val paymentCategory = paymentCategoryModel.toEntity()
        val expected = paymentCategory.copy().apply { id = 1L }
        whenever(paymentCategoryRepository.save(paymentCategory)).thenReturn(expected)

        val actual = paymentCategoryService.create(paymentCategoryModel)
        assertEquals(expected, actual)
    }

    @Test
    fun delete() {
        val paymentCategoryId = 1L
        val paymentCategory = PaymentCategory(name = "testName").apply { id = paymentCategoryId }
        whenever(paymentCategoryRepository.findById(paymentCategoryId)).thenReturn(Optional.of(paymentCategory))

        val result = paymentCategoryService.delete(paymentCategoryId)
        assertTrue(result)
        verify(paymentCategoryRepository, times(1)).delete(paymentCategory)
    }

}
