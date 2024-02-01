package umc.com.mobile.project.data.model.career

data class CategoryListResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: CareerResults,
)

data class CareerResults(
    val activityWithAccumulatedHours: List<ActivityWithAccumulatedHour>,
)

data class ActivityWithAccumulatedHour(
    val id: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val award: String,
    val certificationType: String,
    val volunteerHour: Long,
    val accum: Long,
    val reindex: Long,
)

