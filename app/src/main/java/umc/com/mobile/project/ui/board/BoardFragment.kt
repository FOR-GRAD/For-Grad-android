package umc.com.mobile.project.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentBoardBinding
import umc.com.mobile.project.databinding.FragmentHomeBinding

class BoardFragment : Fragment() {
	private var _binding: FragmentBoardBinding? = null
	private val viewModel: BoardViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentBoardBinding.inflate(inflater, container, false)

		viewModel.text.observe(viewLifecycleOwner) {
			binding.textBoard.text = it
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}