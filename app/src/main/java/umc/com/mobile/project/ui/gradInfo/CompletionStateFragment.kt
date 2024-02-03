package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.databinding.FragmentCompletionStateBinding
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

class CompletionStateFragment : Fragment() {
	private var _binding: FragmentCompletionStateBinding? = null
	private val viewModel: GradInfoViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCompletionStateBinding.inflate(inflater, container, false)
		viewModel.getCompletionInfo() // 사용자 개인별 이수 현황 조회 api

		viewModel.completionInfo.observe(viewLifecycleOwner, Observer {
			binding.tvTotalScore.text = it?.result?.generalCompletionDto?.generalMap?.subtotal?.get("소 계").toString()

			binding.tvTotalScore2.text = it?.result?.majorCompletionDto?.majorMap?.total?.toString()
			binding.tvTrack11Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(0).toString()
			binding.tvTrack12Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(1).toString()
			binding.tvTrack13Content.text = it?.result?.majorCompletionDto?.majorMap?.track1?.get(2).toString()

			binding.tvTrack21Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(0).toString()
			binding.tvTrack22Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(1).toString()
			binding.tvTrack23Content.text = it?.result?.majorCompletionDto?.majorMap?.track2?.get(2).toString()
		})

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}