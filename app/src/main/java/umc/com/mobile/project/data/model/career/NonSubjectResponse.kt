package umc.com.mobile.project.data.model.career

data class NonSubjectResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result,
)

data class Result(
    val listSize: Long,
    val pageSize: Long,
    val pointSummaryDto: PointSummaryDto,
    val pointDtoList: List<PointDtoList>,
)

data class PointSummaryDto(
    val semesterPoints: String,
    val carryoverPoints: String,
    val accumulatedPoints: String,
    val graduationPoints: String,
)

data class PointDtoList(
    val title: String,
    val rewardPoints: String,
    val accumulatedPoints: String,
    val date: String,
)
