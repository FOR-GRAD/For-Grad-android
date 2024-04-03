package umc.com.mobile.project.data.model.plan.timetable

data class PutTimeTableResponse (
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: DeleteResult
)

data class DeleteResult(
	val updateResponseDtos: List<UpdateResponseDto>
)

data class UpdateResponseDto(
	val subjectId: Int,
	val updatedAt: String
)