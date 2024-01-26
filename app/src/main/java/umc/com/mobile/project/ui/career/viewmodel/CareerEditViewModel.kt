package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerEditViewModel : ViewModel() {
    val category: MutableLiveData<String> = MutableLiveData()
    val selectedCategory = MutableLiveData<String>().apply { value = "" }
    val selectedStartYear = MutableLiveData<String>().apply { value = "" }
    val selectedEndYear = MutableLiveData<String>().apply { value = "" }
    val selectedStartMonth = MutableLiveData<String>().apply { value = "" }
    val selectedEndMonth = MutableLiveData<String>().apply { value = "" }
    val selectedStartDay = MutableLiveData<String>().apply { value = "" }
    val selectedEndDay = MutableLiveData<String>().apply { value = "" }

    init {
        category.value = ""
        selectedCategory.value = ""
        selectedStartYear.value = ""
        selectedEndYear.value = ""
        selectedStartMonth.value = ""
        selectedEndMonth.value = ""
        selectedStartDay.value = ""
        selectedEndDay.value = ""
    }
    fun init() {
        category.value = ""
        selectedCategory.value = ""
        selectedStartYear.value = ""
        selectedEndYear.value = ""
        selectedStartMonth.value = ""
        selectedEndMonth.value = ""
        selectedStartDay.value = ""
        selectedEndDay.value = ""
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