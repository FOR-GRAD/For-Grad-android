package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.CareerDetailResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.data.CertificateDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CareerEditCertificateViewModel : ViewModel() {
    val studentId: MutableLiveData<Long> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val type: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        studentId.value = 0
        title.value = ""
        type.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun init() {
        title.value = ""
        type.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    private val careerApiService = ApiClient.createService<CareerApi>()

    private val _certificateDetailInfo: MutableLiveData<CareerDetailResponse?> = MutableLiveData()
    val certificateDetailInfo: MutableLiveData<CareerDetailResponse?>
        get() = _certificateDetailInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun getCertificateDetail() {
        careerApiService.getCareerDetail(studentId.value!!)
            .enqueue(object : Callback<CareerDetailResponse> {
                override fun onResponse(
                    call: Call<CareerDetailResponse>,
                    response: Response<CareerDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            _certificateDetailInfo.postValue(CareerDetailResponse)
                            Log.d("getCertificateInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("자격증 세부정보를 불러오지 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e(
                                "getCertificateDetailInfo",
                                "getCertificateInfo API 오류: ${e.message}"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<CareerDetailResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("getCertificateDetailInfo", "getCertificateInfo 네트워크 오류: ${t.message}")
                }
            })
    }

    fun updateCertificateDetails() {
        val updatedTitle = title.value ?: ""
        val updatedType = type.value ?: ""
        val updatedStartDate = startDate.value ?: ""
        val updatedEndDate = endDate.value ?: ""

        val currentCertificateDetail = certificateDetailInfo.value

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedStartDate = if (updatedStartDate.isNotEmpty()) LocalDate.parse(updatedStartDate, formatter) else null
        val formattedEndDate = if (updatedEndDate.isNotEmpty()) LocalDate.parse(updatedEndDate, formatter) else null

        currentCertificateDetail?.let { currentDetail ->
            val updatedDetail = CertificateDto(
                title = if (updatedTitle.isNotEmpty()) updatedTitle else currentDetail.result.title,
                category = "CERTIFICATIONS",
                certificationType = if (updatedType.isNotEmpty()) updatedType else currentDetail.result.certificationType,
                startDate = formattedStartDate ?: LocalDate.parse(currentDetail.result.startDate, formatter),
                endDate = formattedEndDate ?: LocalDate.parse(currentDetail.result.endDate, formatter)
            )
        }
    }
}