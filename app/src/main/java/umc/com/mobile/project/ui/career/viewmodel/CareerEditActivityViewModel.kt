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
import umc.com.mobile.project.data.model.career.CareerDetailResponse
import umc.com.mobile.project.data.model.career.DeleteCareerResponse
import umc.com.mobile.project.data.model.career.UpdateCareerResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.ui.career.adapter.LocalDateAdapter
import umc.com.mobile.project.ui.career.data.ActivityDto
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CareerEditActivityViewModel : ViewModel() {
    val studentId: MutableLiveData<Long> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val file: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()
    val fileAddedEvent: MutableLiveData<Boolean> = MutableLiveData()
    val addFiles: MutableList<MultipartBody.Part> = mutableListOf()
    val addFilesLive: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = addFiles.isNotEmpty()
    }
    val totalFileSize = MutableLiveData<Long>(0)

    init {
        studentId.value = 0
        title.value = ""
        file.value = ""
        startDate.value = ""
        endDate.value = ""
        addFiles.clear()
        totalFileSize.value = 0
    }

    fun init() {
        title.value = ""
        file.value = ""
        startDate.value = ""
        endDate.value = ""
        addFiles.clear()
        addFilesLive.value = addFiles.isNotEmpty()
        totalFileSize.value = 0
    }

    val isFilledAnyOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = isAnyFieldFilled()
        addSource(title) { value = isAnyFieldFilled() }
        addSource(startDate) { value = isAnyFieldFilled() }
        addSource(endDate) { value = isAnyFieldFilled() }
        addSource(addFilesLive) { value = isAnyFieldFilled() }
        addSource(totalFileSize) { value = isAnyFieldFilled() }
    }

    private fun isAnyFieldFilled(): Boolean {
        return (!title.value.isNullOrBlank() || !startDate.value.isNullOrBlank() || !endDate.value.isNullOrBlank() || addFilesLive.value ?: false) && isFileSizeValid()
    }

    private fun isDateValid(date: String?): Boolean {
        return date.isNullOrBlank() || date.length == 8
    }

    //파일 사이즈가 30MB를 넘지 않는지 체크
    private fun isFileSizeValid(): Boolean {
        return totalFileSize.value ?: 0 <= 30 * 1024 * 1024
    }

    fun addImageFile(file: File) {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("addFiles", file.name, requestFile)
        addFiles.add(body)
        addFilesLive.value = addFiles.isNotEmpty()
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
        val filePart = MultipartBody.Part.createFormData("addFiles", fileName, requestFile)
        addFiles.add(filePart)
        addFilesLive.value = addFiles.isNotEmpty()
        calculateTotalFileSize() //파일 사이즈 계산
        fileAddedEvent.value = true
    }

    //파일 사이즈 측정
    fun calculateTotalFileSize() {
        var totalSize: Long = 0
        for (filePart in addFiles) {
            val file = filePart.body
            totalSize += file?.contentLength() ?: 0
        }
        totalFileSize.value = totalSize
    }

    private val careerApiService = ApiClient.createService<CareerApi>()

    private val _activityDetailInfo: MutableLiveData<CareerDetailResponse?> = MutableLiveData()
    val activityDetailInfo: MutableLiveData<CareerDetailResponse?>
        get() = _activityDetailInfo

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun getActivityDetail() {
        careerApiService.getCareerDetail(studentId.value!!)
            .enqueue(object : Callback<CareerDetailResponse> {
                override fun onResponse(
                    call: Call<CareerDetailResponse>,
                    response: Response<CareerDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            _activityDetailInfo.postValue(CareerDetailResponse)
                            Log.d("getContestInfo 성공", "${response.body()}")
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                        }
                    } else {
                        _error.postValue("교외활동 세부정보를 불러오지 못했습니다.")
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e("getActivityDetailInfo", "getActivityInfo API 오류: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<CareerDetailResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("getActivityDetailInfo", "getActivityInfo 네트워크 오류: ${t.message}")
                }
            })
    }

    fun updateActivity(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val updatedTitle = title.value ?: ""
        val updatedStartDate = startDate.value ?: ""
        val updatedEndDate = endDate.value ?: ""

        val currentActivityDetail = activityDetailInfo.value

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
                currentActivityDetail?.result?.startDate?.replace("-", ""),
                formatter
            )
        }

        val formattedEndDate = if (updatedEndDate.isNotEmpty()) {
            parseDate(updatedEndDate, formatter1, formatter2) ?: LocalDate.parse(
                updatedEndDate,
                formatter
            )
        } else {
            LocalDate.parse(currentActivityDetail?.result?.endDate?.replace("-", ""), formatter)
        }

        val updatedDetail = currentActivityDetail?.let { currentDetail ->
            ActivityDto(
                title = if (updatedTitle.isNotEmpty()) updatedTitle else currentDetail.result.title,
                category = "EXTRAS",
                startDate = formattedStartDate,
                endDate = formattedEndDate
            )
        }
        //파일 사이즈 확인
        fun calculateTotalFileSize(): Long {
            var totalSize: Long = 0
            for (filePart in addFiles) {
                val file = filePart.body
                totalSize += file?.contentLength() ?: 0
            }
            return totalSize
        }
        val totalFileSize = calculateTotalFileSize()
        Log.d("EditFile Size", "Total File Size: $totalFileSize bytes")

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        val activityIdPart = studentId.value!!.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
        val updateDtoPart =
            gson.toJson(updatedDetail).toRequestBody("application/json".toMediaTypeOrNull())

        careerApiService.updateCareer(activityIdPart, updateDtoPart, addFiles)
            .enqueue(object : Callback<UpdateCareerResponse> {
                override fun onResponse(
                    call: Call<UpdateCareerResponse>,
                    response: Response<UpdateCareerResponse>
                ) {
                    if (response.isSuccessful) {
                        val CareerDetailResponse = response.body()
                        if (CareerDetailResponse != null) {
                            Log.d("updateActivityInfo 성공", "${response.body()}")
                            Log.d("editSuccessFileName", addFiles.toString())
                            result.postValue(true)
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                            result.postValue(false)
                        }
                    } else {
                        _error.postValue("교외활동 정보 수정을 못했습니다.")
                        result.postValue(false)
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e(
                                "updateActivityInfo",
                                "updateActivityInfo API 오류: ${e.message}"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("updateActivityInfo", "updateActivityInfo 네트워크 오류: ${t.message}")
                    result.postValue(false)
                }
            })
        return result
    }

    fun deleteActivity(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        careerApiService.deleteCareer(studentId.value!!)
            .enqueue(object : Callback<DeleteCareerResponse> {
                override fun onResponse(
                    call: Call<DeleteCareerResponse>,
                    response: Response<DeleteCareerResponse>
                ) {
                    if (response.isSuccessful) {
                        val deleteCareerResponse = response.body()
                        if (deleteCareerResponse != null) {
                            Log.d("deleteActivityInfo 성공", "${response.body()}")
                            result.postValue(true)
                        } else {
                            _error.postValue("서버 응답이 올바르지 않습니다.")
                            result.postValue(false)
                        }
                    } else {
                        _error.postValue("교외활동을 삭제하지 못했습니다.")
                        result.postValue(false)
                        try {
                            throw response.errorBody()?.string()?.let {
                                RuntimeException(it)
                            } ?: RuntimeException("Unknown error")
                        } catch (e: Exception) {
                            Log.e("deleteActivityInfo", "deleteActivityInfo API 오류: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteCareerResponse>, t: Throwable) {
                    _error.postValue("네트워크 오류: ${t.message}")
                    Log.d("deleteActivityInfo", "deleteActivityInfo 네트워크 오류: ${t.message}")
                    result.postValue(false)
                }
            })
        return result
    }
}