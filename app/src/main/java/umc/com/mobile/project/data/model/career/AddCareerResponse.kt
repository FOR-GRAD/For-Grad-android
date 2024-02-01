package umc.com.mobile.project.data.model.career

data class AddCareerResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: AddResult,
)

data class AddResult(
    val id: Long,
    val title: String,
)