package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import umc.com.mobile.project.data.model.career.NonSubjectResponse

interface CareerApi {
	@GET("/careers/point?page=1")
	fun getNonSubject(): Call<NonSubjectResponse>
}