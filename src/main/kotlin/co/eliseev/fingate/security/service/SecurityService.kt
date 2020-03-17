package co.eliseev.fingate.security.service

import co.eliseev.fingate.core.model.entity.User
import org.springframework.stereotype.Service

interface SecurityService {
    fun getCurrentUser(): User
}

@Service
class SecurityServiceImpl(private val securityUserService: SecurityUserService) : SecurityService {

    override fun getCurrentUser(): User = securityUserService.get(1L) // FIXME Get user from SecurityContext

}
