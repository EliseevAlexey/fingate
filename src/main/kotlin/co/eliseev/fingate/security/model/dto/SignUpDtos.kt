package co.eliseev.fingate.security.model.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Validated
data class SignUpRequest(
    var email: @Size(max = 50) @Email String,
    var roles: Set<String>? = null,
    var password: @Size(min = 3, max = 40) String
)

data class SignUpResponse(var message: String)
