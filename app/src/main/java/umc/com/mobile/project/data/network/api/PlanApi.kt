package umc.com.mobile.project.data.network.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import umc.com.mobile.project.data.model.plan.license.DeleteLicense
import umc.com.mobile.project.data.model.plan.timetable.request.AddTimeRequest
import umc.com.mobile.project.data.model.plan.timetable.AddTimeResponse
import umc.com.mobile.project.data.model.plan.license.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.license.CertificateLicenseRequest
import umc.com.mobile.project.data.model.plan.license.CertificateResponse
import umc.com.mobile.project.data.model.plan.timetable.DeleteTimeTableResponse
import umc.com.mobile.project.data.model.plan.freememo.PlanFreeRequest
import umc.com.mobile.project.data.model.plan.freememo.PlanFreeResponse
import umc.com.mobile.project.data.model.plan.timetable.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.license.SaveInfo
import umc.com.mobile.project.data.model.plan.license.UPlicenseResponse
import umc.com.mobile.project.data.model.plan.freememo.EditMemoRequest
import umc.com.mobile.project.data.model.plan.timetable.request.PutTimeTableRequest
import umc.com.mobile.project.data.model.plan.timetable.PutTimeTableResponse
import umc.com.mobile.project.data.model.plan.timetable.SearchSemesterResponse
import umc.com.mobile.project.data.model.plan.timetable.SearchSubjectResponse
import umc.com.mobile.project.data.model.plan.timetable.TimeTableResponse


interface PlanApi {
	/**
	 * 시간표
	 */
	@POST("/plans/timetable")
	fun addTime(@Body addTimeRequest: AddTimeRequest): Call<AddTimeResponse>

	@GET("/plans/timetable")
	fun getUptime(
		@Query("grade") grade: Int,
		@Query("semester") semester: Int
	): Call<TimeTableResponse>

	@PUT("/plans/timetable")
	fun putTimeTable(
		@Body request: PutTimeTableRequest
	): Call<PutTimeTableResponse>

	@DELETE("/plans/timetable")
	fun deleteTimeTable(
		@Query("grade") grade: Int,
		@Query("semester") semester: Int,
		@Query("subjectId") subjectId: Int
	): Call<DeleteTimeTableResponse>

	/**
	 * 시간표 검색
	 */
	@GET("/plans/timetable/searchSubject")
	fun getListTime(
		@Query("hakki") hakki: String,
		@Query("track") track: String
	): Call<SearchSubjectResponse>

	@GET("/plans/timetable/searchTrack")
	fun getTrackInfo(@Query("hakki") hakki: String): Call<PlanTrackResponse>

	@GET("/plans/timetable/searchHakki")
	fun getSemesterInfo(): Call<SearchSemesterResponse>

	/**
	 * 자유 메모
	 */
	@GET("/plans/memo")
	fun getFreeInfo(): Call<PlanFreeResponse>

	@POST("/plans/memo")
	fun postFreeMemo(@Body request: PlanFreeRequest): Call<PlanFreeResponse>

	@PATCH("/plans/memo")
	fun editMemo(@Body editMemoRequest: EditMemoRequest): Call<PlanFreeResponse>

	/**
	 * 자격증
	 */
	@GET("plans/certifications")
	fun getUPlicense(): Call<UPlicenseResponse>

	@POST("plans/certifications")
	fun saveLicense(@Body request: List<SaveInfo>): Call<BringlicenseResponse>

	@PATCH("/plans/certifications")
	fun certificateLicense(@Body request: List<CertificateLicenseRequest>): Call<CertificateResponse>

	@DELETE("/plans/certifications")
	fun deleteLicense(@Query("certificateId") certificateId: Long): Call<DeleteLicense>
}