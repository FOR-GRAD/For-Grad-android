package umc.com.mobile.project.ui.gradInfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.gradInfo.CompletionResponse
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.gradInfo.RequirementsResponse
import umc.com.mobile.project.data.model.home.UserResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.GradInfoApi

class GradInfoViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _requirementsInfo: MutableLiveData<RequirementsResponse?> = MutableLiveData()
	val requirementsInfo: MutableLiveData<RequirementsResponse?>
		get() = _requirementsInfo

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	fun getGradRequirementsInfo() {
		gradInfoApiService.getRequirements().enqueue(object : Callback<RequirementsResponse> {
			override fun onResponse(
				call: Call<RequirementsResponse>,
				response: Response<RequirementsResponse>
			) {
				if (response.isSuccessful) {
					val userResponse = response.body()
					if (userResponse != null) {
						_requirementsInfo.postValue(userResponse)
						Log.d("gradInfo", "${response.body()}")
					} else {
						_error.postValue("서버 응답이 올바르지 않습니다.")
					}
				} else {
					_error.postValue("사용자 정보를 가져오지 못했습니다.")
					try {
						throw response.errorBody()?.string()?.let {
							RuntimeException(it)
						} ?: RuntimeException("Unknown error")
					} catch (e: Exception) {
						Log.e("gradInfo", "requirements API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<RequirementsResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "requirements: ${t.message}")
			}
		})
	}

}