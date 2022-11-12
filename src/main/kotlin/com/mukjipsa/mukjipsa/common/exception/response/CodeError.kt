package com.mukjipsa.mukjipsa.common.exception.response

enum class CodeError(
    val status: Int, /* 계속 추가 가능 */
    val code: String,
    val message: String
) {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " 입력 값이 잘못되었습니다."),
    METHOD_NOT_ALLOWED(405, "C002", " 해당 Method는 사용 할 수 없습니다."),
    ENTITY_NOT_FOUND(400, "C003", " Entity를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러"),
    INVALID_TYPE_VALUE(400, "C005", " 값 타입이 잘못되었습니다."),
    HANDLE_ACCESS_DENIED(403, "C006", " 접근이 거부되었습니다."),
    HANDLE_NOT_FOUND(404, "C007", " 요청을 찾을 수 없습니다."),  // Header
    HTTP_CLIENT_EXCEPTION(403, "C008", "HTTP Client Error"),

    // Header Issue
    HEADER_VALUE_NULL(400, "H001", "요청 헤더에 'apiKey' 필드가 없습니다."),
    INVALID_HEADER_VALUE(403, "H002", "요청 헤더에 'apiKey' 인증이 실패했습니다."),  // Date

    // Date Issue
    INVALID_DATE_RANGE(400, "D001", "시작날짜와 종료날짜가 올바르지 않습니다."),
    DATE_RANGE_TOO_LONG(400, "D001", "조회 기간은 1년을 넘을 수 없습니다."),  // Bucket4j

    // Internal Issue
    NPE(501, "NPE001", "Null Point Exception 발생"),
    INVALID_DATA_ACCESS_API_USAGE_EXCEPTION(500, "DSL001", "QueryDSL Error 발생")

    ;
}