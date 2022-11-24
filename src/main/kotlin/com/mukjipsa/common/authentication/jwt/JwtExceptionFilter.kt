package com.mukjipsa.common.authentication.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.mukjipsa.common.authentication.exception.JWTExceptionCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtExceptionFilter(private val objectMapper: ObjectMapper) : OncePerRequestFilter() {
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        response.characterEncoding = "utf-8"
        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            setErrorResponse(response, JWTExceptionCode.EXPIRED_TOKEN)
        } catch (e: MalformedJwtException) {
            setErrorResponse(response, JWTExceptionCode.MALFORMED_TOKEN)
        } catch (e: SignatureException) {
            setErrorResponse(response, JWTExceptionCode.INVALID_SIGNATURE_TOKEN)
        } catch (e: JwtException) {
            setErrorResponse(response, JWTExceptionCode.UNKNOWN_ERROR)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, exception: JWTExceptionCode) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseJson = mapOf(
                "errors" to listOf(
                        mapOf(
                                "message" to "${exception::class.simpleName}",
                                "extensions" to mapOf(
                                        "statusCode" to HttpServletResponse.SC_UNAUTHORIZED,
                                        "errorCode" to exception.code,
                                        "clientMessage" to exception.message
                                )
                        )
                )
        )
        val responseJsonStr = objectMapper.writeValueAsString(responseJson)
        response.writer.write(responseJsonStr)
    }
}