package umc.com.mobile.project.data.network.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import umc.com.mobile.project.data.model.DeleteLicense
import umc.com.mobile.project.data.model.plan.AddTimeRequest
import umc.com.mobile.project.data.model.plan.AddTimeResponse
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.CertificateLicenseRequest
import umc.com.mobile.project.data.model.plan.CertificateResponse
import umc.com.mobile.project.data.model.plan.ListTimeResponse
import umc.com.mobile.project.data.model.plan.PlanFreeRequest
import umc.com.mobile.project.data.model.plan.PlanFreeResponse
import umc.com.mobile.project.data.model.plan.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.SaveInfo
import umc.com.mobile.project.data.model.plan.SemesterTimeResponse
import umc.com.mobile.project.data.model.plan.UPlicenseResponse
import umc.com.mobile.project.data.model.plan.EditMemoRequest
import umc.com.mobile.project.data.model.plan.UpTimeResponse


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

    @POST("/plans/timetable")
    fun addTime(@Body addTimeRequest: AddTimeRequest): Call<AddTimeResponse>

    @GET("/plans/memo")
    fun getFreeInfo() : Call<PlanFreeResponse>

    @POST("/plans/memo")
    fun postFreeMemo(@Body request: PlanFreeRequest): Call<PlanFreeResponse>

    @GET("/plans/timetable")
    fun getUptime(@Query("grade") grade:Int, @Query("semseter") semester : Int):Call<UpTimeResponse>

    @PATCH("/plans/memo")
    fun editMemo(@Body editMemoRequest: EditMemoRequest) : Call<PlanFreeResponse>

    @PATCH("/plans/certifications")
    fun certificateLicense(@Body request: CertificateLicenseRequest) : Call<CertificateResponse>
    @DELETE("/plans/certifications")
    fun deleteLicense(@Query("certificateId") certificateId: Long): Call<DeleteLicense>











}