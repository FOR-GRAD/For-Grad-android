package umc.com.mobile.project.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.home.UserResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.HomeApi

class HomeViewModel : ViewModel() {
	private val userInfoApiService = ApiClient.createService<HomeApi>()

	private val _userInfo: MutableLiveData<UserResponse?> = MutableLiveData()
	val userInfo: MutableLiveData<UserResponse?>
		get() = _userInfo

	private val _error: MutableLiveData<String> = MutableLiveData()
	val error: LiveData<String>
		get() = _error

	fun fetchUserInfo() {
		userInfoApiService.getUserInfo().enqueue(object : Callback<UserResponse> {
			override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
				if (response.isSuccessful) {
					val userResponse = response.body()
					if (userResponse != null) {
						_userInfo.postValue(userResponse)
						Log.d("home", "${response.body()}")
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