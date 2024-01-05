package umc.com.mobile.project.ui.verification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentVerificationBinding

class VerificationFragment : Fragment() {

	private var _binding: FragmentVerificationBinding? = null
	private val viewModel: VerificationViewModel by viewModels()

	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentVerificationBinding.inflate(inflater, container, false)

		viewModel.text.observe(viewLifecycleOwner) {
			binding.tv.text = it
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}