package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.BankAccountModel
import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.converter.toEntity
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.repository.BankAccountRepository
import co.eliseev.fingate.core.service.exception.BankAccountNotFoundException
import co.eliseev.fingate.security.service.SecurityService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDate

interface BankAccountService {
    fun create(bankAccountModel: BankAccountModel): BankAccount
    fun get(accountId: Long): BankAccount
    fun getDefaultAccount(): BankAccount
    fun delete(accountId: Long): Boolean
    fun getAll(): List<BankAccount>
    fun setBalance(balance: BigDecimal, accountId: Long): BankAccount
    fun countAllByIssuer(issuer: User): Long
}

@Service
class BankAccountServiceImpl(
    private val bankAccountRepository: BankAccountRepository,
    private val securityService: SecurityService,
    private val bankAccountFeeService: BankAccountFeeService,
    private val clock: Clock
) : BankAccountService {

    @Transactional
    override fun create(bankAccountModel: BankAccountModel): BankAccount =
        bankAccountModel.toEntity(getCurrentUser(), getCurrentUser(), getToday()).let { bankAccount ->
            bankAccountFeeService.applyFee(bankAccount)
            bankAccountRepository.save(bankAccount)
                .also { notifyNewAccountCreation() }
        }

    private fun notifyNewAccountCreation() {} // TODO

    override fun get(accountId: Long): BankAccount = getOne(accountId)

    override fun getDefaultAccount(): BankAccount = bankAccountRepository.getByIsDefaultAccountTrue()

    private fun getToday() = LocalDate.now(clock)

    @Transactional
    override fun delete(accountId: Long): Boolean =
        getOne(accountId).let {
            bankAccountRepository.delete(it)
            true
        }

    override fun getAll(): List<BankAccount> = bankAccountRepository.getAllByUser(getCurrentUser())

    @Transactional
    override fun setBalance(balance: BigDecimal, accountId: Long): BankAccount =
        getOne(accountId)
            .apply { this.balance = balance }

    private fun getCurrentUser() = securityService.getCurrentUser()

    private fun getOne(accountId: Long): BankAccount =
        bankAccountRepository.findById(accountId)
            .orElseThrow { BankAccountNotFoundException("bank_accounts.not_found", accountId) }

    override fun countAllByIssuer(issuer: User): Long = bankAccountRepository.countAllByUser(issuer)

}
