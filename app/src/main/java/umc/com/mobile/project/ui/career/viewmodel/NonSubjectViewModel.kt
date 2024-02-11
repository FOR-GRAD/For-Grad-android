package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.NonSubjectResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi

class NonSubjectViewModel : ViewModel() {
    private val careerApiService = ApiClient.createService<CareerApi>()

    private val _nonSubjectInfo: MutableLiveData<NonSubjectResponse?> = MutableLiveData()
    val nonSubjectInfo: MutableLiveData<NonSubjectResponse?>
        get() = _nonSubjectInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    var accumulatedPoints: String = ""
    var pageSize: Long = 0
    var currentPage = 1

    fun getNonSubjectInfo2(page: Int) {
        currentPage = page
        careerApiService.getNonSubjectList(page).enqueue(object : Callback<NonSubjectResponse> {
            override fun onResponse(
                call: Call<NonSubjectResponse>,
                response: Response<NonSubjectResponse>
            ) {
                if (response.isSuccessful) {
                    val nonSubjectResponse = response.body()
                    if (nonSubjectResponse != null) {
                        accumulatedPoints =
                            nonSubjectResponse.result?.pointSummaryDto?.accumulatedPoints as String
                        pageSize = nonSubjectResponse.result?.pageSize ?: 20
                        _nonSubjectInfo.postValue(nonSubjectResponse)
                        Log.d("nonSubjectInfo", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("비교과 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("nonSubjectInfo", "nonSubject API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<NonSubjectResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("nonSubjectInfo", "nonSubject: ${t.message}")
            }
        })
    }
}