package umc.com.mobile.project.data.model.plan

import java.time.LocalDate


data class SaveInfo(
    val name: String,
    val date: String
)

data class SavelicenseRequest(
    val info: List<SaveInfo>
)



