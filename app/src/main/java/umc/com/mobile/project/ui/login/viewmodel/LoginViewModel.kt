package umc.com.mobile.project.ui.login.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.login.LoginRequest
import umc.com.mobile.project.data.model.login.LoginResponse
import umc.com.mobile.project.data.model.login.LoginResult
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.LoginApi

class LoginViewModel : ViewModel() {
	/**
	 * 버튼 활성화 기능
	 */
	val id: MutableLiveData<String> = MutableLiveData()
	val pw: MutableLiveData<String> = MutableLiveData()

	val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
		addSource(id) { value = isBothFieldsFilled() }
		addSource(pw) { value = isBothFieldsFilled() }
	}

	private fun isBothFieldsFilled(): Boolean {
		return !id.value.isNullOrEmpty() && !pw.value.isNullOrEmpty()
	}

	fun init() {
		id.postValue("")
		pw.postValue("")
	}

	// 로그인 결과 LiveData
	private val _loginResult: MutableLiveData<LoginResponse> = MutableLiveData()
	val loginResult: LiveData<LoginResponse>
		get() = _loginResult

	private val loginApiService = ApiClient.createService<LoginApi>()

	fun login() {
		loginApiService.login(id.value.orEmpty(), pw.value.orEmpty()).enqueue(object : Callback<LoginResponse> {
			override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
				if (response.isSuccessful) {
					_loginResult.postValue(response.body())
					Log.d("Login", "${response.body()}")
				} else {
					_loginResult.postValue(
						LoginResponse(
							isSuccess = false,
							code = "LOGIN FAIL",
							message = "아이디 또는 비밀번호를 확인하세요",
							result = null
						)
					)
					try {
						throw response.errorBody()?.string()?.let {
							RuntimeException(it)
						} ?: RuntimeException("Unknown error")
					} catch (e: Exception) {
						Log.e("Login", "API 오류: ${e.message}")
					}
				}
			}

			override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
				_loginResult.postValue(
					LoginResponse(
						isSuccess = false,
						code = "NETWORK ERROR",
						message = "네트워크 오류",
						result = null
					)
				)
			}
		})
	}
}