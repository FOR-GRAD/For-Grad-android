import com.google.gson.annotations.SerializedName

data class CompletionResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: Result
)

data class Result(
	@SerializedName("titleList")
	val titleList: List<String>,
	@SerializedName("completionDtoMap")
	val completionDtoMap: List<Map<String, List<String>>>
)

data class CompletionDtoMap(
	@SerializedName("필수교양(기초)")
	val requiredBasicCourses: List<String>,
	@SerializedName("필수교양(소양)")
	val requiredGeneralCourses: List<String>,
	@SerializedName("선택필수교양")
	val electiveRequiredCourses: List<String>,
	@SerializedName("소 계")
	val subtotal: List<String>,
	@SerializedName("제1트랙")
	val track1: List<String>,
	@SerializedName("제2트랙")
	val track2: List<String>,
	@SerializedName("부전공 부전공 - micro college")
	val minor: List<String>
)
