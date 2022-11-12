package com.mukjipsa.mukjipsa.common.exception

import com.mukjipsa.mukjipsa.common.exception.response.CodeError
import com.mukjipsa.mukjipsa.common.exception.response.ResponseError
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ResponseError> {
        val response: ResponseError = ResponseError.of(CodeError.METHOD_NOT_ALLOWED)
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }
}