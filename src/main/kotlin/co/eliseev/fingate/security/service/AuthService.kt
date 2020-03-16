package co.eliseev.fingate.security.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.core.service.UserService
import co.eliseev.fingate.security.model.dto.MessageResponse
import co.eliseev.fingate.security.model.dto.SignUpRequest
import co.eliseev.fingate.security.model.entity.Role
import co.eliseev.fingate.security.model.entity.UserRole
import co.eliseev.fingate.security.repository.SecurityUserRepository
import co.eliseev.fingate.security.service.exception.EmailDuplicateException
import org.springframework.context.MessageSource
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Locale

interface AuthService {
    fun singUp(signUpRequest: SignUpRequest): MessageResponse
}

@Service
class AuthServiceImpl(
    private val messageSource: MessageSource,
    private val securityUserRepository: SecurityUserRepository,
    private val userService: UserService,
    private val encoder: PasswordEncoder,
    private val roleService: RoleService
) : AuthService {

    override fun singUp(signUpRequest: SignUpRequest): MessageResponse {
        validate(signUpRequest)
        saveNewUser(signUpRequest)
        return createSuccessMessage()
    }

    private fun createSuccessMessage(): MessageResponse {
        val message = messageSource.getMessage("sing-up.response.success", null, Locale.ROOT)
        return MessageResponse(message)
    }

    private fun validate(signUpRequest: SignUpRequest) {
        val email = signUpRequest.email
        if (securityUserRepository.existsByEmail(email)) {
            throw EmailDuplicateException("sing-up.email.duplicate", email)
        }
    }

    private fun saveNewUser(signUpRequest: SignUpRequest) {
        createUser(signUpRequest).also {
            userService.save(it)
        }
    }

    private fun createUser(signUpRequest: SignUpRequest): User =
        User(
            email = signUpRequest.email,
            password = encoder.encode(signUpRequest.password),
            roles = convertRoles(signUpRequest.roles)
        )

    private fun convertRoles(stringRoles: Set<String>?): Set<Role> {
        val roles = mutableSetOf<Role>()
        if (stringRoles == null) {
            roles.add(getRole(UserRole.USER))
        } else {
            stringRoles.forEach { role ->
                roles.add(getRole(role))
            }
        }
        return roles
    }

    fun getRole(roleString: String): Role {
        val userRole = UserRole.parse(roleString)
        return getRole(userRole)
    }

    fun getRole(userRole: UserRole): Role = roleService.getByName(userRole)

}
