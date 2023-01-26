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
    INVALID_DATA_ACCESS_API_USAGE_EXCEPTION(500, "DSL001", "QueryDSL Error 발생")

    ;
}