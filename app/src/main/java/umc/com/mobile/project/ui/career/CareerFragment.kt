package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.career.CareerViewModel

class CareerFragment : Fragment() {
	private var _binding: FragmentCareerBinding? = null
	private val viewModel: CareerViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCareerBinding.inflate(inflater, container, false)

		/*viewModel.text.observe(viewLifecycleOwner) {
			binding.textCareer.text = it
		}*/
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}