package umc.com.mobile.project.ui.plan.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.plan.license.DeleteLicense
import umc.com.mobile.project.data.model.plan.timetable.request.AddTimeRequest
import umc.com.mobile.project.data.model.plan.timetable.AddTimeResponse
import umc.com.mobile.project.data.model.plan.license.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.license.CertificateLicenseRequest
import umc.com.mobile.project.data.model.plan.license.CertificateResponse
import umc.com.mobile.project.data.model.plan.freememo.EditMemoRequest
import umc.com.mobile.project.data.model.plan.freememo.PlanFreeRequest
import umc.com.mobile.project.data.model.plan.freememo.PlanFreeResponse
import umc.com.mobile.project.data.model.plan.timetable.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.license.SaveInfo
import umc.com.mobile.project.data.model.plan.license.SavelicenseRequest
import umc.com.mobile.project.data.model.plan.timetable.request.SemesterDto
import umc.com.mobile.project.data.model.plan.timetable.request.SubjectDtoList

import umc.com.mobile.project.data.model.plan.license.UPlicenseResponse
import umc.com.mobile.project.data.model.plan.timetable.DeleteTimeTableResponse
import umc.com.mobile.project.data.model.plan.timetable.SearchSemesterResponse
import umc.com.mobile.project.data.model.plan.timetable.SearchSubjectResponse
import umc.com.mobile.project.data.model.plan.timetable.TimeResult
import umc.com.mobile.project.data.model.plan.timetable.TimeTableResponse
import umc.com.mobile.project.data.model.plan.timetable.UpTimeResult
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.PlanApi

class PlanViewModel : ViewModel() {

	private val planApiService = ApiClient.createService<PlanApi>()

	private val _timeTableResponse: MutableLiveData<TimeTableResponse?> = MutableLiveData()
	val timeTableResponse: LiveData<TimeTableResponse?>
		get() = _timeTableResponse

	private val _grade: MutableLiveData<Int> = MutableLiveData()
	val grade: LiveData<Int>
		get() = _grade

	private val _semester: MutableLiveData<Int> = MutableLiveData()
	val semester: LiveData<Int>
		get() = _semester

	private val _subjectId: MutableLiveData<Int> = MutableLiveData()
	val subjectId: LiveData<Int>
		get() = _subjectId

	fun setGradeAndSemester(grade: Int, semester: Int) {
		_grade.value = grade
		_semester.value = semester
	}

	private val _selectedTimeResults = MutableLiveData<ArrayList<UpTimeResult>>()
	val selectedTimeResults: LiveData<ArrayList<UpTimeResult>> = _selectedTimeResults

	private val _searchSubjectResults = MutableLiveData<ArrayList<TimeResult>>()
	val searchSubjectResults: LiveData<ArrayList<TimeResult>> = _searchSubjectResults

	fun setSelectedTimeResult(timeResult: TimeResult) {
		val currentList = _searchSubjectResults.value ?: ArrayList()
		currentList.add(timeResult)
		_searchSubjectResults.value = currentList
		Log.d("PlanTimetable", "setSelectedTimeResult: $timeResult")
	}

	private val _planFreeInfo: MutableLiveData<PlanFreeResponse?> = MutableLiveData()
	val planFreeInfo: LiveData<PlanFreeResponse?>
		get() = _planFreeInfo

	private val _postMemoResult = MutableLiveData<Boolean>()
	val postMemoResult: LiveData<Boolean>
		get() = _postMemoResult

	private val _hakki: MutableLiveData<String> = MutableLiveData()
	val hakki: LiveData<String>
		get() = _hakki

	private val _track: MutableLiveData<String> = MutableLiveData()
	val track: LiveData<String>
		get() = _track


	private val _planSemesterInfo: MutableLiveData<SearchSemesterResponse?> = MutableLiveData()
	val planSemesterInfo: LiveData<SearchSemesterResponse?>
		get() = _planSemesterInfo

	private val _planTrackInfo: MutableLiveData<PlanTrackResponse?> = MutableLiveData()
	val planTrackInfo: LiveData<PlanTrackResponse?>
		get() = _planTrackInfo

	private val _timeTableInfo = MutableLiveData<AddTimeResponse?>()
	val timeTableInfo: LiveData<AddTimeResponse?> = _timeTableInfo


	private val _planTimeStatus: MutableLiveData<Boolean> = MutableLiveData()
	val planTimeStatus: LiveData<Boolean>
		get() = _planTimeStatus

	private val _bringLicenseInfo = MutableLiveData<BringlicenseResponse?>()

	private val _listTimeInfo = MutableLiveData<SearchSubjectResponse?>()
	val listTimeInfo: LiveData<SearchSubjectResponse?>
		get() = _listTimeInfo

	val bringLicenseInfo: LiveData<BringlicenseResponse?>
		get() = _bringLicenseInfo


