package umc.com.mobile.project.ui.gradInfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GradInfoViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is gradInfo Fragment"
	}
	val text: LiveData<String> = _text
}