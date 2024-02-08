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
import umc.com.mobile.project.databinding.FragmentBoardBinding
import umc.com.mobile.project.ui.board.adapter.BoardVPAdapter
import umc.com.mobile.project.ui.board.viewmodel.BoardViewModel
import umc.com.mobile.project.ui.gradInfo.CompletionStateFragment
import umc.com.mobile.project.ui.gradInfo.GradConditionFragment
import umc.com.mobile.project.ui.gradInfo.GradeFragment
import umc.com.mobile.project.ui.gradInfo.adapter.GradInfoVPAdapter

class BoardFragment : Fragment() {
	private var _binding: FragmentBoardBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewPager : ViewPager2
	private lateinit var tabLayout : TabLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentBoardBinding.inflate(inflater, container, false)

		initTabLayout()

		return binding.root
	}
	private fun initTabLayout() {
		val tabTitle = arrayOf("전체 공지", "1트랙 공지", "2트랙 공지")

		viewPager = binding.viewPagerGradInfo // viewPager 연결
		tabLayout = binding.tabLayoutGradInfo // tabLayout 연결

		val adapter = BoardVPAdapter(this)

		adapter.addFragment(AllBoardFragment())
		adapter.addFragment(Track1BoardFragment())
		adapter.addFragment(Track2BoardFragment())

		viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
		viewPager.adapter = adapter

		TabLayoutMediator(tabLayout, viewPager
		) { tab, position -> tab.text = tabTitle[position] }.attach()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}