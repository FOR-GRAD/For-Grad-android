package umc.com.mobile.project.data.model.gradInfo

import com.google.gson.annotations.SerializedName

data class GeneralCompletionDto(
	val generalMap: GeneralMap
)

data class GeneralMap(
	@SerializedName("필수(기초)교양")
	val requiredBasicCourses: Map<String, String>,

	@SerializedName("토대교양")
	val foundationElectiveCourses: Map<String, String>,

	@SerializedName("소 계")
	val subtotal: Map<String, String>
)

data class MajorCompletionDto(
	val majorMap: MajorMap
)

data class MajorMap(
	@SerializedName("총합계")
	val total: List<String>,

	@SerializedName("제1트랙")
	val track1: List<String>,

	@SerializedName("제2트랙")
	val track2: List<String>,

	@SerializedName("부전공 부전공 - micro college")
	val minorAndMicroCollege: List<String>
)

data class Results(
	val generalCompletionDto: GeneralCompletionDto,
	val majorCompletionDto: MajorCompletionDto
)

data class CompletionResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: Results
)

/*data class CompletionResponse(
	@SerializedName("completionDtoMap") val completionDtoMap: CompletionDtoMap
)

data class CompletionDtoMap(
	@SerializedName("교양 - 이수한 학점 (필수이수학점)") val liberalArts: LiberalArts,
	@SerializedName("전공 - 이수한 학점 (필수이수학점)") val major: Major
)

data class LiberalArts(
	@SerializedName("필수(기초)교양") val requiredBasic: List<Course>,
	@SerializedName("토대교양") val fundamental: List<Course>,
	@SerializedName("소 계") val subtotal: List<Course>,
	@SerializedName("thead") val thead: List<String>
)

data class Major(
	@SerializedName("제1트랙") val firstTrack: List<String>,
	@SerializedName("제2트랙") val secondTrack: List<String>,
	@SerializedName("부전공 부전공 - micro college") val minor: List<String>,
	@SerializedName("thead") val thead: List<String>
)

data class Course(
	@SerializedName("교과목") val courseName: String,
	@SerializedName("학점") val credit: String
)*/
