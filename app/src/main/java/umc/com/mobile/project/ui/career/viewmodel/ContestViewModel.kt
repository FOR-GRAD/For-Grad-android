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

class ContestViewModel : ViewModel() {
    private val contestApiService = ApiClient.createService<CareerApi>()

    private val _contestInfo: MutableLiveData<CategoryListResponse?> = MutableLiveData()
    val contestInfo: MutableLiveData<CategoryListResponse?>
        get() = _contestInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun getContestInfo() {
        contestApiService.getCareerList("COMPETITIONS").enqueue(object :
            Callback<CategoryListResponse> {
            override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                if (response.isSuccessful) {
                    val CategoryListResponse = response.body()
                    if (CategoryListResponse != null) {
                        _contestInfo.postValue(CategoryListResponse)
                        Log.d("contestInfo", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("공모전 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("contestInfo", "contestInfo API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("contestInfo", "contestInfo: ${t.message}")
            }
        })
    }
}