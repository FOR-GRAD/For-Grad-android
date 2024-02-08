package umc.com.mobile.project.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import umc.com.mobile.project.data.model.notice.NoticeResponse

interface NoticeApi {
	@GET("/departmentUrl/{trackNum}")
	fun getBoardUrl(@Path("trackNum") trackNum: Int): Call<NoticeResponse>
}