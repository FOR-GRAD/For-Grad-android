package umc.com.mobile.project.data.model.plan.timetable

data class DeleteTimeTableResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: List<DeleteResultDTO>
)

data class DeleteResultDTO(
	val subjectId: Int,
	val type: String,
	val name: String,
	val credit: Int
)