package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerEditContestViewModel : ViewModel() {
    val selectedAward: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        selectedAward.postValue("")
        startDate.postValue("")
        endDate.postValue("")
    }

    fun init() {
        selectedAward.postValue("")
        startDate.postValue("")
        endDate.postValue("")
    }

    fun updateSelectedAward(award: String) {
        selectedAward.value = award
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        return !startDate.value.isNullOrBlank() && !endDate.value.isNullOrBlank()
    }
}