package umc.com.mobile.project.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.login.LoginResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.LoginApi
import java.net.HttpCookie

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

	// 로그인 결과 LiveData
	private val _loginResult: MutableLiveData<LoginResponse> = MutableLiveData()
	val loginResult: LiveData<LoginResponse>
		get() = _loginResult

	private val loginApiService = ApiClient.createService<LoginApi>()

	// 로그인 실패 시 textView 띄우기
	private val _loginStatus: MutableLiveData<Boolean> = MutableLiveData()
	val loginStatus: LiveData<Boolean>
		get() = _loginStatus

	// 추가: 로그인 성공 여부 LiveData
	private val _loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
	val loginSuccess: LiveData<Boolean>
		get() = _loginSuccess

	private fun isBothFieldsFilled(): Boolean {
		return !id.value.isNullOrEmpty() && !pw.value.isNullOrEmpty()
	}

	fun init() {
		id.postValue("")
		pw.postValue("")
		_loginStatus.postValue(false)
	}

	fun login() {
		loginApiService.login(id.value.orEmpty(), pw.value.orEmpty())
			.enqueue(object : Callback<LoginResponse> {
				override fun onResponse(
					call: Call<LoginResponse>,
					response: Response<LoginResponse>
				) {
					if (response.isSuccessful) {
						// 성공적으로 로그인이 되었을 때 쿠키 값 추출
						val cookies = response.headers().values("Set-Cookie")

						// 쿠키 값을 CookieJar에 저장
						cookies.forEach { cookie ->
							ApiClient.cookieManager.cookieStore.add(
								null,
								HttpCookie.parse(cookie).first()
							)
						}
						_loginResult.postValue(response.body())
						_loginStatus.postValue(false)

						Log.d("cookies", "$cookies")
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
						_loginStatus.postValue(true)
						Log.e("Login", "정보 틀림: $loginResult")
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
					_loginStatus.postValue(true)
					Log.e("Login", "네트워크 오류: $loginResult / $loginStatus")
				}
			})
	}
}