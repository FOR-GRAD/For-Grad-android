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
import umc.com.mobile.project.ui.career.data.ActivityDto
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CareerAddActivityViewModel : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = ""
        startDate.value = ""
        endDate.value = ""
    }

    fun init() {
        title.value = ""
        startDate.value = ""
        endDate.value = ""
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

    fun addImageFile(file: File) {
        Log.d("addImageFile", file.toString())
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        imageList.add(body)
    }

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
    fun createRequestDto(): ActivityDto? {
        val startDateString = startDate.value
        val endDateString = endDate.value

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val temporaryStartDateString = "20220131"
        val formattedStartDate = LocalDate.parse(startDateString, formatter)
        val formattedEndDate = LocalDate.parse(endDateString, formatter)

        return ActivityDto(
            title = title.value!!,
            category = "EXTRAS",
            startDate = formattedStartDate,
            endDate = formattedEndDate
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
        println(requestDtoJson)
        val requestDtoPart: RequestBody =
            requestDtoJson.toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("AddCareer imageList", imageList.toString())
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
                            Log.d("addCareer:Extras 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("커리어:대외활동을 추가하지 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e("addCareerInfo", "addCareer:Extras API 오류: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<AddCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("addCareerInfo", "addCareer:Extras 네트워크 오류: ${t.message}")
                }
            })
    }
}