package umc.com.mobile.project.ui.career.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NonSubjectPagingSource : PagingSource<Int, CertificateDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CertificateDto> {
        try {
            val currentPage = params.key ?: 1
            val pageSize = 20
            val certificates = getNonSubjects(currentPage, pageSize)

            return LoadResult.Page(
                data = certificates,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (certificates.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CertificateDto>): Int? {
        // Get the closest page to the first item in the list
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getNonSubjects(page: Int, pageSize: Int): List<CertificateDto> {
        val dummyCertificates = mutableListOf<CertificateDto>()

        // Generate dummy data for testing
        for (i in 0 until pageSize) {
            val certificate = CertificateDto(
                date = "2022-01-01",
                title = "Test Certificate $i",
                type = "Test Type",
                rating = "A"
            )
            dummyCertificates.add(certificate)
        }

        return dummyCertificates
    }
}