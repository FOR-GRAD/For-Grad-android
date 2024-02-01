package umc.com.mobile.project.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlanViewModel : ViewModel() {

    // 기존에 있던 text LiveData
    private val _text = MutableLiveData<String>().apply {
        value = "This is Plan Fragment"
    }
    val text: LiveData<String> = _text

    // 새로 추가된 isFilledAllOptions LiveData
    private val _isFilledAllOptions = MutableLiveData<Boolean>().apply {
        value = false // 초기값 설정
    }
    val isFilledAllOptions: LiveData<Boolean> = _isFilledAllOptions

    // isFilledAllOptions의 값을 업데이트하는 메서드
    fun updateIsFilledAllOptions(isFilled: Boolean) {
        _isFilledAllOptions.value = isFilled
    }
}
