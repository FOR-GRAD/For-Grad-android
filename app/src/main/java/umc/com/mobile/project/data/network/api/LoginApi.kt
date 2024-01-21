package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.com.mobile.project.data.model.login.LoginRequest
import umc.com.mobile.project.data.model.login.LoginResponse

interface LoginApi {
	@POST("/login")
	fun login(@Body request: LoginRequest): Call<LoginResponse>
}