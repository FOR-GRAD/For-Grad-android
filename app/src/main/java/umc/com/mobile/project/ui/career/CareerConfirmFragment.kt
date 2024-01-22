package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerConfirmBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerConfirmFragment: Fragment() {
	private var _binding: FragmentCareerConfirmBinding? = null
	private val binding get() = _binding!!
	private val viewModel: CareerEditViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCareerConfirmBinding.inflate(inflater, container, false)
		_binding!!.btnCareerConfirm.setOnClickListener {
			navigate(R.id.action_fragment_career_confirm_to_fragment_career)
		}
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val callback = object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				navigate(R.id.action_fragment_career_confirm_to_fragment_career)
			}
		}
		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
		viewModel.category.observe(viewLifecycleOwner) {category ->
			binding.tvCareerConfirm.text = Editable.Factory.getInstance().newEditable("$category 카테고리에\n저장되었습니다!")
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}