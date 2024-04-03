package umc.com.mobile.project.data.model.plan.timetable

data class TimeTableResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: List<UpTimeResult>,
)

data class UpTimeResult(
    val subjectId: Int,
    val type: String,
    val name: String,
    val credit: Int,
)

