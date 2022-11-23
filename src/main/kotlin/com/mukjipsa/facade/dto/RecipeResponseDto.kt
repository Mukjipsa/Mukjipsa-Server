import com.mukjipsa.facade.dto.RecipeDto

data class RecipeResponseDto(
        val data: RecipeDto,
        val message: String, // 북마한 레시피 조회 성공
        val status: Int, // 200
        val success: Boolean // true
)