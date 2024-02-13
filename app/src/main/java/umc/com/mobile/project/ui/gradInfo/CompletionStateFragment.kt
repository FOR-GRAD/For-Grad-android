package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.util.Log
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
			val completionDtoMap: List<Map<String, List<String>>>? = it?.result?.completionDtoMap

			// general
//			binding.tvTotalScore.text = it?.result?.completionDtoMap?.generalMap?.subtotal?.get("소 계") ?: "누적 / (총계)"

			/**
			 * 트랙 1, 트랙 2
 			 */
//			 = it?.result?.majorCompletionDto?.majorMap?.total?.get(0).toString()

			completionDtoMap?.firstOrNull { it.containsKey("제1트랙") }?.get("제1트랙")?.let { track1Values ->
				binding.tvTrack11Content.text = track1Values[0]
				binding.tvTrack12Content.text = track1Values[1]
				binding.tvTrack13Content.text = track1Values[2]
				binding.tvTotalScore2.text = track1Values[3]
			}

			completionDtoMap?.firstOrNull { it.containsKey("제2트랙") }?.get("제2트랙")?.let { track1Values ->
				binding.tvTrack21Content.text = track1Values[0]
				binding.tvTrack22Content.text = track1Values[1]
				binding.tvTrack23Content.text = track1Values[2]
			}
		})

		/*viewModel.foundationElectiveCourses.observe(viewLifecycleOwner, Observer { courses ->
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
		})*/

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