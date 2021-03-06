package co.eliseev.fingate.report.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.CardType
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.model.entity.Operation
import co.eliseev.fingate.core.model.entity.OperationStatus
import co.eliseev.fingate.core.model.entity.OperationType
import co.eliseev.fingate.core.model.entity.PaymentCategory
import co.eliseev.fingate.report.repository.AdminOperationReportRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId

internal class AdminOperationReportImplTest {

    private val operationReportRepository = mock<AdminOperationReportRepository>()
    private val clock = mock<Clock>()
    private lateinit var adminOperationReport: AdminOperationReport


    @BeforeEach
    fun reset() {
        reset(operationReportRepository, clock)
        adminOperationReport = AdminOperationReportImpl(
            operationReportRepository,
            clock
        )
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
        val firstYearDay = Year.now(fixedClock).atDay(1).atStartOfDay()
        whenever(operationReportRepository.findAllYTD(firstYearDay)).thenReturn(expected)

        val actual = adminOperationReport.getAllYTD()
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
            cardNumber = "0000111122223333",
            currency = "USD",
            cardCvvNumber = 999,
            cardExpirationDateTime = testDateTime.plusYears(1),
            cardSystem = CardSystem.MASTER_CARD,
            cardType = CardType.CREDIT,
            feeFrequency = FeeFrequency.MONTHLY,
            registrationDate = LocalDate.now()
        ).apply { id = 1L }
        private val testPaymentCategory = PaymentCategory(name = "testName").apply { id = 1L }
    }

}
