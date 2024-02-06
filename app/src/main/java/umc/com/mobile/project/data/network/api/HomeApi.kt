package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import umc.com.mobile.project.data.model.home.GradDateResponse
import umc.com.mobile.project.data.model.home.UpdateGradDateRequest
import umc.com.mobile.project.data.model.home.UpdateGradDateResponse
import umc.com.mobile.project.data.model.home.UserResponse

interface HomeApi {
	@GET("/home")
	fun getUserInfo(): Call<UserResponse>

	@GET("/grad")
	fun getDateInfo(): Call<GradDateResponse>

	@PATCH("/grad")
	fun updateDateInfo(
		@Body request: UpdateGradDateRequest,
	): Call<UpdateGradDateResponse>
}