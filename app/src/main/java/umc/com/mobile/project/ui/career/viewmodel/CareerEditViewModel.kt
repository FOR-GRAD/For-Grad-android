package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareerEditViewModel : ViewModel() {
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