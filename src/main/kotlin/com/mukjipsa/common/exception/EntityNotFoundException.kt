package com.mukjipsa.common.exception

import com.mukjipsa.common.exception.response.ErrorCode

class EntityNotFoundException(message: String?) : BusinessException(message, ErrorCode.ENTITY_NOT_FOUND)