	private val _licenseInfo: MutableLiveData<UPlicenseResponse?> = MutableLiveData()
	val licenseInfo: LiveData<UPlicenseResponse?>
		get() = _licenseInfo


	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error


	private val _savelicenseInfo: MutableLiveData<SavelicenseRequest> = MutableLiveData()
	val savelicenseInfo: LiveData<SavelicenseRequest>
		get() = _savelicenseInfo

	private val _certificateResponse = MutableLiveData<CertificateResponse?>()
	val certificateResponse: LiveData<CertificateResponse?>
		get() = _certificateResponse


	// 기존에 있던 text LiveData
	private val _text = MutableLiveData<String>().apply {
		value = "This is Plan Fragment"
	}
	val text: LiveData<String> = _text

	// 새로 추가된 isFilledAllOptions LiveData
	private val _isFilledAllOptions = MutableLiveData<Boolean>().apply {
		value = false // 초기값 설정
	}
	val isFilledAllOptions: LiveData<Boolean> = _isFilledAllOptions

	fun updateIsFilledAllOptions(isFilled: Boolean) {
		_isFilledAllOptions.value = isFilled
	}

	fun resetSemesterSelection() {
		// 학기 정보 관련 상태 초기화
		_planSemesterInfo.postValue(null) // 학기 정보 초기화
		_hakki.postValue("") // 학기 식별자 초기화 (또는 적절한 초기 값으로 설정)
	}

	fun resetTrackSelection() {
		// 선택된 트랙 정보 초기화
		_track.postValue("") // 트랙 식별자 초기화 (또는 적절한 초기 값으로 설정)
	}

	fun setHakki(hakki: String) {
		_hakki.value = hakki
		Log.d("hakki value", "setHakki 호출됨: hakki=$hakki")
	}


	fun setHakkiAndTrack(hakki: String, trackId: String) {
		Log.d("hakkitrackvalue", "setHakkiAndTrack 호출됨: hakki=$hakki, trackId=$trackId")
		_hakki.value = hakki
		_track.value = trackId
	}

	private val _addTimeResponse = MutableLiveData<AddTimeResponse?>()
	val addTimeResponse: MutableLiveData<AddTimeResponse?> = _addTimeResponse

	/**
	 * 시간표 조회하기
	 */
	private val _deleteTimeTableResponse = MutableLiveData<DeleteTimeTableResponse>()
	val deleteTimeTableResponse: MutableLiveData<DeleteTimeTableResponse>
		get() = _deleteTimeTableResponse

	private val _subjectIdList: MutableList<Int> = mutableListOf()
	val subjectIdList: MutableList<Int>
		get() = _subjectIdList

