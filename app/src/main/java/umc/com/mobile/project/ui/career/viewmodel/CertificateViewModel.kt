package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.CategoryListResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi

class CertificateViewModel : ViewModel() {
    private val certificateApiService = ApiClient.createService<CareerApi>()

    private val _certificateInfo: MutableLiveData<CategoryListResponse?> = MutableLiveData()
    val certificateInfo: MutableLiveData<CategoryListResponse?>
        get() = _certificateInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun getCertificateInfo() {
        certificateApiService.getCareerList("CERTIFICATIONS").enqueue(object :
            Callback<CategoryListResponse> {
            override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                if (response.isSuccessful) {
                    val CategoryListResponse = response.body()
                    if (CategoryListResponse != null) {
                        _certificateInfo.postValue(CategoryListResponse)
                        Log.d("certificateInfo", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("자격증 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("certificateInfo", "certificateInfo API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("certificateInfo", "certificateInfo: ${t.message}")
            }
        })
    }
}