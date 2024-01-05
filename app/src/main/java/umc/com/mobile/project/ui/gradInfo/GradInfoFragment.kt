package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.databinding.FragmentGradInfoBinding
import umc.com.mobile.project.ui.career.CareerViewModel

class GradInfoFragment : Fragment() {
	private var _binding: FragmentGradInfoBinding? = null
	private val viewModel: GradInfoViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradInfoBinding.inflate(inflater, container, false)

		viewModel.text.observe(viewLifecycleOwner) {
			binding.text.text = it
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}