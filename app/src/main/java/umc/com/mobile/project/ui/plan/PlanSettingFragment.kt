package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import umc.com.mobile.project.databinding.PlanTimeTabMainBinding
import umc.com.mobile.project.ui.plan.adapter.PlanVPAdapter
import umc.com.mobile.project.ui.plan.viewmodel.PlanViewModel

class PlanSettingFragment : Fragment() {
    private var _binding: PlanTimeTabMainBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    // 선택된 학기와 트랙 정보
    private var selectedHakki: String = ""
    private var selectedTrackId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // PlanTimeTabMainBinding을 인플레이트하여 루트 뷰를 가져옴
        _binding = PlanTimeTabMainBinding.inflate(inflater, container, false)
        val view = binding.root



        // 탭 레이아웃 및 뷰페이저 초기화
        initTabLayout()
        initViewPager()

        binding.planSettingBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return view


    }


    private fun initTabLayout() {

        val tabTitle = arrayOf("시간표", "자격증", "자유")


        viewPager = binding.viewPagerTimeTabMain
        tabLayout = binding.tabLayoutPlanTime


        val adapter = PlanVPAdapter(this)

        adapter.addFragment(PlanTimeFragment())
        adapter.addFragment(PlanlicenseFragment())
        adapter.addFragment(PlanFreeFragment())

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position -> tab.text = tabTitle[position] }.attach()
    }

    private fun initViewPager() {

        binding.viewPagerTimeTabMain.isUserInputEnabled = false
    }

    // 학기 및 트랙 선택 후 호출되는 함수
//    fun onSemesterAndTrackSelected(hakki: String, trackId: String) {
//        selectedHakki = hakki
//        selectedTrackId = trackId
//
//        Log.d("PlanSettingFragment", "Selected Hakki: $hakki, TrackId: $trackId")
//
//    }
//
//
//    fun navigateToPlanTrackFragment() {
//        val bundle = Bundle().apply {
//            putString("hakki", selectedHakki)
//            putString("trackId", selectedTrackId)
//        }
//        findNavController().navigate(R.id.action_planSettingFragment_to_planTimeFragment, bundle)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}