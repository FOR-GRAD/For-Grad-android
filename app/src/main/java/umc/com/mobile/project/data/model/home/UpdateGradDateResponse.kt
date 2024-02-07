package umc.com.mobile.project.data.model.home

data class UpdateGradDateResponse (
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: UpdateDateInfo
)

data class UpdateDateInfo (
	val gradDate: String,
	val message: String,
)