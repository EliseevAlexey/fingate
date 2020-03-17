package co.eliseev.fingate.security.model.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Validated
data class SignInRequest(
    var email: @Size(max = 50) @Email String,
    var password: @Size(min = 3, max = 40) String
)

data class SignInResponse(
    var accessToken: String,
    var id: Long,
    var email: String,
    val roles: List<String>,
    var tokenType: String = BEARER
) {
    companion object {
        private const val BEARER = "Bearer"
    }
}
