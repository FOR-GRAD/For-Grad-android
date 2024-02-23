package umc.com.mobile.project.data.model.plan.timetable.request

data class PutTimeTableRequest(
	val semesterDto: Semester,
	val subjectDtoList: List<SubjectDTO>
)

data class Semester(
	val grade: Int,
	val semester: Int
)

data class SubjectDTO(
	val subjectId: Int,
	val type: String,
	val name: String,
	val credit: Int
)

