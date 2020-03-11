package co.eliseev.fingate.service

import co.eliseev.fingate.model.entity.User
import co.eliseev.fingate.repository.UserRepository
import org.springframework.stereotype.Service

interface SecurityService {
    fun getCurrentUser(): User
}

@Service
class SecurityServiceImpl(private val userRepository: UserRepository): SecurityService {

    override fun getCurrentUser(): User = userRepository.getOne(1L) // FIXME Get user from SecurityContext

}
