package umc.com.mobile.project.ui.gradInfo.viewmodel

import CompletionResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.GradInfoApi

class CompletionStateViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	private val _completionInfo: MutableLiveData<CompletionResponse?> = MutableLiveData()
	val completionInfo: MutableLiveData<CompletionResponse?>
		get() = _completionInfo

	fun getCompletionInfo() {
		gradInfoApiService.getCompletion().enqueue(object : Callback<CompletionResponse> {
			override fun onResponse(
				call: Call<CompletionResponse>,
				response: Response<CompletionResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_completionInfo.postValue(response.body())
//						processRequiredBasicCourses(response.body()!!)
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
						Log.e("gradInfo", "completion API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<CompletionResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "completion: ${t.message}")
			}
		})
	}

}