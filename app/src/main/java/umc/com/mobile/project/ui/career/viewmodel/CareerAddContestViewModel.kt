package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.career.AddCareerResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.data.RequestDto
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.RequestBody.Companion.toRequestBody
import umc.com.mobile.project.ui.career.adapter.LocalDateAdapter
import java.lang.reflect.Type

class CareerAddContestViewModel : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val selectedAward: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = ""
        selectedAward.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun init() {
        title.value = ""
        selectedAward.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun updateSelectedAward(award: String) {
        selectedAward.value = award
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = areBothFieldsFilled() }
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return !title.value.isNullOrBlank() && !startDate.value.isNullOrBlank() && !endDate.value.isNullOrBlank()
    }

    private val imageList: MutableList<MultipartBody.Part> = mutableListOf()

    fun addImageFiles(files: List<File>) {
        for (file in files) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            imageList.add(body)
        }
    }
    // 빈 이미지 생성
    fun addEmptyImage() {
        val emptyImageBytes: ByteArray = byteArrayOf()

        val requestFile =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), emptyImageBytes)
        val body = MultipartBody.Part.createFormData("image", "empty_image.jpg", requestFile)
        imageList.add(body)
    }

    // API에 전송할 데이터를 포함하는 RequestDto를 생성하는 함수
    fun createRequestDto(): RequestDto? {
        val startDateString = startDate.value
        val endDateString = endDate.value
        // 날짜가 입력되지 않은 경우
        if (startDateString.isNullOrBlank() || endDateString.isNullOrBlank()) {
            return null
        }

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val temporaryStartDateString = "20220131"
        val temporaryStartDate = LocalDate.parse(temporaryStartDateString, formatter)

        return RequestDto(
            title = "테스트12",
            content = "",
            category = "COMPETITIONS",
            startDate = temporaryStartDate,
            endDate = temporaryStartDate,
            award = "GRAND_PRIZE"
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

        val multipartBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

        for (imagePart in imageList) {
            multipartBodyBuilder.addPart(imagePart)
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        val requestDtoJson = gson.toJson(requestDto)
        println(requestDtoJson)
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