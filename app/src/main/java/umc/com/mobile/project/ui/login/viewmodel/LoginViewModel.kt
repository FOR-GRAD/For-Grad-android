package umc.com.mobile.project.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

	val apiService = RetrofitClient.createService<LoginApi>()

}