package com.mukjipsa.mukjipsa.common.exception

class UserNotFoundException(override val message: String): RuntimeException(message) {
}