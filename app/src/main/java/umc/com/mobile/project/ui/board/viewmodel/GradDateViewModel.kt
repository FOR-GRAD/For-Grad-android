package umc.com.mobile.project.ui.board.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.home.GradDateResponse
import umc.com.mobile.project.data.model.home.UpdateGradDateRequest
import umc.com.mobile.project.data.model.home.UpdateGradDateResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.HomeApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class GradDateViewModel : ViewModel() {
    val _selectedDate: MutableLiveData<String> = MutableLiveData()
    val selectedDate: LiveData<String>
        get() = _selectedDate

    val _selectedDateRequest: MutableLiveData<String> = MutableLiveData()
    val selectedDateRequest: LiveData<String>
        get() = _selectedDateRequest

    private val dateInfoApiService = ApiClient.createService<HomeApi>()

    //졸업예정일에서는 안 씀-> home에 코드 있어서 놔둠
    private val _dateResponse: MutableLiveData<GradDateResponse?> = MutableLiveData()
    val dateResponse: MutableLiveData<GradDateResponse?>
        get() = _dateResponse

    //졸업예정일이 쓰는 response
    val _dateResponse2: MutableLiveData<GradDateResponse?> = MutableLiveData()

    private val _updateDateResponse: MutableLiveData<UpdateGradDateResponse?> = MutableLiveData()
    val updateDateResponse: MutableLiveData<UpdateGradDateResponse?>
        get() = _updateDateResponse

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    val _dday: MutableLiveData<Int> = MutableLiveData()
    val dday: LiveData<Int>
        get() = _dday

    val cheeringMessage: MutableLiveData<String> = MutableLiveData()

    private val _isEditMode = MutableLiveData<Boolean>(false)
    val isEditMode: LiveData<Boolean>
        get() = _isEditMode

    init {
        cheeringMessage.value = ""
    }

    fun init() {
        _isEditMode.value = false
    }

    fun updateSelectedDate(year: String, month: String, day: String) {
        val selectedDateString = "졸업 예정일 ${year}년 $month ${day}일"
        _selectedDate.value = selectedDateString
        _selectedDateRequest.value = "${year}-${month}-${day}"
        calculateDDay(year, month, day)
    }

    fun calculateDDay(year: String, month: String, day: String) {
        val monthNumber = month.replace("월", "").trim().padStart(2, '0')
        val dayNumber = day.padStart(2, '0')

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val selectedDate = LocalDate.parse("$year-$monthNumber-$dayNumber", formatter)
        val currentDate = LocalDate.now()

        _dday.value = ChronoUnit.DAYS.between(currentDate, selectedDate).toInt()
    }

    //버튼 말고 수정하기 text
    fun onEditButtonClick() {
        _isEditMode.value = !_isEditMode.value!!
    }

    //전의 메모랑 같으면 저장 안됨
    private var previousMessage: String? = null
    private var previousDDay: Int? = null

    //버튼 활성화 조건
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        var previousMessage: String? = null
        var previousDDay: String? = null

        //cheeringMessage 값이 변경될 때마다 실행
        val cheeringMessageObserver = Observer<String> { currentMessage ->
            val dDayValue = _dday.value
            value =
                previousMessage != currentMessage && currentMessage.isNotEmpty() && dDayValue != null && dDayValue != 0
            previousMessage = currentMessage
        }
        //_dday 값이 변경될 때마다 실행
        val dDayObserver = Observer<Int> { currentDDay ->
            val messageValue = cheeringMessage.value
            value =
                previousDDay != currentDDay.toString() && currentDDay != null && currentDDay != 0 && messageValue != null && messageValue.isNotEmpty()
            previousDDay = currentDDay.toString()
        }
        addSource(cheeringMessage, cheeringMessageObserver)
        addSource(_dday, dDayObserver)
    }

    fun getDateInfo() {
        dateInfoApiService.getDateInfo().enqueue(object : Callback<GradDateResponse> {
            override fun onResponse(
                call: Call<GradDateResponse>,
                response: Response<GradDateResponse>
            ) {
                if (response.isSuccessful) {
                    val dateInfoResponse = response.body()
                    if (dateInfoResponse != null) {
                        dateResponse.postValue(dateInfoResponse)//home에 있어서 놔둠
                        _dateResponse2.postValue(dateInfoResponse)
                        if (_dateResponse2.value != null && _dateResponse2.value!!.result != null) {
                            _selectedDate.value = _dateResponse2.value!!.result.gradDate
                        }
                        Log.d("gradDate", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("사용자 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("gradDate", "API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<GradDateResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("gradDate", "${t.message}")
            }
        })
    }

    fun updateDateInfo(date: String) {
        dateInfoApiService.updateDateInfo(
            UpdateGradDateRequest(date, cheeringMessage.value.orEmpty())
        ).enqueue(object : Callback<UpdateGradDateResponse> {
            override fun onResponse(
                call: Call<UpdateGradDateResponse>,
                response: Response<UpdateGradDateResponse>
            ) {
                if (response.isSuccessful) {
                    val updateDateInfoResponse = response.body()
                    if (updateDateInfoResponse != null) {
                        updateDateResponse.postValue(updateDateInfoResponse)
                        Log.d("updateGradDate", "${response.body()}")
                    } else {
                        _error.postValue("서버 응답이 올바르지 않습니다.")
                    }
                } else {
                    _error.postValue("사용자 정보를 가져오지 못했습니다.")
                    try {
                        throw response.errorBody()?.string()?.let {
                            RuntimeException(it)
                        } ?: RuntimeException("Unknown error")
                    } catch (e: Exception) {
                        Log.e("updateGradDate", "API 오류: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<UpdateGradDateResponse>, t: Throwable) {
                _error.postValue("네트워크 오류: ${t.message}")
                Log.d("updateGradDate", "${t.message}")
            }
        })
    }
}