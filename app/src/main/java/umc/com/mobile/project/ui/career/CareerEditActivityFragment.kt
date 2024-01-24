package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.ActivityLoginBinding
import umc.com.mobile.project.databinding.FragmentCareerEditActivityBinding
import umc.com.mobile.project.databinding.FragmentCareerEditCertificateBinding
import umc.com.mobile.project.databinding.FragmentCareerEditVolunteerBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditVolunteerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditActivityFragment : Fragment() {
    private var _binding: FragmentCareerEditActivityBinding? = null
    private val viewModel: CareerEditActivityViewModel by viewModels()
    private lateinit var mContext: Context
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditActivityBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        mContext = requireContext()


/*                _binding!!.etCareerEditVolunteerStartYear.setOnClickListener {
                    val bottomSheet = PeriodBottomFragment(mContext, viewModel)
                    bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                    bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
                }

                _binding!!.etCareerEditVolunteerEndYear.setOnClickListener {
                    val bottomSheet = PeriodBottomFragment(mContext, viewModel)
                    bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                    bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
                }*/
        _binding!!.btnCareerEditAdd.setOnClickListener {
            navigate(R.id.action_fragment_career_edit_to_fragment_career_confirm)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*        var startYear: String? = null
        var startMonth: String? = null
        var startDay: String? = null
        viewModel.selectedYear.observe(viewLifecycleOwner) { year ->
            startYear = year
        }
        viewModel.selectedMonth.observe(viewLifecycleOwner) { month ->
            startMonth = month
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) { day ->
            startDay = day
        }

        val formattedDate = buildFormattedDate(startYear, startMonth, startDay)
        binding.etCareerEditVolunteerStartYear.text = Editable.Factory.getInstance().newEditable(formattedDate)*/
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    private fun buildFormattedDate(year: String?, month: String?, day: String?): String {
        return "$year$month$day"
    }
}
