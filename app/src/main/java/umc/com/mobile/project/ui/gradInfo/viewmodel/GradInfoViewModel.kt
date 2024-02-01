package umc.com.mobile.project.ui.gradInfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.gradInfo.CompletionResponse
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.gradInfo.RequirementsResponse
import umc.com.mobile.project.data.model.gradInfo.SemesterGradesDto
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.GradInfoApi
import umc.com.mobile.project.data.network.api.HomeApi

class GradInfoViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _requirementsInfo: MutableLiveData<RequirementsResponse?> = MutableLiveData()
	val requirementsInfo: MutableLiveData<RequirementsResponse?>
		get() = _requirementsInfo

	private val _gradesInfo: MutableLiveData<GradesResponse?> = MutableLiveData()
	val gradesInfo: MutableLiveData<GradesResponse?>
		get() = _gradesInfo

	private val _userSemester: MutableLiveData<String> = MutableLiveData()
	val userSemester: LiveData<String>
		get() = _userSemester

	private val _userClassification: MutableLiveData<String> = MutableLiveData()
	val userClassification: LiveData<String>
		get() = _userClassification

	private val _userSubjectName: MutableLiveData<String> = MutableLiveData()
	val userSubjectName: LiveData<String>
		get() = _userSubjectName

	private val _userCredits: MutableLiveData<String> = MutableLiveData()
	val userCredits: LiveData<String>
		get() = _userCredits

	private val _userGrade: MutableLiveData<String> = MutableLiveData()
	val userGrade: LiveData<String>
		get() = _userGrade

	private val _userTrack: MutableLiveData<String> = MutableLiveData()
	val userTrack: LiveData<String>
		get() = _userTrack

	private val _completionInfo: MutableLiveData<CompletionResponse?> = MutableLiveData()
	val completionInfo: MutableLiveData<CompletionResponse?>
		get() = _completionInfo

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	private var dataList = mutableListOf<SemesterGradesDto>()

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

	fun getGradeInfo() {
		gradInfoApiService.getGrades().enqueue(object : Callback<GradesResponse> {
			override fun onResponse(
				call: Call<GradesResponse>,
				response: Response<GradesResponse>
			) {
				if (response.isSuccessful) {
					val userResponse = response.body()
					if (userResponse != null) {
						_gradesInfo.postValue(userResponse)
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
						Log.e("gradInfo", "grade API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<GradesResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "grade: ${t.message}")
			}
		})
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

//	fun processApiResponse(apiResponseString: SemesterGradesDto?) {
//		viewModelScope.launch {
//			val gson = Gson()
//
//			apiResponseString?.let {
//				try {
//					// JSON 형식으로 파싱
//					val gradesResponse: GradesResponse = gson.fromJson(it, GradesResponse::class.java)
//
//					// 학기 정보를 받아와서 dataList에 추가
//					for ((_, semesterGradesDto) in gradesResponse.result.semesters) {
//						dataList.add(semesterGradesDto)
//						Log.d("Grade", dataList[0].toString())
//					}
//				} catch (e: JsonSyntaxException) {
//					Log.e("Grade", "JSON 파싱 오류: ${e.message}")
//				}
//			}
//		}
//	}
}