package umc.com.mobile.project.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is board Fragment"
	}
	val text: LiveData<String> = _text
}