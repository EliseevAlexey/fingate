package co.eliseev.fingate.security.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.security.repository.SecurityUserRepository
import co.eliseev.fingate.security.service.exception.UserByEmailNotFoundException
import co.eliseev.fingate.security.service.exception.UserByIdNotFoundException
import org.springframework.stereotype.Service

interface SecurityUserService {
    fun save(user: User): User
    fun get(userId: Long): User
    fun findByEmail(email: String): User
}

@Service
class SecurityUserServiceImpl(
    private val securityUserRepository: SecurityUserRepository
) : SecurityUserService {

    override fun save(user: User): User = securityUserRepository.save(user)

    override fun get(userId: Long): User =
        securityUserRepository.findById(userId)
            .orElseThrow { UserByIdNotFoundException("users.by_id_not_found", userId) }

    override fun findByEmail(email: String): User =
        securityUserRepository.findByEmail(email)
            ?: throw UserByEmailNotFoundException("users.by_email_not_found", email)

}
