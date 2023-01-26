package com.mukjipsa.common.exception

import com.mukjipsa.common.exception.response.ErrorCode

open class BusinessException : RuntimeException {
    private var errorCode: ErrorCode

    constructor(message: String?, errorCode: ErrorCode) : super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
    }

    fun getErrorCode(): ErrorCode {
        return errorCode
    }
}