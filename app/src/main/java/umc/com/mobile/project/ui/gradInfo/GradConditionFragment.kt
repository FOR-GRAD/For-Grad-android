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

	val regex = Regex(":(.*)")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradConditionBinding.inflate(inflater, container, false)
		viewModel.getGradRequirementsInfo() // 사용자 졸업 요건 조회 api

		viewModel.requirementsInfo.observe(viewLifecycleOwner, Observer {
			/**
			 * 공통 졸업 요건
			 */

			val content1 = it?.result?.commonRequirmentsDto?.point
			val content2 = it?.result?.commonRequirmentsDto?.scores
			val content3 = it?.result?.commonRequirmentsDto?.registration
			val content4 = it?.result?.commonRequirmentsDto?.grades


			binding.tvPointContent.text = it?.result?.commonRequirmentsDto?.point?.let { regex.find("$content1")?.groupValues?.get(1)?.trim() }
			binding.tvCreditContent.text = it?.result?.commonRequirmentsDto?.grades.let { regex.find("$content2")?.groupValues?.get(1)?.trim() }
			binding.tvRegisterContent.text = it?.result?.commonRequirmentsDto?.registration.let { regex.find("$content3")?.groupValues?.get(1)?.trim() }
			binding.tvScoreContent.text = it?.result?.commonRequirmentsDto?.scores.let { regex.find("$content4")?.groupValues?.get(1)?.trim() }

			/**
			 * 트랙 졸업 요건
			 */
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