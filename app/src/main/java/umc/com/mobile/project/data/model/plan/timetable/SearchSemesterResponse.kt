package umc.com.mobile.project.data.model.plan.timetable

data class SearchSemesterResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: List<semesterResult>,
)

data class semesterResult(
    val hakkiNum: String,
    val hakkiText: String,
)

