package com.mukjipsa.common.exception.response

enum class ErrorCode(
        val status: Int, /* 계속 추가 가능 */
        val code: String,
        val message: String
) {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " 입력 값이 잘못되었습니다."),
    METHOD_NOT_ALLOWED(405, "C002", " 해당 Method는 사용 할 수 없습니다."),
    ENTITY_NOT_FOUND(400, "C003", " Entity를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERRORCode(500, "C004", "서버 에러"),
    INVALID_TYPE_VALUE(400, "C005", " 값 타입이 잘못되었습니다."),
    HANDLE_ACCESS_DENIED(403, "C006", " 접근이 거부되었습니다."),
    HANDLE_NOT_FOUND(404, "C007", " 요청을 찾을 수 없습니다."),  // Header
    HTTP_CLIENT_EXCEPTION(403, "C008", "HTTP Client Error"),

    // Internal Issue
    NPE(501, "NPE001", "Null Point Exception 발생"),
    INVALID_DATA_ACCESS_API_USAGE_EXCEPTION(500, "DSL001", "QueryDSL Error 발생"),

    // 로그인
    INVALID_PROVIDER_NAME(400,"", "유효하지 않은 providerName 입니다."),
    FAILED_TO_VALIDATE_APPLE_LOGIN(400,"", "애플 로그인에 실패하였습니다."),
    FAILED_TO_FIND_AVALIABLE_RSA(400,"","일치하는 공개키가 없습니다"),
    REFRESH_TOKEN_DOESNT_MATCH(400,"","리프레시 토큰이 일치하지 않습니다"),
}