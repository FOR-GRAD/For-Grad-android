package umc.com.mobile.project.data.model.plan

data class CertificateResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<CertificateUpdateInfo>
)

data class CertificateUpdateInfo(
    val certificateId: Long,
    val updatedAt: String
)
