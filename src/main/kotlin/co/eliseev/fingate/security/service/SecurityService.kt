package co.eliseev.fingate.security.service

import co.eliseev.fingate.core.model.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

interface SecurityService {
    fun getCurrentUser(): User
}

@Service
class SecurityServiceImpl(private val securityUserService: SecurityUserService) : SecurityService {

    override fun getCurrentUser(): User {
        val email = when (val principal = SecurityContextHolder.getContext().authentication.principal) {
            is String -> principal
            is CustomUserDetails -> principal.getEmail()
            else -> throw error("Unsupported principal $principal")
        }
        return securityUserService.findByEmail(email)
    }

}
