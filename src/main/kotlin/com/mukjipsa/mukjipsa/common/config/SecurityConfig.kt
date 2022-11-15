package com.mukjipsa.mukjipsa.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mukjipsa.mukjipsa.common.authentication.jwt.JwtAuthenticationFilter
import com.mukjipsa.mukjipsa.common.authentication.jwt.JwtAuthenticationProvider
import com.mukjipsa.mukjipsa.common.authentication.jwt.JwtExceptionFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnBean(JwtAuthenticationProvider::class)
class SecurityConfig(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val objectMapper: ObjectMapper,
    jwtAuthenticationProvider: JwtAuthenticationProvider,
) : WebSecurityConfigurerAdapter(){
    init {
        this.authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(authenticationManagerBuilder.orBuild),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                JwtExceptionFilter(objectMapper),
                JwtAuthenticationFilter::class.java
            )
            /* CSRF */
            .csrf().disable()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}