package com.mukjipsa.common.exception.response

import org.springframework.validation.BindingResult
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.LocalDateTime
import java.util.stream.Collectors

class ResponseError {
    private var timestamp: String
    private var message: String
    private var status: Int
    private var code: String
    private var errors: List<FieldError>

    constructor(code: CodeError, errors: List<FieldError>) {
        timestamp = LocalDateTime.now().toString()
        message = code.message
        status = code.status
        this.errors = errors
        this.code = code.code
    }

    constructor(code: CodeError) {
        timestamp = LocalDateTime.now().toString()
        message = code.message
        status = code.status
        this.code = code.code
        errors = ArrayList()
    }

    companion object {
        fun of(code: CodeError, bindingResult: BindingResult): ResponseError {
            return ResponseError(code, FieldError.of(bindingResult))
        }

        fun of(code: CodeError): ResponseError {
            return ResponseError(code)
        }

        fun of(code: CodeError, errors: List<FieldError>): ResponseError {
            return ResponseError(code, errors)
        }

        fun of(e: MethodArgumentTypeMismatchException): ResponseError? {
            val value = if (e.value == null) "" else e.value?.toString()
            val errors = value?.let { FieldError.of(e.name, it, e.errorCode) }
            return errors?.let { ResponseError(CodeError.INVALID_TYPE_VALUE, it) }
        }

        fun filterLogMessageByExecEnv() {

        }
    }

    /* 에러 내역을 항상 List 형으로 반환함 */
    class FieldError(
            private var field: String,
            private var value: String,
            private var reason: String?
    ) {
        companion object {
            fun of(field: String, value: String, reason: String?): List<FieldError> {
                val fieldErrors: MutableList<FieldError> = ArrayList()
                fieldErrors.add(FieldError(field, value, reason))
                return fieldErrors
            }

            fun of(bindingResult: BindingResult): List<FieldError> {
                val fieldErrors = bindingResult.fieldErrors
                return fieldErrors.stream()
                        .map { error: org.springframework.validation.FieldError ->
                            FieldError(
                                    error.field,
                                    if (error.rejectedValue == null) "" else error.rejectedValue.toString(),
                                    error.defaultMessage
                            )
                        }
                        .collect(Collectors.toList())
            }
        }
    }
}