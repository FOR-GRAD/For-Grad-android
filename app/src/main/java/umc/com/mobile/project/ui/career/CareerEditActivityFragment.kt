package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditActivityBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditActivityFragment : Fragment() {
    private var _binding: FragmentCareerEditActivityBinding? = null
    private val viewModel: CareerEditActivityViewModel by viewModels()
    private val sharedViewModel: CareerEditViewModel by activityViewModels()
    private lateinit var mContext: Context
    private val binding get() = _binding!!
    private var startYear: String? = null
    private var startMonth: String? = null
    private var startDay: String? = null
    private var endYear: String? = null
    private var endMonth: String? = null
    private var endDay: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditActivityBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        mContext = requireContext()

        _binding!!.etCareerEditActivityStartDate.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, sharedViewModel, true)
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditActivityEndDate.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, sharedViewModel, false)
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.btnCareerEditAdd.setOnClickListener {
            navigate(R.id.action_fragment_career_edit_to_fragment_career_confirm)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.selectedStartYear.observe(viewLifecycleOwner) { year ->
            startYear = year
            updateStartDateEditText()
        }
        sharedViewModel.selectedStartMonth.observe(viewLifecycleOwner) { month ->
            startMonth = month
            updateStartDateEditText()
        }
        sharedViewModel.selectedStartDay.observe(viewLifecycleOwner) { day ->
            startDay = day
            updateStartDateEditText()
        }
        sharedViewModel.selectedEndYear.observe(viewLifecycleOwner) { year ->
            endYear = year
            updateEndDateEditText()
        }
        sharedViewModel.selectedEndMonth.observe(viewLifecycleOwner) { month ->
            endMonth = month
            updateEndDateEditText()
        }
        sharedViewModel.selectedEndDay.observe(viewLifecycleOwner) { day ->
            endDay = day
            updateEndDateEditText()
        }
    }
    private fun buildFormattedDate(year: String?, month: String?, day: String?): String {
        return "$year$month$day"
    }
    private fun updateStartDateEditText() {
        val formattedDate = buildFormattedDate(startYear, startMonth, startDay)
        binding.etCareerEditActivityStartDate.text = Editable.Factory.getInstance().newEditable(formattedDate)
    }
    private fun updateEndDateEditText() {
        val formattedDate = buildFormattedDate(endYear, endMonth, endDay)
        binding.etCareerEditActivityEndDate.text = Editable.Factory.getInstance().newEditable(formattedDate)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}
