package umc.com.mobile.project.ui.setting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is setting Fragment"
	}
	val text: LiveData<String> = _text
}