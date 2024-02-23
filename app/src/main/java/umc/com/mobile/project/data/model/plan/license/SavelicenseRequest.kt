package umc.com.mobile.project.data.model.plan.license


data class SaveInfo(
    val name: String,
    val date: String
)

data class SavelicenseRequest(
    val info: List<SaveInfo>
)



