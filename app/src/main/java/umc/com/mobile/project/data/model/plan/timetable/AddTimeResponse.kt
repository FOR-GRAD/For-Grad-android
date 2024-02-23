package umc.com.mobile.project.data.model.plan.timetable

data class AddTimeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: AddResponseResult
)

data class AddResponseResult(
    val addResponseDtos: List<AddResponseDto>
)

data class AddResponseDto(
    val subjectId: Long,
    val createdAt: String
)
