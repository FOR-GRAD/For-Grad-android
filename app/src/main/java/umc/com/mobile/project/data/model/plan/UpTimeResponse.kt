package umc.com.mobile.project.data.model.plan

data class UpTimeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<upTimeResult>,
)

data class upTimeResult(
    val subjectId: Long,
    val type: String,
    val name: String,
    val credit: Long,
)

