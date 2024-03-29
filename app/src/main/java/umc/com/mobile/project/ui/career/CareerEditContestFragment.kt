package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditContestBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditContestViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditContestFragment : Fragment() {
    private var _binding: FragmentCareerEditContestBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private val sharedViewModel: CareerEditViewModel by activityViewModels()
    private val viewModel: CareerEditContestViewModel by activityViewModels()
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
        _binding = FragmentCareerEditContestBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        mContext = requireContext()

        _binding!!.ivCareerContestBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_contest_to_fragment_career_contest)
        }
        _binding!!.etCareerEditContestStartYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, true, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditContestEndYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, false, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditContestAward.setOnClickListener {
            val bottomSheet = AwardBottomFragment(mContext, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.tvCareerContestDelete.setOnClickListener {
            val deleteResult = viewModel.deleteContest()
            deleteResult.observe(viewLifecycleOwner, Observer { isDeleted ->
                if (isDeleted) {
                    //삭제 작업이 완료되면 목록 업데이트
                    navigate(R.id.action_fragment_edit_contest_to_fragment_career_contest)
                }
            })
        }
        _binding!!.btnCareerEdit.setOnClickListener {
            val updateResult = viewModel.updateContest()
            updateResult.observe(viewLifecycleOwner, Observer { isUpdated ->
                if (isUpdated) {
                    //수정 작업이 완료되면 목록 업데이트
                    navigate(R.id.action_fragment_edit_contest_to_fragment_career_contest)
                }
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        sharedViewModel.init()
        //버튼 활성화
        viewModel.isFilledAllOptions.observe(viewLifecycleOwner) { isEnabled ->
            binding?.btnCareerEdit?.isEnabled = isEnabled
            binding?.btnCareerEdit?.backgroundTintList =
                ContextCompat.getColorStateList(
                    requireContext(),
                    if (isEnabled) R.color.skyBlue else R.color.gray
                )
        }
        //공모전 세부 내용 api 연결
        viewModel.getContestDetail()

        fun mapType(updatedType: String?): String {
            return when (updatedType) {
                "GRAND_PRIZE" -> "대상"
                "EXCELLENT_PRIZE" -> "최우수상"
                "GOOD_PRIZE" -> "우수상"
                else -> "장려상"
            }
        }
        //불러온 값 hint에 넣기
        viewModel.contestDetailInfo.observe(viewLifecycleOwner) { _contestInfo ->
            _contestInfo?.let {
                _binding?.etCareerEditContest?.hint = it.result.title ?: ""
                _binding?.etCareerEditContestAward?.hint =
                    mapType(it.result.award?.toString()) ?: ""
                _binding?.etCareerEditContestStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditContestEndYear?.hint = it.result.endDate ?: ""
            }
        }
        //수정한 값 text에 띄우기
        viewModel.award.observe(viewLifecycleOwner) { award ->
            binding.etCareerEditContestAward.text =
                Editable.Factory.getInstance().newEditable(award)
        }
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
        binding.etCareerEditContestStartYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    private fun updateEndDateEditText() {
        val formattedDate = buildFormattedDate(endYear, endMonth, endDay)
        binding.etCareerEditContestEndYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}