package co.eliseev.fingate.security.service

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface UnauthorizedHandler : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    )
}

@Component
class UnauthorizedHandlerImpl : UnauthorizedHandler {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error("Unauthorized error: ${authException.message}")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UnauthorizedHandlerImpl::class.java)
    }

}
