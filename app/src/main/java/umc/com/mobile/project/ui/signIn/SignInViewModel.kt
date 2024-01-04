package umc.com.mobile.project.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
	private val _text = MutableLiveData<String>().apply {
		value = "This is signIn Fragment"
	}
	val text: LiveData<String> = _text
}