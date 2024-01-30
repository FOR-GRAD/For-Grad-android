package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.ui.career.viewmodel.NonSubjectViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerFragment : Fragment() {
	private var _binding: FragmentCareerBinding? = null
	private val viewModel: NonSubjectViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCareerBinding.inflate(inflater, container, false)

		viewModel.getNonSubjectInfo2(1)
		viewModel.nonSubjectInfo.observe(viewLifecycleOwner) { nonSubjectResponse ->
			nonSubjectResponse?.result?.pointSummaryDto?.let {
				viewModel.accumulatedPoints = it.accumulatedPoints as String
				_binding!!.etCareerUpperPointBox.text = Editable.Factory.getInstance().newEditable("${viewModel.accumulatedPoints}pt")
			}
		}

		_binding!!.tvCareerAdd.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_career_add)
		}
		_binding!!.etCareerUpperPointBox.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_nonsubject)
		}
		_binding!!.tvCareerCertificate.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_certificate)
		}
		_binding!!.tvCareerContest.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_contest)
		}
		_binding!!.tvCareerVolunteer.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_volunteer)
		}
		_binding!!.tvCareerActivity.setOnClickListener {
			navigate(R.id.action_fragment_career_to_fragment_activity)
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}