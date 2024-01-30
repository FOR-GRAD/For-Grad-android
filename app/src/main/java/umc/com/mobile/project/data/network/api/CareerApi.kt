package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import umc.com.mobile.project.data.model.career.CategoryListResponse
import umc.com.mobile.project.data.model.career.NonSubjectResponse

interface CareerApi {
	@GET("/careers/point?page=1")
	fun getNonSubject(): Call<NonSubjectResponse>
	@GET("/career-list/volunteers")
	fun getVolunteerList(): Call<CategoryListResponse>
	@GET("/careers/point")
	fun getNonSubjectList(@Query("page") page: Int): Call<NonSubjectResponse>
}