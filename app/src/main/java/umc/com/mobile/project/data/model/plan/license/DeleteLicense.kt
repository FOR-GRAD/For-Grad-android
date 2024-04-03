package umc.com.mobile.project.data.model.plan.license

data class DeleteLicense(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<DeleteItem>
)

data class DeleteItem(
    val certificateId: Long,
    val name: String,
    val date: String
)
