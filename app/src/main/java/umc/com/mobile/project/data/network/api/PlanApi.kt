package umc.com.mobile.project.data.network.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.ListTimeResponse
import umc.com.mobile.project.data.model.plan.PlanFreeRequest
import umc.com.mobile.project.data.model.plan.PlanFreeResponse
import umc.com.mobile.project.data.model.plan.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.SaveInfo
import umc.com.mobile.project.data.model.plan.SemesterTimeResponse
import umc.com.mobile.project.data.model.plan.UPlicenseResponse
import umc.com.mobile.project.data.model.plan.EditMemoRequest


interface PlanApi {
    @GET("plans/certifications")
    fun getUPlicense(): Call<UPlicenseResponse>


    @POST("plans/certifications")
    fun saveLicense(@Body request: List<SaveInfo>): Call<BringlicenseResponse>




    @GET("/plans/timetable/searchSubject")
    fun getListTime(@Query("hakki") hakki:String, @Query("track") track : String) : Call<ListTimeResponse>

    @GET("/plans/timetable/searchTrack")
    fun getTrackInfo(@Query("hakki") hakki: String) : Call<PlanTrackResponse>

    @GET("/plans/timetable/searchHakki")
    fun getSemesterInfo():Call<SemesterTimeResponse>

    @GET("/plans/memo")
    fun getFreeInfo() : Call<PlanFreeResponse>

    @POST("/plans/memo")
    fun postFreeMemo(@Body request: PlanFreeRequest): Call<PlanFreeResponse>

    @PATCH("/plans/memo")
    fun editMemo(@Body editMemoRequest: EditMemoRequest) : Call<PlanFreeResponse>






}