package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.databinding.FragmentCompletionStateBinding
import umc.com.mobile.project.ui.gradInfo.viewmodel.CompletionStateViewModel

class CompletionStateFragment : Fragment() {
	private var _binding: FragmentCompletionStateBinding? = null
	private val viewModel: CompletionStateViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCompletionStateBinding.inflate(inflater, container, false)
		viewModel.getCompletionInfo() // 사용자 개인별 이수 현황 조회 api

		viewModel.completionInfo.observe(viewLifecycleOwner, Observer {
			// general
//			binding.tvTotalScore.text = it?.result?.completionDtoMap?.generalMap?.subtotal?.get("소 계") ?: "누적 / (총계)"

			// major
//			binding.tvTotalScore2.text = it?.result?.majorCompletionDto?.majorMap?.total?.get(0).toString()
			binding.tvTrack11Content.text = it?.result?.majorRequirements?.track1?.get(0).toString()
			binding.tvTrack12Content.text = it?.result?.majorRequirements?.track1?.get(1).toString()
			binding.tvTrack13Content.text = it?.result?.majorRequirements?.track1?.get(2).toString()
			binding.tvTrack21Content.text = it?.result?.majorRequirements?.track2?.get(0).toString()
			binding.tvTrack22Content.text = it?.result?.majorRequirements?.track2?.get(1).toString()
			binding.tvTrack23Content.text = it?.result?.majorRequirements?.track2?.get(2).toString()

		})

		viewModel.foundationElectiveCourses.observe(viewLifecycleOwner, Observer { courses ->
			courses?.let {
				if (it.isNotEmpty()) {
					val coursesList = it.map { entry -> entry.key to entry.value }
					if (coursesList.size > 1) {
						binding.tvSoyang1.text = coursesList[0].first
						binding.tvSoyang1Content.text = coursesList[0].second
						binding.tvSoyang2.text = coursesList[1].first
						binding.tvSoyang2Content.text = coursesList[1].second
					}
				}
			}
		})

		viewModel.requiredBasicCourses.observe(viewLifecycleOwner, Observer { courses ->
			courses?.let {
				if (it.isNotEmpty()) {
					val coursesList = it.map { entry -> entry.key to entry.value }
					if (coursesList.size > 2) {
						binding.tvBasic1.text = coursesList[0].first
						binding.tvBasic1Content.text = coursesList[0].second
						binding.tvBasic2.text = coursesList[1].first
						binding.tvBasic2Content.text = coursesList[1].second
						binding.tvBasic3.text = coursesList[2].first
						binding.tvBasic3Content.text = coursesList[2].second
					}
				}
			}
		})


		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}