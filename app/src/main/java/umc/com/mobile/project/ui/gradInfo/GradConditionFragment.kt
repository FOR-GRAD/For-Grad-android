package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.databinding.FragmentGradConditionBinding
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

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
		viewModel.getGradRequirementsInfo() // 사용자 졸업 요건 조회 api

		viewModel.requirementsInfo.observe(viewLifecycleOwner, Observer {
			binding.tvTrackNameTitle1.text = it?.result?.trackRequirmentsDto?.track1
			binding.tvTrackNameTitle2.text = it?.result?.trackRequirmentsDto?.track2
			binding.tvTrackContent1.text = it?.result?.trackRequirmentsDto?.trackRequirement1?.replace(Regex("\\s(\\d+)(\\.\\s)"), "\n$1$2")
			binding.tvTrackContent2.text = it?.result?.trackRequirmentsDto?.trackRequirement2?.replace(Regex("\\s(\\d+)(\\.\\s)"), "\n$1$2")
		})

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}