package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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