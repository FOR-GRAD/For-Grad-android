package umc.com.mobile.project.ui.plan


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.ListTimeResponse
import umc.com.mobile.project.data.model.plan.SaveInfo

import umc.com.mobile.project.data.model.plan.SavelicenseRequest
import umc.com.mobile.project.data.model.plan.SemesterTimeResponse

import umc.com.mobile.project.data.model.plan.UPlicenseResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.PlanApi

class PlanViewModel : ViewModel() {


    //api
    private val planApiService = ApiClient.createService<PlanApi>()

    private val _hakki: MutableLiveData<String> = MutableLiveData()
    val hakki: LiveData<String>
        get() = _hakki

    private val _track: MutableLiveData<String> = MutableLiveData()
    val track: LiveData<String>
        get() = _track


    private val _planSemesterInfo : MutableLiveData<SemesterTimeResponse?> = MutableLiveData()
    val planSemesterInfo :LiveData<SemesterTimeResponse?>
        get()=_planSemesterInfo


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


    private val _savelicenseInfo : MutableLiveData<SavelicenseRequest> =MutableLiveData()
    val savelicenseInfo : LiveData<SavelicenseRequest>
        get()=_savelicenseInfo


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


    fun getListTimeInfo() {
        _hakki.value?.let {
            _track.value?.let { it1 ->
                planApiService.getListTime(it, it1).enqueue(object : Callback<ListTimeResponse> {
                    override fun onResponse(
                        call: Call<ListTimeResponse>,
                        response: Response<ListTimeResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                _listTimeInfo.postValue(response.body())
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

                    override fun onFailure(call: Call<ListTimeResponse>, t: Throwable) {
                        _error.postValue("네트워크 오류: ${t.message}")
                        Log.d("gradInfo", "completion: ${t.message}")
                    }
                })
            }
        }
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
            override fun onResponse(call: Call<BringlicenseResponse>, response: Response<BringlicenseResponse>
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
}
















