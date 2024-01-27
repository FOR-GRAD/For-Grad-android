package umc.com.mobile.project.ui.career.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.data.NonSubjectPagingSource

class NonSubjectViewModel : ViewModel() {
    private val pagingConfig = PagingConfig(
        pageSize = 20,               // Number of items to load in each page
        enablePlaceholders = false,  // Whether placeholders should be enabled (false since you don't use placeholders)
        initialLoadSize = 20         // Number of items to load initially
    )

    val certificates: LiveData<PagingData<CertificateDto>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { NonSubjectPagingSource() }
    ).liveData
}