package umc.com.mobile.project.data.model.gradInfo

import com.google.gson.annotations.SerializedName

data class GradesDto(
	val classification: String,
	val subjectName: String,
	val credits: String,
	val grade: String,
	val track: String
)

data class GradesTotalDto(
	val appliedCredits: String,
	val acquiredCredits: String,
	val totalGrade: String,
	val averageGrade: String,
	val percentile: String
)

data class SemesterGradesDto(
	val gradesDtoList: List<GradesDto>,
	val gradesTotalDto: GradesTotalDto
)

data class MyGradesInfoListDto(
	@SerializedName("myGradesInfoListDto")
	val semesters: Map<String, SemesterGradesDto>
)

data class GradesResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: MyGradesInfoListDto
)
