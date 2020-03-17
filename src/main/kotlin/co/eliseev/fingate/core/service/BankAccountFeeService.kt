package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.BankAccountFee
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.repository.BankAccountFeeRepository
import co.eliseev.fingate.core.repository.BankAccountRepository
import co.eliseev.fingate.core.service.exception.BankAccountFeeNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

interface BankAccountFeeService {
    fun applyFee(bankAccount: BankAccount)
    fun getAll(): List<BankAccountFee>
}

@Component
class BankAccountFeeServiceImpl(
    private val bankAccountFeeRepository: BankAccountFeeRepository,
    private val bankAccountService: BankAccountRepository, // FIXME call service
    @Value("\${fingate.free_account_number_threshold}") private val freeAccountNumberThreshold: Long
) : BankAccountFeeService {

    override fun applyFee(bankAccount: BankAccount) {
        if (isNotFreeAccount(bankAccount)) {
            setFee(bankAccount)
        }
    }

    private fun isNotFreeAccount(bankAccount: BankAccount) =
        countCurrentAccounts(bankAccount) > freeAccountNumberThreshold

    private fun countCurrentAccounts(bankAccount: BankAccount): Long =
        bankAccountService.countAllByUser(bankAccount.user!!)

    private fun setFee(bankAccount: BankAccount) {
        bankAccount.bankAccountFee = getFee(bankAccount.cardSystem, bankAccount.feeFrequency)
    }

    private fun getFee(system: CardSystem?, feeFrequency: FeeFrequency?) =
        bankAccountFeeRepository.findByCardSystemAndFeeFrequency(system, feeFrequency)
            ?: throw BankAccountFeeNotFoundException("bank_account_fees.not_found", arrayOf(system, feeFrequency))

    override fun getAll(): List<BankAccountFee> = bankAccountFeeRepository.findAll()

}
