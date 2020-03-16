package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.User
import org.springframework.stereotype.Service

interface SecurityService {
    fun getCurrentUser(): User
}

@Service
class SecurityServiceImpl(private val userService: UserService):
    SecurityService {

    override fun getCurrentUser(): User = userService.get(1L) // FIXME Get user from SecurityContext

}
