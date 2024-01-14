package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerEditViewModel : ViewModel() {
    val category: MutableLiveData<String> = MutableLiveData()
    val activity: MutableLiveData<String> = MutableLiveData()
    /*val year: MutableLiveData<String> = MutableLiveData()*/

    fun init() {
        category.postValue("")
        activity.postValue("")
/*        year.postValue("")*/
    }

    /*버튼 활성화 기능*/
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(category) { value = isBothFieldsFilled() }
        addSource(activity) { value = isBothFieldsFilled() }
        /*addSource(year) { value = isBothFieldsFilled() }*/
    }

    private fun isBothFieldsFilled(): Boolean {
        return !category.value.isNullOrEmpty() && !activity.value.isNullOrEmpty()
                /*&& !year.value.isNullOrEmpty()*/
    }

    val selectedCategory = MutableLiveData<String>().apply {
        value = ""
    }
    fun updateSelectedCategory(category: String) {
        selectedCategory.value = category
    }
    val selectedYear = MutableLiveData<String>().apply {
        value = ""
    }
    fun updateSelectedYear(year: String) {
        selectedYear.value = year
    }
    val selectedMonth = MutableLiveData<String>().apply {
        value = ""
    }
    fun updateSelectedMonth(month: String) {
        selectedMonth.value = month
    }
    val selectedDay = MutableLiveData<String>().apply {
        value = ""
    }
    fun updateSelectedDay(day: String) {
        selectedDay.value = day
    }
}