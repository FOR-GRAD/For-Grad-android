package umc.com.mobile.project.data.model.plan.timetable.request

data class AddTimeRequest(
	val semesterDto: SemesterDto,
	val subjectDtoList: List<SubjectDtoList>,
)

data class SemesterDto(
    val grade: Int,
    val semester: Int,
)

data class SubjectDtoList(
    val type: String,
    val name: String,
    val credit: String,
)

