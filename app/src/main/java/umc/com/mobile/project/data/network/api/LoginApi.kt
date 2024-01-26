package umc.com.mobile.project.data.network.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import umc.com.mobile.project.data.model.login.LoginResponse

interface LoginApi {

	@FormUrlEncoded
	@POST("/login")
	fun login(
		@Field("id") id: String,
		@Field("passwd") passwd: String
	): Call<LoginResponse>

	@POST("/logout")
	fun logout(): Call<ResponseBody>
}