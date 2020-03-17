package co.eliseev.fingate.security.service

import co.eliseev.fingate.core.model.entity.User
import co.eliseev.fingate.security.model.dto.SignInRequest
import co.eliseev.fingate.security.model.dto.SignInResponse
import co.eliseev.fingate.security.model.dto.SignUpResponse
import co.eliseev.fingate.security.model.dto.SignUpRequest
import co.eliseev.fingate.security.model.entity.Role
import co.eliseev.fingate.security.model.entity.UserRole
import co.eliseev.fingate.security.repository.SecurityUserRepository
import co.eliseev.fingate.security.service.exception.EmailDuplicateException
import org.springframework.context.MessageSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Locale

interface AuthService {
    fun singUp(signUpRequest: SignUpRequest): SignUpResponse
    fun signIn(sighInRequest: SignInRequest): SignInResponse
}

@Service
class AuthServiceImpl(
    private val messageSource: MessageSource,
    private val securityUserRepository: SecurityUserRepository,
    private val securityUserService: SecurityUserService,
    private val encoder: PasswordEncoder,
    private val roleService: RoleService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) : AuthService {

    override fun singUp(signUpRequest: SignUpRequest): SignUpResponse {
        validate(signUpRequest)
        saveNewUser(signUpRequest)
        return createSuccessSignUpResponse()
    }

    private fun createSuccessSignUpResponse(): SignUpResponse {
        val message = messageSource.getMessage("sing-up.response.success", null, Locale.ROOT)
        return SignUpResponse(message)
    }

    private fun validate(signUpRequest: SignUpRequest) {
        val email = signUpRequest.email
        if (securityUserRepository.existsByEmail(email)) {
            throw EmailDuplicateException("sing-up.email.duplicate", email)
        }
    }

    private fun saveNewUser(signUpRequest: SignUpRequest) {
        createUser(signUpRequest).also {
            securityUserService.save(it)
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

    override fun signIn(sighInRequest: SignInRequest): SignInResponse {
        createAuthentication(sighInRequest).also {
            setToContext(it)
            return createSingInResponse(it)
        }
    }

    private fun createAuthentication(sighInRequest: SignInRequest): Authentication =
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(sighInRequest.email, sighInRequest.password)
        )

    private fun setToContext(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun createSingInResponse(authentication: Authentication): SignInResponse {
        val userDetail = authentication.principal as CustomUserDetails
        return SignInResponse(
            accessToken = jwtService.generateJwtToken(userDetail),
            id = userDetail.getId(),
            email = userDetail.getEmail(),
            roles = userDetail.authorities.map { it.authority }
        )
    }

}
