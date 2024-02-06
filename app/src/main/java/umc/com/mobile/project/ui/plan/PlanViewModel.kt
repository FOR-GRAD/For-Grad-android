package umc.com.mobile.project.ui.plan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.plan.BringlicenseResponse
import umc.com.mobile.project.data.model.plan.SavelicenseRequest

import umc.com.mobile.project.data.model.plan.UPlicenseResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.PlanApi

class PlanViewModel : ViewModel() {


    //api
    private val planApiService = ApiClient.createService<PlanApi>()

    private val _bringLicenseInfo= MutableLiveData<BringlicenseResponse?>()

    val bringLicenseInfo : LiveData<BringlicenseResponse?>
        get()=_bringLicenseInfo

    private val _savelicenseInfo: MutableLiveData<SavelicenseRequest?> = MutableLiveData()

    private val _licenseInfo: MutableLiveData<UPlicenseResponse?> = MutableLiveData()

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error
    val licenseInfo: MutableLiveData<UPlicenseResponse?>
        get() = _licenseInfo

    val SaveInfo : MutableLiveData<SavelicenseRequest?>
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





    fun getLicenseInfo() {
        planApiService.getUPlicense().enqueue(object : Callback<UPlicenseResponse> {
            override fun onResponse(call: Call<UPlicenseResponse>, response: Response<UPlicenseResponse>) {
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


    fun getSavelicense(request: SavelicenseRequest) {
        planApiService.getSavelicense(request).enqueue(object : Callback<SavelicenseRequest> {
            override fun onResponse(call: Call<SavelicenseRequest>, response: Response<SavelicenseRequest>) {
                if (response.isSuccessful) {
                    _savelicenseInfo.postValue(response.body())
                    Log.d("PlanViewModel", "License saved successfully: ${response.body()}")
                } else {
                    _error.postValue("자격증 정보 저장 실패: ${response.errorBody()?.string()}")
                    Log.e("PlanViewModel", "Error saving license: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SavelicenseRequest>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.e("PlanViewModel", "Network error: ${t.message}")
            }
        })
    }

    fun getBringlicense(request: SavelicenseRequest) {
        planApiService.getBringlicense(request).enqueue(object : Callback<BringlicenseResponse> {
            override fun onResponse(call: Call<BringlicenseResponse>, response: Response<BringlicenseResponse>) {
                if (response.isSuccessful) {
                    _bringLicenseInfo.postValue(response.body())
                    Log.d("PlanViewModel", "Bring license info successfully fetched: ${response.body()}")
                } else {
                    _error.postValue("Failed to fetch license info: ${response.errorBody()?.string()}")
                    Log.e("PlanViewModel", "Error fetching license info: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BringlicenseResponse>, t: Throwable) {
                _error.postValue("Network error on fetching license info: ${t.message}")
                Log.e("PlanViewModel", "Network error on fetching license info: ${t.message}")
            }
        })
    }

}


