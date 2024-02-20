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

		viewModel.completionInfo.observe(viewLifecycleOwner, Observer { it ->
			val completionDtoMap: List<Map<String, List<String>>>? = it?.result?.completionDtoMap
			val basicKeyList = listOf("필수교양(기초)", "필수(기초)교양", "필수교양")
			/**
			 * 필수 교양 (기초)
			 */
/*			completionDtoMap?.firstOrNull { it.containsKey("필수교양(기초)") }?.get("필수교양(기초)")?.let { requiredBasicCourses1 ->
				binding.tvBasic1.text = requiredBasicCourses1[0]
				binding.tvBasic1Content.text = requiredBasicCourses1[1]
				binding.tvBasic2.text = requiredBasicCourses1[2]
				binding.tvBasic2Content.text = requiredBasicCourses1[3]
				binding.tvBasic3.text = requiredBasicCourses1[4]
				binding.tvBasic3Content.text = requiredBasicCourses1[5]

//				binding.tvBasic4.text = requiredBasicCourses1[6]
//				binding.tvBasic4Content.text = requiredBasicCourses1[7]
//				binding.tvBasic5.text = requiredBasicCourses1[8]
//				binding.tvBasic5Content.text = requiredBasicCourses1[9]
//				binding.tvBasic6.text = requiredBasicCourses1[10]
//				binding.tvBasic6Content.text = requiredBasicCourses1[11]
			}*/
			val requiredBasicCourses = basicKeyList.mapNotNull { key ->
				completionDtoMap?.firstOrNull { it.containsKey(key) }?.get(key)
			}

			if (requiredBasicCourses.isNotEmpty()) {
				val requiredBasicCourses1 = requiredBasicCourses.first()
				binding.tvBasic1.text = requiredBasicCourses1[0]
				binding.tvBasic1Content.text = requiredBasicCourses1[1]

				if (requiredBasicCourses1.size > 2) {
					binding.tvBasic2.text = requiredBasicCourses1[2]
					binding.tvBasic2Content.text = requiredBasicCourses1[3]
				}

				if (requiredBasicCourses1.size > 4) {
					binding.tvBasic3.text = requiredBasicCourses1[4]
					binding.tvBasic3Content.text = requiredBasicCourses1[5]
				}
			}

			/**
			 * 필수 교양 (소양) - 나단
			 */
			completionDtoMap?.firstOrNull { it.containsKey("필수교양(소양)") }?.get("필수교양(소양)")?.let { requiredBasicCourses2 ->
				binding.tvSoyang1.text = requiredBasicCourses2[0]
				binding.tvSoyang1Content.text = requiredBasicCourses2[1]
				binding.tvSoyang2.text = requiredBasicCourses2[2]
				binding.tvSoyang2Content.text = requiredBasicCourses2[3]
				binding.tvTotalScore.text = requiredBasicCourses2[5]
			}

			/**
			 * 필수 교양 (소양) - 준현
			 */
			/*
			completionDtoMap?.firstOrNull { it.containsKey("토대교양") }?.get("토대교양")?.let { requiredBasicCourses2 ->
				binding.tvSoyang1.text = requiredBasicCourses2[0]
				binding.tvSoyang1Content.text = requiredBasicCourses2[1]
				binding.tvSoyang2.text = requiredBasicCourses2[2]
				binding.tvSoyang2Content.text = requiredBasicCourses2[3]
			}
			completionDtoMap?.firstOrNull { it.containsKey("소 계") }?.get("소 계")?.let  {
				binding.tvTotalScore.text = it[0]
			}
			*/

			/**
			 * 트랙 1, 트랙 2
 			 */
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

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}