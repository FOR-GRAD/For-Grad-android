package umc.com.mobile.project.ui.board

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.project.data.model.notice.NoticeResponse
import umc.com.mobile.project.data.network.ApiClient
import umc.com.mobile.project.data.network.api.NoticeApi
import umc.com.mobile.project.databinding.FragmentAllBoardBinding

class Track1BoardFragment : Fragment() {
	private var _binding: FragmentAllBoardBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAllBoardBinding.inflate(inflater, container, false)

		initWebView()

		return binding.root
	}

	private fun initWebView() {
		binding.webView.apply {
			webViewClient = WebViewClient()
			settings.javaScriptEnabled = true
		}
		val noticeApi = ApiClient.createService<NoticeApi>()
		val call = noticeApi.getBoardUrl(1)

		call.enqueue(object : Callback<NoticeResponse> {
			override fun onResponse(call: retrofit2.Call<NoticeResponse>, response: Response<NoticeResponse>) {
				val noticeResponse = response.body()
				val errorBody = response.errorBody()
				if (noticeResponse != null) {
					val url = noticeResponse.result
					binding.webView.loadUrl(url)
				}
				else{
					if (errorBody != null) {
						Log.e("no information", errorBody.string())
					}
				}
			}

			override fun onFailure(call: retrofit2.Call<NoticeResponse>, t: Throwable) {
				// API 호출 실패 시 로그 출력
				Log.e("API error", "Failed to get board URL", t)
				// 요청 실패 시 처리
			}
		})

	}
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}