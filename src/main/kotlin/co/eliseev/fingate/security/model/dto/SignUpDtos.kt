package co.eliseev.fingate.security.model.dto

import co.eliseev.fingate.security.model.validator.EmailConstraint
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Size

@Validated
data class SignUpRequest(
    @field:EmailConstraint var email: String,
    @JsonIgnore var roles: Set<String>? = null,
    var password: @Size(min = 3, max = 40) String
)

data class SignUpResponse(var message: String)
