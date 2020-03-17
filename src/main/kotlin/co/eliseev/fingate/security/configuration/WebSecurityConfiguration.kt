package co.eliseev.fingate.security.configuration

import co.eliseev.fingate.security.service.AuthTokenFilter
import co.eliseev.fingate.security.service.CustomUserDetailsService
import co.eliseev.fingate.security.service.CustomUserDetailsServiceImpl
import co.eliseev.fingate.security.service.JwtEntryPoint
import co.eliseev.fingate.security.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtEntryPoint: JwtEntryPoint,
    private val jwtService: JwtService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.headers().frameOptions().disable().and()
            .cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/auth/**", "/h2/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun authenticationJwtTokenFilter() = AuthTokenFilter(
        jwtService = jwtService,
        userDetailsService = customUserDetailsService
    )

}
