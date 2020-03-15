package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.CardSystem
import co.eliseev.fingate.core.model.entity.FeeFrequency
import co.eliseev.fingate.core.repository.BankAccountFeeRepository
import co.eliseev.fingate.core.repository.BankAccountRepository
import co.eliseev.fingate.core.service.exception.BankAccountFeeNotFoundException
import org.springframework.stereotype.Component

interface BankAccountFeeSetter {
    fun applyFee(bankAccount: BankAccount)
}

@Component
class BankAccountFeeSetterImpl(
    private val bankAccountFeeRepository: BankAccountFeeRepository,
    private val bankAccountRepository: BankAccountRepository // FIXME call service
) : BankAccountFeeSetter {

    override fun applyFee(bankAccount: BankAccount) {
        if (countCurrentAccounts(bankAccount) > FEE_THRESHOLD) {
            setFee(bankAccount)
        }
    }

    private fun countCurrentAccounts(bankAccount: BankAccount): Long = bankAccountRepository.countAllByIssuer(bankAccount.issuer!!) // FIXME find by users

    private fun setFee(bankAccount: BankAccount) {
        bankAccount.bankAccountFee = getFee(bankAccount.system, bankAccount.feeFrequency)
    }

    private fun getFee(system: CardSystem?, feeFrequency: FeeFrequency?) =
        bankAccountFeeRepository.findBySystemAndFeeFrequency(system, feeFrequency)
            ?: throw BankAccountFeeNotFoundException("Account fee by system $system and fee frequency $feeFrequency not found")

    companion object {
        private const val FEE_THRESHOLD = 2 // TODO move to properties
    }

}
