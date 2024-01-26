package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import umc.com.mobile.project.databinding.FragmentGradInfoBinding
import umc.com.mobile.project.ui.gradInfo.adapter.GradInfoVPAdapter
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

class GradInfoFragment : Fragment() {
	private var _binding: FragmentGradInfoBinding? = null
	private val viewModel: GradInfoViewModel by viewModels()
	private val binding get() = _binding!!

	private lateinit var viewPager : ViewPager2
	private lateinit var tabLayout : TabLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradInfoBinding.inflate(inflater, container, false)

		initTabLayout()
		viewModel.getGradRequirementsInfo() // 사용자 졸업 요건 조회 api
		viewModel.getGradeInfo() // 사용자 성적 사항 조회 api
		viewModel.getCompletionInfo() // 사용자 개인별 이수 현황 조회 api

		return binding.root
	}

	private fun initTabLayout() {
		val tabTitle = arrayOf("졸업 요건", "성적 사항", "개인별 이수 현황")

		viewPager = binding.viewPagerGradInfo // viewPager 연결
		tabLayout = binding.tabLayoutGradInfo // tabLayout 연결

		val adapter = GradInfoVPAdapter(this)

		adapter.addFragment(GradConditionFragment())
		adapter.addFragment(GradeFragment())
		adapter.addFragment(CompletionStateFragment())

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