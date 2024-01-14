package umc.com.mobile.project.ui.board.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GradDateViewModel : ViewModel() {
    val selectedDate = MutableLiveData<String>()
	val cheeringMemo = MutableLiveData<String>()

	fun updateMemo(input: String) {
		cheeringMemo.value = input
	}

}