package umc.com.mobile.project.data.model.login

data class LoginResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: LoginResult?
)

data class LoginResult(val message: String)
