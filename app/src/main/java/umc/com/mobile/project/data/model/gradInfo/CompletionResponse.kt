import com.google.gson.annotations.SerializedName

data class CompletionResponse(
	@SerializedName("isSuccess")
	val isSuccess: Boolean,
	@SerializedName("code")
	val code: String,
	@SerializedName("message")
	val message: String,
	@SerializedName("result")
	val result: Result
)

data class Result(
	@SerializedName("titleList")
	val titleList: TitleList,
	@SerializedName("completionDtoMap")
	val completionDtoMap: List<Map<String, List<String>>>,
	@SerializedName("majorRequirements")
	val majorRequirements: MajorRequirements
)

data class TitleList(
	@SerializedName("titleList")
	val titles: List<String>
)

data class CompletionDto(
	@SerializedName("필수교양(기초)")
	val requiredBasic: List<String>,
	@SerializedName("필수교양(소양)")
	val requiredCultural: List<String>,
	@SerializedName("선택필수교양")
	val optionalRequired: List<String>,
	@SerializedName("소 계")
	val optionalRequiredSubtotal: List<String>
)

data class MajorRequirements(
	@SerializedName("제1트랙")
	val track1: List<String>,
	@SerializedName("제2트랙")
	val track2: List<String>,
	@SerializedName("부전공 부전공 - micro college")
	val minorMicroCollege: List<String>
)
