package umc.com.mobile.project.data.model.plan


data class SaveInfo(
    val name: String,
    val date: String
)


//typealias Root = List<SaveInfo>


data class SavelicenseRequest(
    val info : List<SaveInfo>,

)


//data class SavelicenseRequest(
//    val isSuccess: Boolean,
//    val code: Int,
//    val message: String,
//    val result: Root
//)
