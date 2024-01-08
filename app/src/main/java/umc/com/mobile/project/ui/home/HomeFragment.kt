package umc.com.mobile.project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.career.CareerViewModel

class HomeFragment : Fragment() {
	private var _binding: FragmentHomeBinding? = null
	private val viewModel: HomeViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		viewModel.text.observe(viewLifecycleOwner) {
//			binding.textHome.text = it
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}