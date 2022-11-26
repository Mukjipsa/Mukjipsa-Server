package com.mukjipsa.common.authentication.exception

enum class JWTExceptionCode(
        val code: String,
        val message: String,
) {
    MALFORMED_TOKEN("MALFORMED_TOKEN", "유효하지 않은 토큰 형식입니다."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 토큰입니다."),
    INVALID_SIGNATURE_TOKEN("INVALID_SIGNATURE_TOKEN", "잘못된 시그니처입니다."),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "일시적인 문제가 발생했습니다. 잠시후 다시 시도해주세요.");
}