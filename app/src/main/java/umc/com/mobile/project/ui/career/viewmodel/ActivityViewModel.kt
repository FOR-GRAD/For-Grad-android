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

class ActivityViewModel : ViewModel() {
    private val activityApiService = ApiClient.createService<CareerApi>()

    private val _activityInfo: MutableLiveData<CategoryListResponse?> = MutableLiveData()
    val activityInfo: MutableLiveData<CategoryListResponse?>
        get() = _activityInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _searchInfo: MutableLiveData<CategoryListResponse?> = MutableLiveData()
    val searchInfo: MutableLiveData<CategoryListResponse?>
        get() = _searchInfo

    fun searchActivityInfo(searchWord: String) {
        activityApiService.getSearchCareer("EXTRAS", searchWord).enqueue(object :
            Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                if (response.isSuccessful) {
                    val CategoryListResponse = response.body()
                    if (CategoryListResponse != null) {
                        _searchInfo.postValue(CategoryListResponse)
                        Log.d("searchActivityInfo", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("검색한 교외활동 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("searchActivityInfo", "searchActivityInfo API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("searchCertificateInfo", "searchCertificateInfo: ${t.message}")
            }
        })
    }

    fun getActivityInfo() {
        activityApiService.getCareerList("EXTRAS").enqueue(object :
            Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                if (response.isSuccessful) {
                    val CategoryListResponse = response.body()
                    if (CategoryListResponse != null) {
                        _activityInfo.postValue(CategoryListResponse)
                        Log.d("activityInfo", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("교외활동 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("activityInfo", "activityInfo API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("activityInfo", "activityInfo: ${t.message}")
            }
        })
    }
}