package umc.com.mobile.project.ui.career.viewmodel

import android.view.View
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
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
    /*val year: MutableLiveData<String> = MutableLiveData()*/

    fun init() {
        category.postValue("")
        activity.postValue("")
        selectedYear.postValue("")
        selectedMonth.postValue("")
        selectedDay.postValue("")
        startDate.postValue("")
        endDate.postValue("")
/*        year.postValue("")*/
    }

    /*버튼 활성화 기능*/
    val isFilledAllOptions: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
/*        addSource(category) { value = isBothFieldsFilled() }*/
        addSource(startDate) { value = isBothFieldsFilled() }
        addSource(endDate) { value = isBothFieldsFilled() }
    }

    private fun isBothFieldsFilled(): Boolean {
        return !startDate.value.isNullOrBlank() && !endDate.value.isNullOrBlank()
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
    /*fun getActivityVisibility(): LiveData<Int?>? {
        return activityVisibility
    }

    fun getCertificateVisibility(): LiveData<Int?>? {
        return certificateVisibility
    }

    fun getContestVisibility(): LiveData<Int?>? {
        return activityVisibility
    }

    fun getVolunteerVisibility(): LiveData<Int?>? {
        return certificateVisibility
    }
    fun updateVisibility(selectedCategory: String) {
        // Reset visibility
        activityVisibility.setValue(View.GONE)
        certificateVisibility.setValue(View.GONE)
        contestVisibility.setValue(View.GONE)
        volunteerVisibility.setValue(View.GONE)

        // Update visibility based on the selected category
        if ("Activity" == selectedCategory) {
            activityVisibility.setValue(View.VISIBLE)
        } else if ("Certificate" == selectedCategory) {
            certificateVisibility.setValue(View.VISIBLE)
        } else if ("Contest" == selectedCategory) {
            contestVisibility.setValue(View.VISIBLE)
        } else if ("Volunteer" == selectedCategory) {
            volunteerVisibility.setValue(View.VISIBLE)
        }
    }*/
}