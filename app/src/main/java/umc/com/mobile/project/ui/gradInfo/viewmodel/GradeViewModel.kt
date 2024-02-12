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
import umc.com.mobile.project.data.model.gradInfo.GradesTotalDto
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.GradInfoApi

class GradeViewModel : ViewModel() {
	private val gradInfoApiService = ApiClient.createService<GradInfoApi>()

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	private val _gradesInfo: MutableLiveData<GradesResponse?> = MutableLiveData()
	val gradesInfo: MutableLiveData<GradesResponse?>
		get() = _gradesInfo

	private val _semesters: MutableLiveData<Map<String, List<GradesDto>>>? = MutableLiveData()
	val semesters: LiveData<Map<String, List<GradesDto>>>?
		get() = _semesters

	private val _grades: MutableLiveData<Map<String, GradesTotalDto>> = MutableLiveData()
	val grades: LiveData<Map<String, GradesTotalDto>>
		get() = _grades

	private fun processRequiredBasicCourses(gradesResponse: GradesResponse) {
		val semestersMap = mutableMapOf<String, MutableList<GradesDto>>()
		val gradesTotalMap = mutableMapOf<String, GradesTotalDto>()
		val semestersDto = gradesResponse.result.semesters
		var count = 1

		semestersDto.let {
			for ((semester, semesterClasses) in it) {
				val gradesDtoList = semesterClasses.gradesDtoList
//				val semesterNum = semesterClasses.gradesDtoList.size - count
				val newKey = "$count 학기"
				val semesterList = semestersMap.getOrPut(newKey) { mutableListOf() }

				semesterList.addAll(gradesDtoList)
				count++
			}
			Log.d("Grade: semestersMap ", "$semestersMap")

			count = 1
		}

		semestersDto.let {
			for ((semester, semesterClasses) in it) {
				val gradeTotalDto = semesterClasses.gradesTotalDto
//				val semesterNum = count
				val newGradeKey = "$count 학기 성적"

				gradesTotalMap[newGradeKey] = gradeTotalDto

				count++
			}
			Log.d("Grade: gradeTotalList ", "$gradesTotalMap")

			count = 0
		}
		_semesters?.postValue(semestersMap)
		_grades.postValue(gradesTotalMap)
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

	fun onSemesterItemClick(semester: String) {
		// 클릭된 학기 정보를 사용하여 필요한 작업 수행
		// 예: 해당 학기 정보를 띄우는 메서드 호출 등
	}
}