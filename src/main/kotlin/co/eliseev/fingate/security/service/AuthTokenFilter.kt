package co.eliseev.fingate.security.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = parseJwt(request)
        if (isValid(jwt)) {
            createAuthentication(jwt!!, request)
                .also { setToContext(it) }
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }

    private fun isValid(jwt: String?): Boolean {
        return jwt != null && jwtService.validateJwtToken(jwt)
    }

    private fun createAuthentication(jwt: String, request: HttpServletRequest): UsernamePasswordAuthenticationToken =
        getUserDetails(jwt).let { userDetails ->
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }.apply { details = createDetails(request) }

    private fun getUserDetails(jwt: String): UserDetails {
        val email = jwtService.getUserEmailFromJwtToken(jwt)
        return userDetailsService.loadUserByUsername(email)
    }

    private fun createDetails(request: HttpServletRequest): WebAuthenticationDetails =
        WebAuthenticationDetailsSource().buildDetails(request)

    private fun setToContext(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

}
