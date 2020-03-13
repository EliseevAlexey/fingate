package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.CardType
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.model.entity.Operation
import co.eliseev.fingate.model.entity.OperationStatus
import co.eliseev.fingate.model.entity.OperationType
import co.eliseev.fingate.model.entity.PaymentCategory
import co.eliseev.fingate.repository.OperationReportRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import java.time.Clock
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId

internal class OperationReportImplTest {

    private val operationReportRepository = mock<OperationReportRepository>()
    private val clock = mock<Clock>()
    private lateinit var operationReport: OperationReport


    @BeforeEach
    fun reset() {
        reset(operationReportRepository, clock)
        operationReport = OperationReportImpl(operationReportRepository, clock)
    }

    @Test
    fun getAllYTD() {
        val operation = Operation(
            bankAccount = testBankAccount,
            operationType = OperationType.WITHDRAW,
            operationStatus = OperationStatus.REJECTED,
            paymentAmount = 100.toBigDecimal(),
            paymentDateTime = testDateTime,
            paymentCategory = testPaymentCategory,
            withdrawServiceName = "testWithdrawServiceName"
        )
        val expected = listOf(operation)
        prepareClock()
        val firstYearDay = Year.now(fixedClock).atDay(1)
        whenever(operationReportRepository.findAllYTD(firstYearDay)).thenReturn(expected)

        val actual = operationReport.getAllYTD()
        assertEquals(expected, actual)
    }

    private fun prepareClock() {
        whenever(clock.instant()).thenReturn(fixedClock.instant())
        whenever(clock.zone).thenReturn(fixedClock.zone)
    }

    companion object {
        private val testDateTime = LocalDateTime.of(2020, 3, 11, 22, 10, 0)
        private val testDate = testDateTime.toLocalDate()
        private val fixedClock =
            Clock.fixed(testDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
        private val testBankAccount = BankAccount(
            number = 9990,
            currency = "USD",
            cvv = 999,
            expirationDateTime = testDateTime.plusYears(1),
            system = CardSystem.MASTER_CARD,
            type = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = testDate
        ).apply { id = 1L }
        private val testPaymentCategory = PaymentCategory(name = "testName").apply { id = 1L }
    }

}
