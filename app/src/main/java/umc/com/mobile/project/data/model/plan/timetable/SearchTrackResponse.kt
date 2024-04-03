package umc.com.mobile.project.data.model.plan.timetable

data class PlanTrackResponse(
	val isSuccess: Boolean,
	val code: String,
	val message: String,
	val result: List<TrackResult>,
)

data class TrackResult(
    val trackCode: String,
    val trackName: String,
)

