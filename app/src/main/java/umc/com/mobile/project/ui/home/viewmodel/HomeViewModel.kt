package umc.com.mobile.project.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.home.GradDateResponse
import umc.com.mobile.project.data.model.home.UserResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.HomeApi

class HomeViewModel : ViewModel() {
	private val userInfoApiService = ApiClient.createService<HomeApi>()

	private val _userInfoResponse: MutableLiveData<UserResponse?> = MutableLiveData()
	val userInfoResponse: MutableLiveData<UserResponse?>
		get() = _userInfoResponse

	private val _dateResponse: MutableLiveData<GradDateResponse?> = MutableLiveData()
	val dateResponse: MutableLiveData<GradDateResponse?>
		get() = _dateResponse

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	private val _planStatus: MutableLiveData<Boolean> = MutableLiveData()
	val planStatus: LiveData<Boolean>
		get() = _planStatus

	private val _userName: MutableLiveData<String> = MutableLiveData()
	val userName: LiveData<String>
		get() = _userName

	private val _userId: MutableLiveData<String> = MutableLiveData()
	val userId: LiveData<String>
		get() = _userId

	private val _userDepart: MutableLiveData<String> = MutableLiveData()
	val userDepart: LiveData<String>
		get() = _userDepart

	private val _userGrade: MutableLiveData<String> = MutableLiveData()
	val userGrade: LiveData<String>
		get() = _userGrade

	private val _userStatus: MutableLiveData<String> = MutableLiveData()
	val userStatus: LiveData<String>
		get() = _userStatus

	private val _cheeringMemo: MutableLiveData<String> = MutableLiveData()
	val cheeringMemo: LiveData<String>
		get() = _cheeringMemo

	private val _dday: MutableLiveData<Int> = MutableLiveData()
	val dday: LiveData<Int>
		get() = _dday

	private val _userProfile: MutableLiveData<String> = MutableLiveData()
	val userProfile: LiveData<String>
		get() = _userProfile

	fun init(value: UserResponse) {
		_userName.postValue(value.result.name)
		_userId.postValue(value.result.id.toString())
		_userDepart.postValue(value.result.department)
		_userGrade.postValue(value.result.grade)
		_userStatus.postValue(value.result.status)
		_cheeringMemo.postValue(value.result.message)
		_dday.postValue(value.result.dday)
		_userProfile.postValue(value.result.base64Image)
		_planStatus.postValue(true)
	}

	fun getUserInfo() {
		userInfoApiService.getUserInfo().enqueue(object : Callback<UserResponse> {
			override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
				if (response.isSuccessful) {
					val userResponse = response.body()
					if (userResponse != null) {
						userInfoResponse.postValue(userResponse)

						if (userResponse.result.futureTimeTableDto.isEmpty()) {
							_planStatus.postValue(false)
						}
//						Log.d("home", "${response.body()}")
						Log.d("home", "${userResponse.result.futureTimeTableDto}")
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
						Log.e("home", "API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<UserResponse>, t: Throwable) {
				_error.postValue("네트워크 오류: ${t.message}")
				Log.d("home", "${t.message}")
			}
		})
	}


}