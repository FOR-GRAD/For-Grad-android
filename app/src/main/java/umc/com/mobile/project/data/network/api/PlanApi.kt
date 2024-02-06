package umc.com.mobile.project.data.network.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.SavelicenseRequest
import umc.com.mobile.project.data.model.plan.UPlicenseResponse


interface PlanApi {
    @GET("/plans/certifications")
    fun getUPlicense(): Call<UPlicenseResponse>


    @POST("/plans/certifications")
    fun getSavelicense(request: SavelicenseRequest): Call<SavelicenseRequest>

    @POST("/plans/certifications")
    fun getBringlicense(@Body request:SavelicenseRequest):Call<BringlicenseResponse>

}