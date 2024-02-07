package umc.com.mobile.project.data.model.career

data class CareerDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: DetailResult,
)

data class DetailResult(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val startDate: String,
    val endDate: String,
    val fileUrls: List<String>,
    val award: String,
    val certificationType: String,
    val volunteerHour: Long,
)