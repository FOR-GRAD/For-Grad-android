package umc.com.mobile.project.data.model.home
data class UserResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: Result
)

data class Result(
	val name: String,
	val id: Int,
	val department: String,
	val grade: String,
	val status: String,
	val message: String,
	val base64Image: String,
	val dday: Int,
	val futureTimeTableDto: Map<String, FutureTimeTableDto>
)

data class FutureTimeTableDto(
	val sumCredits: Int,
	val timeTableDtoList: List<TimeTableDto>
)

data class TimeTableDto(
	val majorType: String,
	val subject: String,
	val grades: Int
)