package com.mukjipsa.mukjipsa.service.dto

data class KakaoProfile(
    var id: String? = null,
    var connected_at: String? = null,
    var properties: Properties? = null,
    var kakao_account: KakaoAccount? = null
) {
    data class Properties(
        var nickname: String? = null,
        var profile_image: String? = null,
        var thumbnail_image: String? = null,
    )

    data class KakaoAccount(
        var profile_nickname_needs_agreement: Boolean? = null,
        var profile_image_needs_agreement: Boolean? = null,
        var profile: Profile? = null,
        var has_email: Boolean? = null,
        var email_needs_agreement: Boolean? = null,
        var is_email_valid: Boolean? = null,
        var is_email_verified: Boolean? = null,
        var email: String? = null,
    )

    data class Profile(
        var nickname: String? = null,
        var thumbnail_image_url: String? = null,
        var profile_image_url: String? = null,
        var is_default_image: Boolean? = null,
    )
}
