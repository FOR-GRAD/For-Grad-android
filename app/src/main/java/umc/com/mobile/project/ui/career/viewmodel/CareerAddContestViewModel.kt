package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerAddContestViewModel : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val selectedAward: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        title.value=""
        selectedAward.value=""
        startDate.value=""
        endDate.value=""
    }

    fun init() {
        title.value=""
        selectedAward.value=""
        startDate.value=""
        endDate.value=""
    }

    fun updateSelectedAward(award: String) {
        selectedAward.value = award
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