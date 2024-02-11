package umc.com.mobile.project.ui.gradInfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.gradInfo.RequirementsResponse
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

	private val _register: MutableLiveData<String> = MutableLiveData()
	val register: LiveData<String>
		get() = _register

	private val _grades: MutableLiveData<String> = MutableLiveData()
	val grades: LiveData<String>
		get() = _grades

	private val _point: MutableLiveData<String> = MutableLiveData()
	val point: LiveData<String>
		get() = _point

	private val _scores: MutableLiveData<String> = MutableLiveData()
	val scores: LiveData<String>
		get() = _scores

	fun init(requirementsResponse: RequirementsResponse) {
		_register.postValue(requirementsResponse.result.commonRequirmentsDto.registration)
		_grades.postValue(requirementsResponse.result.commonRequirmentsDto.grades)
		_point.postValue(requirementsResponse.result.commonRequirmentsDto.point)
		_scores.postValue(requirementsResponse.result.commonRequirmentsDto.scores)
	}

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