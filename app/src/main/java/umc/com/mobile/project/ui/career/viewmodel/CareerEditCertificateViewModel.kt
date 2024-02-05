package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.CareerDetailResponse
import umc.com.mobile.project.data.model.career.UpdateCareerResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.adapter.LocalDateAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.data.UpdateDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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

    fun updateCertificateType(selectedType: String) {
        type.value = selectedType
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

    fun updateCertificate() {
        val updatedTitle = title.value ?: ""
        val updatedType = type.value ?: ""
        val updatedStartDate = startDate.value ?: ""
        val updatedEndDate = endDate.value ?: ""

        val currentCertificateDetail = certificateDetailInfo.value

        fun parseDate(dateString: String, vararg formatters: DateTimeFormatter): LocalDate? {
            for (formatter in formatters) {
                try {
                    return LocalDate.parse(dateString, formatter)
                } catch (e: DateTimeParseException) {
                }
            }
            return null
        }

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatter2 = DateTimeFormatter.ISO_LOCAL_DATE

        val formattedStartDate = parseDate(updatedStartDate, formatter1, formatter2)
        val formattedEndDate = parseDate(updatedEndDate, formatter1, formatter2)


        /*        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                val formattedStartDate = if (updatedStartDate.isNotEmpty()) LocalDate.parse(
                    updatedStartDate,
                    formatter
                ) else null
                val formattedEndDate =
                    if (updatedEndDate.isNotEmpty()) LocalDate.parse(updatedEndDate, formatter) else null*/

        val updatedDetail = currentCertificateDetail?.let { currentDetail ->
            UpdateDto(
                title = if (updatedTitle.isNotEmpty()) updatedTitle else currentDetail.result.title,
                category = "CERTIFICATIONS",
                startDate = formattedStartDate ?: LocalDate.parse(
                    currentDetail.result.startDate,
                    formatter
                ),
                endDate = formattedEndDate ?: LocalDate.parse(
                    currentDetail.result.endDate,
                    formatter
                ),
                certificationType = if (updatedType.isNotEmpty()) updatedType else currentDetail.result.certificationType
            )
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        val activityIdPart = _certificateDetailInfo.value!!.result.id.toString()
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val updateDtoPart =
            gson.toJson(updatedDetail).toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val emptyRequestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val temporaryFilePart =
            MultipartBody.Part.createFormData("files", "your_temporary_file_name", emptyRequestBody)

        careerApiService.updateCertificate(activityIdPart, updateDtoPart, listOf(temporaryFilePart))
            .enqueue(object : Callback<UpdateCareerResponse> {
                override fun onResponse(
                    call: Call<UpdateCareerResponse>,
                    response: Response<UpdateCareerResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            Log.d("updateCertificateInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("자격증 정보 수정을 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e(
                                "updateCertificateInfo",
                                "updateCertificateInfo API 오류: ${e.message}"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("updateCertificateInfo", "updateCertificateInfo 네트워크 오류: ${t.message}")
                }
            })
    }
}