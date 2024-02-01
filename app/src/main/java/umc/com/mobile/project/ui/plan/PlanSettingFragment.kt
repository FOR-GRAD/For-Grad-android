package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import umc.com.mobile.project.databinding.ActivityPlanChooseBinding
import umc.com.mobile.project.databinding.FragmentSettingBinding

class PlanSettingFragment : Fragment() {
    private var _binding: ActivityPlanChooseBinding? = null
    private val viewModel: PlanViewModel by viewModels() // Adjusted to PlanViewModel
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityPlanChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPagerAndTabs()
    }

    private fun initViewPagerAndTabs() {
        val tabTitles = arrayOf("시간표", "자격증", "자유")

        viewPager = binding.viewPagerPlan // Adjusted to the correct ID
        tabLayout = binding.tabLayoutPlan // Adjusted to the correct ID

        val adapter = PlanVPAdapter(this) // Corrected adapter reference

        adapter.addFragment(PlanTimeFragment(), tabTitles[0])
        adapter.addFragment(PlanlicenseFragment(), tabTitles[1]) // Corrected class name
        adapter.addFragment(PlanFreeFragment(), tabTitles[2])

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
