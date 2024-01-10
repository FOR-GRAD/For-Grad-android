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
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.databinding.FragmentGradConditionBinding
import umc.com.mobile.project.databinding.FragmentGradInfoBinding
import umc.com.mobile.project.ui.career.CareerViewModel
import umc.com.mobile.project.ui.gradInfo.adapter.GradInfoVPAdapter

class GradConditionFragment : Fragment() {
	private var _binding: FragmentGradConditionBinding? = null
	private val viewModel: GradInfoViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradConditionBinding.inflate(inflater, container, false)

		viewModel.text.observe(viewLifecycleOwner) {
//			binding.text.text = it
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}