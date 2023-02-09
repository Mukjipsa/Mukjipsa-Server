package com.mukjipsa.contoroller


import com.mukjipsa.service.dto.KakaoProfile
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "kakaoClient", url="https://kapi.kakao.com")
interface KakaoClient {

    @GetMapping("/v2/user/me")
    fun getUserInfo(@RequestHeader("Authorization") accessToken: String): KakaoProfile
}