package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.common.authentication.jwt.JwtAuthenticationToken
import com.mukjipsa.mukjipsa.common.exception.UserNotFoundException
import com.mukjipsa.mukjipsa.service.AuthService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(

): AuthService {
    override fun getUserId(): Int {
        return (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).getUserDetails()?.id ?: throw UserNotFoundException("user가 존재하지 않습니다.")
    }
}