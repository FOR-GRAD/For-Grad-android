package umc.com.mobile.project.data.model.login

data class LoginResponse(
	val isSuccess: Boolean,
	val code: Int,
	val message: String,
	val result: Any?
)
