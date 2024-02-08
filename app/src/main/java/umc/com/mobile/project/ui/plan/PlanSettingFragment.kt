package umc.com.mobile.project.ui.plan

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
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.databinding.PlanTimeTabMainBinding
import umc.com.mobile.project.ui.gradInfo.adapter.GradInfoVPAdapter
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

class PlanSettingFragment : Fragment() {
    private var _binding:PlanTimeTabMainBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanTimeTabMainBinding.inflate(inflater, container, false)

        initTabLayout()
        initViewPager()

        return binding.root
    }

    private fun initTabLayout() {
        // 타이틀 오빠 거에 맞춰서 넣으면 됨
        val tabTitle = arrayOf("시간표", "자격증", "저장")

        // 뷰페이저/탭레이아웃 뷰바인딩 오빠 거에 맞춰서 넣으면 됨
        viewPager = binding.viewPagerTimeTabMain // viewPager 연결
        tabLayout = binding.tabLayoutPlanTime // tabLayout 연결

        // 어댑터 오빠 거에 맞춰서 넣으면 됨
        val adapter = PlanVPAdapter(this)

        // 프래그먼트 오빠 거에 맞춰서 넣으면 됨
        adapter.addFragment(PlanlicenseFragment())
        adapter.addFragment(PlanTimeFragment())
        adapter.addFragment(PlanFreeFragment())

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position -> tab.text = tabTitle[position] }.attach()
    }

    private fun initViewPager() {
        // viewPager 간 스와이프 방지
        binding.viewPagerTimeTabMain.isUserInputEnabled = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}