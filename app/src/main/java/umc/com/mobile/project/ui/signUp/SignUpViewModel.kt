package umc.com.mobile.project.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
	private val _text = MutableLiveData<String>().apply {
		value = "This is signUp Fragment"
	}
	val text: LiveData<String> = _text
}