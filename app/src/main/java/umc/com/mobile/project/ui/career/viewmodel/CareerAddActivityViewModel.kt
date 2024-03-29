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
    val fileAddedEvent: MutableLiveData<Boolean> = MutableLiveData()
    val imageList: MutableList<MultipartBody.Part> = mutableListOf()
    val totalFileSize = MutableLiveData<Long>(0)

    init {
        title.value = ""
        startDate.value = ""
        endDate.value = ""
        imageList.clear()
        totalFileSize.value = 0
    }

    fun init() {
        title.value = ""
        startDate.value = ""
        endDate.value = ""
        imageList.clear()
        totalFileSize.value = 0
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = areBothFieldsFilled() }
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
        addSource(totalFileSize) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return !(title.value.isNullOrEmpty() || title.value!!.contains(" ") || title.value!!.length > 20) && isDateValid(
            startDate.value
        ) && isDateValid(endDate.value) && isFileSizeValid()
    }

    private fun isDateValid(date: String?): Boolean {
        return !date.isNullOrBlank() && date.length == 8
    }

    //파일 사이즈가 30MB를 넘지 않는지 체크
    private fun isFileSizeValid(): Boolean {
        return totalFileSize.value ?: 0 <= 30 * 1024 * 1024
    }

    fun addImageFile(file: File) {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        imageList.add(body)
        calculateTotalFileSize() //파일 사이즈 계산
        fileAddedEvent.value = true
    }

    //파일 확장자에 따른 MIME 타입 반환
    fun getMimeType(file: File): String {
        val extension = file.extension

        return when (extension.toLowerCase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "xls" -> "application/vnd.ms-excel"
            "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            "ppt" -> "application/vnd.ms-powerpoint"
            "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            else -> "application/octet-stream" //알 수 없는 파일 형식
        }
    }

    fun addFile(file: File) {
        val fileName = file.name
        val mimeType = getMimeType(file) //파일 확장자에 따른 MIME 타입 결정
        val requestFile: RequestBody = RequestBody.create(mimeType?.toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("image", fileName, requestFile)

        imageList.add(filePart)
        calculateTotalFileSize() //파일 사이즈 계산
        fileAddedEvent.value = true
    }

    //파일 사이즈 측정
    fun calculateTotalFileSize() {
        var totalSize: Long = 0
        for (filePart in imageList) {
            val file = filePart.body
            totalSize += file?.contentLength() ?: 0
        }
        totalFileSize.value = totalSize
    }

    //API에 전송할 데이터를 포함하는 RequestDto 생성 함수
    fun createRequestDto(): ActivityDto? {
        val startDateString = startDate.value
        val endDateString = endDate.value

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
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
                            Log.d("addCareer:Extras 성공", "${response.body()}")
                            Log.d("totalFileSize", totalFileSize.value.toString())
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
                            e.printStackTrace()
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