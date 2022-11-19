package com.mukjipsa.common.authentication.jwt

import com.mukjipsa.common.authentication.dto.CustomUserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken : AbstractAuthenticationToken {
    var jsonWebToken: String? = null
    private var principal: String?
    private var credentials: String?
    private var userDetails: CustomUserDetails? = null

    constructor(jsonWebToken: String?) : super(null) {
        this.jsonWebToken = jsonWebToken
        this.principal = null
        this.credentials = null
        this.isAuthenticated = false
    }

    // authorities는 개발 필요에 따라 추가.
    constructor(principal: String?, credentials: String?, userDetails: CustomUserDetails?) : super(
        null
    ) {
        this.principal = principal
        this.credentials = credentials
        this.userDetails = userDetails
        this.isAuthenticated = true
    }


    override fun getCredentials(): String? {
        return credentials
    }

    override fun getPrincipal(): String? {
        return principal
    }

    fun getUserDetails(): CustomUserDetails? {
        return userDetails
    }
}