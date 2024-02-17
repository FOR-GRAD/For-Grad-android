package umc.com.mobile.project.ui.plan


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.EditMemoRequest
import umc.com.mobile.project.data.model.plan.ListTimeResponse
import umc.com.mobile.project.data.model.plan.PlanFreeRequest
import umc.com.mobile.project.data.model.plan.PlanFreeResponse
import umc.com.mobile.project.data.model.plan.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.SaveInfo

import umc.com.mobile.project.data.model.plan.SavelicenseRequest
import umc.com.mobile.project.data.model.plan.SemesterTimeResponse

import umc.com.mobile.project.data.model.plan.UPlicenseResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.PlanApi

class PlanViewModel : ViewModel() {


    //api
    private val planApiService = ApiClient.createService<PlanApi>()


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


    private val _planSemesterInfo: MutableLiveData<SemesterTimeResponse?> = MutableLiveData()
    val planSemesterInfo: LiveData<SemesterTimeResponse?>
        get() = _planSemesterInfo

    private val _planTrackInfo: MutableLiveData<PlanTrackResponse?> = MutableLiveData()
    val planTrackInfo: LiveData<PlanTrackResponse?>
        get() = _planTrackInfo


    private val _planTimeStatus: MutableLiveData<Boolean> = MutableLiveData()
    val planTimeStatus: LiveData<Boolean>
        get() = _planTimeStatus

    private val _bringLicenseInfo = MutableLiveData<BringlicenseResponse?>()


    private val _listTimeInfo = MutableLiveData<ListTimeResponse?>()

    val listTimeInfo: LiveData<ListTimeResponse?>
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

    // isFilledAllOptions의 값을 업데이트하는 메서드
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





    fun postMemo(memoRequest: PlanFreeRequest) {
        planApiService.postFreeMemo(memoRequest).enqueue(object : Callback<PlanFreeResponse> {
            override fun onResponse(
                call: Call<PlanFreeResponse>,
                response: Response<PlanFreeResponse>
            ) {
                if (response.isSuccessful) {
                    // API 호출 성공 시, 성공 LiveData 업데이트
                    _postMemoResult.postValue(true)
                } else {
                    // 실패 시, 실패 LiveData 업데이트
                    Log.e("PlanViewModel", "Error posting memo: ${response.errorBody()?.string()}")
                    _postMemoResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<PlanFreeResponse>, t: Throwable) {
                // 네트워크 오류 등으로 호출 실패 시, 실패 LiveData 업데이트
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


    fun getListTimeInfo(hakki: String, track: String) {
        Log.d("PlanViewModel", "getListTimeInfo 호출됨: hakki=$hakki, track=$track")
        planApiService.getListTime(hakki, track).enqueue(object : Callback<ListTimeResponse> {
            override fun onResponse(call: Call<ListTimeResponse>, response: Response<ListTimeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("PlanViewModel", "getListTimeInfo 성공: ${response.body()}")
                    _listTimeInfo.postValue(response.body())
                } else {
                    Log.e("PlanViewModel", "getListTimeInfo 실패: ${response.errorBody()?.string()}")
                    _error.postValue("리스트 정보를 가져오는데 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<ListTimeResponse>, t: Throwable) {
                Log.e("PlanViewModel", "getListTimeInfo 네트워크 오류: ${t.message}")
                _error.postValue("네트워크 오류: ${t.message}")
            }
        })
    }



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
                    Log.d("PlanViewModel", "License saved successfully: ${response.body()}")
                } else {
                    _error.postValue("자격증 정보 저장 실패: ${response.errorBody()?.string()}")
                    Log.e(
                        "PlanViewModel",
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


    fun getSemesterInfo() {
        planApiService.getSemesterInfo().enqueue(object : Callback<SemesterTimeResponse> {
            override fun onResponse(
                call: Call<SemesterTimeResponse>,
                response: Response<SemesterTimeResponse>
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

            override fun onFailure(call: Call<SemesterTimeResponse>, t: Throwable) {
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

}


















