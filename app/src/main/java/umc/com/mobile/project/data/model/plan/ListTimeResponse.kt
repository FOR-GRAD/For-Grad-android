package umc.com.mobile.project.data.model.plan



data class ListTimeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<TimeResult>,
)

data class TimeResult(
    val searchGrade: String,
    val searchType: String,
    val searchName: String,
    val searchCredit: String,
)
