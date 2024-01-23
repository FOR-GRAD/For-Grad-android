package umc.com.mobile.project.data.model.home

data class FutureTimeTableItem(
	val majorType: String,
	val subject: String,
	val grades: Int
)

data class UserInfo(
	val name: String,
	val id: Int,
	val department: String,
	val grade: String,
	val status: String,
	val message: String,
	val track1: String,
	val track2: String,
	val trackRequirement1: String,
	val trackRequirement2: String,
	val note1: String,
	val note2: String,
	val futureTimeTableDto: List<FutureTimeTableItem>
)

data class UserResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: UserInfo
)