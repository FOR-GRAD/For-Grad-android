package umc.com.mobile.project.data.network.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import umc.com.mobile.project.data.model.career.AddCareerResponse
import umc.com.mobile.project.data.model.career.CareerDetailResponse
import umc.com.mobile.project.data.model.career.CategoryListResponse
import umc.com.mobile.project.data.model.career.NonSubjectResponse

interface CareerApi {
    @GET("/careers/point?page=1")
    fun getNonSubject(): Call<NonSubjectResponse>

    @GET("/career-list/{category}")
    fun getCareerList(@Path("category") category: String): Call<CategoryListResponse>

    @GET("/careers/point")
    fun getNonSubjectList(@Query("page") page: Int): Call<NonSubjectResponse>

    @Multipart
    @POST("/activity")
    fun addCareer(
        @Part image: List<MultipartBody.Part>,
        @Part("requestDto") requestDto: RequestBody
    ): Call<AddCareerResponse>

    @GET("/career-detail")
    fun getCareerDetail(@Query("activityId") activityId: Long): Call<CareerDetailResponse>
}