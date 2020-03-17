package co.eliseev.fingate.security.service

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

interface CustomUserDetails : UserDetails {
    fun getId(): Long
    fun getEmail(): String
}

data class CustomUserDetailsImpl(
    private val id: Long,
    private val email: String,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : CustomUserDetails {

    override fun getId(): Long = id

    override fun getEmail(): String = email

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun isEnabled() = true

    override fun getUsername() = ""

    override fun isCredentialsNonExpired() = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

}
