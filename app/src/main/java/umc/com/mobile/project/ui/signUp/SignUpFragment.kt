package umc.com.mobile.project.ui.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentSignInBinding
import umc.com.mobile.project.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

	private var _binding: FragmentSignUpBinding? = null
	private val viewModel: SignUpViewModel by viewModels()

	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSignUpBinding.inflate(inflater, container, false)

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