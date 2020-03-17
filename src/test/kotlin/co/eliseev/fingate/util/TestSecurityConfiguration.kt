package co.eliseev.fingate.util

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter

@TestConfiguration
class TestSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Bean
    fun securityContextHolderAwareRequestFilter() =
        SecurityContextHolderAwareRequestFilter()

    override fun configure(webSecurity: WebSecurity) {
        webSecurity
            .ignoring()
            .antMatchers(HttpMethod.OPTIONS)
            .antMatchers("/**")
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
    }

}
