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
	val track1: String,
	val track2: String,
	val base64Image: String,
	val dday: Int,
	val trackRequirement1: String,
	val trackRequirement2: String,
	val note1: String,
	val note2: String,
	val futureTimeTableDto: Map<String, FutureTimeTableDto>
)

data class FutureTimeTableDto(
	val semester: SemesterInfo
)

data class SemesterInfo(
	val sumCredits: Int,
	val timeTableDtoList: List<TimeTableDto>
)

data class TimeTableDto(
	val majorType: String,
	val subject: String,
	val grades: Int
)