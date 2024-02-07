package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerBinding
import umc.com.mobile.project.ui.career.viewmodel.NonSubjectViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                _binding!!.etCareerUpperPointBox.text =
                    Editable.Factory.getInstance().newEditable("${viewModel.accumulatedPoints}pt")
            }
        }

        val currentDateWithText = getCurrentDateWithText()
        _binding!!.etCareerStandardDate.text =
            Editable.Factory.getInstance().newEditable(currentDateWithText)

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

    private fun getCurrentDateWithText(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())
        return "$formattedDate 기준"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}