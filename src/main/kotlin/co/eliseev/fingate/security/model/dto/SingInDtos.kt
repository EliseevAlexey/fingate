package co.eliseev.fingate.security.model.dto

data class SignInRequest(
    var email: String,
    var password: String
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
