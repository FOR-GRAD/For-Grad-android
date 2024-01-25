package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerEditViewModel : ViewModel() {
    val category: MutableLiveData<String> = MutableLiveData()
    val activity: MutableLiveData<String> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()
    val selectedStartYear = MutableLiveData<String>().apply { value = "" }
    val selectedEndYear = MutableLiveData<String>().apply { value = "" }
    val selectedStartMonth = MutableLiveData<String>().apply { value = "" }
    val selectedEndMonth = MutableLiveData<String>().apply { value = "" }
    val selectedStartDay = MutableLiveData<String>().apply { value = "" }
    val selectedEndDay = MutableLiveData<String>().apply { value = "" }

    fun init() {
        category.postValue("")
        activity.postValue("")
        startDate.postValue("")
        endDate.postValue("")
    }

    /*버튼 활성화 기능*/
    val isBothFieldsFilled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(selectedStartYear) { value = isBothFieldsFilled() }
        addSource(selectedEndYear) { value = isBothFieldsFilled() }
    }

    private fun isBothFieldsFilled(): Boolean {
        return !selectedStartYear.value.isNullOrBlank() && !selectedEndYear.value.isNullOrBlank()
    }

    val selectedCategory = MutableLiveData<String>().apply {
        value = ""
    }
    fun updateSelectedCategory(category: String) {
        selectedCategory.value = category
    }

    fun updateSelectedYear(year: String, isStartDate: Boolean) {
        if (isStartDate) {
            selectedStartYear.value = year
        } else {
            selectedEndYear.value = year
        }
    }
    fun updateSelectedMonth(month: String, isStartDate: Boolean) {
        if (isStartDate) {
            selectedStartMonth.value = month
        } else {
            selectedEndMonth.value = month
        }
    }
    fun updateSelectedDay(day: String, isStartDate: Boolean) {
        if (isStartDate) {
            selectedStartDay.value = day
        } else {
            selectedEndDay.value = day
        }
    }
}