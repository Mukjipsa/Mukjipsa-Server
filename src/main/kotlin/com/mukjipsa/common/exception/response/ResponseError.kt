package com.mukjipsa.common.exception.response

import org.springframework.validation.BindingResult
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.LocalDateTime
import java.util.stream.Collectors

data class ResponseError(
    var status: Int,
    var code: String,
    var message: String,
    var errors: List<FieldError>,
    var timestamp: String
) {

    constructor(code: ErrorCode, errors: List<FieldError>) : this(
        message = code.message,
        status = code.status,
        code = code.code,
        errors = errors,
        timestamp = LocalDateTime.now().toString()
    )

    constructor(code: ErrorCode) : this(
        message = code.message,
        status = code.status,
        code = code.code,
        errors = arrayListOf(),
        timestamp = LocalDateTime.now().toString()
    )

    companion object {
        fun of(code: ErrorCode, bindingResult: BindingResult): ResponseError {
            return ResponseError(code, FieldError.of(bindingResult))
        }

        fun of(code: ErrorCode): ResponseError {
            return ResponseError(code)
        }

        fun of(code: ErrorCode, errors: List<FieldError>): ResponseError {
            return ResponseError(code, errors)
        }

        fun of(e: MethodArgumentTypeMismatchException): ResponseError? {
            val value = if (e.value == null) "" else e.value?.toString()
            val errors = value?.let { FieldError.of(e.name, it, e.errorCode) }
            return errors?.let { ResponseError(ErrorCode.INVALID_TYPE_VALUE, it) }
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