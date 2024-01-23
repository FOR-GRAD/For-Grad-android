package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import umc.com.mobile.project.data.model.home.UserResponse
import umc.com.mobile.project.data.model.login.LoginResponse

interface HomeApi {
	@GET("/home")
	fun getUserInfo(): Call<UserResponse>
}