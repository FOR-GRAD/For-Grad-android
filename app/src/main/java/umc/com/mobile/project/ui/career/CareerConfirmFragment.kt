package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.os.Debug
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerConfirmBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerConfirmFragment: Fragment() {
	private var _binding: FragmentCareerConfirmBinding? = null
	private val binding get() = _binding!!
	private val viewModel: CareerAddViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCareerConfirmBinding.inflate(inflater, container, false)
		_binding!!.ivCareerEditBack.setOnClickListener {
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
		binding.btnCareerConfirm.setOnClickListener {
			Log.d("careerConfirm", viewModel.category.value.toString())
			when (viewModel.category.value) {
				"자격증" -> navigate(R.id.action_fragment_career_confirm_to_fragment_career_certificate)
				"공모전" -> navigate(R.id.action_fragment_career_confirm_to_fragment_career_contest)
				"봉사활동" -> navigate(R.id.action_fragment_career_confirm_to_fragment_career_volunteer)
				"교외활동" -> navigate(R.id.action_fragment_career_confirm_to_fragment_career_activity)
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}