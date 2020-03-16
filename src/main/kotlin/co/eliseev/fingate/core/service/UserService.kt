package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.BankAccount
import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

interface UserService {
    fun save(user: User): User
    fun get(userId: Long): User
    fun getAllWithTodayBirthday(): List<User>
    fun setDefaultAccount(user: User, bankAccount: BankAccount): Boolean
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val clock: Clock
): UserService {

    override fun save(user: User): User = userRepository.save(user)

    override fun get(userId: Long): User = userRepository.getOne(userId)

    override fun getAllWithTodayBirthday(): List<User> = userRepository.getAllByBirthday(LocalDate.now(clock))

    override fun setDefaultAccount(user: User, bankAccount: BankAccount): Boolean {
        user.defaultAccount = bankAccount
        return true
    }

}
