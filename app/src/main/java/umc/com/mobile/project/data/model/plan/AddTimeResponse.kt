package umc.com.mobile.project.data.model.plan

data class AddTimeResponse(
val semesterDto: SemesterDto,
val subjectDtoList: List<SubjectDtoList>,
)

data class SemesterDto(
    val grade: Long,
    val semester: Long,
)

data class SubjectDtoList(
    val type: String,
    val name: String,
    val credit: Long,
)

