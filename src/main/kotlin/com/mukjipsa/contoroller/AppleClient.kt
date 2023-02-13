package com.mukjipsa.contoroller

import com.mukjipsa.service.dto.ApplePublicKeyResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name="appleClient", url = "https://appleid.apple.com/auth")
interface AppleClient {

    @GetMapping("/keys")
    fun getAppleAuthPublicKey(): ApplePublicKeyResponse
}