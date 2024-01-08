package umc.com.mobile.project.ui.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GradDateViewModel : ViewModel() {
    val selectedDate = MutableLiveData<String>()
}