package umc.com.mobile.project.ui.board.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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

	private val _dateResponse: MutableLiveData<GradDateResponse?> = MutableLiveData()
	val dateResponse: MutableLiveData<GradDateResponse?>
		get() = _dateResponse
	private val _updateDateResponse: MutableLiveData<UpdateGradDateResponse?> = MutableLiveData()
	val updateDateResponse: MutableLiveData<UpdateGradDateResponse?>
		get() = _updateDateResponse
	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error
	val _dday: MutableLiveData<Int> = MutableLiveData()
	val dday: LiveData<Int>
		get() = _dday
	private val _cheeringMessage: MutableLiveData<String> = MutableLiveData()
	val cheeringMessage: LiveData<String>
		get() = _cheeringMessage
	val isFilledMemo: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
		addSource(cheeringMessage) { value = isFieldFilled() }
	}
	private fun isFieldFilled(): Boolean {
		return !cheeringMessage.value.isNullOrEmpty()
	}

	fun init(value: GradDateResponse) {
		_dday.postValue(value.result.dday)
		_dday.postValue(0)
		_selectedDate.postValue(" 선택하기!")
		_cheeringMessage.postValue(value.result.message)
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

	fun updateCheeringMessage(message: String) {
		_cheeringMessage.value = message
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
						dateResponse.postValue(dateInfoResponse)
						if (dateResponse.value != null && dateResponse.value!!.result != null) {
							_selectedDate.value = dateResponse.value!!.result.gradDate
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