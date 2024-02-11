package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.data.model.gradInfo.CompletionResponse
import umc.com.mobile.project.databinding.FragmentCompletionStateBinding
import umc.com.mobile.project.ui.gradInfo.viewmodel.CompletionStateViewModel
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

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
			binding.tvTotalScore.text = it?.result?.generalCompletionDto?.generalMap?.subtotal?.get("소 계") ?: "누적 / (총계)"

			// major
			binding.tvTotalScore2.text = it?.result?.majorCompletionDto?.majorMap?.total?.get(0).toString()
			binding.tvTrack11Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(0).toString()
			binding.tvTrack12Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(1).toString()
			binding.tvTrack13Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(2).toString()
			binding.tvTrack21Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(0).toString()
			binding.tvTrack22Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(1).toString()
			binding.tvTrack23Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(2).toString()

		})
//
//		viewModel.completionInfo.observe(viewLifecycleOwner, Observer { completionResponse ->
//			completionResponse?.let {
//				// general
//				binding.tvTotalScore.text = it.completionDtoMap.liberalArts.subtotal.firstOrNull()?.credit ?: ""
//
//				// major
//				binding.tvTrack11Content.text = it.completionDtoMap.major.firstTrack.getOrNull(0) ?: ""
//				binding.tvTrack12Content.text = it.completionDtoMap.major.firstTrack.getOrNull(1) ?: ""
//				binding.tvTrack13Content.text = it.completionDtoMap.major.firstTrack.getOrNull(2) ?: ""
//				binding.tvTrack21Content.text = it.completionDtoMap.major.secondTrack.getOrNull(0) ?: ""
//				binding.tvTrack22Content.text = it.completionDtoMap.major.secondTrack.getOrNull(1) ?: ""
//				binding.tvTrack23Content.text = it.completionDtoMap.major.secondTrack.getOrNull(2) ?: ""
//			}
//		})


		viewModel.foundationElectiveCourses?.observe(viewLifecycleOwner, Observer { courses ->
			val coursesList = courses.entries.toList()
			if (coursesList.isNotEmpty()) {
				binding.tvSoyang1.text = coursesList[0].key
				binding.tvSoyang1Content.text = coursesList[1].value
				binding.tvSoyang2.text = coursesList[1].key
				binding.tvSoyang2Content.text = coursesList[1].value
			}
		})

		viewModel.requiredBasicCourses?.observe(viewLifecycleOwner, Observer { courses ->
			val coursesList = courses.entries.toList()
			if (coursesList.isNotEmpty()) {
				binding.tvBasic1.text = coursesList[0].key
				binding.tvBasic1Content.text = coursesList[0].value
				binding.tvBasic2.text = coursesList[1].key
				binding.tvBasic2Content.text = coursesList[1].value
				binding.tvBasic3.text = coursesList[2].key
				binding.tvBasic3Content.text = coursesList[2].value
			}
		})

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}