package umc.com.mobile.project.data.model.home

data class GradDateResponse (
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: DateInfo
)

data class DateInfo (
	val nowDate: String,
	val gradDate: String,
	val message: String,
	val dday: Int,
)