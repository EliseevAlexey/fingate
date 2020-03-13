package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.repository.AccountFeeRepository
import co.eliseev.fingate.repository.AccountRepository
import co.eliseev.fingate.service.exception.AccountFeeNotFoundException
import org.springframework.stereotype.Service

interface AccountFeeService {
    fun applyFee(bankAccount: BankAccount)
}

@Service
class AccountFeeServiceImpl(
    private val accountFeeRepository: AccountFeeRepository,
    private val accountRepository: AccountRepository // FIXME call service
) : AccountFeeService {

    override fun applyFee(bankAccount: BankAccount) {
        if (countCurrentAccounts(bankAccount) > FEE_THRESHOLD) {
            setFee(bankAccount)
        }
    }

    private fun countCurrentAccounts(bankAccount: BankAccount): Long = accountRepository.countAllByIssuer(bankAccount.issuer!!) // FIXME find by users

    private fun setFee(bankAccount: BankAccount) {
        bankAccount.accountFee = getFee(bankAccount.system, bankAccount.feeFrequency)
    }

    private fun getFee(system: CardSystem, feeFrequency: FeeFrequency) =
        accountFeeRepository.findBySystemAndFeeFrequency(system, feeFrequency)
            ?: throw AccountFeeNotFoundException("Account fee by system $system and fee frequency $feeFrequency not found")

    companion object {
        private const val FEE_THRESHOLD = 2
    }

}
