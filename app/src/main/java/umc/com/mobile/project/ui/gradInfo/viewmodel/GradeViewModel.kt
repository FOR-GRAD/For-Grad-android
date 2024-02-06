package umc.com.mobile.project.ui.gradInfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.gradInfo.GradesDto
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.GradInfoApi

class GradeViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

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

	private val _userAppliedCredit: MutableLiveData<String> = MutableLiveData()
	val userAppliedCredit: LiveData<String>
		get() = _userAppliedCredit

	private val _userAcquiredCredit: MutableLiveData<String> = MutableLiveData()
	val userAcquiredCredit: LiveData<String>
		get() = _userAcquiredCredit

	private val _userAverageTotal: MutableLiveData<String> = MutableLiveData()
	val userAverageTotal: LiveData<String>
		get() = _userAverageTotal

	private val _userAverageGrade: MutableLiveData<String> = MutableLiveData()
	val userAverageGrade: LiveData<String>
		get() = _userAverageGrade

	private val _userPercentile: MutableLiveData<String> = MutableLiveData()
	val userPercentile: LiveData<String>
		get() = _userPercentile

	private val _gradesInfo: MutableLiveData<GradesResponse?> = MutableLiveData()
	val gradesInfo: MutableLiveData<GradesResponse?>
		get() = _gradesInfo

	private val _semesters: MutableLiveData<Map<String, List<GradesDto>>>? = MutableLiveData()
	val semesters: LiveData<Map<String, List<GradesDto>>>?
		get() = _semesters


		private fun processRequiredBasicCourses(gradesResponse: GradesResponse) {
			val semestersMap = mutableMapOf<String, List<GradesDto>>()
			val semesterList = mutableListOf<String>()
			val semestersDto = gradesResponse.result.semesters
			var count = 1

			semestersDto.let {
				for ((semester, semesterClasses) in it) {
					for (i in 0 until semesterClasses.gradesDtoList.size) {
						val newKey = "$count 학기"
						semestersMap[newKey] = listOf(semesterClasses.gradesDtoList[i])

						semesterList.add(i, semestersMap[newKey]!!.toString())
						count++
					}
				}
				Log.d("Grade: semestersMap ", "$semestersMap")
				count = 0
			}
			_semesters?.postValue(semestersMap)
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
						processRequiredBasicCourses(userResponse)
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
}