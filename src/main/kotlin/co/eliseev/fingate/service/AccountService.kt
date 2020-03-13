package co.eliseev.fingate.service

import co.eliseev.fingate.model.AccountModel
import co.eliseev.fingate.model.entity.BankAccount
import co.eliseev.fingate.model.converter.toEntity
import co.eliseev.fingate.repository.AccountRepository
import co.eliseev.fingate.service.exception.AccountNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDate

interface AccountService {
    fun create(accountModel: AccountModel): BankAccount
    fun get(accountId: Long): BankAccount
    fun delete(accountId: Long): Boolean
    fun getAll(): List<BankAccount>
    fun setBalance(balance: BigDecimal, accountId: Long): BankAccount
}

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val securityService: SecurityService,
    private val accountFeeService: AccountFeeService,
    private val clock: Clock
) : AccountService {

    override fun create(accountModel: AccountModel): BankAccount =
        accountModel.toEntity(getCurrentUser(), LocalDate.now(clock)).let { account ->
            accountFeeService.applyFee(account)
            accountRepository.save(account).also {
                notifyNewAccountCreation()
            }
        }

    private fun notifyNewAccountCreation() {} // TODO

    override fun get(accountId: Long): BankAccount = getOne(accountId)

    @Transactional
    override fun delete(accountId: Long): Boolean = // TODO check delete rights
        getOne(accountId).let {
            accountRepository.delete(it)
            true
        }

    override fun getAll(): List<BankAccount> = accountRepository.getAllByIssuer(getCurrentUser())

    @Transactional
    override fun setBalance(balance: BigDecimal, accountId: Long): BankAccount =
        getOne(accountId)
            .apply { this.balance = balance }

    private fun getCurrentUser() = securityService.getCurrentUser()

    private fun getOne(accountId: Long): BankAccount =
        accountRepository.findById(accountId)
            .orElseThrow { AccountNotFoundException("Account with id '$accountId' not found") }

}
