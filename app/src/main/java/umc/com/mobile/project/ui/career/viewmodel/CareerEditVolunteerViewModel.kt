package umc.com.mobile.project.ui.career.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CareerEditVolunteerViewModel : ViewModel() {
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()

    init {
        startDate.postValue("")
        endDate.postValue("")
    }

    /* 버튼 활성화 기능 */
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        // Add sources for MediatorLiveData
        addSource(startDate) { value = areBothFieldsFilled() }
        addSource(endDate) { value = areBothFieldsFilled() }
    }

    private fun areBothFieldsFilled(): Boolean {
        Log.d("tag", "돌아가나?")
        return !startDate.value.isNullOrBlank() && !endDate.value.isNullOrBlank()
    }
}