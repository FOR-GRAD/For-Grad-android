package umc.com.mobile.project.ui.board.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.HomeApi
import umc.com.mobile.project.data.network.api.NoticeApi

class BoardViewModel : ViewModel() {

	private val noticeApiService = ApiClient.createService<NoticeApi>()

	fun getTrack1() {

	}
}