package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.BankAccountFee
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.repository.BankAccountFeeRepository
import co.eliseev.fingate.core.repository.BankAccountRepository
import co.eliseev.fingate.core.service.exception.BankAccountFeeNotFoundException
import org.springframework.stereotype.Component

interface BankAccountFeeService {
    fun applyFee(bankAccount: BankAccount)
    fun getAll(): List<BankAccountFee>
}

@Component
class BankAccountFeeServiceImpl(
    private val bankAccountFeeRepository: BankAccountFeeRepository,
    private val bankAccountService: BankAccountRepository // FIXME call service
) : BankAccountFeeService {

    override fun applyFee(bankAccount: BankAccount) {
        if (countCurrentAccounts(bankAccount) > FEE_THRESHOLD) {
            setFee(bankAccount)
        }
    }

    private fun countCurrentAccounts(bankAccount: BankAccount): Long =
        bankAccountService.countAllByIssuer(bankAccount.issuer!!) // FIXME find by users

    private fun setFee(bankAccount: BankAccount) {
        bankAccount.bankAccountFee = getFee(bankAccount.system, bankAccount.feeFrequency)
    }

    private fun getFee(system: CardSystem?, feeFrequency: FeeFrequency?) =
        bankAccountFeeRepository.findBySystemAndFeeFrequency(system, feeFrequency)
            ?: throw BankAccountFeeNotFoundException("bank_account_fee.not_found", arrayOf(system, feeFrequency))

    override fun getAll(): List<BankAccountFee> = bankAccountFeeRepository.findAll()

    companion object {
        private const val FEE_THRESHOLD = 2 // TODO move to properties
    }

}
