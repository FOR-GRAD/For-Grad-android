package umc.com.mobile.project.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.login.LoginResponse
import umc.com.mobile.project.data.network.RetrofitClient
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

	// 로그인 요청 메서드
	/*fun login() {
		val request = LoginRequest(id.value.orEmpty(), pw.value.orEmpty())

		loginApiService.login(request.id, request.passwd).enqueue(object : Callback<LoginResponse> {
			override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
				if (response.isSuccessful) {
					_loginResult.postValue(response.body())
				} else {
					// 실패 시 처리
					_loginResult.postValue(
						LoginResponse(
							isSuccess = false,
							code = "LOGIN FAIL",
							message = "아이디 또는 비밀번호를 확인하세요",
							result = null
						)
					)
				}
			}

			override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
				// 네트워크 오류 등 실패 시 처리
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
	}*/

}