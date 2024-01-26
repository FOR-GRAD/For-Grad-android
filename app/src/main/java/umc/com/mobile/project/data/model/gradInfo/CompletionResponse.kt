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

