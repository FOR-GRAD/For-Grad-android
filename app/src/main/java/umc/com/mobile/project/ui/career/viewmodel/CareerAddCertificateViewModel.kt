package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.AddCareerResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.adapter.LocalDateAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CareerAddCertificateViewModel : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val selectedCertificateType: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = ""
        selectedCertificateType.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun init() {
        title.value = ""
        selectedCertificateType.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun updateCertificateType(type: String) {
        selectedCertificateType.value = type
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = areBothFieldsFilled() }
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return !(title.value.isNullOrEmpty() || title.value!!.contains(" ") || title.value!!.length > 20) && isDateValid(
            startDate.value
        ) && isDateValid(endDate.value)
    }

    private fun isDateValid(date: String?): Boolean {
        return !date.isNullOrBlank() && date.length == 8
    }

    private val imageList: MutableList<MultipartBody.Part> = mutableListOf()

    //API에 전송할 데이터를 포함하는 RequestDto 생성 함수
    fun createRequestDto(): CertificateDto? {
        val startDateString = startDate.value
        val endDateString = endDate.value
        //날짜가 입력되지 않은 경우
        if (startDateString.isNullOrBlank() || endDateString.isNullOrBlank()) {
            return null
        }

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedStartDate = LocalDate.parse(startDateString, formatter)
        val formattedEndDate = LocalDate.parse(endDateString, formatter)

        val certificationType = when (selectedCertificateType.value) {
            "실기" -> "PRACTICAL_EXAM"
            "필기" -> "WRITTEN_EXAM"
            else -> "INTERVIEW"
        }

        return CertificateDto(
            title = title.value!!,
            category = "CERTIFICATIONS",
            startDate = formattedStartDate,
            endDate = formattedEndDate,
            certificationType = certificationType
        )
    }

    private val careerApiService = ApiClient.createService<CareerApi>()

    private val _addedCareerInfo: MutableLiveData<AddCareerResponse?> = MutableLiveData()
    val addedCareerInfo: MutableLiveData<AddCareerResponse?>
        get() = _addedCareerInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun addCareer() {
        val requestDto = createRequestDto()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        val requestDtoJson = gson.toJson(requestDto)
        val requestDtoPart: RequestBody =
            requestDtoJson.toRequestBody("application/json".toMediaTypeOrNull())

        careerApiService.addCareer(imageList, requestDtoPart)
            .enqueue(object : Callback<AddCareerResponse> {
                override fun onResponse(
                    call: Call<AddCareerResponse>,
                    response: Response<AddCareerResponse>
                ) {
                    if (response.isSuccessful) {
                        val addCareerResponse = response.body()
                        if (addCareerResponse != null) {
                            _addedCareerInfo.postValue(addCareerResponse)
                            Log.d("addedCareerInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("커리어를 추가하지 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e("addCareerInfo", "addCareer API 오류: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<AddCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("addCareerInfo", "addCareer 네트워크 오류: ${t.message}")
                }
            })
    }
}