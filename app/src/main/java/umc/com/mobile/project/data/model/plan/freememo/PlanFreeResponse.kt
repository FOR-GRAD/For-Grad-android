package umc.com.mobile.project.data.model.plan.freememo

data class PlanFreeResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: FreeInfo,
)

data class FreeInfo(
    val memo: String,
)

