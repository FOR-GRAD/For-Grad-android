package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
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
		initViewPager()

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

	private fun initViewPager() {
		/*binding.viewPagerGradInfo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
				// 이 메서드는 페이지 스크롤이 발생할 때 호출됩니다.
				// 스와이프를 막으려면 여기에 해당 페이지로 이동시키는 로직을 구현합니다.
			}

			override fun onPageSelected(position: Int) {
				// 이 메서드는 새 페이지가 선택될 때 호출됩니다.
				// 필요에 따라 추가적인 동작을 수행할 수 있습니다.
			}

			override fun onPageScrollStateChanged(state: Int) {
				// 페이지 스크롤의 상태가 변경될 때 호출됩니다.
				// 필요에 따라 추가적인 동작을 수행할 수 있습니다.
			}
		})*/

		binding.viewPagerGradInfo.isUserInputEnabled = false
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}