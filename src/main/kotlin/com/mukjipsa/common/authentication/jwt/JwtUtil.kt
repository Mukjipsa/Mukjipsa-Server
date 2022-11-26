package com.mukjipsa.common.authentication.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.Key
import java.sql.Timestamp
import java.time.LocalDateTime

class JwtUtil(
        private val secretKey: String?,
) {
    fun decodeToken(token: String): Claims? {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey?.toByteArray()?.copyOf(64))
                .build()
                .parseClaimsJws(token)
                .body
    }

    fun encodeAccessToken(claims: Claims, key: Key?, expiresIn: Long): String {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setIssuer("team.Mukjipsa")
                .setExpiration(Timestamp(System.currentTimeMillis() + expiresIn * 1000))
                .setSubject("userInfo")
                .setHeaderParam("typ", "JWT")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    fun encodeRefreshToken(claims: Claims, key: Key?, expiresIn: Long): String {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setIssuer("team.Mukjipsa")
                .setExpiration(Timestamp(System.currentTimeMillis() + expiresIn * 1000))
                .setSubject("refreshToken")
                .setHeaderParam("typ", "JWT")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }
}