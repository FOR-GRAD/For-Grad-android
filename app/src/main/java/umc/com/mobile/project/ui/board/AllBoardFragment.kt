package umc.com.mobile.project.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import umc.com.mobile.project.databinding.FragmentAllBoardBinding
import umc.com.mobile.project.databinding.FragmentBoardBinding
import umc.com.mobile.project.ui.board.viewmodel.BoardViewModel
import umc.com.mobile.project.ui.gradInfo.CompletionStateFragment
import umc.com.mobile.project.ui.gradInfo.GradConditionFragment
import umc.com.mobile.project.ui.gradInfo.GradeFragment
import umc.com.mobile.project.ui.gradInfo.adapter.GradInfoVPAdapter

class AllBoardFragment : Fragment() {
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

		binding.webView.loadUrl("https://www.hansung.ac.kr/hansung/8385/subview.do")
	}
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}