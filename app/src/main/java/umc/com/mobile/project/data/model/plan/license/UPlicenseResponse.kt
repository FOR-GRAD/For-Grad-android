package umc.com.mobile.project.data.model.plan.license

data class UPlicenseResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: List<Result>,
)

data class Result(
    val certificateId: Long,
    val name: String,
    val date: String,
)
