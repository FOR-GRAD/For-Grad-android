package umc.com.mobile.project.ui.gradInfo.viewmodel

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import kotlin.coroutines.coroutineContext

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

	private val _selectedSemester: MutableLiveData<String> = MutableLiveData()
	val selectedSemester: LiveData<String>
		get() = _selectedSemester

	private val _selectedSemesterGrade: MutableLiveData<String> = MutableLiveData()
	private val selectedSemesterGrade: LiveData<String>
		get() = _selectedSemesterGrade

	private val _totalAverage: MutableLiveData<Double> = MutableLiveData()
	val totalAverage: LiveData<Double>
		get() = _totalAverage

	private val _isNullCheckGrade: MutableLiveData<Boolean> = MutableLiveData()
	val isNullCheckGrade: LiveData<Boolean>
		get() = _isNullCheckGrade

	val selectedSemesterGradeAndGrades: MediatorLiveData<Pair<String?, Map<String, GradesTotalDto>?>> = MediatorLiveData()

	init {
		selectedSemesterGradeAndGrades.addSource(selectedSemesterGrade) { selectedGrade ->
			selectedSemesterGradeAndGrades.value = Pair(selectedGrade, _grades.value)
		}
		selectedSemesterGradeAndGrades.addSource(_grades) { grades ->
			selectedSemesterGradeAndGrades.value = Pair(_selectedSemesterGrade.value, grades)
		}
	}

	private fun processRequiredBasicCourses(gradesResponse: GradesResponse) {
		val semestersMap = mutableMapOf<String, MutableList<GradesDto>>()
		val gradesTotalMap = mutableMapOf<String, GradesTotalDto>()
		val semestersDto = gradesResponse.result.semesters
		var count = 1

		semestersDto.let {
			for ((semester, semesterClasses) in it) {
				val gradesDtoList = semesterClasses.gradesDtoList
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
		_selectedSemester.postValue(semester)
	}

	fun onSemesterGradeItemClick(grade: String) {
		_selectedSemesterGrade.postValue(grade)
	}

	fun onSetTotalAverageGrade(totalAverageGrade: Double, totalNumber: Int) {
		_totalAverage.postValue((totalAverageGrade/totalNumber))
	}

	fun onSetNullCheckGrade(flag: Boolean) {
		_isNullCheckGrade.postValue(flag)
	}
 }