package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class CareerEditCertificateViewModel : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val selectedCertificateType: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        title.value=""
        selectedCertificateType.value=""
        startDate.value=""
        endDate.value=""
    }

    fun init() {
        title.value=""
        selectedCertificateType.value=""
        startDate.value=""
        endDate.value=""
    }

    fun updateCertificateType(type: String) {
        selectedCertificateType.value = type
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = areBothFieldsFilled() }
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return !title.value.isNullOrBlank() && !startDate.value.isNullOrBlank() && !endDate.value.isNullOrBlank()
    }
}