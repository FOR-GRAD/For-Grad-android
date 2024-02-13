package umc.com.mobile.project.data.model.plan

import java.time.LocalDateTime

data class BringlicenseResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<BringResult>,
)

data class BringResult(
    val certificateId: Long,
    val createdAt: String,
)

