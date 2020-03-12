package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.Account
import co.eliseev.fingate.model.entity.CardSystem
import co.eliseev.fingate.model.entity.FeeFrequency
import co.eliseev.fingate.repository.AccountFeeRepository
import co.eliseev.fingate.repository.AccountRepository
import co.eliseev.fingate.service.exception.AccountFeeNotFoundException
import org.springframework.stereotype.Service

interface AccountFeeService {
    fun applyFee(account: Account)
}

@Service
class AccountFeeServiceImpl(
    private val accountFeeRepository: AccountFeeRepository,
    private val accountRepository: AccountRepository // FIXME call service
) : AccountFeeService {

    override fun applyFee(account: Account) {
        if (countCurrentAccounts(account) > FEE_THRESHOLD) {
            setFee(account)
        }
    }

    private fun countCurrentAccounts(account: Account): Long = accountRepository.countAllByIssuer(account.issuer!!) // FIXME find by users

    private fun setFee(account: Account) {
        account.accountFee = getFee(account.system, account.feeFrequency)
    }

    private fun getFee(system: CardSystem, feeFrequency: FeeFrequency) =
        accountFeeRepository.findBySystemAndFeeFrequency(system, feeFrequency)
            ?: throw AccountFeeNotFoundException("Account fee by system $system and fee frequency $feeFrequency not found")

    companion object {
        private const val FEE_THRESHOLD = 2
    }

}
