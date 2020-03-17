package co.eliseev.fingate.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

interface JwtService {
    fun generateJwtToken(authentication: CustomUserDetails): String
    fun validateJwtToken(token: String?): Boolean
    fun getUserEmailFromJwtToken(token: String?): String
}

@Service
class JwtServiceImpl(
    @Value("\${fingate.jwt.expiration_millis}") private val jwtExpirationMillis: Int,
    @Value("\${fingate.jwt.secret}") private val jwtSecret: String
) : JwtService {

    override fun generateJwtToken(authentication: CustomUserDetails): String {
        val now = Date()
        val expirationDate = Date(now.time + jwtExpirationMillis)
        return Jwts.builder()
            .setSubject(authentication.getEmail())
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    override fun validateJwtToken(token: String?): Boolean {
        try {
            parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ${e.message}")
        }
        return false
    }

    private fun parseClaimsJws(token: String?): Jws<Claims> =
        Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)

    override fun getUserEmailFromJwtToken(token: String?): String = parseClaimsJws(token).body.subject

    companion object {
        private val logger = LoggerFactory.getLogger(JwtServiceImpl::class.java)
    }

}