	fun getTimeInfo(grade: Int, semester: Int) {
		planApiService.getUptime(grade, semester).enqueue(object : Callback<TimeTableResponse> {
			override fun onResponse(
				call: Call<TimeTableResponse>,
				response: Response<TimeTableResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						val timeList = response.body()!!.result
						val itemList = ArrayList<UpTimeResult>()
						timeList.forEach {
							itemList.add(
								UpTimeResult(
									it.subjectId,
									it.type,
									it.name,
									it.credit
								)
							)
						}
						_selectedTimeResults.value = itemList

						Log.d("PlanUpTime1", "TimeInfoResponse: $timeList")
						Log.d("PlanUpTime", "${response.body()}")
					} else {
						Log.d("PlanUpTime", "${response.body()}")
						_error.postValue("서버 응답이 올바르지 않습니다.")
					}
				} else {
					_error.postValue("사용자 정보를 가져오지 못했습니다.")
					try {
						throw response.errorBody()?.string()?.let {
							RuntimeException(it)
						} ?: RuntimeException("Unknown error")
					} catch (e: Exception) {
						Log.e(
							"PlanViewModel",
							"Failed to get time info: ${response.errorBody()?.string()}"
						)

					}
				}
			}

			override fun onFailure(call: Call<TimeTableResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("PlanUpTime12", "Network error: ${t.message}")
			}
		})
	}

	fun getSemesterInfo() {
		planApiService.getSemesterInfo().enqueue(object : Callback<SearchSemesterResponse> {
			override fun onResponse(
				call: Call<SearchSemesterResponse>,
				response: Response<SearchSemesterResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_planSemesterInfo.postValue(response.body())
						Log.d("PlanSemester", "${response.body()}")
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
						Log.e("PlanInfo", "PlanResponse API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<SearchSemesterResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "completion: ${t.message}")
			}
		})
	}

	fun getTrackInfo(hakki: String) {
		planApiService.getTrackInfo(hakki).enqueue(object : Callback<PlanTrackResponse> {
			override fun onResponse(
				call: Call<PlanTrackResponse>,
				response: Response<PlanTrackResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_planTrackInfo.postValue(response.body())
						Log.d("PlanTrackInfo", "${response.body()}")
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
						Log.e("PlanInfo", "PlanResponse API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<PlanTrackResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "completion: ${t.message}")
			}
		})
	}

	fun sendAddTimeRequest() {
		val currentSelectedSubjects = _selectedTimeResults.value ?: return
		val grade = _grade.value
		val semester = _semester.value
		val subjectId = _subjectId.value

		if (grade == null || semester == null) {
			return
		} else {
			Log.d(
				"gradesemester1",
				"Sending AddTimeRequest with grade: $grade, semester: $semester"
			)
		}

		val semesterDto = SemesterDto(grade = grade, semester = semester)

		val request = AddTimeRequest(
			semesterDto = semesterDto,
			subjectDtoList = currentSelectedSubjects.map {
				SubjectDtoList(
					type = it.type,
					name = it.name,
					credit = it.credit.toString()
				)
			}
		)

		addTime(request)
	}

	fun addTime(request: AddTimeRequest) {
		planApiService.addTime(request).enqueue(object : Callback<AddTimeResponse> {
			override fun onResponse(
				call: Call<AddTimeResponse>,
				response: Response<AddTimeResponse>
			) {
				if (response.isSuccessful) {
//                        Log.d("TimeTableApi", "getListTimeInfo 성공: ${response.body()}")
//                        Log.d("gradesemester", "Sending AddTimeRequest with grade: ${request.semesterDto.grade}, semester: ${request.semesterDto.semester}")
					_addTimeResponse.postValue(response.body())
				} else {
					Log.e("TimeTableApi", "Error posting memo: ${response.errorBody()?.string()}")
					_addTimeResponse.postValue(null)
				}
			}

			override fun onFailure(call: Call<AddTimeResponse>, t: Throwable) {
				_addTimeResponse.postValue(null)
			}
		})
	}

	fun getListTimeInfo(hakki: String, track: String) {
		Log.d("PlanViewModel", "getListTimeInfo 호출됨: hakki=$hakki, track=$track")
		planApiService.getListTime(hakki, track).enqueue(object : Callback<SearchSubjectResponse> {
			override fun onResponse(
				call: Call<SearchSubjectResponse>,
				response: Response<SearchSubjectResponse>
			) {
				if (response.isSuccessful && response.body() != null) {
					Log.d("PlanViewModel", "getListTimeInfo 성공: ${response.body()}")
					_listTimeInfo.postValue(response.body())
				} else {
					Log.e("PlanViewModel", "getListTimeInfo 실패: ${response.errorBody()?.string()}")
					_error.postValue("리스트 정보를 가져오는데 실패했습니다.")
				}
			}

			override fun onFailure(call: Call<SearchSubjectResponse>, t: Throwable) {
				Log.e("PlanViewModel", "getListTimeInfo 네트워크 오류: ${t.message}")
				_error.postValue("네트워크 오류: ${t.message}")
			}
		})
	}

	fun deleteTimeTable(grade: Int, semester: Int, subjectId: Int) {
		planApiService.deleteTimeTable(grade, semester, subjectId)
			.enqueue(object : Callback<DeleteTimeTableResponse> {
				override fun onResponse(
					call: Call<DeleteTimeTableResponse>,
					response: Response<DeleteTimeTableResponse>
				) {
					if (response.isSuccessful) {
						if (response.body() != null) {
							_deleteTimeTableResponse.postValue(response.body())
							Log.d("deleteTimeTableResponse", "${response.body()}")
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
							Log.e("DeleteTimeTable", "DeleteTimeTable API 오류: ${e.message}")
						}
					}
				}

				override fun onFailure(call: Call<DeleteTimeTableResponse>, t: Throwable) {
					_error.postValue("네트워크 오류: ${t.message}")
					Log.d("DeleteTimeTabl", "DeleteTimeTable: ${t.message}")
				}
			})
	}

	/**
	 * 자격증
	 */
	fun getLicenseInfo() {
		planApiService.getUPlicense().enqueue(object : Callback<UPlicenseResponse> {
			override fun onResponse(
				call: Call<UPlicenseResponse>,
				response: Response<UPlicenseResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_licenseInfo.postValue(response.body())
						Log.d("Planlicense", "${response.body()}")
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
						Log.e("PlanInfo", "PlanResponse API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<UPlicenseResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "completion: ${t.message}")
			}
		})
	}

	fun saveLicense(request: List<SaveInfo>) {

		planApiService.saveLicense(request).enqueue(object : Callback<BringlicenseResponse> {
			override fun onResponse(
				call: Call<BringlicenseResponse>, response: Response<BringlicenseResponse>
			) {
				if (response.isSuccessful) {
					_bringLicenseInfo.postValue(response.body())
					Log.d("PlanLicenseSave", "License saved successfully: ${response.body()}")
				} else {
					_error.postValue("자격증 정보 저장 실패: ${response.errorBody()?.string()}")
					Log.e(
						"PlanLicenseSave",
						"Error saving license: ${response.errorBody()?.string()}"
					)
				}
			}

			override fun onFailure(call: Call<BringlicenseResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.e("PlanViewModel", "Network error: ${t.message}")
			}
		})
	}

	fun deleteLicense(certificateId: Long) {
		planApiService.deleteLicense(certificateId).enqueue(object : Callback<DeleteLicense> {
			override fun onResponse(call: Call<DeleteLicense>, response: Response<DeleteLicense>) {
				if (response.isSuccessful) {
					// 삭제 요청이 성공적으로 처리된 경우
					Log.d("DeleteLicense", "License deleted successfully")
					// 여기에 필요한 처리를 추가하세요.
				} else {
					// 삭제 요청이 실패한 경우
					val errorMessage = response.errorBody()?.string() ?: "Unknown error"
					Log.e("DeleteLicense", "Failed to delete license: $errorMessage")
					// 여기에 필요한 처리를 추가하세요.
				}
			}

			override fun onFailure(call: Call<DeleteLicense>, t: Throwable) {
				// 네트워크 오류 등으로 요청이 실패한 경우
				Log.e("DeleteLicense", "Network error: ${t.message}")
				// 여기에 필요한 처리를 추가하세요.
			}
		})
	}

	fun certificateLicense(request: List<CertificateLicenseRequest>) {
		planApiService.certificateLicense(request).enqueue(object : Callback<CertificateResponse> {
			override fun onResponse(
				call: Call<CertificateResponse>,
				response: Response<CertificateResponse>
			) {
				if (response.isSuccessful) {
					// 서버로부터 응답을 성공적으로 받았을 때 LiveData 업데이트
					_certificateResponse.postValue(response.body())
					Log.d("PlanViewModel", "Certificate License update success: ${response.body()}")
				} else {
					// 에러 처리: 실패 응답 처리
					_certificateResponse.postValue(null)
					Log.e(
						"PlanViewModel",
						"Failed to update certificate license: ${response.errorBody()?.string()}"
					)
				}
			}

			override fun onFailure(call: Call<CertificateResponse>, t: Throwable) {
				// 네트워크 에러 처리: LiveData 업데이트로 에러 상태 전달 가능
				_certificateResponse.postValue(null)
				Log.e("PlanViewModel", "Network error on certificate license update: ${t.message}")
			}
		})
	}

	/**
	 * 자유 메모
	 */
	fun editMemo(memo: String) {
		val editMemoRequest = EditMemoRequest(memo = memo)
		planApiService.editMemo(editMemoRequest).enqueue(object : Callback<PlanFreeResponse> {
			override fun onResponse(
				call: Call<PlanFreeResponse>,
				response: Response<PlanFreeResponse>
			) {
				if (response.isSuccessful) {
					Log.d("PlanMemoEdit", "Memo successfully edited: ${response.body()}")
					// 필요한 경우, UI 업데이트를 위한 LiveData 업데이트 로직을 여기에 추가하세요.
				} else {
					Log.e("PlanMemoEdit", "Failed to edit memo: ${response.errorBody()?.string()}")
				}
			}

			override fun onFailure(call: Call<PlanFreeResponse>, t: Throwable) {
				Log.e("PlanMemoEdit", "Network error on edit memo: ${t.message}")
			}
		})
	}

	fun postMemo(memoRequest: PlanFreeRequest) {
		planApiService.postFreeMemo(memoRequest).enqueue(object : Callback<PlanFreeResponse> {
			override fun onResponse(
				call: Call<PlanFreeResponse>,
				response: Response<PlanFreeResponse>
			) {
				if (response.isSuccessful) {
					_postMemoResult.postValue(true)
				} else {
					Log.e("PlanViewModel", "Error posting memo: ${response.errorBody()?.string()}")
					_postMemoResult.postValue(false)
				}
			}

			override fun onFailure(call: Call<PlanFreeResponse>, t: Throwable) {
				_postMemoResult.postValue(false)
			}
		})
	}

	fun getFreeInfo() {
		planApiService.getFreeInfo().enqueue(object : Callback<PlanFreeResponse> {
			override fun onResponse(
				call: Call<PlanFreeResponse>,
				response: Response<PlanFreeResponse>
			) {
				if (response.isSuccessful) {
					if (response.body() != null) {
						_planFreeInfo.postValue(response.body())
						Log.d("PlanFree", "${response.body()}")
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
						Log.e("PlanInfo", "PlanResponse API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<PlanFreeResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("gradInfo", "completion: ${t.message}")
			}
		})
	}
}


















