package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.repository.UserRepository
import org.springframework.stereotype.Service

interface SecurityService {
    fun getCurrentUser(): User
}

@Service
class SecurityServiceImpl(private val userRepository: UserRepository):
    SecurityService {

    override fun getCurrentUser(): User = userRepository.getOne(1L) // FIXME Get user from SecurityContext

}
