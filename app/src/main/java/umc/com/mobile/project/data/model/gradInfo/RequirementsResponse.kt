package umc.com.mobile.project.data.model.gradInfo

data class CommonRequirementsDto(
	val registration: String,
	val grades: String,
	val point: String,
	val scores: String
)

data class TrackRequirementsDto(
	val track1: String,
	val track2: String,
	val trackRequirement1: String,
	val trackRequirement2: String,
	val note1: String,
	val note2: String
)

data class Result(
	val commonRequirmentsDto: CommonRequirementsDto,
	val trackRequirmentsDto: TrackRequirementsDto
)

data class RequirementsResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: Result
)
