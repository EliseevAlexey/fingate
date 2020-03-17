package co.eliseev.fingate.security.controller

import co.eliseev.fingate.security.model.dto.SignInRequest
import co.eliseev.fingate.security.model.dto.SignInResponse
import co.eliseev.fingate.security.model.dto.SignUpResponse
import co.eliseev.fingate.security.model.dto.SignUpRequest
import co.eliseev.fingate.security.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): SignUpResponse =
        authService.singUp(signUpRequest)

    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest): SignInResponse =
        authService.signIn(signInRequest)

}
