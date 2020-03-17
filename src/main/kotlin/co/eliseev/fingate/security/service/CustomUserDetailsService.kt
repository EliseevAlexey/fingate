package co.eliseev.fingate.security.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CustomUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails
}

@Service
class CustomUserDetailsServiceImpl(
    private val securityUserService: SecurityUserService
) : CustomUserDetailsService {

    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        val user = securityUserService.findByEmail(email)
        return CustomUserDetailsImpl(
            user.id!!,
            user.email,
            user.password,
            user.roles.map { SimpleGrantedAuthority("ROLE_" + it.name!!.name) }
        )
    }

}
