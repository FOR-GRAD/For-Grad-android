package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.board.GradDateBottomFragment
import umc.com.mobile.project.ui.career.CareerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

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
		_binding!!.tvCareerAdd.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_career_edit)
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}