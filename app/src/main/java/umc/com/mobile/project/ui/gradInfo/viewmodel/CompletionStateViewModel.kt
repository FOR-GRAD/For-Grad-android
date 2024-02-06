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

class CompletionStateViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	private val _completionInfo: MutableLiveData<CompletionResponse?> = MutableLiveData()
	val completionInfo: MutableLiveData<CompletionResponse?>
		get() = _completionInfo

	private val _requiredBasicCourses: MutableLiveData<Map<String, String>>? = MutableLiveData()
	val requiredBasicCourses: LiveData<Map<String, String>>?
		get() = _requiredBasicCourses

	private val _foundationElectiveCourses: MutableLiveData<Map<String, String>>? = MutableLiveData()
	val foundationElectiveCourses: LiveData<Map<String, String>>?
		get() = _foundationElectiveCourses

	private fun processRequiredBasicCourses(completionResponse: CompletionResponse) {
		val requiredBasicCoursesMap = mutableMapOf<String, String>()
		val foundationElectiveCoursesMap = mutableMapOf<String, String>()

		val requiredBasicCourses = completionResponse.result.generalCompletionDto.generalMap.requiredBasicCourses
		val foundationElectiveCourses = completionResponse.result.generalCompletionDto.generalMap.foundationElectiveCourses

		requiredBasicCourses.let {
			for ((courseName, courseStatus) in it) {
				requiredBasicCoursesMap[courseName] = courseStatus
				Log.d("Completion: requiredBasicCoursesMap ", "$courseName : $courseStatus")
			}
		}

		foundationElectiveCourses.let {
			for ((courseName, courseStatus) in it) {
				foundationElectiveCoursesMap[courseName] = courseStatus
				Log.d("Completion: foundationElectiveCoursesMap ", "$courseName : $courseStatus")
			}
		}
		_requiredBasicCourses?.postValue(requiredBasicCoursesMap)
		_foundationElectiveCourses?.postValue(foundationElectiveCoursesMap)
	}

	fun getCompletionInfo() {
		gradInfoApiService.getCompletion().enqueue(object : Callback<CompletionResponse> {
			override fun onResponse(
				call: Call<CompletionResponse>,
				response: Response<CompletionResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_completionInfo.postValue(response.body())
						processRequiredBasicCourses(response.body()!!)
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