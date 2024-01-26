package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import umc.com.mobile.project.data.model.gradInfo.CompletionResponse
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.gradInfo.RequirementsResponse

interface GradInfoApi {
	@GET("/gradinfo/requirments")
	fun getRequirements(): Call<RequirementsResponse>

	@GET("/gradinfo/grades")
	fun getGrades(): Call<GradesResponse>

	@GET("/gradinfo/completion")
	fun getCompletion(): Call<CompletionResponse>
}