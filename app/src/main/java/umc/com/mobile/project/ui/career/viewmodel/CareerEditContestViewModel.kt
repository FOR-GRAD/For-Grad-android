package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.CareerDetailResponse
import umc.com.mobile.project.data.model.career.UpdateCareerResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.adapter.LocalDateAdapter
import umc.com.mobile.project.ui.career.data.ContestDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CareerEditContestViewModel : ViewModel() {
    val studentId: MutableLiveData<Long> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val award: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        studentId.value = 0
        title.value = ""
        award.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun init() {
        title.value = ""
        award.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = areBothFieldsFilled()
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return (startDate.value.isNullOrBlank() && endDate.value.isNullOrBlank()) || (isDateValid(
            startDate.value
        ) && isDateValid(endDate.value))
    }

    private fun isDateValid(date: String?): Boolean {
        return date.isNullOrBlank() || date.length == 8
    }

    fun updateSelectedAward(selectedAward: String) {
        award.value = selectedAward
    }

    private val careerApiService = ApiClient.createService<CareerApi>()

    private val _contestDetailInfo: MutableLiveData<CareerDetailResponse?> = MutableLiveData()
    val contestDetailInfo: MutableLiveData<CareerDetailResponse?>
        get() = _contestDetailInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun getContestDetail() {
        careerApiService.getCareerDetail(studentId.value!!)
            .enqueue(object : Callback<CareerDetailResponse> {
                override fun onResponse(
                    call: Call<CareerDetailResponse>,
                    response: Response<CareerDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            _contestDetailInfo.postValue(CareerDetailResponse)
                            Log.d("getContestInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("봉사활동 세부정보를 불러오지 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e("getContestDetailInfo", "getContestInfo API 오류: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<CareerDetailResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("getContestDetailInfo", "getContestInfo 네트워크 오류: ${t.message}")
                }
            })
    }

    fun updateContest() {
        val updatedTitle = title.value ?: ""
        val updatedAward = award.value ?: ""
        val updatedStartDate = startDate.value ?: ""
        val updatedEndDate = endDate.value ?: ""

        val currentContestDetail = contestDetailInfo.value

        fun mapType(updatedType: String): String {
            return when (updatedType) {
                "대상" -> "GRAND_PRIZE"
                "최우수상" -> "EXCELLENT_PRIZE"
                "우수상" -> "GOOD_PRIZE"
                else -> "ENCOURAGEMENT_PRIZE"
            }
        }

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

        val formattedStartDate = if (updatedStartDate.isNotEmpty()) {
            parseDate(updatedStartDate, formatter1, formatter2) ?: LocalDate.parse(
                updatedStartDate,
                formatter
            )
        } else {
            LocalDate.parse(
                currentContestDetail?.result?.startDate?.replace("-", ""),
                formatter
            )
        }

        val formattedEndDate = if (updatedEndDate.isNotEmpty()) {
            parseDate(updatedEndDate, formatter1, formatter2) ?: LocalDate.parse(
                updatedEndDate,
                formatter
            )
        } else {
            LocalDate.parse(currentContestDetail?.result?.endDate?.replace("-", ""), formatter)
        }

        val updatedDetail = currentContestDetail?.let { currentDetail ->
            ContestDto(
                title = if (updatedTitle.isNotEmpty()) updatedTitle else currentDetail.result.title,
                content = "",
                category = "COMPETITIONS",
                startDate = formattedStartDate,
                endDate = formattedEndDate,
                award = if (updatedAward.isNotEmpty()) mapType(updatedAward) else currentDetail.result.award
            )
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        val activityIdPart = studentId.value!!.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
        val updateDtoPart =
            gson.toJson(updatedDetail).toRequestBody("application/json".toMediaTypeOrNull())

        val emptyRequestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val temporaryFilePart =
            MultipartBody.Part.createFormData("files", "your_temporary_file_name", emptyRequestBody)

        careerApiService.updateCareer(activityIdPart, updateDtoPart, listOf(temporaryFilePart))
            .enqueue(object : Callback<UpdateCareerResponse> {
                override fun onResponse(
                    call: Call<UpdateCareerResponse>,
                    response: Response<UpdateCareerResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            Log.d("updateContestInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("공모전 정보 수정을 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e(
                                "updateContestInfo",
                                "updateContestInfo API 오류: ${e.message}"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("updateContestInfo", "updateContestInfo 네트워크 오류: ${t.message}")
                }
            })
    }
}