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

data class MyGradesInfoListDto(
	@SerializedName("2023 학년도 1 학기")
	val semester2023: SemesterGradesDto,

	@SerializedName("2021 학년도 2 학기")
	val semester2021: SemesterGradesDto,

	@SerializedName("2018 학년도 1 학기")
	val semester2018: SemesterGradesDto,

	@SerializedName("2022 학년도 2 학기")
	val semester2022: SemesterGradesDto,

	@SerializedName("2022 학년도 1 학기")
	val semester2022_1: SemesterGradesDto,

	@SerializedName("2021 학년도 1 학기")
	val semester2021_1: SemesterGradesDto,

	@SerializedName("2018 학년도 2 학기")
	val semester2018_2: SemesterGradesDto
)

data class SemesterGradesDto(
	val gradesDtoList: List<GradesDto>,
	val gradesTotalDto: GradesTotalDto
)

data class GradesResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: MyGradesInfoListDto
)
