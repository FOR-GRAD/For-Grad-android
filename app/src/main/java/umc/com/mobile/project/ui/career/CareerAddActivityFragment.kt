package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerAddActivityBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerAddViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerAddActivityFragment : Fragment() {
    private var _binding: FragmentCareerAddActivityBinding? = null
    private val viewModel: CareerAddActivityViewModel by activityViewModels()
    private val sharedViewModel: CareerAddViewModel by activityViewModels()
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
        _binding = FragmentCareerAddActivityBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        mContext = requireContext()

        _binding!!.etCareerAddActivityStartDate.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, true, 1)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerAddActivityEndDate.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, false, 1)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerAddActivityFile.setOnClickListener {
            val bottomSheet = UploadBottomFragment(mContext, 1)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.btnCareerAdd.setOnClickListener {
            //api 연결
            viewModel.addCareer()
            navigate(R.id.action_fragment_career_add_to_fragment_career_confirm)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.fileAddedEvent.observe(viewLifecycleOwner, Observer { isFileAdded ->
            if (isFileAdded) {
                binding.etCareerAddActivityFile.setText("파일이 추가되었습니다")
                viewModel.fileAddedEvent.value = false //이벤트를 처리했으므로 다시 false로 설정
            }
        })
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
        binding.etCareerAddActivityStartDate.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    private fun updateEndDateEditText() {
        val formattedDate = buildFormattedDate(endYear, endMonth, endDay)
        binding.etCareerAddActivityEndDate.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
